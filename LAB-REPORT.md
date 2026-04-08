Lab Report: Building a Layered Java App

1. The Goal
The point of this lab was to build a software system using three distinct "layers" (Presentation, Business, and Data). I focused on **Constructor Injection**, which is a way of connecting these layers manually to see how data flows from one to the other without using fancy frameworks.

2. How I Built It
I built the project from the ground up, making sure each layer only talked to the one directly below it.



* Step 1: The Data Layer (`ProductRepository.java`)
    I used a `HashMap` to act as a fake database. This part of the code is only responsible for finding products and saving updates.
* Step 2: The Logic Layer (`OrderService.java`)
    I "injected" the Data Layer here so the service could use it. I wrote the code that checks if we have enough items in stock. If we do, it subtracts the amount; if not, it sends back an error.
* Step 3: The Entry Point (`OrderController.java`)
    This layer handles user requests. I used a `try-catch` block so that if the logic layer finds an error (like being out of stock), the app displays a neat message instead of crashing.
* Step 4: Putting it Together (`Main.java`)
    I manually connected everything in order: I created the Repository, then the Service, then the Controller.


3. Testing the Code
I ran three main tests to make sure the logic was solid:

| Item | Starting Stock | Ordered | Result                                     |
| MacBook Pro | 5 | 2 | **Success** (Stock updated to 3)                   |
| Logitech Mousr | 20 | 5 | **Success** (Stock updated to 15)              |
| Over-order Test | Various | Too many | **Blocked** (Error message shown) |

4. What I Learned
This lab helped me understand why we keep code separated. By giving each part of the app its own specific job, the code becomes much cleaner. Because I connected the parts manually, I could clearly see how data travels through the system and how to handle errors properly at the top level.