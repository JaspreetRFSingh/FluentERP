<?php

error_reporting(E_ERROR|E_PARSE);
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
 
 
$lsql = "SELECT SUM(bill_price) FROM `sales_orders_list` WHERE `Customer` IN (SELECT `Customer_id` FROM `customers` WHERE `City` = 'Ludhiana')";
$lresponse = array();
$lresult = $conn->query($lsql);
if(mysqli_num_rows($lresult)>0){

	$lresponse['ludhiana_orders'] = array();
	while($row=mysqli_fetch_array($lresult)){
		array_push($lresponse['ludhiana_orders'], $row);
	}

	$lresponse['success']=1;
 	$lresponse['message']="Records retrieved successfully!";
 	
}else{
 	$lresponse['success']=0;
 	$lresponse['message']="No records found!";
 }
 echo json_encode($lresponse);
?>