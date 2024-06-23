package lv.phonevalidator.homework.repository;

import lv.phonevalidator.homework.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneValidatorRepository extends JpaRepository<CountryEntity, Long> {

    @Query("""
                select c from CountryEntity c
                join c.codes cd
                where cd.countryCode like :number%
            """)
    List<CountryEntity> renameMe(String number);

    boolean existsByName(String name);
}
