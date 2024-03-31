package esprit.tn.proj.serviceInterface;

import esprit.tn.proj.entity.User;

public interface IUserService {
    User addClient(User c);
    User retrieveClient(Long id);

    void removeClient(Long id);

}
