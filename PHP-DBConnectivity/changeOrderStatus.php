<?php
error_reporting(E_ERROR|E_PARSE);

$sales_doc_no = $_POST['sales_doc_no'];
$order_status = $_POST['order_status'];
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE sales_orders_list set order_status = '$order_status' WHERE sales_doc_no = '$sales_doc_no'";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Order Status Changed to " . $order_status;
}else{
 	$response['success']=0;
 	$response['message']="Failed!";
}
$conn->close();
echo json_encode($response)
?>