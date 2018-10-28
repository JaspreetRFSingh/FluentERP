<?php

error_reporting(E_ERROR|E_PARSE);

$servername = "localhost";
$username = "id3425739_db_new_july_2018";
$password = "shikhajaspreetajay";
$dbname = "id3425739_majorprojectdb";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "select material_description, stock FROM materials, current_stock where materials.material_code = current_stock.material_code";
$response = array();
$result = $conn->query($sql);
if(mysqli_num_rows($result)>0){
	$response['current_stock'] = array();
	while($row=mysqli_fetch_array($result)){
		array_push($response['current_stock'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 echo json_encode($response);
?>