package tn.fintech.fintech.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.fintech.fintech.domain.BankAccount;
import tn.fintech.fintech.model.BankAccountDTO;
import tn.fintech.fintech.model.TransactionDTO;
import tn.fintech.fintech.repos.BankAccountRepository;
import tn.fintech.fintech.service.EmailSenderService;
import tn.fintech.fintech.service.TransactionService;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

    private final TransactionService transactionService;
    private final BankAccountRepository bankAccountRepository;
    private final EmailSenderService senderService;


    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransaction(
            @PathVariable(name = "transactionId") final Long transactionId) {
        return ResponseEntity.ok(transactionService.get(transactionId));
    }



    @PutMapping("/{transactionId}")
    public ResponseEntity<Long> updateTransaction(
            @PathVariable(name = "transactionId") final Long transactionId,
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        transactionService.update(transactionId, transactionDTO);
        return ResponseEntity.ok(transactionId);
    }

    @DeleteMapping("/{transactionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable(name = "transactionId") final Long transactionId) {
        transactionService.delete(transactionId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Long sourceAccountId,
                                                @RequestParam Long destinationAccountId,
                                                @RequestParam Double amount) {
        BankAccount sourceAccount = bankAccountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        BankAccount destinationAccount = bankAccountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(amount);
            transactionDTO.setSourceRIB(sourceAccountId);
            transactionDTO.setDestination(destinationAccountId);
            transactionDTO.setTransactionDate(LocalDate.now());

            transactionService.create(transactionDTO);
            transactionService.transferMoney(mapToDTO(sourceAccount), mapToDTO(destinationAccount), transactionDTO);
            return ResponseEntity.ok("Transaction successful");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestParam Long destinationAccountId,
                                               @RequestParam Double amount,
                                               @RequestParam String mail  ) {
        BankAccount destinationAccount = bankAccountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(amount);
            transactionDTO.setDestination(destinationAccountId);
            transactionDTO.setTransactionDate(LocalDate.now());

            // Deposit money
            Long transactionId = transactionService.depositMoney(mapToDTO(destinationAccount), transactionDTO);

            // Send email to account owner

            senderService.sendSimpleEmail(
                            mail,
                     "Deposit Recive",
                      "Deposit successful, Transaction ID:" +

                              transactionId+ System.lineSeparator()+
                            "You have successfully deposited " +
                              System.lineSeparator()+
                              amount +
                            "into your account.");

            return ResponseEntity.ok("Deposit successful, Transaction ID: " + transactionId);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/Withdraws")
    public ResponseEntity<String> Withdrawsmoney(@RequestParam Long sourceAccountId,
                                               @RequestParam Double amount,
                                               @RequestParam String mail  ) {
        BankAccount sourceAccount = bankAccountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("source account not found"));

        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(amount);
            transactionDTO.setSourceRIB(sourceAccountId);
            transactionDTO.setTransactionDate(LocalDate.now());

            // Deposit money
            Long transactionId = transactionService.Withdrawsmoney(mapToDTO(sourceAccount), transactionDTO);

            // Send email to account owner

            senderService.sendSimpleEmail(
                    mail,
                    "retrive Recive",
                    "retrive successful, Transaction ID:" +

                            transactionId+ System.lineSeparator()+
                            "You have successfully retrived " +
                            System.lineSeparator()+
                            amount +
                            "into your account.");


            return ResponseEntity.ok("retrive successful, Transaction ID: " + transactionId);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Utility method to map BankAccount entity to BankAccountDTO
    private BankAccountDTO mapToDTO(BankAccount bankAccount) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setRib(bankAccount.getRib());
        dto.setBalance(bankAccount.getBalance());
        // Map other fields as needed
        return dto;
    }

}
