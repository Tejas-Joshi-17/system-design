// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


package com.sarvatra.design.pattern.open.closed.principal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// Product Class Representing any item in eCommerce.
record Product(String name, double price) {
}

// 1. ShoppingCart : Only Responsible for Cart Related Business Logic.
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

// 2. ShoppingCartPrinter : Only Responsible for Printing Invoices
@Slf4j
@RequiredArgsConstructor
class ShoppingCartPrinter {
    private final ShoppingCart cart;

    public void printInvoice() {
        log.info("Shopping Cart Invoice:");
        for (Product p : cart.getProducts()) {
            log.info("{} - Rs {}", p.name(), p.price());
        }
        log.info("Total: Rs {}", cart.calculateTotal());
    }
}

interface Persistence {
    Logger log = LoggerFactory.getLogger(Persistence.class);

    void save(ShoppingCart cart);
}

class SQLPersistence implements Persistence {
    @Override
    public void save(ShoppingCart cart) {
        log.info("Saving shopping cart to SQL DB...");
    }
}

class MongoPersistence implements Persistence {
    @Override
    public void save(ShoppingCart cart) {
        log.info("Saving shopping cart to MongoDB...");
    }
}

class FilePersistence implements Persistence {
    @Override
    public void save(ShoppingCart cart) {
        log.info("Saving shopping cart to a file...");
    }
}

public class OCPFollowed {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product("Laptop", 50000));
        cart.addProduct(new Product("Mouse", 2000));

        ShoppingCartPrinter printer = new ShoppingCartPrinter(cart);
        printer.printInvoice();

        Persistence db    = new SQLPersistence();
        Persistence mongo = new MongoPersistence();
        Persistence file  = new FilePersistence();

        db.save(cart);    // Save to SQL database
        mongo.save(cart); // Save to MongoDB
        file.save(cart);  // Save to File
    }

}


// 23:47:35.420 [main] INFO c.s.d.p.o.c.p.ShoppingCartPrinter -- Shopping Cart Invoice :
// 23:47:35.421 [main] INFO c.s.d.p.o.c.p.ShoppingCartPrinter -- Laptop - Rs 50000.0
// 23:47:35.422 [main] INFO c.s.d.p.o.c.p.ShoppingCartPrinter -- Mouse - Rs 2000.0
// 23:47:35.422 [main] INFO c.s.d.p.o.c.p.ShoppingCartPrinter -- Total: Rs 52000.0
// 23:47:35.422 [main] INFO c.s.d.p.o.c.p.Persistence -- Saving shopping cart to SQL DB...
// 23:47:35.422 [main] INFO c.s.d.p.o.c.p.Persistence -- Saving shopping cart to MongoDB...
// 23:47:35.422 [main] INFO c.s.d.p.o.c.p.Persistence -- Saving shopping cart to a file...


// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------