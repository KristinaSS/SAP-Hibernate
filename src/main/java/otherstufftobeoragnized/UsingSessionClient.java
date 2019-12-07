package otherstufftobeoragnized;

import models.Product;
import org.hibernate.Session;
import services.classes.ProductService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class UsingSessionClient {
    private Map<Product, Integer> shoppingCart = new HashMap<>();
    private ProductService productService = new ProductService();
    private Scanner scanner = new Scanner(System.in);

    void seeAllAvailableProducts(Session session, CriteriaBuilder builder){
        for(Product product: productService.findAll(session, builder)){
            if(product.getQuantity()>0){
                printProduct(product);
            }
        }
    }

    private void printProduct(Product product){
        System.out.println("-> Product: ");
        System.out.println("        id: "+ product.getProductId());
        System.out.println("        name: "+ product.getName());
        System.out.println("        price: "+ product.getPrice());
        System.out.println("        price: "+ product.getCatagory().getName());
    }

    void addProductToCart(Session session, CriteriaBuilder builder){
        seeAllAvailableProducts(session, builder);
        System.out.println("Enter product number or enter 'EXIT; to cancel");
        String result = scanner.nextLine();
        Product product = productService.getOne(result);
        System.out.println("Enter qunatity you want to purchase: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if(product.getQuantity()< quantity){
            //throw exception
        }else{
            shoppingCart.put(product, quantity);
        }
    }

    void checkOutCart(Session session){
        for (Map.Entry<Product, Integer> product: shoppingCart.entrySet()){
            if(product.getKey().getQuantity()>=product.getValue()){
                product.getKey().setQuantity(product.getKey().getQuantity()- product.getValue());
                productService.updateByID(product.getKey(), session);
            }else{
                //throw exception
            }
        }
    }
}
