package lv.phonevalidator.homework.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "country_codes")
public class CountryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryCodeId;

    @Column(name = "country_code", nullable = false)
    private String codeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    public CountryCode(Long countryCodeId, String codeValue, Country country) {
        this.countryCodeId = countryCodeId;
        this.codeValue = codeValue;
        this.country = country;
    }

    public CountryCode() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
