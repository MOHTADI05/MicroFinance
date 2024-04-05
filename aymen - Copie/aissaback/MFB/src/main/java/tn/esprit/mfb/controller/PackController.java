package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.PackC;
import tn.esprit.mfb.serviceInterface.IPackService;

import java.util.List;

@RestController
@RequestMapping("Pack")
@AllArgsConstructor
public class PackController {
private final IPackService packService;
    @PostMapping("/create")
    public PackC create(@RequestBody PackC packC){
    return packService.addPack(packC);
}

    @GetMapping("/read")
    public List<PackC> read(){
        return packService.AllPack();
    }

    @PutMapping ("/update/{id}")
    public PackC update(@PathVariable Long id,@RequestBody PackC packC){
        return packService.updatePack(id , packC);
    }

    @DeleteMapping ("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        return packService.deletePack(id);
        }

}
