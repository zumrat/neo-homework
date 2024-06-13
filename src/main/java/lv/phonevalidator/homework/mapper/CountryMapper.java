package lv.phonevalidator.homework.mapper;

import lv.phonevalidator.homework.entity.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CountryMapper {

    public List<CountryEntity> toEntities(List<HashMap<String, List<String>>> countries) {
        countries.stream()
            .flatMap(map -> map.entrySet().stream())
            .forEach(this::toCountry)


    }

    private CountryEntity toCountry(Map.Entry<String, List<String>> country){
        //TODO krasivoe
        //TODO consider Pair
        var newCountry = new CountryEntity();
        newCountry.setName(country.getKey());
//        newCountry.setCodes(country.getValue().stream().collect(Collectors.toSet()));

        return newCountry;
    }
}
