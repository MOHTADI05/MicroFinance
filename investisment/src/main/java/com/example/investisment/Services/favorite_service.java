package com.example.investisment.Services;

import com.example.investisment.entity.favorite;
import com.example.investisment.Repository.favorite_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class favorite_service {

        private final favorite_repo Favorite_repo;
    @Autowired


    public favorite_service(favorite_repo favoriteRepo) {
        Favorite_repo = favoriteRepo;
    }





        public favorite addfavorite(favorite FIV){
            FIV.setinvestismentCode(UUID.randomUUID(),toString());
            return Favorite_repo.save(FIV);
        }
        public List<favorite> findAllfavorite(){
            return Favorite_repo.findAll();
        }
        public favorite updatefavorite(favorite FAV){
            return Favorite_repo.save(FAV);
        }



        public  favorite findfavoriteById(Long id){

            return Favorite_repo.findById(id).orElse(null);
        }


        public  void deletefavorite(Long id){

            Favorite_repo.deleteById(id);
        }
    }





