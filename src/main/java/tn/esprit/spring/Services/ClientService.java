package tn.esprit.spring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Repository.ClientRepository;
import tn.esprit.spring.entity.Client;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;
    public Client addClient(Client c) {


        clientRepository.save(c);
        return c ;
    }
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }
}
