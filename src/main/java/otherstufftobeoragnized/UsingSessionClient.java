package otherstufftobeoragnized;

import models.Account;
import models.Order;
import models.OrderDetails;
import models.Product;
import org.hibernate.Session;
import services.classes.OrderDetailsService;
import services.classes.OrderServices;
import services.classes.ProductService;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.*;

public class UsingSessionClient {
    private Map<Product, Integer> shoppingCart = new HashMap<>();
    private ProductService productService = new ProductService();
    private OrderServices orderServices = new OrderServices();
    private OrderDetailsService orderDetailsService = new OrderDetailsService();

    private Scanner scanner = new Scanner(System.in);

    void seeAllAvailableProducts(Session session, CriteriaBuilder builder) {
        for (Product product : productService.findAll(session, builder)) {
            if (product.getQuantity() > 0) {
                printProduct(product);
            }
        }
    }

    private void printProduct(Product product) {
        System.out.println("-> Product: ");
        System.out.println("        id: " + product.getProductId());
        System.out.println("        name: " + product.getName());
        System.out.println("        price: " + product.getPrice());
        System.out.println("        category: " + product.getCatagory().getName());
    }

    void addProductToCart(Session session, CriteriaBuilder builder) {
        seeAllAvailableProducts(session, builder);
        System.out.println("Enter product number or enter 'EXIT; to cancel");
        String result = scanner.nextLine();
        Product product = productService.getOne(result);
        System.out.println("Enter qunatity you want to purchase: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (product.getQuantity() < quantity) {
            //throw exception
        } else {
            shoppingCart.put(product, quantity);
        }
    }

    void checkOutCart(Session session, Account account, int discount) { //todo what will be the discount
        Order order = createOrder(account, session);
        for (Map.Entry<Product, Integer> product : shoppingCart.entrySet()) {
            if (product.getKey().getQuantity() >= product.getValue()) {
                product.getKey().setQuantity(product.getKey().getQuantity() - product.getValue());
                productService.updateByID(product.getKey(), session);
                createOrderDetails(order, product.getKey(), product.getValue(), discount, session);
            } else {
                //throw exception
            }
        }
    }

    private Order createOrder(Account account, Session session) {
        String date = LocalDateTime.now().toString();
        Order order = new Order(date, account);
        return orderServices.createOne(order, session);
    }

    private OrderDetails createOrderDetails(Order order, Product product, int quantity, float discount, Session session) {
        float discountSum = quantity * product.getPrice() * discount;
        float sum = (quantity * product.getPrice()) - discountSum;
        OrderDetails orderDetails = new OrderDetails(order, product, quantity, discount, sum);
        return orderDetailsService.createOne(orderDetails, session);
    }
}
