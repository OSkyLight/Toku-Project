<?php
    include "connect.php";
    $user_id = $_POST['user_id'];
    $sql = "SELECT * FROM `FIGHT_HISTORY` WHERE user_id = :user_id"; // Using prepared statement
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':user_id', $user_id, PDO::PARAM_INT); // Binding parameters to avoid SQL injection
        $stmt->execute();
        $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $jsonData = json_encode($users);
        echo $jsonData;
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>
