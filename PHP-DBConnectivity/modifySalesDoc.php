<?php
error_reporting(E_ERROR|E_PARSE);

$sales_doc_no = $_POST['sales_doc_no'];
$customer_id = $_POST['customer_id'];
$date_of_order = $_POST['date_of_order'];
$price = $_POST['price'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE sales_orders_list SET sales_doc_no = '$sales_doc_no', Customer = '$customer_id', date_of_order = '$date_of_order', bill_price = '$price', order_status = 'Created' WHERE sales_doc_no = '$sales_doc_no'";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Sales Document Updated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Sales Document Update Failed";
}
$conn->close();
echo json_encode($response)
?>