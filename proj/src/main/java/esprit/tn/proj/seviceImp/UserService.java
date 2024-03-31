package esprit.tn.proj.seviceImp;

import esprit.tn.proj.entity.User;
import esprit.tn.proj.repository.UserRepo;
import esprit.tn.proj.serviceInterface.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
     UserRepo userRepo;
    @Override
    public User addClient(User c) {
        return userRepo.save(c);
    }

    @Override
    public User retrieveClient(Long id) {
User u=userRepo.findById(id).orElse(null);
return u;
    }

    @Override
    public void removeClient(Long id) {
        userRepo.deleteById(id);

    }
}
