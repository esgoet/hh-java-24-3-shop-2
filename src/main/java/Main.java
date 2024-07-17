import order.Order;
import order.OrderMapRepo;
import order.OrderRepo;
import order.OrderStatus;
import product.Product;
import product.ProductRepo;
import service.IdService;
import service.ShopService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        Order fruitOrder = shopService.addOrder(List.of("1", "2", "5"));
//        Order vegetableOrder = shopService.addOrder(List.of("3", "4", "7"));
//        Order fishOrder = shopService.addOrder(List.of("6"));
//        Order fruitAndVegOrder = shopService.addOrder(List.of("1","2","3","4"));
//
//        shopService.updateOrder(fruitOrder.id(), OrderStatus.IN_DELIVERY);
//        shopService.updateOrder(vegetableOrder.id(), OrderStatus.IN_DELIVERY);
//
//        System.out.println(shopService.getOrdersWithStatus(OrderStatus.PROCESSING));
//        System.out.println(shopService.getOldestOrderPerStatus());

        try {
            readFile(shopService);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void readFile(ShopService shopService) throws IOException {
        Map<String, Order> aliasMap = new HashMap<>();
        Files.lines(Path.of("transactions.txt")).forEach(line -> {
            String[] subString = line.split(" ");
            switch (subString[0]) {
                case "addOrder":
                    List<String> productIds = new ArrayList<>();
                    for (int i = 2; i < subString.length; i++) {
                        productIds.add(subString[i]);
                    }
                    Order newOrder = shopService.addOrder(productIds);
                    aliasMap.put(subString[1], newOrder);
                    break;
                case "setStatus":
                    shopService.updateOrder(aliasMap.get(subString[1]).id(), OrderStatus.valueOf(subString[2]));
                    break;
                case "printOrders":
                    System.out.println(shopService.getAllOrders());
            }
        });
    }
}
