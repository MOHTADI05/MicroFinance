package tn.esprit.mfb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.entity.User;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthenficationController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {

        return ResponseEntity.ok(service.register(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User user) {

        return ResponseEntity.ok(service.authenticate(user));

    }


}
