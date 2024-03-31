package esprit.tn.proj.Controller;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.entity.User;
import esprit.tn.proj.serviceInterface.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Client")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/create")
    public User create(@RequestBody User user){
        return userService.addClient(user);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id)
    {
         userService.removeClient(id);
    }
    @GetMapping("/read/{id}")
    public User read(@PathVariable Long id){
        return userService.retrieveClient(id);
    }

}
