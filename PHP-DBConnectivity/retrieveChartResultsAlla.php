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

$asql = "SELECT SUM(bill_price) FROM `sales_orders_list` WHERE `Customer` IN (SELECT `Customer_id` FROM `customers` WHERE `City` = 'Allahabad')";
$aresponse = array();
$aresult = $conn->query($asql);
if(mysqli_num_rows($aresult)>0){

	$aresponse['allahabad_orders'] = array();
	while($row=mysqli_fetch_array($aresult)){
		array_push($aresponse['allahabad_orders'], $row);
	}

	$aresponse['success']=1;
 	$aresponse['message']="Records retrieved successfully!";
 	
}else{
 	$aresponse['success']=0;
 	$aresponse['message']="No records found!";
 }
 echo json_encode($aresponse);
?>