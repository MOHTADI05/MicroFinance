package tn.esprit.mfb.Services;

import org.springframework.http.ResponseEntity;
import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface UserService {


    void DeleteUser(User u);
    void DeleteUser(Long id);
    List<User> getAllUser();
    User GetUser(User u);
    AuthenticationResponse register(User u);
    AuthenticationResponse authenticate(User u);

    Long findByRolee(TypeUser role);

    ResponseEntity<String> logout(User u);
    void BlockUser(Long id);
    void UnblockUser(Long id);
    void requestPasswordReset(String userEmail);
    ResponseEntity<String> newpassword(String userEmail, String password);





}
