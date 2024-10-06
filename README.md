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
- [ ] Complete the code

## Object Class list

- [x] VendingMachine
  - [ ] state
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
title: Vending Machine
---
classDiagram

VendingMachine "1" o-- "0..*" Coin : accepts
VendingMachine "1" o-- "0..*" Item : contains

VendingMachine "1" <--> "0..*" Customer : returnChange/getChange
VendingMachine --* MachineState

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
    <<enumeration>>
    ONE_PENCE(0.01)
    TWO_PENCE(0.02)
    FIVE_PENCE(0.05)
    TEN_PENCE(0.10)
    TWENTY_PENCE(0.20)
    FIFTY_PENCE(0.50)
    ONE_POUND(1.00)
    TWO_POUNDS(2.00)
}

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
  HashMap~Coin,amount~ withdrawCoin()
  addCoin(Coin money, amount)
  fillItem(String code, amount)
  addNewItem(Item newItem)
}

class Item {
  String code
  String name
  float price
  int amount

  increase()
  decrease()
}

class VendingMachine {
  HashMap~Coin,amount~ coins
  List~Item~ items
  String selectedCode
  HashMap~Coin,amount~ holdCoins

  withdrawCoin()
  addNewItem(Item newItem)
  filledCoin(Coin money, amount)
  addNewContent()
  getShelfItem()
  setSelectedCode(String code)
  insertHoldCoins(Coin coin)
  returnChange(Float total, Float inserted)
  getHoldCoins()
  sellItem()
  sentItem()
  printState()
}

class MachineState

class InsufficientMoneyException
class InsufficientCoinsException
class OutStocksException
class InvalidItemCodeException

%% x > 99
class OutOfLimitOfContentsException
```
