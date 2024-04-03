package tn.esprit.spring.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Services.ClientService;
import tn.esprit.spring.Services.PDFGeneratorService;
import tn.esprit.spring.Services.ProductService;
import tn.esprit.spring.entity.Client;
import tn.esprit.spring.entity.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
//@Validated
public class ProductController {
    private final ProductService productService;
private final ClientService clientService;

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createOrUpdateProduct(@Valid @RequestBody Product product) {
        productService.saveOrUpdateProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        // Ajouter les contrôles de saisie ici si nécessaire

        if (productService.getProductById(id) == null) {
            return ResponseEntity.notFound().build();
        }


        product.setProductId(id);
        Product updatedProduct = productService.saveOrUpdateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    //MODIFICATION PRODUIT
    @PutMapping("/updateProduct/{IdPartner}")
    @ResponseBody
    public Product updateProduct(@RequestBody Product p,@PathVariable("IdPartner") Long id) {
        Product prd=productService.updateProduct(p, id);
        return prd;

    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
/*
    @GetMapping(value = "/PDF")
    public ResponseEntity<InputStreamResource> employeeReport() throws IOException {
        List<Product> p = (List<Product>) productService.getAllProducts();

        ByteArrayInputStream bis = PDFGeneratorService.employeePDFReport(p);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Products.pdf");

       // return ResponseEntity.ok().headers(headers)
       //         .body(new InputStreamResource(bis));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(bis.toByteArray())));
    }*/
@GetMapping(value = "/PDF")
public ResponseEntity<byte[]> employeeReport() throws IOException {
    List<Product> p = (List<Product>) productService.getAllProducts();

    ByteArrayOutputStream bos = PDFGeneratorService.employeePDFReport(p);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=Products.pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(bos.toByteArray());
}

    @PostMapping("/buy/{clientId}/{productId}/{periode}")
    public ResponseEntity<Boolean> buyProduct(@PathVariable Long clientId, @PathVariable Long productId, @PathVariable int periode) {
        Client client = clientService.getClientById(clientId);
        Product product = productService.getProductById(productId);
        boolean canAfford = productService.acheter(client, product, periode);
        if (canAfford) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    /*
    @PostMapping("/addCredit/{productId}")
    public ResponseEntity<Product> addCreditToProduct(@PathVariable Long productId, @RequestBody Pack productCredit) {
        productService.addCreditToProduct(productId, productCredit);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateCredit/{productId}")
    public ResponseEntity<Product> updateCreditForProduct(@PathVariable Long productId, @RequestBody Pack productCredit) {
        productService.updateCreditForProduct(productId, productCredit);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeCredit/{productId}/{productCreditId}")
    public ResponseEntity<Product> removeCreditFromProduct(@PathVariable Long productId, @PathVariable Long productCreditId) {
        productService.removeCreditFromProduct(productId, productCreditId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{productId}/profitability")
    public ResponseEntity<Double> getProductProfitability(@PathVariable Long productId) {
        double profitability = productService.calculateProfitability(productId);
        return new ResponseEntity<>(profitability, HttpStatus.OK);
    }
    @PostMapping("/{productId}/analyze-sales-trends")
    public ResponseEntity<Void> analyzeSalesTrends(@PathVariable Long productId) {
        productService.analyzeSalesTrends(productId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{productId}/revenue")
    public ResponseEntity<Double> calculateRevenueForProduct(
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        double revenue = productService.calculateRevenueForProduct(productId, startDate, endDate);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }*/
}
