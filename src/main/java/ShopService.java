import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            try {
                Product productToOrder = productRepo.getProductById(productId).orElseThrow();
                products.add(productToOrder);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                return null;
            }
//            if (productRepo.getProductById(productId).isPresent()) {
//                products.add(productRepo.getProductById(productId).get());
//            } else {
//                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
//                return null;
//            }
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersWithStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> order.status().equals(orderStatus)).toList();
    }

    public Order updateOrder(String orderId, OrderStatus status) {
        return orderRepo.addOrder(orderRepo.getOrderById(orderId).withStatus(status));
    }
}
