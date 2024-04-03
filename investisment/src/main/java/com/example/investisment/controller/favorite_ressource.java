package com.example.investisment.controller;

import com.example.investisment.entity.favorite;
import com.example.investisment.Services.favorite_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/favorite")
public class favorite_ressource {


        private final favorite_service Favorite_service;

    public favorite_ressource(favorite_service favoriteService) {
        Favorite_service = favoriteService;
    }


    @GetMapping("/all")
        public ResponseEntity<List<favorite>> getAllFavorite() {
            List<favorite> FAV = Favorite_service.findAllfavorite();
            return new ResponseEntity<>(FAV, HttpStatus.OK);
        }

        @GetMapping("/find/{INV_id}")
        public ResponseEntity<favorite> getFavoriteById(@PathVariable("INV_id") Long id) {
            favorite INV = Favorite_service.findfavoriteById(id);
            return new ResponseEntity<>(INV, HttpStatus.OK);

        }

        @PostMapping("/add")
        public ResponseEntity<favorite> addFavorite(@RequestBody favorite FAV) {
            favorite newINV = Favorite_service.addfavorite(FAV);
            return new ResponseEntity<>(newINV, HttpStatus.CREATED);

        }

        @PutMapping("/update")
        public ResponseEntity<favorite> updateFavorite(@RequestBody favorite FAV) {
            favorite updateINV = Favorite_service.updatefavorite(FAV);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);
    }

        @DeleteMapping("/delete/{INV_id}")
        public ResponseEntity<?> deleteFavorite(@PathVariable("INV_id") Long id) {
            Favorite_service.deletefavorite(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }

}
