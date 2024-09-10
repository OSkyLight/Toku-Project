<?php
    include "connect.php";
    
    // Assuming you have already validated and sanitized the input
    
    $id = $_POST['id'];
    $attack_point = $_POST['attack_point'];
    $defense_point = $_POST['defense_point'];
    $health_point = $_POST['health_point'];
    $attribute_point = $_POST['attribute_point'];
    
    $sql = "UPDATE HERO_DETAILS SET attack_point = '$attack_point', defense_point = '$defense_point', health_point = '$health_point', attribute_point = '$attribute_point' WHERE id = '$id'";
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        
        // Check if any rows were affected by the update
        $rowCount = $stmt->rowCount();
        
        if ($rowCount > 0) {
            // If rows were affected, return success message
            $message = array("status" => "success", "message" => "Hero stats change successfully.");
        } else {
            // If no rows were affected, return a message indicating no update occurred
            $message = array("status" => "info", "message" => "No changes were made.");
        }
    } catch (PDOException $e) {
        // If an error occurs during execution, return an error message
        $message = array("status" => "error", "message" => "Server Error: " . $e->getMessage());
    }
    
    // Encode the message array as JSON and output it
    echo json_encode($message);
?>