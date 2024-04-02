package esprit.tn.proj.seviceImp;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.entity.PackC;
import esprit.tn.proj.repository.PackRepository;
import esprit.tn.proj.serviceInterface.IPackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackServiceImp implements IPackService {
    PackRepository packRepository;
    @Override
    public List<PackC> AllPack() {
        return packRepository.findAll();
    }

    @Override
    public PackC addPack(PackC packC) {
        return packRepository.save(packC);
    }

    @Override
    public PackC updatePack(Long id, PackC packC) {
        return packRepository.findById(id)
                .map(c-> {
                    c.setTypePack(packC.getTypePack());
                    c.setDescription(packC.getDescription());
                    c.setName(packC.getName());

                    return packRepository.save(c);
                }).orElseThrow(()-> new RuntimeException("Credit not found"));
    }

    @Override
    public String deletePack(Long id) {
         packRepository.deleteById(id);
        return "Pack supprimé";
    }
}
