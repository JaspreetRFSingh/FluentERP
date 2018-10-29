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
 
$tsql = "SELECT SUM(bill_price) FROM `sales_orders_list`";
$tresponse = array();
$tresult = $conn->query($tsql);
if(mysqli_num_rows($tresult)>0){

	$tresponse['total_orders'] = array();
	while($row=mysqli_fetch_array($tresult)){
		array_push($tresponse['total_orders'], $row);
	}

	$tresponse['success']=1;
 	$tresponse['message']="Records retrieved successfully!";
 	
}else{
 	$tresponse['success']=0;
 	$tresponse['message']="No records found!";
 } 
 
 echo json_encode($tresponse);
?>