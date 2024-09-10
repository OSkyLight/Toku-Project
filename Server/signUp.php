<?php
    include "connect.php";
    $id = "";
    $username = $_POST['username'];
    $password = $_POST['pass'];
    $email = $_POST['email'];
    $phoneNumber = $_POST['phone_number'];
    $coins = '0';
    $avatar = 'https://i.imgur.com/9JxB5DS.png';

    $sql = "INSERT INTO USER(id, username, pass, email, phone_number, hero_details_id, coins, avatar)
            VALUE ('$id', '$username', '$password', '$email', '$phoneNumber', '0', '$coins', '$avatar')";

    try {
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
?>