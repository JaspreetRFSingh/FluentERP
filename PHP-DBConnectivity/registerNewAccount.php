<?php
error_reporting(E_ERROR|E_PARSE);

$id = $_POST['id'];
$username = $_POST['username'];
$password = $_POST['password'];
$authPerson = $_POST['auth_person'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO emp_credentials VALUES ('$id', '$username','$password', '$authPerson');";
$response = array();
if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']=$username."'s account created!";
}else{
 	$response['success']=0;
 	$response['message']="Account Creation Failed!";
}

$conn->close();
echo json_encode($response)
?>