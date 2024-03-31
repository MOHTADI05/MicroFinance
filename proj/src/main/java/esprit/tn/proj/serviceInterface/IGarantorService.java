package esprit.tn.proj.serviceInterface;

import esprit.tn.proj.entity.Garantor;

public interface IGarantorService {

    Garantor add(Garantor garantor);
    void deleteG(Long id);
    Garantor retriveG(Long id);
}
