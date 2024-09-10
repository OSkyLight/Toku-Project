<?php
include "connect.php"; // Assuming this file contains database connection details

// Check if required fields are provided
if(empty($_POST['user_id']) || empty($_POST['address']) || empty($_POST['total_price'])) {
    die("Error: Missing required fields.");
}

$user_id = $_POST['user_id'];
$address = $_POST['address'];
$total_price = $_POST['total_price'];

try {
    // Start a transaction
    $pdo->beginTransaction();

    // Insert the order into the database
    $insert_sql = "INSERT INTO `ORDER` (user_id, address, total_price)
                   VALUES (:user_id, :address, :total_price)";
    $insert_stmt = $pdo->prepare($insert_sql);
    $insert_stmt->bindParam(':user_id', $user_id);
    $insert_stmt->bindParam(':address', $address);
    $insert_stmt->bindParam(':total_price', $total_price);
    $insert_stmt->execute();

    // Fetch the inserted order data
    $order_id = $pdo->lastInsertId();
    $select_sql = "SELECT * FROM `ORDER` WHERE id = :order_id";
    $select_stmt = $pdo->prepare($select_sql);
    $select_stmt->bindParam(':order_id', $order_id);
    $select_stmt->execute();
    $order = $select_stmt->fetch(PDO::FETCH_ASSOC);

    // Update user's coins
    $update_sql = "UPDATE USER SET coins = coins - :total_price WHERE id = :user_id";
    $update_stmt = $pdo->prepare($update_sql);
    $update_stmt->bindParam(':user_id', $user_id);
    $update_stmt->bindParam(':total_price', $total_price);
    $update_stmt->execute();

    // Commit the transaction
    $pdo->commit();

    // Return JSON response
    header('Content-Type: application/json');
    echo json_encode($order);
} catch (PDOException $e) {
    // Rollback the transaction in case of failure
    $pdo->rollBack();
    die("Query failed: " . $e->getMessage());
}
?>
