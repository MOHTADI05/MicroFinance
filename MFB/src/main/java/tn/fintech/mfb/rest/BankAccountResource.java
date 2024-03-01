package tn.fintech.mfb.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.fintech.mfb.model.BankAccountDTO;
import tn.fintech.mfb.service.BankAccountService;
import tn.fintech.mfb.service.IbankAccount;
import tn.fintech.mfb.util.ReferencedException;
import tn.fintech.mfb.util.ReferencedWarning;

 @AllArgsConstructor
@RestController
@RequestMapping(value = "/api/bankAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountResource {

      IbankAccount bankAccountService;



    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }


}
