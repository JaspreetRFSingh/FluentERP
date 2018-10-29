<?php
error_reporting(E_ERROR|E_PARSE);

$purchase_doc_no = $_POST['purchase_doc_no'];
$seller_id = $_POST['seller_id'];
$date_of_order = $_POST['date_of_order'];
$status = $_POST['order_status'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE purchase_orders_list SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', date_of_order = '$date_of_order', order_status = '$status' WHERE purchase_doc_no = '$purchase_doc_no'";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Purchase Document Updated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Purchase Document Update Failed";
}
$conn->close();
echo json_encode($response)
?>