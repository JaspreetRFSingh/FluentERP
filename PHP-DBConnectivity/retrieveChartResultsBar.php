<?php

error_reporting(E_ERROR|E_PARSE);
$var1 = $_POST['current_date'];
$var2 = $var1 - 1;
$var3 = $var1 - 2;
$var4 = $var1 - 3;
$var5 = $var1 - 4;
$var6 = $var1 - 4;

$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_db";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
 
 
$sql_one = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var1%')";
$sql_two = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var2%')";
$sql_three = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var3%')";
$sql_four = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var4%')";
$sql_five = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var5%')";
$sql_six = "SELECT SUM(bill_price) FROM sales_orders_list WHERE bill_price IN (SELECT bill_price FROM sales_orders_list WHERE sales_doc_no LIKE '$var6%')";
$response = array();

$result_one = $conn->query($sql_one);
if(mysqli_num_rows($result_one)>0){

	$response['orders_one'] = array();
	while($row=mysqli_fetch_array($result_one)){
		array_push($response['orders_one'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
$result_two = $conn->query($sql_two);
if(mysqli_num_rows($result_two)>0){

	$response['orders_two'] = array();
	while($row=mysqli_fetch_array($result_two)){
		array_push($response['orders_two'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
$result_three = $conn->query($sql_three);
if(mysqli_num_rows($result_three)>0){

	$response['orders_three'] = array();
	while($row=mysqli_fetch_array($result_three)){
		array_push($response['orders_three'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
$result_four = $conn->query($sql_four);
if(mysqli_num_rows($result_four)>0){

	$response['orders_four'] = array();
	while($row=mysqli_fetch_array($result_four)){
		array_push($response['orders_four'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
$result_five = $conn->query($sql_five);
if(mysqli_num_rows($result_five)>0){

	$response['orders_five'] = array();
	while($row=mysqli_fetch_array($result_five)){
		array_push($response['orders_five'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
$result_six = $conn->query($sql_six);
if(mysqli_num_rows($result_six)>0){

	$response['orders_six'] = array();
	while($row=mysqli_fetch_array($result_six)){
		array_push($response['orders_six'], $row);
	}
	$response['success']=1;
 	$response['message']="Records retrieved successfully!";
 	
}else{
 	$response['success']=0;
 	$response['message']="No records found!";
 }
 
echo json_encode($response);
?>