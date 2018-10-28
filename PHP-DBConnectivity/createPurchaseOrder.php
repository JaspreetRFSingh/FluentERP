<?php
error_reporting(E_ERROR|E_PARSE);

$purchase_doc_no = $_POST['purchase_doc_no'];
$seller_id = $_POST['seller_id'];

$material_code0 = $_POST['material_code0'];
$quantity0 = $_POST['quantity0'];
$material_code1 = $_POST['material_code1'];
$quantity1 = $_POST['quantity1'];
$material_code2 = $_POST['material_code2'];
$quantity2 = $_POST['quantity2'];
$material_code3 = $_POST['material_code3'];
$quantity3 = $_POST['quantity3'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

if (isset($quantity0, $quantity1, $quantity2, $quantity3)) {
 $sql = "INSERT INTO purchase_order VALUES ";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code0','$quantity0'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code1','$quantity1'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code2','$quantity2'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code3','$quantity3');";   
}
else if (isset($quantity0) && !isset($quantity1) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO purchase_order VALUES ('$purchase_doc_no', '$seller_id','$material_code0','$quantity0');"; 
}
else if (isset($quantity1)&& !isset($quantity0) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO purchase_order VALUES ('$purchase_doc_no', '$seller_id','$material_code1','$quantity1');"; 
}
else if (isset($quantity2)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity3)) {
 $sql = "INSERT INTO purchase_order VALUES ('$purchase_doc_no', '$seller_id','$material_code2','$quantity2');"; 
}
else if (isset($quantity3)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity2)) {
 $sql = "INSERT INTO purchase_order VALUES ('$purchase_doc_no', '$seller_id','$material_code3','$quantity3');"; 
}
else if (isset($quantity0, $quantity1) && !isset($quantity2) && !isset($quantity3)) {
$sql = "INSERT INTO purchase_order VALUES ";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code0','$quantity0'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code1','$quantity1');"; 
}
else if (isset($quantity0, $quantity1, $quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO purchase_order VALUES ";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code0','$quantity0'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code1','$quantity1'),";
$sql .= "('$purchase_doc_no', '$seller_id','$material_code2','$quantity2');";
}

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Purchase Order Created with number: " . $purchase_doc_no;
}else{
 	$response['success']=0;
 	$response['message']=$conn->error;
}
$conn->close();
echo json_encode($response)
?>