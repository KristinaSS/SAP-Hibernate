package otherstufftobeoragnized;

import models.Catagory;
import models.Product;
import org.hibernate.Session;
import services.classes.CatagoryService;
import services.classes.ProductService;
import sun.util.resources.cldr.aa.CurrencyNames_aa;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Scanner;

public class UsingSessionEmployee {
    private ProductService productService = new ProductService();
    private CatagoryService catagoryService = new CatagoryService();
    private Scanner scanner = new Scanner(System.in);

    public Product editProductName(Product product, Session session) {
        String name = enterName();
        product.setName(name);
        return productService.updateByID(product, session);
    }

    public Product stockUpProduct(Product product, Session session){
        int addedQuantity = enterQuantity();
        product.setQuantity(product.getQuantity()+ addedQuantity);
        return productService.updateByID(product,session);
    }

    public Product changeCategory(Product product, Session session,CriteriaBuilder builder){
        seeAllCatagories(session, builder);
        Catagory catagory = chooseCatagory();
        product.setCatagory(catagory);
        return productService.updateByID(product,session);
    }
    public Product changeCategoryPrice(Product product, Session session){
        float price = enterPrice();
        product.setPrice(price);
        return productService.updateByID(product,session);
    }

    public Product createProduct(Session session, CriteriaBuilder builder) {
        System.out.println("-> Product: ");
        String name = enterName();
        float price = enterPrice();
        Catagory catagory = enterCatagory(session, builder);
        int quantity = enterQuantity();

        Product product = new Product(name, quantity, catagory, price);
        return productService.createOne(product, session);
    }

    public Product deleteProduct(Session session, CriteriaBuilder builder) {
        seeAllProducts(session, builder);
        Product product = chooseProduct();
        productService.deleteByID(String.valueOf(product.getProductId()), session);
        return product;
    }

    private Product chooseProduct() {
        System.out.println("Enter product number or enter 'EXIT; to cancel");
        String result = scanner.nextLine();
        //todo some exceptions
        return productService.getOne(result);
    }

    private void seeAllProducts(Session session, CriteriaBuilder builder) {
        for (Product product : productService.findAll(session, builder)) {
            printProduct(product);
        }
    }

    private void printProduct(Product product) {
        System.out.println("-> Product: ");
        System.out.println("        id: " + product.getProductId());
        System.out.println("        name: " + product.getName());
        System.out.println("        price: " + product.getPrice());
        System.out.println("        category: " + product.getCatagory().getName());
        System.out.println("        quantity: " + product.getQuantity());
    }

    private void seeAllCatagories(Session session, CriteriaBuilder builder) {
        for (Catagory catagory : catagoryService.findAll(session, builder)) {
            printCatagory(catagory);
        }
    }

    private void printCatagory(Catagory catagory) {
        System.out.println("    -> Catagory: ");
        System.out.println("            id: " + catagory.getCategoryId());
        System.out.println("            name: " + catagory.getName());
    }

    private Catagory chooseCatagory() {
        System.out.println("Enter catagory number or enter 'EXIT; to cancel");
        String result = scanner.nextLine();
        //todo some exceptions
        return catagoryService.getOne(result);
    }

    private String enterName() {
        System.out.println("        name: ");
        return scanner.nextLine();
    }

    private float enterPrice() {
        System.out.println("        price: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private Catagory enterCatagory(Session session, CriteriaBuilder builder) {
        System.out.println("        category: ");
        seeAllCatagories(session, builder);
        return chooseCatagory();
    }

    private int enterQuantity() {
        System.out.println("        quantity: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
