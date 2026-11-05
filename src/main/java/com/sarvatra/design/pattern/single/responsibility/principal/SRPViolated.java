// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

package com.sarvatra.design.pattern.single.responsibility.principal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SRPViolated {

    @NoArgsConstructor
    @AllArgsConstructor
    static class Product {
        private String name;
        private double price;
    }

    @Slf4j
    @NoArgsConstructor
    static class ShoppingCart {
        private final List<Product> products = new ArrayList<>();

        private void addProduct(Product p) {
            products.add(p);
        }

        private List<Product> getProducts() {
            return products;
        }

        // 1. Calculates total price in cart.
        private double calculateTotal() {
            double total = 0;
            for (Product p : products) {
                total += p.price;
            }
            return total;
        }

        // 2. Violating SRP - Prints invoice (Should be in a separate class)
        private void printInvoice() {
            log.info("Shopping Cart Invoice:");
            for (Product p : products) {
                log.info("{} - Rs {}", p.name, p.price);
            }
            log.info("Total: Rs - {}", calculateTotal());
        }

        // 3. Violating SRP - Saves to DB (Should be in a separate class)
        private void saveToDatabase() {
            log.info("Saving shopping cart to database...");
        }
    }


    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(new Product("Laptop", 50000));
        cart.addProduct(new Product("Mouse", 2000));

        cart.printInvoice();
        cart.saveToDatabase();
    }

}


// 22:39:08.947 [main] INFO c.s.d.p.s.r.p.SRPViolated$ShoppingCart -- Shopping Cart Invoice:
// 22:39:08.948 [main] INFO c.s.d.p.s.r.p.SRPViolated$ShoppingCart -- Laptop - Rs 50000.0
// 22:39:08.949 [main] INFO c.s.d.p.s.r.p.SRPViolated$ShoppingCart -- Mouse - Rs 2000.0
// 22:39:08.949 [main] INFO c.s.d.p.s.r.p.SRPViolated$ShoppingCart -- Total: Rs - 52000.0
// 22:39:08.949 [main] INFO c.s.d.p.s.r.p.SRPViolated$ShoppingCart -- Saving shopping cart to database...


// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------