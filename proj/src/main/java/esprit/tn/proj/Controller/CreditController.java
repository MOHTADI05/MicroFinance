package esprit.tn.proj.Controller;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.serviceInterface.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Credit")
@AllArgsConstructor
public class CreditController {
    private final ICreditService creditService;

    @PostMapping("/create")
    public Credit create(@RequestBody Credit credit){
        return creditService.addCredit(credit);
    }

    @GetMapping("/read")
    public List<Credit> read(){
        return creditService.AllCredit();
    }
@PutMapping ("/update/{id}")
    public Credit update(@PathVariable Long id,@RequestBody Credit credit){
        return creditService.updateCredit(id , credit);
    }

    @DeleteMapping ("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        return creditService.deleteCredit(id);
    }

}
