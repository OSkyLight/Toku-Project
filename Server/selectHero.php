<?php
include "connect.php";
$id = "";
$user_id = $_POST['user_id'];
$hero_id = $_POST['hero_id'];

$sql = "INSERT INTO HERO_DETAILS(id, user_id, hero_id, attack_point, defense_point, health_point, exp, max_exp, level, attribute_point)
VALUE ('$id', '$user_id', '$hero_id', '10','10','10', '0', '10', '1', '0')";
try {
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}
?>