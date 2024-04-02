package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.mfb.Repo.UserRepository;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository Repuser;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtBlacklistService jwtBlacklistService;
    private final EmailSenderService emailSenderService;




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
    public User GetUser(User u) {
//        var user = Repuser.findByEmail(u.getEmail());
        return null ;
    }

    @Override
    public AuthenticationResponse register(User u) {
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        u.setRole(TypeUser.CLIENT);
        u.setIsbloked(false);
        var jwtToken = jwtService.generateToken( Repuser.save(u));
        emailSenderService.sendSimpleEmail("aissa.swiden@esprit.tn","new user","you have a new user his email is "+ u.getEmail());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    @Override
    public AuthenticationResponse authenticate(User u) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.getEmail(),
                        u.getPassword()

                )

        );
        System.out.println(u.getPassword());
        var user = Repuser.findByEmail(u.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        if(user.isIsbloked()==true){
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();

        }
        else{

            return null;
    }
    }
    @Override
    public ResponseEntity<String> logout(User u) {

//System.out.println(u.getEmail());
//        var user = Repuser.findByEmail(u.getEmail())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(u);
        System.out.println(jwtToken);
        jwtBlacklistService.blacklistToken(jwtToken);

        System.out.println(SecurityContextHolder.getContext());
        SecurityContextHolder.clearContext();

        System.out.println(SecurityContextHolder.getContext());
        // Répond avec un message de déconnexion réussie
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

    @Override
    public void BlockUser(Long id) {
        User u= Repuser.findByCin(id);
        u.setIsbloked(false);
        Repuser.save(u);

    }

    @Override
    public void UnblockUser(Long id) {
        User u= Repuser.findByCin(id);
        System.out.println(u);
        u.setIsbloked(true);
        Repuser.save(u);
    }
    @Override
    public void requestPasswordReset(String userEmail) {

        var user = Repuser.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("user not found"));
        if (user != null) {
            //String token = jwtService.generatePasswordResetToken(user);
            Integer code = generateRandomNumber();
            System.out.println("hetha fel GENEREAT"+ code);
            user.setCode(code);
            Repuser.save(user);
            System.out.println(user.getCode());
            // Envoyer l'e-mail avec le lien de réinitialisation
            String resetUrl = "http://localhost:8084/api/user/validate-token/"+userEmail;
//            String resetUrl = "http://localhost:8084/api/user/validate-token?token=" + code;
            sendResetEmail(userEmail, resetUrl,code);
        }
    }
    private void sendResetEmail(String userEmail, String resetUrl,Integer code) {
        emailSenderService.sendSimpleEmail(userEmail,"Demande de réinitialisation de mot de passe","Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant : " + resetUrl + " et voici votre code de verif : "+ code );
    }
    @Override
    public ResponseEntity<String> newpassword(String userEmail, String password){

        try {
            var user = Repuser.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            Repuser.save(user);
//            var jwtToken = jwtService.generateToken( Repuser.save(user));
//            System.out.println(jwtToken);
            return ResponseEntity.ok("Password reset successful.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password.");
        }
    }


        public static int generateRandomNumber() {

            Random random = new Random();
            int randomNumber = random.nextInt(90000) + 10000;
            return randomNumber;
        }


        private Date calculateExpiryDate() {
        // Calculer la date d'expiration (par exemple, dans 24 heures)
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }


}
