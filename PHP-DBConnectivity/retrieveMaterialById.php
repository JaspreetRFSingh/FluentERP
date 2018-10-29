<?php

error_reporting(E_ERROR|E_PARSE);

$id = $_POST['mat_id'];
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT * FROM materials WHERE material_code = '$id';";
$response = array();
$result = $conn->query($sql);
if(mysqli_num_rows($result)>0){
	$response['materials'] = array();
	while($row=mysqli_fetch_array($result)){
		array_push($response['materials'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 echo json_encode($response);
?>