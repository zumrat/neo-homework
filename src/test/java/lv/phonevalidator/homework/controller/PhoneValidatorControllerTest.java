package lv.phonevalidator.homework.controller;

import lv.phonevalidator.homework.model.CountryPhoneCode;
import lv.phonevalidator.homework.repository.PhoneValidatorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PhoneValidatorController.class)
@ContextConfiguration(classes = PhoneValidatorController.class)
@Import(PhoneValidatorController.class)
//@TestPropertySource("/application-test.yml")
class PhoneValidatorControllerTest {

    public static final String PHONE_NUMBER_PARAM_NAME = "phoneNumber";
    public static final String NEO_COUNTRY_BY_PHONE_NUMBER_PATH = "/neo/country-by-phone-number";

    @MockBean
    private PhoneValidatorRepository repository;

//    @MockBean
//    private PhoneValidatorService service;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideExpectedCountriesByPhoneNumber")
    void whenValidPhoneNumber_shouldReturnListOfMatchingCountries(String phoneNumber, List<String> countries) throws Exception {
        // set up
        when(repository.findAll()).thenReturn(List.of(
                new CountryPhoneCode( "Bahamas", "1242"),
                new CountryPhoneCode( "United States", "1"),
                new CountryPhoneCode( "Canada", "1"),
                new CountryPhoneCode( "Russia", "7"),
                new CountryPhoneCode( "Kazakhstan", "371"),
                new CountryPhoneCode( "Kazakhstan", "76"),
                new CountryPhoneCode( "Kazakhstan", "77"),
                new CountryPhoneCode( "Latvia", "371")
        ));

        var requestBuilder = get(NEO_COUNTRY_BY_PHONE_NUMBER_PATH)
                .param(PHONE_NUMBER_PARAM_NAME, phoneNumber);

        // perform
        var result = this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        // verify
        for (String country : countries) {
            assertTrue(content.contains(country));
        }
    }

    /**
     * Для номера «12423222931» ожидаемая страна “Bahamas”.
     * Для номера «11165384765» ожидаемые страны “United States, Canada”.
     * Для номера «71423423412» ожидаемая страна “Russia”.
     * Для номера «77112227231» ожидаемая страна “Kazakhstan”.
     */
    private static Stream<Arguments> provideExpectedCountriesByPhoneNumber() {
        return Stream.of(
                Arguments.of("12423222931", List.of("Bahamas")),
                Arguments.of("11165384765", List.of("United States", "Canada")),
                Arguments.of("71423423412", List.of("Russia")),
                Arguments.of("77112227231", List.of("Kazakhstan"))
        );
    }
}