// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


package com.sarvatra.design.pattern.single.responsibility.principal;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


// Product Class Representing any item in eCommerce.
record Product(String name, double price) {
}


// 1. ShoppingCart: Only Responsible for Cart Related Business Logic.
@Getter
class ShoppingCart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
    }

    // Calculates total price in cart.
    public double calculateTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.price();
        }
        return total;
    }
}


// 2. ShoppingCartPrinter: Only Responsible for Printing Invoices
@Slf4j
@RequiredArgsConstructor
class ShoppingCartPrinter {
    private final ShoppingCart cart;

    public void printInvoice() {
        log.info("Shopping Cart Invoice:");
        for (Product p : cart.getProducts()) {
            log.info("{} - Rs {}", p.name(), p.price());
        }
        log.info("Total: Rs - {}", cart.calculateTotal());
    }
}


// 3. ShoppingCartStorage: Only Responsible for Saving Cart to DB
@Slf4j
@RequiredArgsConstructor
class ShoppingCartStorage {
    private final ShoppingCart cart;

    public void saveToDatabase() {
        log.info("Saving shopping cart to database...");
    }
}

public class SRPFollowed {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(new Product("Laptop", 50000));
        cart.addProduct(new Product("Mouse", 2000));

        ShoppingCartPrinter printer = new ShoppingCartPrinter(cart);
        printer.printInvoice();

        ShoppingCartStorage db = new ShoppingCartStorage(cart);
        db.saveToDatabase();
    }

}


// 22:47:44.534 [main] INFO c.s.d.p.s.r.p.ShoppingCartPrinter -- Shopping Cart Invoice:
// 22:47:44.536 [main] INFO c.s.d.p.s.r.p.ShoppingCartPrinter -- Laptop - Rs 50000.0
// 22:47:44.537 [main] INFO c.s.d.p.s.r.p.ShoppingCartPrinter -- Mouse - Rs 2000.0
// 22:47:44.537 [main] INFO c.s.d.p.s.r.p.ShoppingCartPrinter -- Total: Rs - 52000.0
// 22:47:44.537 [main] INFO c.s.d.p.s.r.p.ShoppingCartStorage -- Saving shopping cart to database...


// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------