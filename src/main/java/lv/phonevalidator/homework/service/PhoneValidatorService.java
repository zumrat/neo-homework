package lv.phonevalidator.homework.service;
import jakarta.annotation.PostConstruct;
//import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
import lv.phonevalidator.homework.mapper.CountryMapper;
import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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



//    public PhoneValidatorService(PhoneValidatorRepository phoneValidatorRepository) {
//        this.phoneValidatorRepository = phoneValidatorRepository;
//    }




    public void mainFlow(){
    //call populateDatabaseWithPhoneNumbers
    //run alghorithm
    //return
    }



    @PostConstruct
    public void populateDatabaseWithPhoneNumbers() throws IOException {
        Document doc = Jsoup.connect(WIKI_PAGE).get();
//        log(doc.title());
        var table = doc.select(COUNTRY_CODES_TABLE).getFirst();
        List<Map.Entry<String, List<String>>> something = table.childNode(1).childNodes().stream()
                .filter(it -> it.nodeName().equals("tr"))
                .skip(2)
                .map(it -> Map.entry(((TextNode) it.childNode(1).lastChild()).text(),
                        it.childNode(3).childNodes()
                                .stream()
                                .filter(codeValue -> codeValue.nodeName().equals("a"))
                                .map(codeValue -> codeValue.childNode(0))
                                .map(countryCode -> ((TextNode) countryCode).text())
                                .map(text -> text.replaceAll("\\D+", ":"))
                                .map(s -> Arrays.stream(s.split(":"))
                                        .toList())
                                .map(t -> {
                                    String baseCode = t.get(0);
                                    if (t.size() == 1) {
                                        return t;
                                    }
                                    return t.stream().skip(1)
                                            .map(g -> baseCode + g)
                                            .toList();
                                })
                                .flatMap(Collection::stream)
                                .toList()
                )).toList();
        System.out.println(doc.title());
        System.out.println(something);

        Elements newsHeadlines = doc.select("#mp-itn b a");


        saveToDb(something);
//        phoneValidatorRepository.saveAll(something);
    }


    private Map<String, List<String>> identifyCountry(List<Map.Entry<String, List<String>>> allCountries){
        return null;

    }



//TODO consider removing method
    private void saveToDb(List<Map.Entry<String, List<String>>> identifiedCountry){
//        var mappedCountryEntities = countryMapper.toEntities(identifiedCountry);
//        phoneValidatorRepository.saveAll(mappedCountryEntities);

    }

}
