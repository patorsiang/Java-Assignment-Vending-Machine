Name: Napatchol Thaipanich
Login: nt375@kent.ac.uk

Program Description:
This project aims to model a vending machine using an object-oriented concept in Java, which comprises three main objects: a vending machine, a customer, and an admin, where the customer and admin share access to the same vending machine instance. The project supports both customer and admin roles with distinct functionality. An admin can add items, start/reset the machine, return to the idle state for maintenance, add coins, and withdraw coins using the admin object. Customers can insert coins, select items, request refunds (cancel state), request change, and collect coins and/or items using the customer object.

Classes and Interfaces:
1. Main
Initializes the vending machine, admin, and customer objects and simulates interactions to demonstrate the program’s functionality.
2. VendingMachine
The core class for managing vending machine operations is accessible by admin and customer roles through AdminAction and CustomerAction. It is a subclass of VendingMachineService that allows adding coins, withdrawing coins, adding items, resetting, and breaking the machine for maintenance.
3. VendingMachineService
Superclass provides additional utility methods (such as printShelf) for VendingMachine to display items. The methods in VendingMachineService are mainly for internal use and are not accessible directly by users.
4. Admin
Represents an admin user responsible for adding items with specified quantities, starting/resetting the machine, entering maintenance mode, adding coins, and withdrawing balance.
5. Customer
Represents a customer interacting with the vending machine; the customer can insert coins, select items, request refunds (cancel state), request change, and collect coins and/or items.
6. Item
Represents an item in the vending machine, assigning each item a unique code, name, and price.
7. Coin
Represents different denominations of coins the vending machine accepts, each with a predefined value in pounds or pence.
8. Exceptions (e.g., RefundedException, InsufficientSpareChangeCoinsException)
Custom exceptions to handle errors like insufficient change, out-of-stock items, and invalid machine states.
9. Constants
It contains static values used for mock demonstrations.
10. VendingMachineState
Defines different states of the vending machine, helping manage transitions in the machine’s lifecycle such as IDLE, READY, PURCHASING, PURCHASED, PURCHASED_COMPLETED, and CANCELED.
11. AdminAction
Lists actions available to the admin for managing the vending machine.
12. CustomerAction
Lists actions available to the customer for interacting with the vending machine.
13. User
Superclass for Admin and Customer, set up the exact construction and same attitude.
14. GeneralPrintService
It contains general printing methods unrelated to core vending machine functionality, such as printError.
