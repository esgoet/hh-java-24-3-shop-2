package service;

import lombok.RequiredArgsConstructor;
import order.Order;
import order.OrderRepo;
import order.OrderStatus;
import product.Product;
import product.ProductRepo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            try {
                Product productToOrder = productRepo.getProductById(productId).orElseThrow();
                products.add(productToOrder);
            } catch (Exception e) {
                System.out.println("Exception: product.Product mit der Id \"" + productId + "\" konnte nicht bestellt werden! --> " + e.getMessage());
                return null;
            }
        }

        Order newOrder = new Order(idService.generateId().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersWithStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> order.status().equals(orderStatus)).toList();
    }

    public Order updateOrder(String orderId, OrderStatus status) {
        return orderRepo.addOrder(orderRepo.getOrderById(orderId).withStatus(status));
    }
}
