package tn.esprit.spring.Services;

import tn.esprit.spring.entity.Client;
import tn.esprit.spring.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void saveProduct(Product product);
    void deleteProduct(Long productId);
    Product saveOrUpdateProduct(Product product);
    Product updateProduct(Product prod, Long partnerid);
    boolean acheter(Client client, Product product, int periode);
}
