package tn.fintech.mfb.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fintech.mfb.domain.BankAccount;
import tn.fintech.mfb.domain.Transaction;
import tn.fintech.mfb.domain.User;
import tn.fintech.mfb.model.BankAccountDTO;
import tn.fintech.mfb.repos.BankAccountRepository;
import tn.fintech.mfb.repos.TransactionRepository;
import tn.fintech.mfb.repos.UserRepository;
import tn.fintech.mfb.util.NotFoundException;
import tn.fintech.mfb.util.ReferencedWarning;


@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(final BankAccountRepository bankAccountRepository,
            final UserRepository userRepository,
            final TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<BankAccountDTO> findAll() {
        final List<BankAccount> bankAccounts = bankAccountRepository.findAll(Sort.by("rib"));
        return bankAccounts.stream()
                .map(bankAccount -> mapToDTO(bankAccount, new BankAccountDTO()))
                .toList();
    }

    public BankAccountDTO get(final Long rib) {
        return bankAccountRepository.findById(rib)
                .map(bankAccount -> mapToDTO(bankAccount, new BankAccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BankAccountDTO bankAccountDTO) {
        final BankAccount bankAccount = new BankAccount();
        mapToEntity(bankAccountDTO, bankAccount);
        return bankAccountRepository.save(bankAccount).getRib();
    }

    public void update(final Long rib, final BankAccountDTO bankAccountDTO) {
        final BankAccount bankAccount = bankAccountRepository.findById(rib)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bankAccountDTO, bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void delete(final Long rib) {
        bankAccountRepository.deleteById(rib);
    }

    private BankAccountDTO mapToDTO(final BankAccount bankAccount,
            final BankAccountDTO bankAccountDTO) {
        bankAccountDTO.setRib(bankAccount.getRib());
        bankAccountDTO.setBalancer(bankAccount.getBalancer());
        bankAccountDTO.setOpenDate(bankAccount.getOpenDate());
        bankAccountDTO.setStatus(bankAccount.getStatus());
        bankAccountDTO.setTransactionHistory(bankAccount.getTransactionHistory());
        bankAccountDTO.setLoyaltyScore(bankAccount.getLoyaltyScore());
        bankAccountDTO.setTypeAccount(bankAccount.getTypeAccount());
        bankAccountDTO.setUser(bankAccount.getUser() == null ? null : bankAccount.getUser().getCin());
        return bankAccountDTO;
    }

    private BankAccount mapToEntity(final BankAccountDTO bankAccountDTO,
            final BankAccount bankAccount) {
        bankAccount.setBalancer(bankAccountDTO.getBalancer());
        bankAccount.setOpenDate(bankAccountDTO.getOpenDate());
        bankAccount.setStatus(bankAccountDTO.getStatus());
        bankAccount.setTransactionHistory(bankAccountDTO.getTransactionHistory());
        bankAccount.setLoyaltyScore(bankAccountDTO.getLoyaltyScore());
        bankAccount.setTypeAccount(bankAccountDTO.getTypeAccount());
        final User user = bankAccountDTO.getUser() == null ? null : userRepository.findById(bankAccountDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        bankAccount.setUser(user);
        return bankAccount;
    }

    public ReferencedWarning getReferencedWarning(final Long rib) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final BankAccount bankAccount = bankAccountRepository.findById(rib)
                .orElseThrow(NotFoundException::new);
        final Transaction sourceRIBTransaction = transactionRepository.findFirstBySourceRIB(bankAccount);
        if (sourceRIBTransaction != null) {
            referencedWarning.setKey("bankAccount.transaction.sourceRIB.referenced");
            referencedWarning.addParam(sourceRIBTransaction.getTransactionId());
            return referencedWarning;
        }
        return null;
    }

}
