package tn.esprit.spring.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Repository.*;
import tn.esprit.spring.entity.*;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private ProductRepository productRepository;
   private PartnerRepository partnerRepository;
    private EmailSenderService emsender;
@Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
@Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
    @Override
    public Product saveOrUpdateProduct(Product product) {
        if (product.getProductId() != null) {
            // Logique de mise à jour pour un produit existant
            return productRepository.findById(product.getProductId()).map(c -> {
                c.setIsAvailable("Available"); // Met à jour le statut si le produit existe
                // Copiez ici toute autre mise à jour de champ que vous souhaitez appliquer
                return productRepository.save(c);
            }).orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID : " + product.getProductId()));
        } else {
            // Logique pour un nouveau produit
            product.setIsAvailable("Available"); // Initialise le statut pour un nouveau produit
            // Initialiser ici tout autre champ nécessaire pour un nouveau produit
            return productRepository.save(product);
        }
    }

    @Override
    public Product updateProduct(Product prod, Long partnerid) {
        Partner pr =partnerRepository.findById(partnerid).get();
        prod.setPartner(pr);
        return  productRepository.save(prod)	;



    }

   /* public List<Pack> getProductCredits(Long productId) {
        Product product = getProductById(productId);
        return product.getpacks();
    }*/

    //Les ajustements de prix sont basés sur des pourcentages spécifiques en fonction des valeurs initiales des produits.
    @Scheduled(cron = "* * * 05 * *" ) //sera exécutée à chaque seconde de l'heure 5 de chaque jour
    public void exclusivitePrix() {
        List<Product> pr=(List)(productRepository.findAll());
        for (Iterator iterator = pr.iterator(); iterator.hasNext();) {
            Product product = (Product) iterator.next();
            if(product.getValueProduct()<100) {
                product.setValueEXC(product.getValueProduct()*90/100);
                productRepository.save(product);}
            else if ((product.getValueProduct()<1000) && (product.getValueProduct()>100)) {
                product.setValueEXC(product.getValueProduct()*92/100);
                productRepository.save(product);}
            else {
                product.setValueEXC(product.getValueProduct()*94/100);
                productRepository.save(product);
            }
        }
    }

    @Override
    @Transactional
    public boolean acheter(Client client, Product product,  int periode) {
        double monthlyPayment;
       if(Objects.equals(product.getIsAvailable(), "Available"))
       {


            double netIncome = client.getNetIncome(); // Assurez-vous que la classe Client a une méthode getNetIncome
            monthlyPayment=product.getValueEXC()/periode;
            double affordabilityIndex = netIncome / monthlyPayment;
            boolean canAfford = affordabilityIndex >= 1.2;
            if (canAfford) {
                System.out.println("achat avec succe");

                product.setIsAvailable("Not Available");
                client.setProduct(product);
                String message = "Bonjour " + client.getClientName() + ", vous avez acheté le produit " + product.getProductName() + " avec une valeur de " + product.getValueEXC();
                emsender.sendSimpleEmail(client.getClientEmail(), "Confirmation d'achat", message);

            }
           return canAfford;

        } else {
            // Print a message
            System.out.println("Product not available");
            return false;
        }
    }

    /*@Override
    public boolean acheter(Client client, Product product,  int periode) {
        double monthlyPayment;
        AvailableStore availableStore = availableStoreRepository.findById(1L).orElseThrow(() -> new RuntimeException("Available Store not found")); // replace 1L with your store id
        PurchasedStore purchasedStore = purchasedStoreRepository.findById(1L).orElseThrow(() -> new RuntimeException("Purchased Store not found")); // replace 1L with your store id

        double netIncome = client.getNetIncome(); // Assurez-vous que la classe Client a une méthode getNetIncome
        monthlyPayment=product.getValueEXC()/periode;
        double affordabilityIndex = netIncome / monthlyPayment;
        boolean canAfford = affordabilityIndex >= 1.2;
        if (canAfford) {
            availableStore.getAvailableProducts().remove(product);
            purchasedStore.getPurchasedProducts().add(product);
            client.setProduct(product);
            availableStoreRepository.save(availableStore); // save the changes to the available store
            purchasedStoreRepository.save(purchasedStore); // save the changes to the purchased store
        }
        return canAfford;
    }*/
   /* public Amortization Simulateur(Product product, int periode)
    {
        System.out.println(product.getValueEXC());
        Amortization simulator = new Amortization();
        //mnt total
        simulator.setInitialBalance(0);
        //mnt interet
        simulator.setInterestRate(0);
        //mnt monthly
        simulator.setMonthlyPayment(product.getValueEXC()/periode);

        Amortization[] productTab = TabAmortissement(product);
        float s=0;
        float s1=0;
        for (int i=0; i < productTab.length; i++) {
            s1=(float) (s1+productTab[i].getInterestRate());
        }
        //mnt interet
        simulator.setInterestRate(s1);
        //mnt total
        simulator.setFutureValue(product.getValueEXC()+s1);
        //mnt product
        simulator.setInitialBalance(product.getValueEXC());

        return simulator;
    }*/
/*
    public Pack getProductCreditById(Long productId, Long creditId) {
        Product product = getProductById(productId);
        return product.getProductCredits()
                .stream()
                .filter(credit -> credit.getId().equals(creditId))
                .findFirst()
                .orElse(null);
    }


    public int getProductCreditIndex(Long productId, Long creditId) {
        Product product = getProductById(productId);
        List<Pack> credits = product.getProductCredits();
        for (int i = 0; i < credits.size(); i++) {
            if (credits.get(i).getId().equals(creditId)) {
                return i;
            }
        }
        return -1; // Retourne -1 si le crédit n'est pas trouvé
    }


    public void addCreditToProduct(Long productId, Pack productCredit) {
        Product product = getProductById(productId);
        productCredit.setProduct((Set<Product>) product);
        product.getProductCredits().add(productCredit);
        productRepository.save(product);
    }


    public void updateCreditForProduct(Long productId, Pack productCredit) {
        Product product = getProductById(productId);
        int index = getProductCreditIndex(productId, productCredit.getId());
        if (index != -1) {
            productCredit.setProduct((Set<Product>) product);
            product.getProductCredits().set(index, productCredit);
            productRepository.save(product);
        }
    }


    public void removeCreditFromProduct(Long productId, Long creditId) {
        Product product = getProductById(productId);
        int index = getProductCreditIndex(productId, creditId);
        if (index != -1) {
            product.getProductCredits().remove(index);
            productRepository.save(product);
        }
    }

    public double calculateProfitability(Long productId) {
        Product product = getProductById(productId);

        if (product != null) {
            double totalCosts = calculateTotalCosts(product);
            double profitability = (product.getPrice() - totalCosts) / product.getPrice();
            return profitability >= 0 ? profitability : 0.0;
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }
    private double calculateTotalCosts(Product product) {

        // les coûts sont 10% du prix du produit
        return 0.1 * product.getPrice();
    }
    public void analyzeSalesTrends(Long productId) {
        Product product = getProductById(productId);

        if (product != null) {

            List<Pack> credits = getProductCredits(productId);

            Map<Month, Double> salesByMonth = new HashMap<>();

            for (Pack credit : credits) {
                LocalDate startDate = credit.getStartDate();
                Month month = startDate.getMonth();

                double creditAmount = credit.getCreditAmount();
                salesByMonth.merge(month, creditAmount, Double::sum);
            }

            System.out.println("Sales Trends for Product ID " + productId + ":");
            for (Map.Entry<Month, Double> entry : salesByMonth.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " credits");
            }
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }

    public double calculateRevenueForProduct(Long productId, LocalDate startDate, LocalDate endDate) {
        Product product = getProductById(productId);

        if (product != null) {
            double revenue = 0.0;

            List<Pack> productCredits = product.getProductCredits();
            for (Pack credit : productCredits) {
                LocalDate creditStartDate = credit.getStartDate();
                LocalDate creditEndDate = credit.getEndDate();

                if (creditStartDate.isBefore(endDate) && creditEndDate.isAfter(startDate)) {
                    revenue += credit.getCreditAmount();
                }
            }

            return revenue;
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }*/
}