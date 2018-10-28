<?php
error_reporting(E_ERROR|E_PARSE);

$sales_doc_no = $_POST['sales_doc_no'];
$Customer = $_POST['Customer'];
$date_of_order = $_POST['date_of_order'];
$bill_price = $_POST['bill_price'];
$status = 'Created';

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

$sql = "INSERT INTO sales_orders_list VALUES ('$sales_doc_no', '$Customer','$date_of_order','$bill_price', '$status');";

$response = array();
if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Bill Generated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Bill Generation Failed! Please try again!";
}
$conn->close();
echo json_encode($response)
?>