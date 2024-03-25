package tn.esprit.mfb.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.entity.User;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> GetAllUsers(){
        List<User> list=userService.getAllUser();
        return list;
    }

    @DeleteMapping("/deleteuser/{cin}")
    @ResponseBody
    public void deleteuser(@PathParam("cin") Long cin){
        userService.DeleteUser(cin);
    }


    @GetMapping("/getUserByEmail")
    @ResponseBody
    public User getUserByEmail(@RequestBody User user) {
        return userService.GetUser(user);
    }

    @PutMapping("/block/{cin}")
    @ResponseBody
    public void blockuser(@PathVariable("cin") Long cin){
        userService.BlockUser(cin);
    }

    @PutMapping("/unblock/{cin}")
    @ResponseBody
    public void unblockuser(@PathVariable("cin") Long cin){
        System.out.println(cin);
        userService.UnblockUser(cin);
    }


}
