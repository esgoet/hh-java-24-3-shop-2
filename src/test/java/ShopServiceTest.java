import order.Order;
import order.OrderMapRepo;
import order.OrderRepo;
import order.OrderStatus;
import org.junit.jupiter.api.Test;
import product.Product;
import product.ProductRepo;
import service.IdService;
import service.ShopService;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, actual.timestamp());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrdersWithStatusTest_whenNoOrdersWithStatusExist_returnEmptyList() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.getOrdersWithStatus(OrderStatus.COMPLETED);

        //THEN
        assertTrue(actual.isEmpty());
    }

    @Test
    void getOrdersWithStatusTest_whenGivenStatus_thenOnlyReturnOrdersWithGivenStatus() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");
        Order processingOrder = shopService.addOrder(productsIds);
        Order inDeliveryOrder = shopService.addOrder(productsIds);
        inDeliveryOrder = shopService.updateOrder(inDeliveryOrder.id(),OrderStatus.IN_DELIVERY);
        Order completedOrder = shopService.addOrder(productsIds);
        completedOrder = shopService.updateOrder(completedOrder.id(),OrderStatus.COMPLETED);

        //WHEN
        List<Order> actual = shopService.getOrdersWithStatus(OrderStatus.COMPLETED);

        //THEN
        assertEquals(List.of(completedOrder), actual);
    }

    @Test
    void updateOrderTest() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");
        Order savedOrder = shopService.addOrder(productsIds);

        //WHEN
        Order actual = shopService.updateOrder(savedOrder.id(), OrderStatus.IN_DELIVERY);

        //THEN
        Order expected = savedOrder.withStatus(OrderStatus.IN_DELIVERY);
        assertEquals(actual, expected);

    }

    @Test
    void getOldestOrderPerStatusTest_whenTwoOrdersWithSameStatus_thenReturnOldest() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");
        Order firstOrder = shopService.addOrder(productsIds);
        Order secondOrder = shopService.addOrder(productsIds);

        //WHEN
        Instant actual = shopService.getOldestOrderPerStatus().get(OrderStatus.PROCESSING).timestamp();
        Instant expected = firstOrder.timestamp();

        //THEN
        assertEquals(expected, actual);
        assertTrue(actual.isBefore(secondOrder.timestamp()));
    }

    @Test
    void getOldestOrderPerStatusTest_whenNoOrderWithStatus_thenReturnNull() {
        //GIVEN
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(shopRepo, orderRepo, idService);

        //WHEN
        Order actual = shopService.getOldestOrderPerStatus().get(OrderStatus.PROCESSING);

        //THEN
        assertNull(actual);
    }
}
