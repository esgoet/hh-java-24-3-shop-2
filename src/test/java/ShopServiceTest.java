import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrdersWithStatusTest_whenNoOrdersWithStatusExist_returnEmptyList() {
        //GIVEN
        ShopService shopService = new ShopService();
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
        ShopService shopService = new ShopService();
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

    void updateOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order savedOrder = shopService.addOrder(productsIds);

        //WHEN
        Order actual = shopService.updateOrder(savedOrder.id(), OrderStatus.IN_DELIVERY);

        //THEN
        Order expected = savedOrder.withStatus(OrderStatus.IN_DELIVERY);
        assertEquals(actual, expected);

    }
}
