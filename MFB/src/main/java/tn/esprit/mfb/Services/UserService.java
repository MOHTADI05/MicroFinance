package tn.esprit.mfb.Services;

import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface UserService {

    User AddUser(User u);
    void DeleteUser(User u);
    void DeleteUser(Long id);
    List<User> getAllUser();

    AuthenticationResponse register(User u);

    AuthenticationResponse authenticate(User u);
}
