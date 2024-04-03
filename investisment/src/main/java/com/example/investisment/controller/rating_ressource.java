package com.example.investisment.controller;

import com.example.investisment.entity.rating;

import com.example.investisment.Services.rating_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class rating_ressource {

    private final rating_service Rating_service;

    public rating_ressource(rating_service ratingService) {
        Rating_service = ratingService;
    }



        @GetMapping("/all")
        public ResponseEntity<List<rating>> getAllInvestisment() {
            List<rating> INV = Rating_service.findAllrating();
            return new ResponseEntity<>(INV, HttpStatus.OK);
        }

        @GetMapping("/find/{INV_id}")
        public ResponseEntity<rating> getInvestismentById(@PathVariable("INV_id") Long id) {
            rating INV = Rating_service.findratingtById(id);
            return new ResponseEntity<>(INV, HttpStatus.OK);

        }

        @PostMapping("/add")
        public ResponseEntity<rating> addInvestisment(@RequestBody rating INV) {
            rating newINV = Rating_service.addrating(INV);
            return new ResponseEntity<>(newINV, HttpStatus.CREATED);

        }

        @PutMapping("/update")
        public ResponseEntity<rating> updateInvestisment(@RequestBody rating INV) {
            rating updateINV = Rating_service.updaterating(INV);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);

        }

        @DeleteMapping("/delete/{INV_id}")
        public ResponseEntity<?> deleteInvestisment(@PathVariable("INV_id") Long id) {
            Rating_service.deleterating(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }


}
