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
$sql = "SELECT * FROM sales_orders_list where sales_doc_no = '$sales_document_number'";
$response = array();
$result = $conn->query($sql);
if(mysqli_num_rows($result)>0){
	$response['sales_orders_list'] = array();
	while($row=mysqli_fetch_array($result)){
		array_push($response['sales_orders_list'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
}else{
 	$response['success']=0;
 	$response['message']=$sales_document_number . ":";
 }
 echo json_encode($response);
?>