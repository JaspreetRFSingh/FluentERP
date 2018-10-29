<?php
error_reporting(E_ERROR|E_PARSE);

$purchase_doc_no = $_POST['purchase_doc_no'];
$seller_id = $_POST['seller_id'];
$material_code = $_POST['material_code'];
$quantity = $_POST['quantity'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


if (isset($quantity0, $quantity1, $quantity2, $quantity3)) {
$sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code0', quantity = '$quantity0' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code1', quantity = '$quantity1' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code2', quantity = '$quantity2' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code3', quantity = '$quantity3' WHERE purchase_doc_no = '$purchase_doc_no';";   
}
else if (isset($quantity0) && !isset($quantity1) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code0', quantity = '$quantity0' WHERE purchase_doc_no = '$purchase_doc_no';"; 
}
else if (isset($quantity1)&& !isset($quantity0) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code1', quantity = '$quantity1' WHERE purchase_doc_no = '$purchase_doc_no';"; 
}
else if (isset($quantity2)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity3)) {
 $sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code2', quantity = '$quantity2' WHERE purchase_doc_no = '$purchase_doc_no';"; 
}
else if (isset($quantity3)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity2)) {
 $sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code3', quantity = '$quantity3' WHERE purchase_doc_no = '$purchase_doc_no';"; 
}
else if (isset($quantity0, $quantity1) && !isset($quantity2) && !isset($quantity3)) {
$sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code0', quantity = '$quantity0' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code1', quantity = '$quantity1' WHERE purchase_doc_no = '$purchase_doc_no';"; 
}
else if (isset($quantity0, $quantity1, $quantity2) && !isset($quantity3)) {
$sql = "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code0', quantity = '$quantity0' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code1', quantity = '$quantity1' WHERE purchase_doc_no = '$purchase_doc_no';";
$sql .= "UPDATE purchase_order SET purchase_doc_no = '$purchase_doc_no', s_id = '$seller_id', material_code = '$material_code2', quantity = '$quantity2' WHERE purchase_doc_no = '$purchase_doc_no';";
}



$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Purchase Order Updated Successfully";
}else{
 	$response['success']=0;
 	$response['message']="Purchase Order Update Failed";
}
$conn->close();
echo json_encode($response)
?>