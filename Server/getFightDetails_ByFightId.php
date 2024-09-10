<?php
    include "connect.php";
    $fight_id = $_POST['fight_id'];
    $sql = "SELECT * FROM `FIGHT_DETAILS` WHERE fight_id = :fight_id"; // Using prepared statement
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':fight_id', $fight_id, PDO::PARAM_INT); // Correct parameter name and type
        $stmt->execute();
        $orders = $stmt->fetchAll(PDO::FETCH_ASSOC);
        header('Content-Type: application/json'); // Set content type header
        echo json_encode($orders);
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>
