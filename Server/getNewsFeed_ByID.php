<?php
include "connect.php";
$id = $_POST["id"];
$sql = "SELECT nf.*, u.username as user_name, u.avatar as user_avatar
        FROM NEW_FEEDS nf
        JOIN USER u ON nf.user_id = u.id
        WHERE nf.id = ?
        ORDER BY nf.date_post DESC";

try {
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$id]);
} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}

$feeds = $stmt->fetchAll(PDO::FETCH_ASSOC);
$jsonData = json_encode($feeds);
echo $jsonData;
?>
