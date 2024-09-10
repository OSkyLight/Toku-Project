<?php
include "connect.php";

// Assuming $pdo is your database connection

$user_id = $_POST["user_id"];
$content = $_POST["content"];
$news_feed_id = $_POST["news_feed_id"];
$like_count = 0;  // Assuming these are numeric fields
$comment_count = 0;

// Use a prepared statement to avoid SQL injection
$sql = "INSERT INTO COMMENT (user_id, news_feed_id, content) VALUES (?, ?, ?)";

try {
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(1, $user_id);
    $stmt->bindParam(2, $news_feed_id);
    $stmt->bindParam(3, $content, PDO::PARAM_STR);  // Specify the parameter type
    $stmt->execute();

    // After inserting into NEW_FEEDS, update HERO_DETAILS
    $increase_exp = 1 + floor(strlen($content) / 10);

    // Retrieve data from HERO_DETAILS
    $heroDetailsQuery = "SELECT exp, max_exp, level, attribute_point FROM HERO_DETAILS WHERE user_id = ?";
    $heroStmt = $pdo->prepare($heroDetailsQuery);
    $heroStmt->bindParam(1, $user_id);
    $heroStmt->execute();
    $heroDetails = $heroStmt->fetch(PDO::FETCH_ASSOC);

    $current_exp = $heroDetails['exp'] + $increase_exp;

    while ($current_exp >= $heroDetails['max_exp']) {
        // Level up
        $current_exp -= $heroDetails['max_exp'];
        $heroDetails['level']++;
        $heroDetails['attribute_point']++;
        $heroDetails['max_exp'] += 10;
    }

    // Update HERO_DETAILS
    $updateHeroQuery = "UPDATE HERO_DETAILS SET exp = ? , level = ?, attribute_point = ?, max_exp = ? WHERE user_id = ?";
    $updateHeroStmt = $pdo->prepare($updateHeroQuery);
    $updateHeroStmt->bindParam(1, $current_exp);
    $updateHeroStmt->bindParam(2, $heroDetails['level']);
    $updateHeroStmt->bindParam(3, $heroDetails['attribute_point']);
    $updateHeroStmt->bindParam(4, $heroDetails['max_exp']);
    $updateHeroStmt->bindParam(5, $user_id);
    $updateHeroStmt->execute();

    // Update comment_count in NEWS_FEEDS table
    $update_like_count_sql = "UPDATE NEW_FEEDS SET comment_count = comment_count + 1 WHERE id = :news_feed_id";
    $update_like_count_stmt = $pdo->prepare($update_like_count_sql);
    $update_like_count_stmt->bindParam(':news_feed_id', $news_feed_id);
    $update_like_count_stmt->execute();

    // Return the amount of exp gained
    echo json_encode(array(
        "status" => "success",
        "message" => "You got " . $increase_exp . " exp.",
    ));

} catch (PDOException $e) {
    die("Query failed: " . $e->getMessage());
}
?>
