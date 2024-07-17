import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo shopRepo = new ProductRepo();
        String[] productNames = {"Banane", "Kürbis", "Zucchini", "Nektarine", "Fisch", "Broccoli"};
        for (int i = 0; i < productNames.length; i++) {
            Product newProduct = new Product(String.valueOf(i+2), productNames[i]);
            shopRepo.addProduct(newProduct);
        }
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(shopRepo, orderRepo);
        Order fruitOrder = shopService.addOrder(List.of("1","2","5"));
        Order vegetableOrder = shopService.addOrder(List.of("3","4","7"));
        Order fishOrder = shopService.addOrder(List.of("6"));

        System.out.println(shopService.getOrdersWithStatus(OrderStatus.PROCESSING));

    }
}
