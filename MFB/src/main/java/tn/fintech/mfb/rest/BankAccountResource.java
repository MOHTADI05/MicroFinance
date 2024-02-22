package tn.fintech.mfb.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
import tn.fintech.mfb.util.ReferencedException;
import tn.fintech.mfb.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/bankAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountResource {

    private final BankAccountService bankAccountService;

    public BankAccountResource(final BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }

    @GetMapping("/{rib}")
    public ResponseEntity<BankAccountDTO> getBankAccount(
            @PathVariable(name = "rib") final Long rib) {
        return ResponseEntity.ok(bankAccountService.get(rib));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBankAccount(
            @RequestBody @Valid final BankAccountDTO bankAccountDTO) {
        final Long createdRib = bankAccountService.create(bankAccountDTO);
        return new ResponseEntity<>(createdRib, HttpStatus.CREATED);
    }

    @PutMapping("/{rib}")
    public ResponseEntity<Long> updateBankAccount(@PathVariable(name = "rib") final Long rib,
            @RequestBody @Valid final BankAccountDTO bankAccountDTO) {
        bankAccountService.update(rib, bankAccountDTO);
        return ResponseEntity.ok(rib);
    }

    @DeleteMapping("/{rib}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable(name = "rib") final Long rib) {
        final ReferencedWarning referencedWarning = bankAccountService.getReferencedWarning(rib);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bankAccountService.delete(rib);
        return ResponseEntity.noContent().build();
    }

}
