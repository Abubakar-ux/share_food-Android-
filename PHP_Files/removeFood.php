<?php 
include "conn.php";
$response=array();
if(isset($_POST["id"])) {

    $id = $_POST["id"];

    $query = "DELETE FROM `food` WHERE `id`=".$id;
    $result = mysqli_query($con,$query);
    if($result) {
        $response['id'] = $id;
        $response['reqmsg'] = "Contact Deleted!";
        $response['reqcode'] = "1";   
    }
    else {
        $response['id'] = $id;
        $response['reqmsg'] = "Error Deleting Contact!";
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