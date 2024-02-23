package esprit.tn.proj.serviceInterface;

import esprit.tn.proj.entity.Credit;

import java.util.List;

public interface ICreditService {
    List<Credit> AllCredit();
    Credit addCredit(Credit credit);
    Credit updateCredit(Long id,Credit credit);
    String deleteCredit(Long id);
}
