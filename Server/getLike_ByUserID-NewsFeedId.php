<?php
    include "connect.php";
    $news_feed_id = $_POST['news_feed_id'];
    $user_id = $_POST['user_id'];
    // You're using single quotes around 'LIKE', which is incorrect. Use backticks or remove the quotes.
    $sql = "SELECT * FROM `LIKE` WHERE user_id = :user_id AND news_feed_id = :news_feed_id";
    try {
        $stmt = $pdo->prepare($sql);
        // Bind parameters to avoid SQL injection
        $stmt->bindParam(':user_id', $user_id);
        $stmt->bindParam(':news_feed_id', $news_feed_id);
        $stmt->execute();
        $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $jsonData = json_encode($users);
        echo $jsonData;
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>
