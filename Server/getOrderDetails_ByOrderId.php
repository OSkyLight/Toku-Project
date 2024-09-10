<?php
    include "connect.php";
    $order_id = $_POST['order_id'];
    $sql = "SELECT * FROM `ORDER_DETAILS` WHERE order_id = :order_id"; // Using prepared statement
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':order_id', $order_id, PDO::PARAM_INT); // Binding parameters to avoid SQL injection
        $stmt->execute();
        $orders = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $jsonData = json_encode($orders);
        echo $jsonData;
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>
