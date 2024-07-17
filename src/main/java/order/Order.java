package order;

import lombok.With;
import product.Product;

import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus status,
        Instant timestamp
) {
}
