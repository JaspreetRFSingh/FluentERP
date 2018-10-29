<?php

error_reporting(E_ERROR|E_PARSE);

$sales_document_number = $_POST['sales_doc_no'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT sales_doc_no FROM sales_orders_list";
$response = array();
$result = $conn->query($sql);
if(mysqli_num_rows($result)>0){
	$response['sales_order'] = array();
	while($row=mysqli_fetch_array($result)){
		array_push($response['sales_order'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
}else{
 	$response['success']=0;
 	$response['message']=$sales_document_number . ":";
 }
 echo json_encode($response);
?>