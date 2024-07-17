import order.Order;
import order.OrderMapRepo;
import order.OrderRepo;
import order.OrderStatus;
import product.Product;
import product.ProductRepo;
import service.IdService;
import service.ShopService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo shopRepo = new ProductRepo();
        String[] productNames = {"Banane", "Kürbis", "Zucchini", "Nektarine", "Fisch", "Broccoli"};
        for (int i = 0; i < productNames.length; i++) {
            Product newProduct = new Product(String.valueOf(i + 2), productNames[i]);
            shopRepo.addProduct(newProduct);
        }
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        Order fruitOrder = shopService.addOrder(List.of("1", "2", "5"));
        Order vegetableOrder = shopService.addOrder(List.of("3", "4", "7"));
        Order fishOrder = shopService.addOrder(List.of("6"));
        Order fruitAndVegOrder = shopService.addOrder(List.of("1","2","3","4"));

        shopService.updateOrder(fruitOrder.id(), OrderStatus.IN_DELIVERY);
        shopService.updateOrder(vegetableOrder.id(), OrderStatus.IN_DELIVERY);

        System.out.println(shopService.getOrdersWithStatus(OrderStatus.PROCESSING));
        System.out.println(shopService.getOldestOrderPerStatus());

    }
}
