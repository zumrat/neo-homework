package lv.phonevalidator.homework.controller;

import lv.phonevalidator.homework.model.CountryDTO;
import lv.phonevalidator.homework.service.PhoneValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PhoneValidatorController {

    private final PhoneValidatorService phoneValidatorService;

    public PhoneValidatorController(PhoneValidatorService phoneValidatorService) {
        this.phoneValidatorService = phoneValidatorService;
    }

    @GetMapping(path = "/getCountryByPhoneNumber")
    public ResponseEntity<List<CountryDTO>> getCountryByPhoneNumber(@RequestParam String phoneNumber) throws IOException {
        var result = phoneValidatorService.mainFlow(phoneNumber);
        return ResponseEntity.ok(result);
    }
}
