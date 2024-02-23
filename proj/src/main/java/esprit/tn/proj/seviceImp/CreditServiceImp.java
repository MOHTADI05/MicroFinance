package esprit.tn.proj.seviceImp;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.repository.CreditRepository;
import esprit.tn.proj.serviceInterface.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CreditServiceImp implements ICreditService {
    CreditRepository creditRepository;
    @Override
    public List<Credit> AllCredit() {
        return creditRepository.findAll();
    }

    @Override
    public Credit addCredit(Credit credit) {
        return creditRepository.save(credit);
    }
    @Override
    public Credit updateCredit(Long id, Credit credit) {
        return creditRepository.findById(id)
                .map(c-> {
                    c.setAmount(credit.getAmount());
                    c.setDemandedate(credit.getDemandedate());
                    c.setInterest(credit.getInterest());
                    c.setState(credit.getState());
                    c.setMounthlypayment(credit.getMounthlypayment());
                    c.setObtainingdate(credit.getObtainingdate());
                    return creditRepository.save(c);
                }).orElseThrow(()-> new RuntimeException("Credit not found"));
    }

    @Override
    public String deleteCredit(Long id) {
        creditRepository.deleteById(id);
        return "Credit supp";
    }
}
