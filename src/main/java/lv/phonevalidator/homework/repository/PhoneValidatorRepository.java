package lv.phonevalidator.homework.repository;

import lv.phonevalidator.homework.model.CountryPhoneCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneValidatorRepository extends JpaRepository<CountryPhoneCode, Long> {

//    @Query("SELECT c FROM country_codes c WHERE c.country_code = :pattern%")
//    List<Map.Entry<String, List<String>>> findByTitleLike(@Param("pattern") String pattern);
}
