<?php
include "connect.php";
$news_feed_id = $_POST['news_feed_id'];
$user_id = $_POST['user_id'];

try {
    // Check if a row exists with the given news_feed_id and user_id
    $check_sql = "SELECT * FROM `LIKE` WHERE `user_id` = :user_id AND `news_feed_id` = :news_feed_id";
    $check_stmt = $pdo->prepare($check_sql);
    $check_stmt->bindParam(':user_id', $user_id);
    $check_stmt->bindParam(':news_feed_id', $news_feed_id);
    $check_stmt->execute();
    $existing_row = $check_stmt->fetch(PDO::FETCH_ASSOC);

    if ($existing_row) {
        // A row exists, update the is_liked field
        $is_liked = $existing_row['is_liked'];
        $new_is_liked = ($is_liked == 1) ? 0 : 1;

        $update_sql = "UPDATE `LIKE` SET `is_liked` = :new_is_liked WHERE `user_id` = :user_id AND `news_feed_id` = :news_feed_id";
        $update_stmt = $pdo->prepare($update_sql);
        $update_stmt->bindParam(':new_is_liked', $new_is_liked);
        $update_stmt->bindParam(':user_id', $user_id);
        $update_stmt->bindParam(':news_feed_id', $news_feed_id);
        $update_stmt->execute();
        echo $is_like;
        // Update like_count in NEWS_FEEDS table based on is_liked value
        if ($new_is_liked == 0) {
            $like_count_change = -1; // Decrease like_count
        } else {
            $like_count_change = 1; // Increase like_count
        }

        $update_like_count_sql = "UPDATE NEW_FEEDS SET like_count = like_count + :like_count_change WHERE id = :news_feed_id";
        $update_like_count_stmt = $pdo->prepare($update_like_count_sql);
        $update_like_count_stmt->bindParam(':like_count_change', $like_count_change);
        $update_like_count_stmt->bindParam(':news_feed_id', $news_feed_id);
        $update_like_count_stmt->execute();

        echo json_encode(array("status" => "success", "message" => "0"));
    } else {
        // No row exists, insert a new row with is_liked = 1
        $insert_sql = "INSERT INTO `LIKE` (`user_id`, `news_feed_id`, `is_reward`, `is_liked`) 
                       VALUES (:user_id, :news_feed_id, '1', '1')";
        $insert_stmt = $pdo->prepare($insert_sql);
        $insert_stmt->bindParam(':user_id', $user_id);
        $insert_stmt->bindParam(':news_feed_id', $news_feed_id);
        $insert_stmt->execute();

        // Update like_count in NEWS_FEEDS table
        $update_like_count_sql = "UPDATE NEW_FEEDS SET like_count = like_count + 1 WHERE id = :news_feed_id";
        $update_like_count_stmt = $pdo->prepare($update_like_count_sql);
        $update_like_count_stmt->bindParam(':news_feed_id', $news_feed_id);
        $update_like_count_stmt->execute();

        // Update experience points and level in HERO_DETAILS table
        $increase_exp = 1;
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
        // Remaining code for updating experience points and level...
        
        
        echo json_encode(array("status" => "success", "message" => "You got 1 exp."));
    }
} catch (PDOException $e) {
    // Handle database errors
    die("Query failed: " . $e->getMessage());
}
?>
