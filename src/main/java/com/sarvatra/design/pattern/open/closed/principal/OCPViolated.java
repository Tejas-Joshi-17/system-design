// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


package com.sarvatra.design.pattern.open.closed.principal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

public class OCPViolated {

    @RequiredArgsConstructor
    static class Product {
        private final String name;
        private final double price;
    }

    // 1. ShoppingCart : Only Responsible for Cart Related Business Logic.
    static class ShoppingCart {
        private final List<Product> products = new ArrayList<>();

        void addProduct(Product p) {
            products.add(p);
        }

        List<Product> getProducts() {
            return products;
        }

        double calculateTotal() {
            double total = 0;
            for (Product p : products) {
                total += p.price;
            }
            return total;
        }
    }

    // 2. ShoppingCartPrinter : Only Responsible for Printing Invoices
    @Slf4j
    @RequiredArgsConstructor
    static class ShoppingCartPrinter {
        private final ShoppingCart cart;

        void printInvoice() {
            log.info("Shopping Cart Invoice:");
            for (Product p : cart.getProducts()) {
                log.info("{} - Rs {}", p.name, p.price);
            }
            log.info("Total: Rs {}", cart.calculateTotal());
        }
    }

    // 3. ShoppingCartStorage : Only Responsible for Saving Cart to DB
    @Slf4j
    @RequiredArgsConstructor
    static class ShoppingCartStorage {
        private final ShoppingCart cart;

        void saveToSQLDatabase() {
            log.info("Saving shopping cart to SQL DB...");
        }

        void saveToMongoDatabase() {
            log.info("Saving shopping cart to Mongo DB...");
        }

        void saveToFile() {
            log.info("Saving shopping cart to File...");
        }
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(new Product("Laptop", 50000));
        cart.addProduct(new Product("Mouse", 2000));

        ShoppingCartPrinter printer = new ShoppingCartPrinter(cart);
        printer.printInvoice();

        ShoppingCartStorage db = new ShoppingCartStorage(cart);
        db.saveToSQLDatabase();
        db.saveToMongoDatabase();
        db.saveToFile();
    }

}


// 23:39:03.516 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartPrinter -- Shopping Cart Invoice :
// 23:39:03.517 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartPrinter -- Laptop - Rs 50000.0
// 23:39:03.518 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartPrinter -- Mouse - Rs 2000.0
// 23:39:03.518 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartPrinter -- Total: Rs 52000.0
// 23:39:03.518 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartStorage -- Saving shopping cart to SQL DB...
// 23:39:03.518 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartStorage -- Saving shopping cart to Mongo DB...
// 23:39:03.518 [main] INFO c.s.d.p.o.c.p.OCPViolated$ShoppingCartStorage -- Saving shopping cart to File...


// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------