package lv.phonevalidator.homework.mapper;

import lv.phonevalidator.homework.entity.CountryCodeEntity;
import lv.phonevalidator.homework.entity.CountryEntity;
import lv.phonevalidator.homework.model.CountryCodeDTO;
import lv.phonevalidator.homework.model.CountryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CountryMapper {

    public List<CountryEntity> toEntities(Map<String, List<String>> countries) {
        return countries.entrySet().stream()
                .map(CountryMapper::createCountryEntity)
                .toList();
    }

    public CountryDTO toCountryDTO(CountryEntity countryEntity) {
        if (countryEntity == null) {
            return null;
        }
        var codes = countryEntity.getCodes().stream()
                .map(this::toCountryCodeDTO)
                .collect(Collectors.toSet());

        return CountryDTO.builder()
                .name(countryEntity.getName())
                .codes(codes)
                .build();
    }

    private CountryCodeDTO toCountryCodeDTO(CountryCodeEntity countryCodeEntity) {
        if (countryCodeEntity == null) {
            return null;
        }

        return CountryCodeDTO.builder()
                .countryCode(countryCodeEntity.getCountryCode())
                .build();
    }

    private static CountryEntity createCountryEntity(Map.Entry<String, List<String>> entry) {
        CountryEntity countryEntity = new CountryEntity();
        return countryEntity
                .setName(entry.getKey())
                .setCodes(createCountryCodeEntities(entry, countryEntity));
    }

    private static Set<CountryCodeEntity> createCountryCodeEntities(Map.Entry<String, List<String>> entry, CountryEntity countryEntity) {
        return entry.getValue().stream()
                .map(countryCode -> createCountryCodeEntity(countryEntity, countryCode))
                .collect(Collectors.toSet());
    }

    private static CountryCodeEntity createCountryCodeEntity(CountryEntity countryEntity, String countryCode) {
        return new CountryCodeEntity()
                .setCountryCode(countryCode)
                .setCountry(countryEntity);
    }

}
