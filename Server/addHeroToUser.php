<?php
    include "connect.php";
    $id = $_POST['id'];
    $hero_details_id = $_POST['hero_details_id'];
    $sql = "UPDATE USER SET hero_details_id = '$hero_details_id', coins = '0' WHERE id = '$id'";
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>