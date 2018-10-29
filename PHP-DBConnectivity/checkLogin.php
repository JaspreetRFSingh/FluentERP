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

$sql = "SELECT * FROM emp_credentials WHERE empUserName = '$user' AND empPassword = '$pass';";
$result = $conn->query($sql);
if(mysqli_num_rows($result)==1){

$row=mysqli_fetch_array($result)
	$response['emp_credentials'] = array();
	array_push($response['emp_credentials'], $row);
	$response['success']=1;
 	$response['message']="Login successful!";
 	
}else{
 	$response['success']=0;
 	$response['message']="Login failed!";
 }

$conn->close();
echo json_encode($response)
?>