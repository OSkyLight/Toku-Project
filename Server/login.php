<?php
    include "connect.php";
    $username = $_POST['username'];
    $password = $_POST['pass'];
    $sql = "SELECT * FROM USER WHERE username = '$username' AND pass = '$password'";
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