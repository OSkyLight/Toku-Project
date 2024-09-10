<?php
include "connect.php"; // Assuming this file contains database connection details

// Retrieve values from POST
$fight_id = $_POST['fight_id'];
$turn = $_POST['turn'];
$damage = $_POST['damage'];
$user_currentHP = $_POST['user_currentHP'];
$fight_user_currentHP = $_POST['fight_user_currentHP'];

// Define SQL query with placeholders
$sql = "INSERT INTO `FIGHT_DETAILS` (fight_id, turn, damage, user_currentHP, fight_user_currentHP)
        VALUES (:fight_id, :turn, :damage, :user_currentHP, :fight_user_currentHP)";

try {
    // Prepare and execute the SQL statement
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':fight_id', $fight_id);
    $stmt->bindParam(':turn', $turn);
    $stmt->bindParam(':damage', $damage);
    $stmt->bindParam(':user_currentHP', $user_currentHP);
    $stmt->bindParam(':fight_user_currentHP', $fight_user_currentHP);
    $stmt->execute();
    
    // Output success message
    echo "Fight details inserted successfully.";
} catch (PDOException $e) {
    // Output error message if query fails
    die("Query failed: " . $e->getMessage());
}
?>
