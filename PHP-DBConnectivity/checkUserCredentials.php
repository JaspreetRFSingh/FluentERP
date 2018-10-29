<?php
error_reporting(E_ERROR|E_PARSE);
$user = $_POST['username'];
$pass = $_POST['password'];
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT * FROM `emp_credentials` WHERE `empUserName` = '$user' AND `empPassword` = '$pass'";
$result = $conn->query($sql);
if(mysqli_num_rows($result)==1){
	$response['success']=1;
 	$response['message']="Login successful!";
}else{
 	$response['success']=0;
 	$response['message']="Login failed!";
 }
 echo json_encode($response);
?>