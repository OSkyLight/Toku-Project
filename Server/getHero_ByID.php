<?php
     include "connect.php";
     $id = $_POST['id'];
     $sql = "SELECT * FROM HERO WHERE id = '$id'";
     try {
         $stmt = $pdo->prepare($sql);
         $stmt->execute();
         $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
         $jsonData = json_encode($users);
         echo $jsonData;
     } catch (PDOException $e) {
         die("Query failed: " . $e->getMessage());
     }
?>