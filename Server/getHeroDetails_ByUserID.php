<?php
    include "connect.php";
    $user_id = $_POST['user_id'];
    $sql = "SELECT * FROM HERO_DETAILS WHERE user_id = '$user_id'";
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $jsonData = json_encode($users);
        echo $jsonData;
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>