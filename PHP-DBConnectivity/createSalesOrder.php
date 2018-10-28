<?php
error_reporting(E_ERROR|E_PARSE);


$sales_doc_no = $_POST['sales_doc_no'];
$Customer_id = $_POST['Customer_id'];

$material_code0 = $_POST['material_code0'];
$quantity0 = $_POST['quantity0'];
$price0 = $_POST['price0'];

$material_code1 = $_POST['material_code1'];
$quantity1 = $_POST['quantity1'];
$price1 = $_POST['price1'];

$material_code2 = $_POST['material_code2'];
$quantity2 = $_POST['quantity2'];
$price2 = $_POST['price2'];
$material_code3 = $_POST['material_code3'];
$quantity3 = $_POST['quantity3'];
$price3 = $_POST['price3'];

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

if (isset($quantity0, $quantity1, $quantity2, $quantity3)) {
 $sql = "INSERT INTO sales_order VALUES ";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code0','$quantity0', '$price0'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code1','$quantity1', '$price1'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code2','$quantity2', '$price2'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code3','$quantity3', '$price3');";   
}
else if (isset($quantity0) && !isset($quantity1) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO sales_order VALUES ('$sales_doc_no', '$Customer_id','$material_code0','$quantity0', '$price0');"; 
}
else if (isset($quantity1)&& !isset($quantity0) && !isset($quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO sales_order VALUES ('$sales_doc_no', '$Customer_id','$material_code1','$quantity1', '$price1');"; 
}
else if (isset($quantity2)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity3)) {
 $sql = "INSERT INTO sales_order VALUES ('$sales_doc_no', '$Customer_id','$material_code2','$quantity2', '$price2');"; 
}
else if (isset($quantity3)&& !isset($quantity0) && !isset($quantity1) && !isset($quantity2)) {
 $sql = "INSERT INTO sales_order VALUES ('$sales_doc_no', '$Customer_id','$material_code3','$quantity3', '$price3');"; 
}
else if (isset($quantity0, $quantity1) && !isset($quantity2) && !isset($quantity3)) {
$sql = "INSERT INTO sales_order VALUES ";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code0','$quantity0', '$price0'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code1','$quantity1', '$price1');"; 
}
else if (isset($quantity0, $quantity1, $quantity2) && !isset($quantity3)) {
 $sql = "INSERT INTO sales_order VALUES ";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code0','$quantity0', '$price0'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code1','$quantity1', '$price1'),";
$sql .= "('$sales_doc_no', '$Customer_id','$material_code2','$quantity2', '$price2');";
}

$response = array();

if ($conn->query($sql) === TRUE) {
    $response['success']=1;
 	$response['message']="Sales Order Created with number: " . $sales_doc_no;
}else{
 	$response['success']=0;
 	$response['message']=$conn->error;
}
$conn->close();
echo json_encode($response)
?>