package tn.esprit.mfb.controller;

import tn.esprit.mfb.Services.inv_history_service;
import tn.esprit.mfb.entity.inv_history;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inv_history")
public class inv_history_ressource {

        private final inv_history_service Inv_history_service;

    public inv_history_ressource(inv_history_service invHistoryService) {
       this.Inv_history_service  = invHistoryService;
    }


    @GetMapping("/all")
        public ResponseEntity<List<inv_history>> FindAllinv_history() {
            List<inv_history> INV = Inv_history_service.findAllinv_history();
            return new ResponseEntity<>(INV, HttpStatus.OK);
        }

        @GetMapping("/find/{INV_id}")
        public ResponseEntity<inv_history> getinv_historyById(@PathVariable("INV_id") Long id) {
            inv_history HINV = Inv_history_service.findinv_historyById(id);
            return new ResponseEntity<>(HINV, HttpStatus.OK);

        }

        @PostMapping("/add")
        public ResponseEntity<inv_history> addinv_history(@RequestBody inv_history HINV) {
            inv_history newHINV = Inv_history_service.addinv_history(HINV);
            return new ResponseEntity<>(newHINV, HttpStatus.CREATED);

        }

        @PutMapping("/update")
        public ResponseEntity<inv_history> updateInvestisment(@RequestBody inv_history INV) {
            inv_history updateINV = Inv_history_service.updateinv_history(INV);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);

        }

        @DeleteMapping("/delete/{INV_id}")
        public ResponseEntity<?> deleteInv_history(@PathVariable("INV_id") Long id) {
            Inv_history_service.deleteinv_history(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }




}
