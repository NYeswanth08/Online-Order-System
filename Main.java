import java.util.*;
import java.io.*;

// ======================================================
// CO4 + CO5 : OOP + Inheritance (classes are CO4/CO5)
// ======================================================

// CO4: Product is a basic class (constructor, fields)
class Product {                      // CO4: class, constructor, encapsulation concept
    String name;                     // CO4: fields (data members)
    double price;

    Product(String name, double price) { // CO4: constructor
        this.name = name;
        this.price = price;
    }
}

// CO5: DiscountedProduct demonstrates inheritance & specialized behavior
class DiscountedProduct extends Product { // CO5: inheritance (extends)
    double discountPercent;               // CO5: additional field in subclass

    DiscountedProduct(String name, double price, double discountPercent) {
        super(name, price);               // CO5: reuse superclass constructor
        this.discountPercent = discountPercent;
    }

    double getDiscountedPrice() {         // CO5: specialized behavior (polymorphism idea)
        return price - (price * discountPercent / 100);
    }
}

// CO4: CartItem groups product + quantity and provides method (encapsulation + method)
class CartItem {                         // CO4: class for cart item
    Product product;
    int qty;

    CartItem(Product p, int qty) {       // CO4: constructor
        this.product = p;
        this.qty = qty;
    }

    // CO5 (runtime polymorphism) used inside this method via instanceof check
    double getTotal() {
        if (product instanceof DiscountedProduct dp) { // CO5: runtime type check
            return dp.getDiscountedPrice() * qty;     // CO5: discounted price applied
        }
        return product.price * qty;                    // CO4: normal product price
    }
}

// ======================================================
// MAIN PROGRAM = CO1 → CO6 MIXED ORDERWISE
// ======================================================

public class Main {

    // ==================================================
    // CO2 + CO1: cart management uses ArrayList (CO2 arrays/collections)
    // CO6: validate inputs with checks (exception handling in main loop too)
    // ==================================================
    public static void manageCart(ArrayList<CartItem> cart, Scanner sc) {
        if (cart.isEmpty()) {                 // CO1: decision making (if)
            System.out.println("Cart is empty!");
            return;
        }

        while (true) {                        // CO1: iterative constructs (loop)
            System.out.println("\n===== YOUR CART =====");
            for (int i = 0; i < cart.size(); i++) { // CO2: traversal of ArrayList
                CartItem c = cart.get(i);
                System.out.println((i + 1) + ". " + c.product.name + " x " + c.qty);
            }

            System.out.println("\n1. Delete an item");
            System.out.println("2. Update quantity");
            System.out.println("3. Back to Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();       // CO1: input

            if (choice == 1) {               // CO1: decision
                System.out.print("Enter item number to delete: ");
                int del = sc.nextInt();

                if (del < 1 || del > cart.size()) { // CO1+CO6: validation + error handling logic
                    System.out.println("Invalid item!");
                } else {
                    cart.remove(del - 1);    // CO2: modifying ArrayList
                    System.out.println("Item removed.");
                }

            } else if (choice == 2) {
                System.out.print("Enter item number to update: ");
                int up = sc.nextInt();

                if (up < 1 || up > cart.size()) { // CO6: validate index
                    System.out.println("Invalid item!");
                } else {
                    System.out.print("Enter new quantity: ");
                    int newQty = sc.nextInt();

                    if (newQty <= 0) {      // CO1: validation
                        System.out.println("Invalid quantity!");
                    } else {
                        cart.get(up - 1).qty = newQty; // CO2: update ArrayList element
                        System.out.println("Quantity updated.");
                    }
                }

            } else if (choice == 3) {
                return;                      // CO1: control flow
            }
        }
    }

    public static void main(String[] args) {

        // CO1: Scanner input, variables, loop control
        Scanner sc = new Scanner(System.in);     // CO1: input handling
        ArrayList<CartItem> cart = new ArrayList<>(); // CO2: Collections (ArrayList)
        char cont = 'y';

        System.out.println("===== ONLINE ORDER SYSTEM ====="); // CO3: String output / formatting

        // ======================================================
        // CO2: 1D arrays to store categories (array usage & traversal)
        // ======================================================
        Product[] fruits = {
            new Product("Apple", 120),    // CO4: object creation
            new Product("Banana", 50),
            new Product("Grapes", 80)
        };

        Product[] dairy = {
            new Product("Milk", 60),
            new Product("Cheese", 110),
            new Product("Paneer", 90)
        };

        Product[] snacks = {
            new Product("Chips", 20),
            new Product("Cookies", 40),
            new Product("Chocolate", 70)
        };

        // ======================================================
        // START ORDER LOOP: CO1 (loops, decisions), CO2 (arrays), CO3 (strings)
        // ======================================================
        while (cont == 'y' || cont == 'Y') {   // CO1: loop for menu repetition

            System.out.println("\nChoose Category:"); // CO3: formatted output
            System.out.println("1. Fruits");
            System.out.println("2. Dairy");
            System.out.println("3. Snacks");
            System.out.println("4. View/Modify Cart");
            System.out.println("5. Checkout");
            System.out.print("Enter choice: ");

            int ch;

            try {
                ch = sc.nextInt();              // CO1: input, CO6: try-catch protects this block
            } catch (Exception e) {
                System.out.println("Invalid input"); // CO6: basic exception handling feedback
                sc.next();
                continue;
            }

            if (ch == 5)                         // CO1: exit condition
                break;

            if (ch == 4) {                       // CO1 + CO2: manage cart option uses ArrayList ops
                manageCart(cart, sc);            // CO4: calling modular method
                continue;
            }

            Product[] category = null;

            if (ch == 1)                          // CO1: selection logic
                category = fruits;               // CO2: reference to array
            else if (ch == 2)
                category = dairy;
            else if (ch == 3)
                category = snacks;
            else {
                System.out.println("Invalid option"); // CO1: invalid option handling
                continue;
            }

            // CO3: display items (string formatting + array traversal)
            System.out.println("\nSelect Item:");
            for (int i = 0; i < category.length; i++) { // CO2: array traversal
                System.out.println((i + 1) + ". " + category[i].name + " - Rs " + category[i].price);
            }

            System.out.print("Enter item no: ");
            int item = sc.nextInt();              // CO1: input

            if (item < 1 || item > category.length) { // CO6: validate input range
                System.out.println("Invalid item selected!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();               // CO1: input

            if (qty <= 0) {                       // CO1: validation
                System.out.println("Invalid quantity!");
                continue;
            }

            // ======================================================
            // CO5: inheritance used to handle discounted product option
            // CO3: string handling of "yes/no"
            // ======================================================
            System.out.print("Any discount? (yes/no): ");
            String disc = sc.next();

            if (disc.equalsIgnoreCase("yes")) {

                System.out.print("Enter discount % (0-100): ");
                double d = sc.nextDouble();

                if (d < 0 || d > 100) {         // CO6: validate discount range (error handling)
                    System.out.println("Invalid discount! Must be 0–100.");
                    continue;
                }

                // CO5: create DiscountedProduct (subclass) and add to cart (CO2: ArrayList)
                cart.add(new CartItem(new DiscountedProduct(category[item - 1].name,
                        category[item - 1].price, d), qty));
            } else {
                // CO4/CO2: add normal Product wrapped in CartItem
                cart.add(new CartItem(category[item - 1], qty));
            }

            System.out.print("Add more items? (y/n): ");
            cont = sc.next().charAt(0);           // CO1: input to control loop
        }

        // ======================================================
        // CO1+CO3: Output generation, CO6: creating unique bill ID (no DB)
        // ======================================================
        String billID = "BILL-" + System.currentTimeMillis(); // CO6: lightweight unique ID (no DB required)

        double total = 0;
        System.out.println("\n===== FINAL BILL ====="); // CO3: output formatting
        System.out.println("Bill ID: " + billID);       // CO3: show bill id

        // CO2: traverse ArrayList to compute totals (algorithmic logic)
        for (CartItem c : cart) {
            double t = c.getTotal();                     // CO4/CO5: uses getTotal() which may apply discount
            System.out.println(c.product.name + " x " + c.qty + " = Rs " + t);
            total += t;                                  // CO1: arithmetic / accumulation
        }

        System.out.println("Total Amount: Rs " + total);

        // ======================================================
        // CO6: File I/O (save bill to file) + exception handling
        // ======================================================
        System.out.print("\nSave bill to file? (yes/no): ");
        String save = sc.next();

        if (save.equalsIgnoreCase("yes")) {
            try {
                FileWriter fw = new FileWriter("bill.txt"); // CO6: File I/O
                fw.write("===== FINAL BILL =====\n");
                fw.write("Bill ID: " + billID + "\n");

                for (CartItem c : cart) {
                    fw.write(c.product.name + " x " + c.qty + " = Rs " + c.getTotal() + "\n");
                }

                fw.write("Total Amount: Rs " + total);
                fw.close();

                System.out.println("Bill saved to bill.txt successfully!"); // CO6: success message
            } catch (IOException e) {
                System.out.println("Error saving bill: " + e.getMessage()); // CO6: exception feedback
            }
        }

        System.out.println("\nThank you for using the Online Order System!"); // CO3: final output
    }
}


