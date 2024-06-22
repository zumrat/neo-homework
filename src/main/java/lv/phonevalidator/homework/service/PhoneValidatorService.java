package lv.phonevalidator.homework.service;

import jakarta.annotation.PostConstruct;
//import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
//import lv.phonevalidator.homework.mapper.CountryMapper;
//import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneValidatorService {

    private static final String WIKI_PAGE = "https://en.wikipedia.org/wiki/List_of_country_calling_codes#Alphabetical_order";
    private static final String COUNTRY_CODES_TABLE = "table.wikitable.sortable.sticky-header-multi";

//    private final CountryMapper countryMapper;
//    private final PhoneValidatorRepository phoneValidatorRepository;
//
//
//    public PhoneValidatorService(CountryMapper countryMapper, PhoneValidatorRepository phoneValidatorRepository) {
//        this.countryMapper = countryMapper;
//        this.phoneValidatorRepository = phoneValidatorRepository;
//    }


//    public PhoneValidatorService(PhoneValidatorRepository phoneValidatorRepository) {
//        this.phoneValidatorRepository = phoneValidatorRepository;
//    }


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
        Map<String, List<String>> something = table.childNode(1).childNodes().stream() //extracting table body table.childNode(1), afterwards getting all childNodes
                .filter(it -> it.nodeName().equals("tr"))
                .skip(2) // skipping headline (serving/code etc)
                .map(tableRow -> Pair.of(getCountry(tableRow), getCountryCodes(tableRow)
                )).collect(Pair.toMap());
        System.out.println(doc.title());
        System.out.println(something);

        Elements newsHeadlines = doc.select("#mp-itn b a");


//        saveToDb(something);
//        phoneValidatorRepository.saveAll(something);
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
                .orElseThrow();
    }


    private Map<String, List<String>> identifyCountry(List<Map.Entry<String, List<String>>> allCountries) {
        return null;

    }


    //TODO consider removing method
    private void saveToDb(List<Map.Entry<String, List<String>>> identifiedCountry) {
//        var mappedCountryEntities = countryMapper.toEntities(identifiedCountry);
//        phoneValidatorRepository.saveAll(mappedCountryEntities);

    }

}
