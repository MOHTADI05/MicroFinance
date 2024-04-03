package tn.esprit.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Services.ClientService;
import tn.esprit.spring.entity.Client;

@RestController
@RequestMapping("/Client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientservice;
    @PostMapping("/insertClient")
    @ResponseBody
    public Client AjoutClient (@RequestBody Client c)
    {
        return clientservice.addClient(c) ;
    }
}
