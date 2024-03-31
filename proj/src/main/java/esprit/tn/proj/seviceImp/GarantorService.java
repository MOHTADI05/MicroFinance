package esprit.tn.proj.seviceImp;

import esprit.tn.proj.entity.DemandeCredit;
import esprit.tn.proj.entity.Garantor;
import esprit.tn.proj.repository.DemaneCreditRepository;
import esprit.tn.proj.repository.GarantorRepo;
import esprit.tn.proj.serviceInterface.IGarantorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GarantorService implements IGarantorService {
     GarantorRepo garantorRepo;
     DemaneCreditRepository demaneCreditRepository;
    @Override
    public Garantor add(Garantor garantor) {

        garantorRepo.save(garantor);
        return garantor;
    }

    @Override
    public void deleteG(Long id) {
          garantorRepo.deleteById(id);
    }

    @Override
    public Garantor retriveG(Long id) {
        return null;
    }
}
