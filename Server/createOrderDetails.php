<?php
include "connect.php"; // Assuming this file contains database connection details

$order_id = $_POST['order_id'];
$product_id = $_POST['product_id'];
$quantity = $_POST['quantity'];
$price = $_POST['price'];

$sql = "INSERT INTO `ORDER_DETAILS` (order_id, product_id, quantity, price)
        VALUES (:order_id, :product_id, :quantity, :price)";

try {
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':order_id', $order_id);
    $stmt->bindParam(':product_id', $product_id);
    $stmt->bindParam(':quantity', $quantity);
    $stmt->bindParam(':price', $price);
    $stmt->execute();
    echo "Order detail inserted successfully.";
} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}
?>
