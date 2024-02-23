package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repo.UserRepository;
import tn.esprit.mfb.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.User;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository Repuser;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User AddUser(User u) {
        Repuser.save(u);
        return u;
    }

    @Override
    public void DeleteUser(User u) {
        Repuser.delete(u);

    }
    @Override
    public  void DeleteUser(Long id){
            Repuser.deleteById(id);
    }


    @Override
    public List<User> getAllUser() {
        // Convertir l'itérable en liste
        Iterable<User> usersIterable = Repuser.findAll();
        List<User> usersList = new ArrayList<>();
        usersIterable.forEach(usersList::add);
        return usersList;
    }

    @Override
    public AuthenticationResponse register(User u) {
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        var jwtToken = jwtService.generateToken( Repuser.save(u));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

   /* @Override
    public AuthenticationResponse authenticate(User u) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.getEmail(),
                        u.getPassword()
                )

        );
        var user = Repuser.findByEmail(u.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken( Repuser.save(u));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }*/
    @Override
    public AuthenticationResponse authenticate(User u) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.getEmail(),
                        u.getPassword()
                )
        );
        var user = Repuser.findByEmail(u.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
