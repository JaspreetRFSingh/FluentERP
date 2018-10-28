<?php
error_reporting(E_ERROR|E_PARSE);

$id = $_POST['id'];
$user = $_POST['username'];
$pass = $_POST['password'];
$authPerson = $_POST['auth_person'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE emp_credentials SET emp_id = '$id', empUserName = '$user', empPassword = '$pass', auth_person = '$authPerson' where emp_id = '$id';";
$response = array();
if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']=$user."'s account Updated!";
}else{
 	$response['success']=0;
 	$response['message']="Account Update Failed!";
}

$conn->close();
echo json_encode($response)
?>