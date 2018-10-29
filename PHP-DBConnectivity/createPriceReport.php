<?php
require('fpdf/fpdf.php');
//db connection
$conn = mysqli_connect('localhost','yourdb','yourpassword');
mysqli_select_db($conn,'id3425739_majorprojectdb');
$reqN = 'priceReport.pdf';
//get invoices data
$sql1 = "SELECT bill_price FROM sales_orders_list where bill_price < 50000";
$result1 = $conn->query($sql1);
$count1 = mysqli_num_rows($result1);
$sql2 = "SELECT bill_price FROM sales_orders_list where bill_price BETWEEN 50000 AND 100000";
$result2 = $conn->query($sql2);
$count2 = mysqli_num_rows($result2);
$sql3 = "SELECT bill_price FROM sales_orders_list where bill_price BETWEEN 100000 AND 150000";
$result3 = $conn->query($sql3);
$count3 = mysqli_num_rows($result3);
$sql4 = "SELECT bill_price FROM sales_orders_list where bill_price BETWEEN 150000 AND 200000";
$result4 = $conn->query($sql4);
$count4 = mysqli_num_rows($result4);
$sql5 = "SELECT bill_price FROM sales_orders_list where bill_price BETWEEN 200000 AND 250000";
$result5 = $conn->query($sql5);
$count5 = mysqli_num_rows($result5);
$sql6 = "SELECT bill_price FROM sales_orders_list where bill_price BETWEEN 250000 AND 300000";
$result6 = $conn->query($sql6);
$count6 = mysqli_num_rows($result6);
$sql7 = "SELECT bill_price FROM sales_orders_list where bill_price > 300000";
$result7 = $conn->query($sql7);
$count7 = mysqli_num_rows($result7);

$pdf = new FPDF('P','mm','A4');
$pdf->AddPage();
$pdf->SetLeftMargin(floatval(40));
$pdf->SetRightMargin(floatval(40));
$pdf->SetTopMargin(floatval(60));
//set font to arial, bold, 14pt
$pdf->SetFont('Helvetica','B',16);
$pdf->Cell(175	,10,'',0,1);//end of line
//Cell(width , height , text , border , end line , [align] )
$pdf->Cell(90	,8,'P.S. Enterprises',0,1);//end of line

//set font to arial, regular, 12pt
$pdf->SetFont('Helvetica','',12);

$pdf->Cell(90	,8,'Industrial Area, Focal Point',0,0);
$pdf->Cell(65	,8,'',0,1);//end of line
$pdf->Cell(90	,8,'Ludhiana, Punjab, India',0,1);
$pdf->Cell(90	,8,'Phone +919914802110',0,1);
$pdf->Cell(155,10,'',0,1);//end of line

$pdf->SetFont('Helvetica','B',16);
$pdf->Cell(130	,8,'Range of Prices Sales Analysis Report',0,1, 'C');//end of line
$pdf->Cell(155	,10,'',0,1);//end of line
$pdf->Cell(155	,10,'',0,1);//end of line

//invoice contents
$pdf->SetFont('Courier','B',14);
$pdf->Cell(90,10,'Range',1,0,'C');
$pdf->Cell(40,10,'Number of Sales',1,1,'C');

$pdf->SetFont('Courier','',12);

//Numbers are right-aligned so we give 'R' after new line parameter


	$pdf->Cell(80	,8,'Less than 50,000',1,0);
	$pdf->Cell(50	,8,$count1,1,1);
	$pdf->Cell(80	,8,'50,000 - 1,00,000',1,0);
	$pdf->Cell(50	,8,$count2,1,1);
	$pdf->Cell(80	,8,'1,00,000 - 1,50,000',1,0);
	$pdf->Cell(50	,8,$count3,1,1);
	$pdf->Cell(80	,8,'1,50,000 - 2,00,000',1,0);
	$pdf->Cell(50	,8,$count4,1,1);
	$pdf->Cell(80	,8,'2,00,000 - 2,50,000',1,0);
	$pdf->Cell(50	,8,$count5,1,1);
	$pdf->Cell(80	,8,'2,50,000 - 3,00,000',1,0);
	$pdf->Cell(50	,8,$count6,1,1);
	$pdf->Cell(80	,8,'More than 3,00,000',1,0);
	$pdf->Cell(50	,8,$count7,1,1);
$pdf->Cell(135,5,'',0,1);

$pdf->Output($reqN, 'I');
?>	