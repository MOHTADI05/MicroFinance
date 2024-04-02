package esprit.tn.proj.serviceInterface;

import esprit.tn.proj.entity.Fund;

import java.util.List;

public interface IfundService {

    List<Fund> retrieveAllFunds();

    Fund addFund(Fund f);

    void deleteFund(Long idFund);

    Fund updateFund(Fund fun);

    Fund retrieveFund(Long idFund);
}
