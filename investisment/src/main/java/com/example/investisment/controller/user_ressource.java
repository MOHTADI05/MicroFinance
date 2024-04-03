package com.example.investisment.controller;

import com.example.investisment.entity.user;
import com.example.investisment.Services.user_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class user_ressource {


        private final user_service User_service;


    public user_ressource(user_service userService) {
        User_service = userService;
    }


    @GetMapping("/all")
        public ResponseEntity<List<user>> getAllUSER() {
            List<user> INV = User_service.findAlluser();
            return new ResponseEntity<>(INV, HttpStatus.OK);
        }

        @GetMapping("/find/{INV_id}")
        public ResponseEntity<user> getUSERById(@PathVariable("INV_id") Long id) {
            user INV = User_service.finduserById(id);
            return new ResponseEntity<>(INV, HttpStatus.OK);

        }

        @PostMapping("/add")
        public ResponseEntity<user> addUSER(@RequestBody user INV) {
            user newINV = User_service.adduser(INV);
            return new ResponseEntity<>(newINV, HttpStatus.CREATED);

        }

        @PutMapping("/update")
        public ResponseEntity<user> updateUSER(@RequestBody user INV) {
            user updateINV = User_service.updateuser(INV);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);

        }

        @DeleteMapping("/delete/{INV_id}")
        public ResponseEntity<?> deleteUSER(@PathVariable("INV_id") Long id) {
            User_service.deleteuser(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }


    }



