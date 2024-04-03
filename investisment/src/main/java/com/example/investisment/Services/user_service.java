package com.example.investisment.Services;

import com.example.investisment.entity.user;
import com.example.investisment.Repository.user_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class user_service {


        private final user_repo User_repo;
    @Autowired
    public user_service(user_repo userRepo) {
        User_repo = userRepo;
    }





        public user adduser(user USER){
            USER.setUSERCode(UUID.randomUUID().toString());
            return User_repo.save(USER);
        }
        public List<user> findAlluser(){
            return User_repo.findAll();
        }

        public user updateuser(user USER){

            return User_repo.save(USER);
        }



        public  user finduserById(Long id){

            return User_repo.findById(id).orElse(null);
        }


        public  void deleteuser(Long id){

            User_repo.deleteById(id);
        }












}
