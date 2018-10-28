<?php
error_reporting(E_ERROR|E_PARSE);

$mat_id = $_POST['material_id'];
$desc = $_POST['description'];
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

$sql = "UPDATE materials set material_type = '$type', material_description = '$desc', dimensional_unit = '$du', cost_per_du = '$costperdu' WHERE material_code = '$mat_id'";

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Material modified successfully!";
}else{
 	$response['success']=0;
 	$response['message']="Material modification failed!";
}
$conn->close();
echo json_encode($response)
?>