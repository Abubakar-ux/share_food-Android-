<?php 
include "conn.php";
$response=array();
if(isset($_POST["name"],$_POST["amount"], $_POST["donor"],$_POST["recipient"],$_POST["expiry"], $_POST["location"], $_POST["img"])) {
    $name = $_POST["name"];
    $amount = $_POST["amount"];
    $donor = $_POST["donor"];
    $recipient = $_POST["recipient"];
    $expiryDate = $_POST["expiry"];
    $location = $_POST["location"];
    $img = $_POST["img"];
    $target_dir = "upload";

    if(!file_exists($target_dir)) {
        mkdir($target_dir, 0777, true);
    }

    $target_dir = $target_dir ."/". rand() . "_" . time() . ".jpeg";
    
    file_put_contents($target_dir, base64_decode($img));

    $query = "INSERT INTO `food`(`name`, `amount`, `donor`, `recipient`, `expiry`, `location`, `img`) VALUES ('$name','$amount','$donor','$recipient','$expiryDate','$location','$target_dir')";
    $result = mysqli_query($con,$query);
    if($result) {
        $response['id'] = mysqli_insert_id($con);
        $response['reqmsg'] = "Contact Inserted!";
        $response['reqcode'] = "1";   
        $response['img'] = $target_dir;
    }
    else {
        $response['id'] = "NA";
        $response['reqmsg'] = "Error Inserting Contact!";
        $response['reqcode'] = "0";
    }
}
else {
    $response['id'] = "NA";
    $response['reqmsg'] = "Incomplete Request!";
    $response['reqcode'] = "0";
}

$x = json_encode($response);
echo $x;

?>