<?php
include "connect.php"; // Include the file to establish database connection

// Check if coins and id are set in POST data
if(isset($_POST['coins'], $_POST['id'])) {
    // Sanitize input
    $coins = intval($_POST['coins']); // Convert to integer
    $id = intval($_POST['id']); // Convert to integer

    // Prepare and execute the SQL query
    $stmt = $pdo->prepare("UPDATE USER SET coins = coins + ? WHERE id = ?");
    if ($stmt->execute([$coins, $id])) {
        echo "Coins updated successfully.";
    } else {
        echo "Error updating coins: " . $stmt->errorInfo()[2];
    }
} else {
    echo "Coins or ID not provided.";
}
?>