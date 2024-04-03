package com.example.investisment.Services;

import com.example.investisment.serviceinterface.ImmobilierServiceImpl;
import com.example.investisment.entity.immobilier;
import com.example.investisment.Repository.demande_repo;
import com.example.investisment.Repository.immobilier_repo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class immobilier_service implements ImmobilierServiceImpl {

    private final immobilier_repo Immobilier_repo;

    private final demande_repo Demande_repo;


    @Autowired

    public immobilier_service(immobilier_repo immobilierRepo, demande_repo demandeRepo) {
        Immobilier_repo = immobilierRepo;
        Demande_repo = demandeRepo;
    }

    public immobilier addimmobilier(immobilier FIV){
            FIV.setimmobilierCode(UUID.randomUUID(),toString());
            return Immobilier_repo.save(FIV);
        }

        @Transactional
        public List<immobilier> findAllimmobilier(){
            return Immobilier_repo.findAll();
        }


    @Transactional

        public immobilier updatefavorite(immobilier FAV){

            return Immobilier_repo.save(FAV);
        }



        public  immobilier findimmobilierById(Long id){

            return Immobilier_repo.findById(id).orElse(null);
        }


        public  void deletefimmobilier(Long id){
// Example pseudocode


            Immobilier_repo.deleteById(id);
        }
    }



