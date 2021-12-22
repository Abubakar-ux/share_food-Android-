<?php
    $con=mysqli_connect("localhost", "root", "", "foodshare") or die("I cannot connect to the database.".mysql_errno());

    mysqli_select_db($con,"foodshare");
    mysqli_query($con,"SET NAMES 'utf8'");

?>