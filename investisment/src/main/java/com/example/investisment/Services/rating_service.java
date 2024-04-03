package com.example.investisment.Services;

import com.example.investisment.entity.rating;
import com.example.investisment.Repository.rating_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class rating_service {
    private final rating_repo Rating_repo;
    @Autowired
    public rating_service(rating_repo ratingRepo) {
        this.Rating_repo = ratingRepo;
    }

    public rating addrating(rating RIT){
        RIT.setratingtCode(UUID.randomUUID(),toString());
        return Rating_repo.save(RIT);
    }
    public List<rating> findAllrating(){
        return Rating_repo.findAll();
    }
    public rating updaterating(rating RIT){

        return Rating_repo.save(RIT);
    }



    public  rating findratingtById(Long id){

        return Rating_repo.findById(id).orElse(null);
    }


    public  void deleterating(Long id){

        Rating_repo.deleteById(id);
    }
}
