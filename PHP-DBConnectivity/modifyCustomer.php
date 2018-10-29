<?php
error_reporting(E_ERROR|E_PARSE);

$customer_id = $_POST['Customer_id'];
$name = $_POST['Name'];
$address = $_POST['Address'];
$city = $_POST['City'];
$contact = $_POST['Contact'];
$gst_number = $_POST['GST_Number'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE customers set Name = '$name', Address = '$address', City = '$city', Contact = '$contact', GST_Number = '$gst_number' WHERE Customer_id = $customer_id";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Customer Updated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Customer Updation Failed";
}
$conn->close();
echo json_encode($response)
?>