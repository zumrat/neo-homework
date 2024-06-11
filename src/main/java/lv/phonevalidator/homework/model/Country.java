package lv.phonevalidator.homework.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(name = "country_name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<CountryCode> codes;

    public Country(Long countryId, String name, Set<CountryCode> codes) {
        this.countryId = countryId;
        this.name = name;
        this.codes = codes;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public Set<CountryCode> getCodes() {
        return codes;
    }

    public void setCodes(Set<CountryCode> codes) {
        this.codes = codes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country() {
        super();
    }
}
