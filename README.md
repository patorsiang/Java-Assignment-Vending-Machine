# Java-Assignment-Vending-Machine

This is created for recording the 1st assignment for COMP8710 A1 2024/2025 at the University of Kent.

## Deadline

Wednesday, 6 November 2024, 11:55 PM

## Checklist

- [ ] Functionality of your submission [50%]
  - [ ] support all the requirements
  - [ ] compile and run successfully
  - [ ] perform correctly without crashing with an uncaught exception
- [ ] Object-oriented design [20%] -> how well you designed your classes and interfaces
- [ ] Testing [20%] -> main() method should test all the assignment requirements
  - [ ] create a vending machine
  - [ ] an admin add items and coins to it;
  - [ ] a customer to insert money, purchase items, etc.;
  - [ ] an admin to stock items, withdraw money, etc.. Every step of the way,
  - [ ] *** print what is happening to the terminal, and at the end, print the state of the vending machine.
- [ ] Code quality [10%]
  - [ ] clean and easy to understand
  - [ ] comments and documentation

## Task list

- [x] list Object class
- [x] OOP design
- [/] Complete the code
  - [x] Coin (Enumeration)
  - [ ] CustomerActions (Interface)
  - [ ] AdminActions (Interface)
  - [ ] Customer
  - [ ] Admin
  - [x] Item
  - [ ] VendingMachine (Service)
  - [ ] MachineState (Service)
  - [ ] InsufficientMoneyException
  - [ ] InsufficientCoinsException
  - [ ] OutStocksException
  - [ ] InvalidItemCodeException

## Object Class list

- [x] VendingMachine
  - [x] state
  - [x] return remaining change to a user
- [x] Coin -> Enumeration
  - [x] 2 pounds
  - [x] 1 pound
  - [x] 50 pence
  - [x] 20 pence
  - [x] 10 pence
  - [x] 5 pence
  - [x] 1 penny
- [x] Item
  - [x] code
  - [x] name
  - [x] price
- [x] User
  - [x] purchase item
  - [x] deposit money
  - [x] withdraw the choice (if depositing enough money)
  - [x] cancel their purchase -> withdraw the money the they deposit
- [x] Admin
  - [x] add new item
  - [x] add new content
  - [x] deposit money
  - [x] withdraw money

## OOP design

```mermaid
---
title: Vending Machine System
---

classDiagram
VendingMachine "1" o-- "0..*" Coin : accepts
VendingMachine "1" o-- "0..*" Item : contains

VendingMachine "1" <--> "0..*" Customer : returnChange/getChange
VendingMachine "1" *-- MachineState : manages

Exception --|> InsufficientMoneyException
Exception --|> InsufficientCoinsException
Exception --|> OutStocksException
Exception --|> InvalidItemCodeException
Exception --|> OutOfLimitOfContentsException

Customer "1" --> "0..*" Coin : inserts | withdraws
Customer "1" --> "0..1" Item : select | withdraw | cancel

Admin "1" --> "0..*" Coin : fill | withdraws
Admin "1" --> "0..1" Item : fill | addNew

class Coin {
    <<Enumeration>>
    ONE_PENCE(0.01)
    TWO_PENCE(0.02)
    FIVE_PENCE(0.05)
    TEN_PENCE(0.10)
    TWENTY_PENCE(0.20)
    FIFTY_PENCE(0.50)
    ONE_POUND(1.00)
    TWO_POUNDS(2.00)
}

class CustomerActions {
    <<Interface>>
    insertCoin(Coin coin)
    getCurrentBalance()
    selectItem(String code)
    getItemCode()
    requestRefund()
    requestPurchaseItem()
    requestChange()
    collect()
}

class AdminActions {
    <<Interface>>
    +addCoin(Coin coin, int quantity)
    +withdrawCoins()
    +addItem(Item item)
    +restockItem(String code, int quantity)
}

VendingMachine ..|> CustomerActions
VendingMachine ..|> AdminActions
Customer ..|> CustomerActions
Admin ..|> AdminActions

class Customer {
  String selectedCode
  HashMap~Coin, number~ insertedCoin

  insertCoin(Coin coin)
  getCurrentBalance()
  selectItem(String code)
  getItemCode()
  requestRefund()
  requestPurchaseItem()
  requestChange()
  collect()
}

class Admin {
  addCoin(Coin coin, int quantity)
  HashMap~Coin,quantity~ withdrawCoins()
  addItem(Item item)
  restockItem(String code, int quantity)
}

class Item {
  String code
  String name
  double price
  int quantity

  increase()
  decrease()
}

class VendingMachine {
  <<Service>>
  HashMap~Coin,quantity~ coins
  List~Item~ items
  String selectedCode
  HashMap~Coin,quantity~ holdCoins

  withdrawCoin()
  addNewItem(Item newItem)
  filledCoin(Coin money, quantity)
  addNewContent()
  getShelfItem()
  setSelectedCode(String code)
  insertHoldCoins(Coin coin)
  returnChange(double total, double inserted)
  getHoldCoins()
  sellItem()
  sentItem()
  printState()
  validateItemCode(String code) : throws InvalidItemCodeException
  validateCapacity() : throws OutOfLimitOfContentsException
}

class MachineState {
    <<Service>>
    +String state
    +getState()
    +setState(String newState)
    +transitionToIdle()
    +transitionToWaitingForSelection()
    +transitionToDispensingItem()
    +isWaitingForSelection()
    +isDispensingItem()
    +checkForInsufficientMoney() : throws InsufficientMoneyException
    +checkForOutOfStock() : throws OutStocksException
    +checkForInsufficientCoinsForChange() : throws InsufficientCoinsException
}

class InsufficientMoneyException
class InsufficientCoinsException
class OutStocksException
class InvalidItemCodeException

%% x > 99
class OutOfLimitOfContentsException

MachineState --> InsufficientMoneyException : checks
MachineState --> InsufficientCoinsException : checks
MachineState --> OutStocksException : checks
VendingMachine --> InvalidItemCodeException : validates
VendingMachine --> OutOfLimitOfContentsException : validates

```

## Note

- mast have
  - user can user coin
    - (additional) user can use cash
  - content
    - drinks
    - (additional) Items has type -> drinks, snack
    - capacity (should set as constant) -> and show the error message where they reached the limit
  - error state
  - class should prevent invalid actions
- (additional) means you can add for making it more realistic
- print every state and sign even through errors
  - e.g. add content

  ```cmd
    You want to add new items?
    >>> Yes
    You need to add input code in the pattern of 2 digits of number, name, and price (<dd>, <string>, <price>)
    >>>
  ```

### to check that the program is working

- zip
- unzip
- run
- submit
- download
- unzip
- run

## Plan to test

### Test Case

- [x] initialize Vending Machine (1)
- [x] initialize Admin (2)
- [x] initialize Customer (3)
- For Admin
  - add new items
    - case:
      - [ ] successful (4)
      - failed
        - [ ] permission denied (5)
        - [ ] negative amount (6)
    - update variables:
      - spare coins
      - machine balance
  - withdraw coins
    - case:
      - [ ] successful (7)
      - failed
        - [ ] permission denied (8)
        - [ ] inappropriate state (9)
    - update variables:
      - machine balance should be 0
      - spare coins should be empty
  - add new items
    - case:
      - [ ] successful (10)
      - failed
        - [ ] permission denied (11)
        - [ ] duplicate item code (12)
        - [ ] negative amount (13)
        - [ ] over the limit of the vending machine capacity (14)
    - update variables:
      - remainingCapacity
      - shelf
      - codToItemMap
  - start or reset
    - case:
      - [ ] successful (15)
      - failed
        - [ ] permission denied (16)
        - [ ] state is not idle, ready, or purchased_complete (17)
        - [ ] currentBalance is not zero (18)
    - update variables:
      - customerBalance = 0
      - currentBalance = 0
      - customerCoins empty
      - returnCoins empty
      - selectedItem = null
      - returnItem = null
  - break to maintenance
    - case:
      - [ ] successful (19)
      - failed:
        - [ ] permission denied (20)
        - [ ] state is not idle or ready (21)
    - update variables:
      - state = idle
- For Customer
  - insert Coin
    - case:
      - [ ] successful (22)
      - failed:
        - [ ] state is not ready or purchasing (23)
      - update variables:
        - currentBalance
        - customerBalance
        - customerCoins
        - state
  - select Item
    - case:
      - [ ] successful (24)
      - failed:
        - [ ] state is not ready or purchasing (25)
        - [ ] item is not found (26)
        - [ ] item is out of stock (27)
    - update variables:
      - currentBalance
      - selectedItem
      - state
  - request Refund
    - case:
      - [ ] successful (28)
      - failed:
        - [ ] state is purchasing (29)
    - update variables:
      - returnCoins
      - customerCoins
      - state
  - requestPurchaseItem
    - case:
      - successful
        - [ ] enough money (30)
        - [ ] over money (31)
      - failed:
        - [ ] haven't selected item yet (32)
        - [ ] not enough money (33)
    - update variables:
      - state
      - shelf
      - customerCoins is empty
      - spareCoins
      - machineBalance
  - requestChange
    - case:
      - successful
        - [ ] No change (34)
        - [ ] enough money to give change to a customer (35)
      - failed:
        - [ ] not enough money to give change to a customer (36)
    - update variables:
      - currentBalance
      - returnCoins
      - spareCoins
  - collect
    - case:
      - [ ] successful (37)
      - failure:
        - [ ] inappropriate state (38)
        - [ ] balance is not 0 (39)
    - update variables:
      - state
      - customerBalance
      - currentBalance
      - customerCoins
      - returnCoins
      - selectedItem
      - returnItem
