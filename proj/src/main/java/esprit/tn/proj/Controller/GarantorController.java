package esprit.tn.proj.Controller;

import esprit.tn.proj.entity.Garantor;
import esprit.tn.proj.entity.User;
import esprit.tn.proj.serviceInterface.IGarantorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Garantor")
@AllArgsConstructor
public class GarantorController {
    private final IGarantorService garantorService;
    @PostMapping("/create")
    public Garantor create(@RequestBody Garantor garantor){
        return garantorService.add(garantor);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id)
    {
        garantorService.deleteG(id);
    }
    @GetMapping("/read/{id}")
    public Garantor read(@PathVariable Long id){
        return garantorService.retriveG(id);
    }
}

