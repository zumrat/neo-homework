package lv.phonevalidator.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneValidatorController {

    @GetMapping(path = "/getCountryByPhoneNumber")
    public ResponseEntity<String> getCountryByPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(phoneNumber + "ok");
    }
}
