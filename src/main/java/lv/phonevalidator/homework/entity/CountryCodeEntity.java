package lv.phonevalidator.homework.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "country_codes")
public class CountryCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryCodeId;

    @Column(name = "country_code", nullable = false)
    private String codeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public CountryCodeEntity(Long countryCodeId, String codeValue, CountryEntity countryEntity) {
        this.countryCodeId = countryCodeId;
        this.codeValue = codeValue;
        this.countryEntity = countryEntity;
    }

    public CountryCodeEntity() {
        super();
    }

    public Long getCountryCodeId() {
        return countryCodeId;
    }

    public void setCountryCodeId(Long countryCodeId) {
        this.countryCodeId = countryCodeId;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public CountryEntity getCountry() {
        return countryEntity;
    }

    public void setCountry(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }
}
