package tn.fintech.fintech.serviceInterface;

import tn.fintech.fintech.domain.Transaction;
import tn.fintech.fintech.model.BankAccountDTO;
import tn.fintech.fintech.model.TransactionDTO;

public interface InterfaceTransaction {
    Long create(final TransactionDTO transactionDTO);
    void update(final Long transactionId, final TransactionDTO transactionDTO);
    void delete(final Long transactionId);
    Long transferMoney(BankAccountDTO sourceAccountDTO, BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO);
    Long depositMoney(BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO) ;
    Transaction mapToEntity(final TransactionDTO transactionDTO,
                            final Transaction transaction);
    TransactionDTO mapToDTO(final Transaction transaction,
                            final TransactionDTO transactionDTO);
    }
