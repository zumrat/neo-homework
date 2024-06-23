package lv.phonevalidator.homework.service;

import jakarta.annotation.PostConstruct;
import lv.phonevalidator.homework.entity.CountryEntity;
import lv.phonevalidator.homework.mapper.CountryMapper;
import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneValidatorService {

    private static final String WIKI_PAGE = "https://en.wikipedia.org/wiki/List_of_country_calling_codes#Alphabetical_order";
    private static final String COUNTRY_CODES_TABLE = "table.wikitable.sortable.sticky-header-multi";

    private final CountryMapper countryMapper;
    private final PhoneValidatorRepository phoneValidatorRepository;


    public PhoneValidatorService(CountryMapper countryMapper, PhoneValidatorRepository phoneValidatorRepository) {
        this.countryMapper = countryMapper;
        this.phoneValidatorRepository = phoneValidatorRepository;
    }

    public void mainFlow() {
        //call populateDatabaseWithPhoneNumbers
        //run alghorithm
        //return
    }


    @PostConstruct
    public void populateDatabaseWithPhoneNumbers() throws IOException {
        Document doc = Jsoup.connect(WIKI_PAGE).get();
//        log(doc.title());
        var table = doc.select(COUNTRY_CODES_TABLE).getFirst(); // retrieving first occurrence of the table, in this case it is only one
        var countryAndCountryCodesMap = table.childNode(1).childNodes().stream() //extracting table body table.childNode(1), afterwards getting all childNodes
                .filter(it -> it.nodeName().equals("tr"))
                .skip(2) // skipping headline (serving/code etc)
                .map(tableRow -> Pair.of(getCountry(tableRow), getCountryCodes(tableRow)
                )).collect(Pair.toMap());

        var countryEntities = countryMapper.toEntities(countryAndCountryCodesMap);
        countryEntities
                .forEach(countryEntity -> {
                    if (!phoneValidatorRepository.existsByName(countryEntity.getName())) {
                        phoneValidatorRepository.save(countryEntity);
                    }
                });

        identifyCountry("37128818914", 0);

        System.out.println(doc.title());
        System.out.println(countryAndCountryCodesMap);

        Elements newsHeadlines = doc.select("#mp-itn b a");


    }

    private static List<String> getCountryCodes(Node tableRow) {
        return tableRow.childNode(3).childNodes()
                .stream()
                .filter(it -> "a".equals(it.nodeName()))
                .map(it -> it.childNode(0))
                .map(countryCode -> ((TextNode) countryCode).text())
                .map(countryCode -> countryCode.replaceAll("\\D+", ":"))
                .map(countryCode -> Arrays.stream(countryCode.split(":"))
                        .toList())
                .map(splitCountryCode -> {
                    if (splitCountryCode.size() == 1) {
                        return splitCountryCode;
                    }
                    String baseCode = splitCountryCode.getFirst();
                    return splitCountryCode.stream().skip(1)
                            .map(remainder -> baseCode + remainder)
                            .toList();
                })
                .flatMap(Collection::stream)
                .toList();
    }

    private static String getCountry(Node tableRow) {
        return Optional.of(tableRow)// need optional since something in the chain can be null
                .map(row -> row.childNode(1))
                .map(Node::lastChild)
                .map(TextNode.class::cast)
                .map(TextNode::text)
                .map(String::trim)
                .orElseThrow();
    }


    // input number ->
    // if size == 1 when cutInHalf(number)[0] or can't cut in half -> return
    // if size > 1 then cut in half [0] + cutInHalf(cutInHalf[1])[0] -> restart algo
    // if size == 0 then cutInHalf(cutInHalf[0])[0] -> restart algo
    private List<CountryEntity> identifyCountry(String number, int cutIndex) {
        var numberWithoutWhiteSpaces = number.replaceAll("\\s", "");
        cutIndex = cutIndex == 0 ? numberWithoutWhiteSpaces.length() : cutIndex;
        String first = numberWithoutWhiteSpaces.substring(0, cutIndex);



        var result = phoneValidatorRepository.renameMe(first);
        if (!result.isEmpty()) {
            return result;
        }
        return identifyCountry(number, cutIndex - 1);
    }

    private Pair<String, String> cutString(String string, int cutIndex) {
        return Pair.of(string.substring(0, cutIndex), string.substring(cutIndex));
    }

}
