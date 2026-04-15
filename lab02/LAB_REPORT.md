# SE 3006: Lab 02 Report - Modular Monolith

---

## 1. Introduction
In Lab 01, we built a "Layered Architecture." It worked, but it was messy because of the layers. For example, the `OrderService` was reaching straight into the `ProductRepository`. 

For Lab 02, the goal was to fix that by moving to a **Modular Monolith**. This means we split the app into two clear "buckets": `catalog` and `orders`. The big rule here was that these buckets can’t see each other's contents, they can only talk through public interfaces.

---

## 2. The Main Goals
* **Information Hiding:** Use Java’s default access (package-private) so internal classes stay hidden inside their folders.
* **Interfaces Only:** Make sure the `orders` module doesn't know *how* `catalog` works, just that it has a `CatalogService` it can call.
* **Factory Pattern:** Use factories to build the modules so the `Main` class doesn't have to deal with the messy setup.

---

## 3. What I Actually Coded

### Task 1 & 2: The Catalog Module
I spent time in the `catalog` package setting up the `ProductRepository` and `CatalogServiceImpl`. 
* I wrote the `checkAndReduceStock` method to handle logic: find the product, check if there's enough left, and subtract the quantity. 
* I used `CatalogFactory` to "wire" the repository into the service. The factory returns a `CatalogService` interface, which keeps the actual implementation hidden from the rest of the app.

### Task 3 & 4: The Orders Module
In the `orders` package, I set up the `OrderService` to talk to the `CatalogService`. 
* When a user wants to buy something, the `OrderService` asks the catalog to check the stock first. 
* I updated the `OrderController` with a `try-catch` block. This is important because if the catalog says "no stock," the app doesn't crash—it just prints a nice "❌ ERROR" message.

---

## 4. How the "Wiring" Works (Factories)
Instead of creating objects manually in `Main.java`, I used the Factory pattern.
* `CatalogFactory.create()` builds the catalog.
* `OrdersFactory.create(catalog)` builds the orders module and plugs the catalog into it.
This keeps the `Main` class super clean and easy to read.

---

## 5. Testing results
I tested the system in `Main.java` with a couple of scenarios:
1.  **Buying 2 MacBooks:** Success! The stock dropped from 5 to 3.
2.  **Buying 5 Mice:** Success! The stock dropped from 20 to 15.
3.  **Invalid/Overstock:** When I tried to order more than what was available, the system caught the exception and printed the error message I wrote in the controller.

---

## 6. Conclusion
This lab showed me that while layered architecture is easy to start with, it gets messy fast. Modularizing the code makes it much more organized. Even though it’s still one single application (a monolith), it feels much more professional and easier to fix if something goes wrong in just one part.

---

## 7. Layered vs. Modular Monolith: What’s the difference?

This was the biggest takeaway for me. Here is how I see the difference now:



### Layered Architecture (Lab 01)
* **Horizontal Slices:** You split things by what they *do* (Presentation, Logic, Data).
* **Tight Coupling:** Any service can usually talk to any repository. It's like a big open-plan office where everyone is shouting at each other.
* **The Problem:** If you change the database structure, you might accidentally break five different services that were all touching that one repository.

### Modular Monolith (Lab 02)
* **Vertical Slices:** You split things by "Business Domain" (Catalog, Orders, Shipping).
* **Loose Coupling:** Each module is like its own little "black box." The `orders` module has no idea the `ProductRepository` even exists. It only knows about the `CatalogService` interface.
* **The Benefit:** It’s way cleaner. I can rewrite the entire `catalog` module from scratch, and as long as I don't change the `CatalogService` interface, the `orders` module will never even notice the change.