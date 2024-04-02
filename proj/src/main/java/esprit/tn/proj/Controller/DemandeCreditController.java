package esprit.tn.proj.Controller;

import esprit.tn.proj.entity.Amortissement;
import esprit.tn.proj.entity.DemandeCredit;
import esprit.tn.proj.serviceInterface.IDemandeCreditService;
import esprit.tn.proj.seviceImp.DemandeCreditService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/DemandeCredit")
@AllArgsConstructor

public class DemandeCreditController {
    private final IDemandeCreditService demandeCreditService;
    @PostMapping("/create/{id}/{Credit-Id_fund}/{idC}/{credit_idgarant}")
    public DemandeCredit createNewCreditBasedOnExistingCredit(@RequestBody DemandeCredit request,@PathVariable  Long id,  @PathVariable("Credit-Id_fund") Long Id_fund,@PathVariable  Long idC, @PathVariable("credit_idgarant") Long Id_garant) {

            DemandeCredit newCredit = demandeCreditService.add (
                    id,
                    Id_fund,
                    idC,
                     Id_garant,
                    request.getYear(),
                    request.getTypeCredit(),
                    request.getAmount(),
                    request.getMonthlyPaymentDate()

            );
            return newCredit;

    }
    @PutMapping("/update/{id}/{Credit-Id_fund}/{idC}/{credit_idgarant}")
    public DemandeCredit updateDemandeCredit(@RequestBody DemandeCredit request,@PathVariable  Long id,  @PathVariable("Credit-Id_fund") Long Id_fund,@PathVariable  Long idC, @PathVariable("credit_idgarant") Long Id_garant) {

        DemandeCredit newCredit = demandeCreditService.add (
                id,
                Id_fund,
                idC,
                Id_garant,
                request.getYear(),
                request.getTypeCredit(),
                request.getAmount(),
                request.getMonthlyPaymentDate()

        );
        return newCredit;

    }
    @DeleteMapping("/removecredit/{credit-id}")
    @ResponseBody
    public void removecredit(@PathVariable("credit-id") Long creditId) {
        demandeCreditService.DeleteCredit(creditId);
    }


    @GetMapping("/simulate/{mnttotl}/{period}")
    @ResponseBody
    public Amortissement simulate(@PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period) {
        DemandeCredit cr=new DemandeCredit(mnttotl,period);

        return demandeCreditService.Simulateur(cr);

    }

    @GetMapping("/export/excel/{mnttotl}/{period}")
    @ResponseBody
    public void exportToExcel(HttpServletResponse response, @PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period
            ) throws IOException {

        DemandeCredit cr=new DemandeCredit(mnttotl,period);

        float Interest;
        if (cr.getYear() > 5) {
            Interest = 14; // Si la durée est supérieure à 5 ans, définissez le taux d'intérêt à 4%
        } else {
            Interest = 12; // Sinon, définissez le taux d'intérêt à 12%
        }
        cr.setInterest(Interest);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";

        String headervalue = "attachment; filename=Tableau_Credit_N_"+cr.getIdDemandecredit()+".xlsx";
        response.setHeader(headerKey, headervalue);
        Amortissement[] credit = demandeCreditService.TabAmortissement(cr);
        List<Amortissement> list = Arrays.asList(credit);
        esprit.tn.proj.seviceImp.UserExcelExport exp =new esprit.tn.proj.seviceImp.UserExcelExport(list);
        //UserExcelExporter exp = new UserExcelExporter(list);
        exp.export(response);

    }

}
