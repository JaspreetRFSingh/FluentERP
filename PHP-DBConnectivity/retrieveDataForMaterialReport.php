<?php

error_reporting(E_ERROR|E_PARSE);

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$response = array();
$querym = mysqli_query($con,"SELECT material_code FROM materials WHERE material_type = 'FG'");
$resultm = mysqli_fetch_array($querym);
$response['mat_code'] =  array();
$response['mat_desc'] =  array();
$response['mat_quantity'] =  array();
$response['mat_price'] =  array();
	while($row = mysqli_fetch_row($querym)){
	$squery = mysqli_query($con,"select material_code, SUM(quantity), SUM(price) from sales_order where material_code = '$row[0]'");
	while($item = mysqli_fetch_row($squery)){
		$exquery = mysqli_query($con,"select material_description from materials where material_code = '$item[0]'");
		$exrow = mysqli_fetch_row($exquery);
		if($item[2]!= 0){
			$response['mat_code'] =  $item[0];
			$response['mat_desc'] = $exrow[0];
			$response['mat_quantity'] = $item[1];
			$response['mat_price'] = $item[2];
			}
}
}
echo json_encode($response);
?>