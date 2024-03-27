package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.CsvExporter;
import tn.esprit.mfb.Services.ProfilSolvabiliteService;
import tn.esprit.mfb.entity.ProfilSolvabilite;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/solva")
public class ProfilSolvaController {

    final private ProfilSolvabiliteService service;
    final private CsvExporter CsvService;

    @PostMapping("/AddProfil")
    @ResponseBody
    public ProfilSolvabilite AjoutAgent(@RequestBody ProfilSolvabilite p) {
        return service.addprofil(p);
    }

    @GetMapping("/profilsolvabilite/export")
    public ResponseEntity<String> exportDataToCSV() {
        List<ProfilSolvabilite> profilSolvabilites = service.getAllProfiles();
        String filePath = "C:\\Users\\USER\\Desktop\\dataset\\exportt.csv";

        try {
            CsvService.exportDataToCSV(profilSolvabilites, filePath);
            return new ResponseEntity<>("Export des données vers le fichier CSV réussi.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Une erreur s'est produite lors de l'exportation des données.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
