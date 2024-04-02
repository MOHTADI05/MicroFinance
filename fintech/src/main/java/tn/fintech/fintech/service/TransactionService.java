package tn.fintech.fintech.service;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fintech.fintech.domain.BankAccount;
import tn.fintech.fintech.domain.Transaction;
import tn.fintech.fintech.model.BankAccountDTO;
import tn.fintech.fintech.model.TransactionDTO;
import tn.fintech.fintech.repos.BankAccountRepository;
import tn.fintech.fintech.repos.TransactionRepository;
import tn.fintech.fintech.serviceInterface.InterfaceTransaction;
import tn.fintech.fintech.util.NotFoundException;


@AllArgsConstructor
@Service
public class TransactionService implements InterfaceTransaction {


    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;



    public List<TransactionDTO> findAll() {
        final List<Transaction> transactions = transactionRepository.findAll(Sort.by("transactionId"));
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .toList();
    }

    public TransactionDTO get(final Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);

        return transactionRepository.save(transaction).getTransactionId();
    }

    public void update(final Long transactionId, final TransactionDTO transactionDTO) {
        final Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionDTO, transaction);
        transactionRepository.save(transaction);
    }

    public void delete(final Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Transactional
    public Long transferMoney(BankAccountDTO sourceAccountDTO, BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO) {
        // Retrieve source and destination accounts from DTOs
        BankAccount sourceAccount = bankAccountRepository.findById(sourceAccountDTO.getRib())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        BankAccount destinationAccount = bankAccountRepository.findById(destinationAccountDTO.getRib())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        Double amount = transactionDTO.getAmount();

        // Check if source account has enough balance
        Double sourceBalance = sourceAccount.getBalance();
        if (sourceBalance != null && sourceBalance >= amount) {
            // add to history

            // Deduct amount from source account
            sourceAccount.setBalance(sourceBalance - amount);

            // Add amount to destination account
            Double destinationBalance = destinationAccount.getBalance();
            destinationAccount.setBalance((destinationBalance != null ? destinationBalance : 0.0) + amount);

            // Save the modified accounts
            bankAccountRepository.save(sourceAccount);
            bankAccountRepository.save(destinationAccount);

            // Create a new transaction
            Transaction transaction = new Transaction();
            mapToEntity(transactionDTO, transaction);
            return transactionRepository.save(transaction).getTransactionId();
        } else {
            // Handle insufficient balance or null balance scenario
            throw new RuntimeException("Insufficient balance in the source account or balance is null");
        }
    }


    @Transactional
    public Long depositMoney(BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO) {
        // Retrieve the destination account from DTO
        BankAccount destinationAccount = bankAccountRepository.findById(destinationAccountDTO.getRib())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        Double amount = transactionDTO.getAmount();

        // Add amount to destination account
        Double destinationBalance = destinationAccount.getBalance();
        destinationAccount.setBalance((destinationBalance != null ? destinationBalance : 0.0) + amount);

        // Save the modified account
        bankAccountRepository.save(destinationAccount);

        // Create a new transaction
        Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);

        return transactionRepository.save(transaction).getTransactionId();
    }
    @Transactional
    public Long Withdrawsmoney(BankAccountDTO sourceAccountDTO, TransactionDTO transactionDTO) {
        // Retrieve the source account from DTO
        BankAccount sourceAccount = bankAccountRepository.findById(sourceAccountDTO.getRib())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        // Check if source account has sufficient balance
        Double sourceBalance = sourceAccount.getBalance();
        Double amount = transactionDTO.getAmount();
        if (sourceBalance == null || sourceBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance in the source account");
        }

        // Deduct amount from source account
        sourceAccount.setBalance(sourceBalance - amount);

        // Save the modified account
        bankAccountRepository.save(sourceAccount);

        // Create a new transaction
        Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);

        return transactionRepository.save(transaction).getTransactionId();
    }




    public Transaction mapToEntity(final TransactionDTO transactionDTO,
                                    final Transaction transaction) {
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDestination(transactionDTO.getDestination());
        final BankAccount sourceRIB = transactionDTO.getSourceRIB() == null ? null : bankAccountRepository.findById(transactionDTO.getSourceRIB())
                .orElseThrow(() -> new NotFoundException("sourceRIB not found"));
        transaction.setSourceRIB(sourceRIB);
        return transaction;
    }



    public  TransactionDTO mapToDTO(final Transaction transaction,
                                    final TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDestination(transaction.getDestination());
        transactionDTO.setSourceRIB(transaction.getSourceRIB() == null ? null : transaction.getSourceRIB().getRib());
        return transactionDTO;
    }
}
