<?php
error_reporting(E_ERROR|E_PARSE);

$purchase_doc_no = $_POST['purchase_doc_no'];
$seller = $_POST['seller_id'];
$date_of_order = $_POST['date_of_order'];
$status = 'Ordered';

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO sales_orders_list VALUES ('$purchase_doc_no', '$seller','$date_of_order', '$status');";

$response = array();
if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Purchase Bill Generated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Purchase Bill Generation Failed! Please try again!";
}
$conn->close();
echo json_encode($response)
?>