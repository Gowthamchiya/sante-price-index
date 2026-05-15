# Sante Price Index

## Overview

Sante Price Index is an Android application developed for small village market vendors (Santes).

The application helps vendors calculate fair selling prices using mandi prices, transport cost, waste cost, and profit margin. It also provides a live digital price board for customers and real-time product updates using Firebase Firestore.

---

# Problem Statement

Small vendors in weekly village markets usually buy vegetables and products from city Mandis but often do not know the correct retail selling price.

Because of this:

- Vendors may sell products too cheaply and lose profit
- Vendors may sell products too expensively and lose customers

Sante Price Index solves this problem by helping vendors calculate a fair retail price using live market data and pricing formulas.

---

# Features

- Live Market Product Prices
- Add Product Prices
- Edit Product Prices
- Delete Product Prices
- Firebase Realtime Sync
- Profit Calculator
- Fair Selling Price Calculation
- Digital Price Board
- Product Trend Analysis
- Admin PIN Protection
- Vendor Profile Section
- Kannada / English Language Option
- Professional Dark UI Design

---

# Technologies Used

## Frontend

- Kotlin
- Jetpack Compose
- Material 3
- Android Studio

## Backend

- Firebase Firestore
- Firebase SDK

## Version Control

- Git
- GitHub

---

# Formula Used

```text
Recommended Retail Price (RRP) =
Mandi Price + Transport Cost + Waste Cost + Profit Margin
```

---

# Product Trend Rule

```text
If Product Price >= ₹50 → UP Trend
If Product Price < ₹50 → DOWN Trend
```

This helps vendors quickly identify high-value and low-value products.

---

# Screens Included

## Dashboard
- Market overview
- Inventory summary
- Product list
- Edit/Delete options

## Price Watch
- Live product prices
- Product trends
- Search products

## Profit Calculator
- Calculates fair selling price
- Calculates net profit
- Calculates gross sales

## Digital Slate Board
- Customer-facing full-screen price board
- High contrast UI

## Profile Screen
- Vendor information
- Language option
- Edit profile details

---

# Firebase Features

- Live Product Storage
- Realtime Cloud Sync
- Shared Product Data
- Automatic UI Updates

---

# Admin Security

Sensitive operations are protected using Admin PIN authentication.

Protected Actions:
- Add Product
- Edit Product
- Delete Product

## Admin PIN

```text
1234
```

---

# Project Goal

- Support small vendors
- Improve fair pricing
- Increase vendor profit awareness
- Help customers get fair prices
- Promote data-driven pricing

---

# Future Improvements

- AI-based price prediction
- Voice support
- Barcode support
- Sales analytics
- Advanced multi-language support

---

# Folder Structure

```text
app
 ┣ java/com/example/bhuvana
 ┃ ┣ components
 ┃ ┃ ┗ BottomNavigationBar.kt
 ┃ ┣ models
 ┃ ┃ ┗ ProductItem.kt
 ┃ ┣ navigation
 ┃ ┃ ┗ AppNavigation.kt
 ┃ ┣ screens
 ┃ ┃ ┣ DashboardScreen.kt
 ┃ ┃ ┣ MarketWatchScreen.kt
 ┃ ┃ ┣ ProfitCalculatorScreen.kt
 ┃ ┃ ┣ DigitalSlateScreen.kt
 ┃ ┃ ┣ AddProductScreen.kt
 ┃ ┃ ┣ EditProductScreen.kt
 ┃ ┃ ┗ UserProfileScreen.kt
 ┃ ┗ MainActivity.kt
 ┗ res
```

---

# How To Run

1. Clone the repository

```bash
git clone https://github.com/Gowthamchiya/sante-price-index.git
```

2. Open project in Android Studio

3. Connect Firebase to the project

4. Sync Gradle

5. Run the application

---

# Developer

## Gowtham T M

GitHub Repository:

```text
https://github.com/Gowthamchiya/sante-price-index
```
