<?php
include "connect.php";
$sql = "SELECT * FROM USER";
try {
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}
$users = $stmt->fetchAll(PDO::FETCH_ASSOC);
$jsonData = json_encode($users);
echo $jsonData;

?>
