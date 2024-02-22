package tn.fintech.mfb.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fintech.mfb.domain.BankAccount;
import tn.fintech.mfb.domain.Transaction;
import tn.fintech.mfb.model.TransactionDTO;
import tn.fintech.mfb.repos.BankAccountRepository;
import tn.fintech.mfb.repos.TransactionRepository;
import tn.fintech.mfb.util.NotFoundException;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(final TransactionRepository transactionRepository,
            final BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

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

    private TransactionDTO mapToDTO(final Transaction transaction,
            final TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setRecipient(transaction.getRecipient());
        transactionDTO.setSourceRIB(transaction.getSourceRIB() == null ? null : transaction.getSourceRIB().getRib());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setRecipient(transactionDTO.getRecipient());
        final BankAccount sourceRIB = transactionDTO.getSourceRIB() == null ? null : bankAccountRepository.findById(transactionDTO.getSourceRIB())
                .orElseThrow(() -> new NotFoundException("sourceRIB not found"));
        transaction.setSourceRIB(sourceRIB);
        return transaction;
    }

}
