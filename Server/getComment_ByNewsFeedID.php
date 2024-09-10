<?php
    include "connect.php";
    $news_feed_id = $_POST['news_feed_id'];
    $sql = "SELECT cmt.*, u.username as user_name, u.avatar as user_avatar
            FROM COMMENT cmt
            JOIN USER u ON cmt.user_id = u.id
            WHERE cmt.news_feed_id = :news_feed_id
            ORDER BY cmt.id DESC"; // Ordering by comment ID in descending order
    try {
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':news_feed_id', $news_feed_id, PDO::PARAM_INT);
        $stmt->execute();
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
    
    $feeds = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $jsonData = json_encode($feeds);
    echo $jsonData;
?>
