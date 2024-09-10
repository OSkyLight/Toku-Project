<?php
include "connect.php"; // Assuming this file contains database connection details

// Retrieve values from POST
$user_id = $_POST['user_id'];
$fight_user_id = $_POST['fight_user_id'];
$rewards = $_POST['rewards'];
$status = $_POST['status'];

// Define SQL query with placeholders for insertion
$insertSql = "INSERT INTO `FIGHT_HISTORY` (user_id, fight_user_id, rewards, status)
        VALUES (:user_id, :fight_user_id, :rewards, :status)";

try {
    // Prepare and execute the SQL statement for insertion
    $stmt = $pdo->prepare($insertSql);
    $stmt->bindParam(':user_id', $user_id);
    $stmt->bindParam(':fight_user_id', $fight_user_id);
    $stmt->bindParam(':rewards', $rewards);
    $stmt->bindParam(':status', $status); // Bind status parameter
    $stmt->execute();
    
    // Fetch the inserted fight history data
    $fight_id = $pdo->lastInsertId();
    $selectSql = "SELECT * FROM `FIGHT_HISTORY` WHERE id = :fight_id";
    $selectStmt = $pdo->prepare($selectSql);
    $selectStmt->bindParam(':fight_id', $fight_id);
    $selectStmt->execute();
    $fightHistory = $selectStmt->fetch(PDO::FETCH_ASSOC);
    
    // Output the inserted data as JSON
    header('Content-Type: application/json');
    echo json_encode($fightHistory);
} catch (PDOException $e) {
    // Log the error for debugging purposes
    error_log("Database query failed: " . $e->getMessage());
    
    // Return a proper error response
    http_response_code(500);
    echo json_encode(array("error" => "Internal Server Error"));
}
?>
