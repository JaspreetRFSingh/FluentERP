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
$jsql .= "SELECT SUM(bill_price) FROM `sales_orders_list` WHERE `Customer` IN (SELECT `Customer_id` FROM `customers` WHERE `City` = 'Jalandhar')";
$asql .= "SELECT SUM(bill_price) FROM `sales_orders_list` WHERE `Customer` IN (SELECT `Customer_id` FROM `customers` WHERE `City` = 'Allahabad')";
$tsql = "SELECT SUM(bill_price) FROM `sales_orders_list`";
$response = array();
$lresult = $conn->query($lsql);
if(mysqli_num_rows($lresult)>0){

	$response['ludhiana_orders'] = array();
	while($row=mysqli_fetch_array($lresult)){
		array_push($response['ludhiana_orders'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 $jresult = $conn->query($jsql);
 if(mysqli_num_rows($jresult)>0){

	$response['jalandhar_orders'] = array();
	while($row=mysqli_fetch_array($jresult)){
		array_push($response['jalandhar_orders'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 $aresult = $conn->query($asql);
 if(mysqli_num_rows($aresult)>0){

	$response['allahabad_orders'] = array();
	while($row=mysqli_fetch_array($aresult)){
		array_push($response['allahabad_orders'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 $tresult = $conn->query($tsql);
 if(mysqli_num_rows($tresult)>0){

	$response['total_orders'] = array();
	while($row=mysqli_fetch_array($tresult)){
		array_push($response['total_orders'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
echo json_encode($response);
?>