<?php
require('fpdf/fpdf.php');
//db connection
$con = mysqli_connect('localhost','yourdb','yourpassword');
mysqli_select_db($con,'id3425739_majorprojectdb');
$reqN = 'matReport.pdf';
//get invoices data
$querym = mysqli_query($con,"SELECT material_code FROM materials WHERE material_type = 'FG'");
$resultm = mysqli_fetch_array($querym);

$pdf = new FPDF('P','mm','A4');
$pdf->AddPage();
$pdf->SetLeftMargin(floatval(25));
$pdf->SetRightMargin(floatval(25));
$pdf->SetTopMargin(floatval(60));
//set font to arial, bold, 14pt
$pdf->SetFont('Helvetica','B',16);
$pdf->Cell(155	,10,'',0,1);//end of line
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
$pdf->Cell(165	,8,'Material-Wise Sales Analysis Report',0,1, 'C');//end of line
$pdf->Cell(155	,10,'',0,1);//end of line
$pdf->Cell(155	,10,'',0,1);//end of line

//invoice contents
$pdf->SetFont('Courier','B',14);
$pdf->Cell(20,10,'Code',1,0,'C');
$pdf->Cell(90,10,'Material',1,0,'C');
$pdf->Cell(25,10,'Quantity',1,0,'C');
$pdf->Cell(30,10,'Price',1,1,'C');

$pdf->SetFont('Courier','',12);

//Numbers are right-aligned so we give 'R' after new line parameter

	while($row = mysqli_fetch_row($querym)){
	$squery = mysqli_query($con,"select material_code, SUM(quantity), SUM(price) from sales_order where material_code = '$row[0]'");
	while($item = mysqli_fetch_row($squery)){
		$exquery = mysqli_query($con,"select material_description from materials where material_code = '$item[0]'");
		$exrow = mysqli_fetch_row($exquery);
		if($item[2]!= 0){
	$pdf->Cell(20	,8,$item[0],1,0);
	$pdf->Cell(90	,8,$exrow[0],1,0);
	$pdf->Cell(25	,8,number_format($item[1]),1,0);
		$pdf->Cell(30	,8,number_format($item[2]),1,1, 'R');}
}
}

$pdf->Cell(150,5,'',0,1);

$pdf->Output($reqN, 'I');
?>	