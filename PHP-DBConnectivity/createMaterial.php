<?php
error_reporting(E_ERROR|E_PARSE);

$description = $_POST['description'];
$type = $_POST['type'];
$du = $_POST['du'];
$costperdu = $_POST['costperdu'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO materials VALUES (null, '$type','$description','$du', '$costperdu');";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Material Created Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Material Creation Failed";
}
$conn->close();
echo json_encode($response)
?>