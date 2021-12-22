<?php 

include "conn.php";
$result=array();
$result['data'] = array();
$select = "SELECT * from `food`";
$response = mysqli_query($con, $select);

while($row = mysqli_fetch_assoc($response)) {
    $index["id"] = $row['id'];
    $index["name"] = $row['name'];
    $index["amount"] = $row['amount'];
    $index["donor"] = $row['donor'];
    $index["recipient"] = $row['recipient'];
    $index["expiry"] = $row['expiry'];
    $index["location"] = $row['location'];
    $index["img"] = $row['img'];

    array_push($result['data'], $index);    
    }

    $result["success"] = "1";
    echo json_encode($result['data']);
?>