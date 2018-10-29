<?php
require('fpdf/fpdf.php');
//db connection
$con = mysqli_connect('localhost','yourdb','yourpassword');
mysqli_select_db($con,'id3425739_majorprojectdb');
$reqN = 'cusReport.pdf';
//get invoices data
$queryc = mysqli_query($con,"SELECT Customer_id, Name, City FROM customers");
$resultc = mysqli_fetch_array($queryc);

$pdf = new FPDF('P','mm','A4');
$pdf->AddPage();
$pdf->SetLeftMargin(floatval(20));
$pdf->SetRightMargin(floatval(20));
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
$pdf->Cell(175	,8,'Customer-Wise Sales Analysis Report',0,1, 'C');//end of line
$pdf->Cell(155	,10,'',0,1);//end of line
$pdf->Cell(155	,10,'',0,1);//end of line

//invoice contents
$pdf->SetFont('Courier','B',14);
$pdf->Cell(25,10,'Id',1,0,'C');
$pdf->Cell(90,10,'Name',1,0,'C');
$pdf->Cell(30,10,'City',1,0,'C');
$pdf->Cell(30,10,'Sales',1,1,'C');

$pdf->SetFont('Courier','',12);

//Numbers are right-aligned so we give 'R' after new line parameter

	while($row = mysqli_fetch_row($queryc)){
	$squery = mysqli_query($con,"select SUM(bill_price) from sales_orders_list where Customer = '$row[0]'");
	while($item = mysqli_fetch_row($squery)){
		if($item[0]!= 0){
	$pdf->Cell(25	,8,$row[0],1,0);
	$pdf->Cell(90	,8,$row[1],1,0);
	$pdf->Cell(30	,8,$row[2],1,0);
		$pdf->Cell(30	,8,number_format($item[0]),1,1, 'R');}
}
}
$pdf->Cell(150,5,'',0,1);

$pdf->Output($reqN, 'I');
?>	