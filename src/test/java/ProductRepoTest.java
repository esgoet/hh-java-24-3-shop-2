import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @org.junit.jupiter.api.Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        assertEquals(actual, expected);
    }

    @org.junit.jupiter.api.Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        Product actual;
        if (repo.getProductById("1").isPresent()) {
            actual = repo.getProductById("1").get();
        } else {
            actual = null;
        }

        //THEN
        Product expected = new Product("1", "Apfel");
        assertEquals(actual, expected);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("2", "Banane");
        assertEquals(actual, expected);
        assertEquals(repo.getProductById("2").get(), expected);
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        assertTrue(repo.getProductById("1").isEmpty());
    }
}
