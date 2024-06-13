package lv.phonevalidator.homework.repository;

import lv.phonevalidator.homework.entity.CountryEntity;
import lv.phonevalidator.homework.model.CountryPhoneCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PhoneValidatorRepository extends JpaRepository<CountryEntity, Long> {

//    @Query("SELECT c FROM country_codes c WHERE c.country_code = :pattern%")
//    List<Map.Entry<String, List<String>>> findByTitleLike(@Param("pattern") String pattern);

//    List<CountryPhoneCode>
}
