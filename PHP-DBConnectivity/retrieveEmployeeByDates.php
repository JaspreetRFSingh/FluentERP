<?php

error_reporting(E_ERROR|E_PARSE);
$date_of_joining1 = $_POST['emp_doj1'];
$date_of_joining2 = $_POST['emp_doj2'];
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT * FROM employees";
    if (!isset($date_of_joining2)){
        $sql .= " WHERE doj = '$date_of_joining1'";
    }
    else{
        $sql .= " WHERE doj BETWEEN '$date_of_joining1' AND '$date_of_joining2'";
    }
$sql .= ";";
$response = array();
$result = $conn->query($sql);
if(mysqli_num_rows($result)>0){
	$response['employees'] = array();
	while($row=mysqli_fetch_array($result)){
		array_push($response['employees'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
}else{
 	$response['success']=0;
 	$response['message']="No records found for dates: ".$date_of_joining1 ." - ". $date_of_joining2;
 }
 echo json_encode($response);
?>