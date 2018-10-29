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
$jsql = "SELECT SUM(bill_price) FROM `sales_orders_list` WHERE `Customer` IN (SELECT `Customer_id` FROM `customers` WHERE `City` = 'Jalandhar')";
$jresponse = array();
$jresult = $conn->query($jsql);
if(mysqli_num_rows($jresult)>0){

	$jresponse['jalandhar_orders'] = array();
	while($row=mysqli_fetch_array($lresult)){
		array_push($jresponse['jalandhar_orders'], $row);
	}

	$jresponse['success']=1;
 	$jresponse['message']="Records retrieved successfully!";
 	
}else{
 	$jresponse['success']=0;
 	$jresponse['message']="No records found!";
 }
 echo json_encode($jresponse);
?>