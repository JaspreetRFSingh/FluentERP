<?php
error_reporting(E_ERROR|E_PARSE);

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

$sql = "INSERT INTO employees VALUES (null, '$name','$address','$type', '$phone', '$dob', '$doj');";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Employee Added Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Employee Addition Failed";
}
$conn->close();
echo json_encode($response)
?>