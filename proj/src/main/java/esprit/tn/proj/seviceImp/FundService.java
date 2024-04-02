package esprit.tn.proj.seviceImp;

import esprit.tn.proj.entity.Fund;
import esprit.tn.proj.repository.FundRepository;
import esprit.tn.proj.serviceInterface.IfundService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor

public class FundService implements IfundService {
    FundRepository fundrepository;

    @Override
    public List<Fund> retrieveAllFunds() {
        return (List<Fund>) fundrepository.findAll();
    }

    @Override
    public Fund addFund(Fund f) {
        return fundrepository.save(f);
    }

    @Override
    public void deleteFund(Long idFund) {
        fundrepository.deleteById(idFund);
    }

    @Override
    public Fund updateFund(Fund fun) {
        fundrepository.save(fun)	;
        return fun;
    }

    @Override
    public Fund retrieveFund(Long idFund) {
        return fundrepository.findById(idFund).orElse(null);
    }


}
