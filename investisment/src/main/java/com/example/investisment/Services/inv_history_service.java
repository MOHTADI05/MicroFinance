package com.example.investisment.Services;

import com.example.investisment.serviceinterface.InvHistoryServiceImpl;
import com.example.investisment.entity.inv_history;
import com.example.investisment.Repository.inv_history_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class inv_history_service implements InvHistoryServiceImpl {


        private final inv_history_repo Inv_history_repo;
    @Autowired
    public inv_history_service(inv_history_repo invHistoryRepo) {
        this.Inv_history_repo = invHistoryRepo;
    }






    public inv_history addinv_history(inv_history HIS){
        HIS.setInv_history_repoCode(UUID.randomUUID(),toString());
            return Inv_history_repo.save(HIS);
        }
        public List<inv_history> findAllinv_history(){
            return Inv_history_repo.findAll();
        }


        public inv_history updateinv_history(inv_history HIS){

            return Inv_history_repo.save(HIS);
        }



        public  inv_history findinv_historyById(Long id){

            return Inv_history_repo.findById(id).orElse(null);
        }

        public  void deleteinv_history(Long id){

            Inv_history_repo.deleteById(id);
        }
}
