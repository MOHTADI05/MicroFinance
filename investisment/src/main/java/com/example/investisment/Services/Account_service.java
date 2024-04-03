package com.example.investisment.Services;

import com.example.investisment.entity.Account;
import com.example.investisment.entity.user;
import com.example.investisment.Repository.Account_repo;

import com.example.investisment.Repository.user_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class Account_service  {


    private final Account_repo account_repo;
    private final user_repo User_repo;
    @Autowired

    public Account_service(Account_repo accountRepo,user_repo User_repo ) {
       this.account_repo = accountRepo;
       this.User_repo=User_repo;
    }



        public Account addAccount(Account ACC,Long userId){
            user user = User_repo.findById(userId).orElseThrow(null);
            ACC.setUser(user);
            ACC.setAccountCode(UUID.randomUUID(),toString());
            return account_repo.save(ACC);
        }


    public List<Account> findAllAccount(){
            return account_repo.findAll();
        }
        public Account updateAccount(Account ACC){

            return account_repo.save(ACC);
        }



        public  Account findAccountById(Long id){

            return account_repo.findById(id).orElse(null);
        }


        public  void deleteAccount(Long id){

            account_repo.deleteById(id);
        }

    }








