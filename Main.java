import java.util.*;
import java.io.*;

// ======================================================
// CO4 + CO5 : OOP + Inheritance
// ======================================================

// CO4 Basic Class
class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

// CO5 Inheritance + Polymorphism
class DiscountedProduct extends Product {
    double discountPercent;

    DiscountedProduct(String name, double price, double discountPercent) {
        super(name, price);
        this.discountPercent = discountPercent;
    }

    double getDiscountedPrice() {
        return price - (price * discountPercent / 100);
    }
}

// Cart Item Class
class CartItem {
    Product product;
    int qty;

    CartItem(Product p, int qty) {
        this.product = p;
        this.qty = qty;
    }

    double getTotal() {
        if (product instanceof DiscountedProduct dp) {
            return dp.getDiscountedPrice() * qty;
        }
        return product.price * qty;
    }
}

// ======================================================
// MAIN PROGRAM = CO1 → CO6 MIXED ORDERWISE
// ======================================================

public class Main {

    // ⭐ NEW FEATURE: View, Delete, Update Cart
    public static void manageCart(ArrayList<CartItem> cart, Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        while (true) {
            System.out.println("\n===== YOUR CART =====");
            for (int i = 0; i < cart.size(); i++) {
                CartItem c = cart.get(i);
                System.out.println((i + 1) + ". " + c.product.name + " x " + c.qty);
            }

            System.out.println("\n1. Delete an item");
            System.out.println("2. Update quantity");
            System.out.println("3. Back to Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter item number to delete: ");
                int del = sc.nextInt();

                if (del < 1 || del > cart.size()) {
                    System.out.println("Invalid item!");
                } else {
                    cart.remove(del - 1);
                    System.out.println("Item removed.");
                }

            } else if (choice == 2) {
                System.out.print("Enter item number to update: ");
                int up = sc.nextInt();

                if (up < 1 || up > cart.size()) {
                    System.out.println("Invalid item!");
                } else {
                    System.out.print("Enter new quantity: ");
                    int newQty = sc.nextInt();

                    if (newQty <= 0) {
                        System.out.println("Invalid quantity!");
                    } else {
                        cart.get(up - 1).qty = newQty;
                        System.out.println("Quantity updated.");
                    }
                }

            } else if (choice == 3) {
                return;
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<CartItem> cart = new ArrayList<>();
        char cont = 'y';

        System.out.println("===== ONLINE ORDER SYSTEM =====");

        // Grocery categories
        Product[] fruits = {
            new Product("Apple", 120),
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

        while (cont == 'y' || cont == 'Y') {

            System.out.println("\nChoose Category:");
            System.out.println("1. Fruits");
            System.out.println("2. Dairy");
            System.out.println("3. Snacks");
            System.out.println("4. View/Modify Cart");
            System.out.println("5. Checkout");
            System.out.print("Enter choice: ");

            int ch;

            try {
                ch = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input");
                sc.next();
                continue;
            }

            if (ch == 5)
                break;

            if (ch == 4) {
                manageCart(cart, sc);
                continue;
            }

            Product[] category = null;

            if (ch == 1)
                category = fruits;
            else if (ch == 2)
                category = dairy;
            else if (ch == 3)
                category = snacks;
            else {
                System.out.println("Invalid option");
                continue;
            }

            System.out.println("\nSelect Item:");
            for (int i = 0; i < category.length; i++) {
                System.out.println((i + 1) + ". " + category[i].name + " - Rs " + category[i].price);
            }

            System.out.print("Enter item no: ");
            int item = sc.nextInt();

            if (item < 1 || item > category.length) {
                System.out.println("Invalid item selected!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            if (qty <= 0) {
                System.out.println("Invalid quantity!");
                continue;
            }

            // Discount logic
            System.out.print("Any discount? (yes/no): ");
            String disc = sc.next();

            if (disc.equalsIgnoreCase("yes")) {

                System.out.print("Enter discount % (0-100): ");
                double d = sc.nextDouble();

                if (d < 0 || d > 100) {
                    System.out.println("Invalid discount! Must be 0–100.");
                    continue;
                }

                cart.add(new CartItem(new DiscountedProduct(category[item - 1].name,
                        category[item - 1].price, d), qty));
            } else {
                cart.add(new CartItem(category[item - 1], qty));
            }

            System.out.print("Add more items? (y/n): ");
            cont = sc.next().charAt(0);
        }

        // UNIQUE BILL ID
        String billID = "BILL-" + System.currentTimeMillis();

        double total = 0;
        System.out.println("\n===== FINAL BILL =====");
        System.out.println("Bill ID: " + billID);

        for (CartItem c : cart) {
            double t = c.getTotal();
            System.out.println(c.product.name + " x " + c.qty + " = Rs " + t);
            total += t;
        }

        System.out.println("Total Amount: Rs " + total);

        System.out.print("\nSave bill to file? (yes/no): ");
        String save = sc.next();

        if (save.equalsIgnoreCase("yes")) {
            try {
                FileWriter fw = new FileWriter("bill.txt");
                fw.write("===== FINAL BILL =====\n");
                fw.write("Bill ID: " + billID + "\n");

                for (CartItem c : cart) {
                    fw.write(c.product.name + " x " + c.qty + " = Rs " + c.getTotal() + "\n");
                }

                fw.write("Total Amount: Rs " + total);
                fw.close();

                System.out.println("Bill saved to bill.txt successfully!");
            } catch (IOException e) {
                System.out.println("Error saving bill: " + e.getMessage());
            }
        }

        System.out.println("\nThank you for using the Online Order System!");
    }
}
