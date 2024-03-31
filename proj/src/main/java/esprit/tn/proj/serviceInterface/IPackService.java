package esprit.tn.proj.serviceInterface;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.entity.PackC;

import java.util.List;

public interface IPackService {
    List<PackC> AllPack();
    PackC addPack(PackC packC);
    PackC updatePack(Long id,PackC packC);
    String deletePack(Long id);
}
