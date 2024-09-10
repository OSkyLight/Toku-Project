<?php
include "connect.php";

$sql = "CREATE TABLE `FIGHT_DETAILS` (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fight_id VARCHAR(30) NOT NULL,
    turn VARCHAR(30) NOT NULL,
    damage VARCHAR(30) NOT NULL,
    user_currentHP VARCHAR(30) NOT NULL,
    fight_user_currentHP VARCHAR(30) NOT NULL
)";

try {
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
    echo "Table created successfully.";
} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}
?>
