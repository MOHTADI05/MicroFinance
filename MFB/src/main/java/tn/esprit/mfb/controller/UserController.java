package tn.esprit.mfb.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.entity.User;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> GetAllUsers(){
        List<User> list=userService.getAllUser();
        return list;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.AddUser(user));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("deleteuser/{cin}")
    @ResponseBody
    public void deleteuser(@PathParam("cin") Long cin){
        userService.DeleteUser(cin);
    }




}
