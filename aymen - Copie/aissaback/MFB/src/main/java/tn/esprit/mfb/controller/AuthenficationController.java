package tn.esprit.mfb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.ClusteringService;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.entity.User;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthenficationController {

    private final UserService service;

    private final ClusteringService clusteringService;
    private AuthenticationResponse jwt;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {

        return ResponseEntity.ok(service.register(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User user) {
       
        AuthenticationResponse authenticationResponse = service.authenticate(user);
        String jwtToken = authenticationResponse.getToken();
        return ResponseEntity.ok(service.authenticate(user));

    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody User user) {

        ResponseEntity<String> response = service.logout(user);
        System.out.println(user.getEmail());
        return response;
    }
    @GetMapping("/cluster")
    public void cluster() throws Exception {

        clusteringService.segmentation();

    }


}
