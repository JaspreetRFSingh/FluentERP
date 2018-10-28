<?php
error_reporting(E_ERROR|E_PARSE);

$emp_id = $_POST['id'];
$name = $_POST['name'];
$address = $_POST['address'];
$type = $_POST['type'];
$phone = $_POST['phone'];
$dob = $_POST['dob'];
$doj = $_POST['doj'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE employees SET emp_name = '$name', emp_address = '$address', emp_type = '$type', emp_phone = '$phone', dob = '$dob', doj = '$doj' WHERE emp_id = '$emp_id'";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Employee Updated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Employee Update Failed";
}
$conn->close();
echo json_encode($response)
?>