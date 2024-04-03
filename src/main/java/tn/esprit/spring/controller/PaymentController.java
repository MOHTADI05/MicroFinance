package tn.esprit.spring.controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Services.AmortizationServiceImpl;
import tn.esprit.spring.Services.PDFGeneratorService;
import tn.esprit.spring.Services.PaymentServiceImpl;
import tn.esprit.spring.Services.UserExcelExporter;
import tn.esprit.spring.entity.Amortization;
import tn.esprit.spring.entity.Payment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {


    private PaymentServiceImpl paymentService;
    private AmortizationServiceImpl amortizationService;
    @GetMapping("/export/excel/{mnttotl}/{period}/{interst}")
    @ResponseBody
    public void exportToExcel(HttpServletResponse response, @PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period,
                              @PathVariable("interst") float interst) throws IOException {

        // Créer un amortissement avec les attributs donnés
        Amortization amortization = new Amortization();
        amortization.setStartDate(new Date()); // Vous devez définir la date de début appropriée
        amortization.setInitialBalance(mnttotl);
        amortization.setDurationInMonths((int) period);
        amortization.setInterestRate(interst);
        amortization.setPaymentType(0); // Vous devez définir le type de paiement approprié
        amortization.setFutureValue(0); // Vous devez définir la valeur future appropriée

        // Initialisez les champs inconnus de l'amortissement
        amortizationService.initializeUnknownFields(amortization);

        // Récupérer la liste des paiements de l'amortissement
        List<Payment> paymentList = amortization.getPaymentList();

        // Exporter la liste des paiements au format Excel
        UserExcelExporter userExcelExporter = new UserExcelExporter(paymentList);
        userExcelExporter.export(response);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/get/{paymentNumber}")
    public ResponseEntity<Payment> getPaymentByNumber(@PathVariable int paymentNumber) {
        Payment payment = paymentService.getPaymentByNumber(paymentNumber);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
/*
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }
*/
@PostMapping("/create")
public ResponseEntity<InputStreamResource> createPayment(@RequestBody @Valid Payment payment) {
    Payment createdPayment = paymentService.createPayment(payment);

    // Générer le PDF avec les détails du paiement créé
    ByteArrayOutputStream bos = PDFGeneratorService.generatePaymentPDFReport(createdPayment);

    // Retourner le PDF dans le corps de la réponse
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=Payment_" + createdPayment.getPaymentNumber() + ".pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(new ByteArrayInputStream(bos.toByteArray())));
}

    @PutMapping("/{paymentNumber}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentNumber, @RequestBody @Valid Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(paymentNumber, payment);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentNumber}")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentNumber) {
        paymentService.deletePayment(paymentNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
