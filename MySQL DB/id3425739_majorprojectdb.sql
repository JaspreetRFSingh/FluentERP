-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 28, 2018 at 09:40 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id3425739_majorprojectdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `access_emp`
--

CREATE TABLE `access_emp` (
  `emp_id` int(11) NOT NULL,
  `sd` tinyint(1) DEFAULT NULL,
  `mm` tinyint(1) DEFAULT NULL,
  `pp` tinyint(1) DEFAULT NULL,
  `hr` tinyint(1) DEFAULT NULL,
  `dd` tinyint(1) DEFAULT NULL,
  `md` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `access_emp`
--

INSERT INTO `access_emp` (`emp_id`, `sd`, `mm`, `pp`, `hr`, `dd`, `md`) VALUES
(10006, 1, 1, 0, 1, 0, 1),
(10007, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `current_stock`
--

CREATE TABLE `current_stock` (
  `material_code` int(11) NOT NULL,
  `stock` bigint(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `current_stock`
--

INSERT INTO `current_stock` (`material_code`, `stock`) VALUES
(3003, 1820),
(3004, 3410),
(3007, 1900),
(3008, 7200),
(3009, 2300),
(3010, 2875),
(3011, 4340),
(3012, 3900),
(3013, 2900),
(3014, 2500);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `Customer_id` bigint(16) NOT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `Address` varchar(200) NOT NULL,
  `City` varchar(20) DEFAULT NULL,
  `Contact` bigint(10) DEFAULT NULL,
  `GST_Number` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`Customer_id`, `Name`, `Address`, `City`, `Contact`, `GST_Number`) VALUES
(987080, 'Garfield Furnishers', '113 SCO 89, Main Market, Gurdev Nagar', 'Ludhiana', 998856423, 'BNAH6810270B907'),
(18001001, 'Ahuja Furnishers', 'Urban Estate Dugri, Main Market, Phase I', 'Ludhiana', 8878912008, '183738192876491'),
(18001002, 'Khalsa Furnishers', 'Near Gill Chowk', 'Ludhiana', 9888767122, '87291026S71B230'),
(18001003, 'Kamal Traders', '35-E BRS Nagar, Ludhiana', 'Ludhiana', 8968761286, '9081267HU78B461'),
(18001004, 'Gurkirpal Hardwares', 'Near Ishmeet Singh Chownk', 'Ludhiana', 9876000004, '10098571735V870'),
(18001005, 'Harish Rai Furnitures', 'Shop No.3, Bus Stand Road', 'Ludhiana', 7009700988, '189625789CB7184'),
(18001006, 'Khurana Furnitures', 'Shop No. 4, Bus Stand Road, Ludhiana', 'Ludhiana', 9872009871, '29122276868A918'),
(18090178, 'Khalsa Hardware Shoppee', 'Railway Station Market', 'Jalandhar', 8054690890, '100976QWK09A899'),
(180090755, 'Hoolika Hardwares', 'SCO89, Backside Oswal Manufacturers, Focal Point', 'Ludhiana', 7009568980, '908292HJB720B79'),
(180090775, 'Darjan Furnitures', 'Shop No.3, Near Bus Stand', 'Ludhiana', 9873605008, '2801720HBJ820B7'),
(180090776, 'Raheja Enterprises', '#34, Shastri Nagar Market', 'Ludhiana', 6987500910, '1002897G78Z1B66'),
(180100744, 'Ramajan Furnishers Pvt. Ltd.', 'SCO-23 Main Market, Jill Park', 'Allahabad', 6978020096, '1097BH910A8B67'),
(180100789, 'Srijan Traders', 'Model Town Market', 'Jalandhar', 9779609875, '435XC23Q898B100'),
(180100959, 'Bombay Cycle House', 'SCO-72, Main Market, Karol Bagh', 'New Delhi', 7973648996, '189BN8968L72A17'),
(180100961, 'Furniture Trendz', 'Jalandhar - Nakodar Rd, Near Sri Guru Ravidass Chowk, Bootan Mandi', 'Jalandhar', 9814328994, '18627XC0918A001'),
(180100963, 'DRS Furniture', 'Phase 1, Bunga Colony, Birring', 'Jalandhar', 9814187670, '281066XC810A80'),
(180100966, 'Evok Furniture Store', 'Plot No. 2439/1284, Kartar Bhawan, Near Ferozepur Road, PAU Road, Gate No.1', 'Ludhiana', 9845673791, '4871OP60581C094');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `emp_id` int(11) NOT NULL,
  `emp_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `emp_address` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `emp_type` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `emp_phone` bigint(10) NOT NULL,
  `dob` date DEFAULT NULL,
  `doj` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`emp_id`, `emp_name`, `emp_address`, `emp_type`, `emp_phone`, `dob`, `doj`) VALUES
(10001, 'Brijesh Chandkumar', 'No. 34, 1st Floor, Janta Nagar, Ludhiana', 'RE-1', 9876009898, '1992-12-02', '2017-01-02'),
(10004, 'Raju', 'Room No. 23, Abdullapur Basti, Ludhiana', 'RE-1', 6890023148, '1995-09-16', '2016-06-24'),
(10005, 'Umkar Yadav', '1787 Second floor, Chhotti Jawaddi, Ludhiana', 'RE-2', 9806915638, '1977-12-23', '2014-07-17'),
(10006, 'Jasjot Singh', '2110 Basant Avenue, Dugri', 'EE-2', 9914802110, '1996-09-30', '2016-05-28'),
(10007, 'Preet Mohinder Singh', '2110 Basant Avenue, Urban Estate Dugri', 'EE-1', 9814102110, '1967-01-14', '1987-05-08');

-- --------------------------------------------------------

--
-- Table structure for table `emp_credentials`
--

CREATE TABLE `emp_credentials` (
  `emp_id` int(11) NOT NULL,
  `empUserName` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `empPassword` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `auth_person` varchar(35) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Jaspreet'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `emp_credentials`
--

INSERT INTO `emp_credentials` (`emp_id`, `empUserName`, `empPassword`, `auth_person`) VALUES
(10004, 'raju', 'raju123', 'Jasjot'),
(10005, 'umkar1yadav', 'umkar123', 'Jasjot'),
(10006, 'jasjot', 'jasjot123', 'Jaspreet Singh'),
(10007, 'preetmohinder', 'preetMoh', 'Jaspreet');

-- --------------------------------------------------------

--
-- Table structure for table `materials`
--

CREATE TABLE `materials` (
  `material_code` int(11) NOT NULL,
  `material_type` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `material_description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `dimensional_unit` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `cost_per_du` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `materials`
--

INSERT INTO `materials` (`material_code`, `material_type`, `material_description`, `dimensional_unit`, `cost_per_du`) VALUES
(3001, 'UG', 'Silicon Rubber', 'mm', 0.29),
(3002, 'UG', 'Polyvinyl Acetate', 'g/cm^', 20),
(3003, 'FG', 'Jevi Bond Waterproof', 'kg', 119.5),
(3004, 'FG', 'Jevi Bond', 'kg', 79.5),
(3005, 'UG', 'Vinyl Acetate Monomer', 'g/ml', 2.34),
(3006, 'UG', 'Starch', 'ml', 1.9),
(3007, 'FG', 'Green Bond', 'kg', 91.5),
(3008, 'FG', 'American Bond', 'kg', 88),
(3009, 'FG', 'American Bond Waterproof', 'kg', 127),
(3010, 'FG', 'J Special Bond', 'kg', 93),
(3011, 'FG', 'J Special Bond Waterproof', 'kg', 129),
(3012, 'FG', 'WG Gum', 'kg', 77),
(3013, 'FG', 'RG Gum', 'kg', 80.5),
(3014, 'FG', 'WP Bond', 'kg', 128);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order`
--

CREATE TABLE `purchase_order` (
  `purchase_doc_no` bigint(30) NOT NULL,
  `s_id` int(12) NOT NULL,
  `material_code` int(11) NOT NULL,
  `quantity` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `purchase_order`
--

INSERT INTO `purchase_order` (`purchase_doc_no`, `s_id`, `material_code`, `quantity`) VALUES
(1810190304, 18001, 3002, 600),
(1810190304, 18001, 3005, 500),
(1810190304, 18001, 3006, 800),
(1810200347, 18002, 3001, 900);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_orders_list`
--

CREATE TABLE `purchase_orders_list` (
  `purchase_doc_no` bigint(30) NOT NULL,
  `s_id` int(12) NOT NULL,
  `date_of_order` date DEFAULT NULL,
  `order_status` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `purchase_orders_list`
--

INSERT INTO `purchase_orders_list` (`purchase_doc_no`, `s_id`, `date_of_order`, `order_status`) VALUES
(1810190252, 18002, '2018-10-19', 'Ordered'),
(1810190254, 18001, '2018-10-19', 'Ordered'),
(1810190304, 18001, '2018-10-19', 'Ordered'),
(1810200347, 18002, '2018-10-20', 'Ordered');

-- --------------------------------------------------------

--
-- Table structure for table `sales_order`
--

CREATE TABLE `sales_order` (
  `sales_doc_no` bigint(30) NOT NULL,
  `Customer_id` bigint(12) NOT NULL,
  `material_code` int(11) NOT NULL,
  `quantity` int(3) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `sales_order`
--

INSERT INTO `sales_order` (`sales_doc_no`, `Customer_id`, `material_code`, `quantity`, `price`) VALUES
(201807241949, 180090776, 3009, 1200, 152400),
(201807241949, 180090776, 3011, 900, 116100),
(201808221946, 18001005, 3003, 300, 35850),
(201808221946, 18001005, 3009, 200, 25400),
(201808221946, 18001005, 3011, 200, 25800),
(201808221946, 18001005, 3013, 350, 27825),
(201808231910, 18001004, 3009, 400, 50800),
(201808231910, 18001004, 3011, 390, 50310),
(201808231910, 18001004, 3012, 380, 29260),
(201808231910, 18001004, 3013, 380, 30210),
(201808232046, 18001003, 3003, 400, 47800),
(201808232046, 18001003, 3004, 400, 31800),
(201808232046, 18001003, 3012, 390, 30030),
(201808232046, 18001003, 3013, 390, 31005),
(201808232124, 18001005, 3003, 390, 46605),
(201808232124, 18001005, 3009, 390, 49530),
(201808232124, 18001005, 3012, 380, 29260),
(201808232124, 18001005, 3013, 380, 30210),
(201808251810, 18001004, 3003, 600, 71700),
(201808251810, 18001004, 3004, 600, 47700),
(201808252029, 18001001, 3004, 500, 39750),
(201808252029, 18001001, 3009, 900, 114300),
(201808252029, 18001001, 3010, 120, 11160),
(201808252029, 18001001, 3013, 570, 45315),
(201808281147, 180100966, 3003, 390, 46605),
(201808281147, 180100966, 3007, 600, 54900),
(201808281147, 180100966, 3009, 900, 114300),
(201808281147, 180100966, 3012, 120, 9240),
(201809051322, 180100789, 3007, 600, 54900),
(201809051322, 180100789, 3012, 500, 38500),
(201809051322, 180100789, 3013, 900, 71550),
(201809091032, 18090178, 3007, 600, 54900),
(201809091032, 18090178, 3011, 500, 64500),
(201809091032, 18090178, 3012, 500, 38500),
(201809091032, 18090178, 3013, 900, 71550),
(201809101333, 18001002, 3003, 600, 71700),
(201809101333, 18001002, 3011, 600, 77400),
(201809101333, 18001002, 3012, 500, 38500),
(201809101333, 18001002, 3013, 200, 15900),
(201809101427, 18001006, 3012, 900, 69300),
(201809101427, 18001006, 3013, 900, 71550),
(201810071207, 18001002, 3003, 1000, 119500),
(201810071207, 18001002, 3009, 1000, 127000),
(201810071330, 180100744, 3003, 1200, 143400),
(201810071330, 180100744, 3009, 900, 114300),
(201810071330, 180100744, 3011, 900, 116100),
(201810071330, 180100744, 3014, 570, 72960),
(201810080041, 180090755, 3004, 950, 75525),
(201810080041, 180090755, 3009, 600, 76200),
(201810080041, 180090755, 3010, 1000, 93000),
(201810170018, 180100966, 3011, 560, 72240),
(201810170018, 180100966, 3012, 580, 44660),
(201810170018, 180100966, 3013, 620, 49910),
(201810170018, 180100966, 3014, 600, 76800),
(201810170023, 180100961, 3012, 800, 61600),
(201810170023, 180100961, 3014, 680, 87040);

-- --------------------------------------------------------

--
-- Table structure for table `sales_orders_list`
--

CREATE TABLE `sales_orders_list` (
  `sales_doc_no` bigint(30) NOT NULL,
  `Customer` bigint(16) NOT NULL,
  `date_of_order` date NOT NULL,
  `bill_price` double NOT NULL,
  `order_status` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `sales_orders_list`
--

INSERT INTO `sales_orders_list` (`sales_doc_no`, `Customer`, `date_of_order`, `bill_price`, `order_status`) VALUES
(201807241949, 180090776, '2018-07-24', 268500, 'Delivered'),
(201808221946, 18001005, '2018-08-22', 114875, 'Delivered'),
(201808231910, 18001004, '2018-08-23', 160580, 'Dispatched'),
(201808232046, 18001003, '2018-08-23', 140635, 'Processing'),
(201808232124, 18001005, '2018-08-23', 155605, 'Dispatched'),
(201808251810, 18001004, '2018-08-25', 119400, 'Dispatched'),
(201808252029, 18001001, '2018-10-25', 210525, 'Created'),
(201808281147, 180100966, '2018-10-25', 225045, 'Created'),
(201809051322, 180100789, '2018-09-05', 164950, 'Processed'),
(201809091032, 18090178, '2018-09-09', 229450, 'Processing'),
(201810071330, 180100744, '2018-10-25', 446760, 'Created'),
(201810080041, 180090755, '2018-10-08', 244725, 'Created'),
(201810170018, 180100966, '2018-10-25', 243610, 'Created'),
(201810170023, 180100961, '2018-10-17', 148640, 'Created');

-- --------------------------------------------------------

--
-- Table structure for table `sellers`
--

CREATE TABLE `sellers` (
  `s_id` int(12) NOT NULL,
  `s_name` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `s_address` varchar(90) COLLATE utf8_unicode_ci NOT NULL,
  `s_city` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `s_gst` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `s_phone` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `sellers`
--

INSERT INTO `sellers` (`s_id`, `s_name`, `s_address`, `s_city`, `s_gst`, `s_phone`) VALUES
(18001, 'Satpal Traders', 'Gill Road, Opposite Arora Palace', 'Ludhiana', '91018JG810B1077', 8556679018),
(18002, 'Khurana Trading Co.', 'Shop No.76, SCO. 23, Near Cheema Chowk', 'Ludhiana', '7191079023O90A6', 7976523570);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access_emp`
--
ALTER TABLE `access_emp`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `current_stock`
--
ALTER TABLE `current_stock`
  ADD PRIMARY KEY (`material_code`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`Customer_id`),
  ADD UNIQUE KEY `GST_Number` (`GST_Number`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `emp_credentials`
--
ALTER TABLE `emp_credentials`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `materials`
--
ALTER TABLE `materials`
  ADD PRIMARY KEY (`material_code`);

--
-- Indexes for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD PRIMARY KEY (`purchase_doc_no`,`material_code`),
  ADD KEY `fk_material_id` (`material_code`),
  ADD KEY `s_id` (`s_id`);

--
-- Indexes for table `purchase_orders_list`
--
ALTER TABLE `purchase_orders_list`
  ADD PRIMARY KEY (`purchase_doc_no`);

--
-- Indexes for table `sales_order`
--
ALTER TABLE `sales_order`
  ADD PRIMARY KEY (`sales_doc_no`,`material_code`),
  ADD KEY `FK_material_code` (`material_code`),
  ADD KEY `Customer_id` (`Customer_id`);

--
-- Indexes for table `sales_orders_list`
--
ALTER TABLE `sales_orders_list`
  ADD PRIMARY KEY (`sales_doc_no`),
  ADD KEY `fk_customer_no` (`Customer`);

--
-- Indexes for table `sellers`
--
ALTER TABLE `sellers`
  ADD PRIMARY KEY (`s_id`),
  ADD UNIQUE KEY `s_gst` (`s_gst`),
  ADD UNIQUE KEY `s_phone` (`s_phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10008;

--
-- AUTO_INCREMENT for table `materials`
--
ALTER TABLE `materials`
  MODIFY `material_code` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3015;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access_emp`
--
ALTER TABLE `access_emp`
  ADD CONSTRAINT `access_emp_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`emp_id`);

--
-- Constraints for table `current_stock`
--
ALTER TABLE `current_stock`
  ADD CONSTRAINT `current_stock_ibfk_1` FOREIGN KEY (`material_code`) REFERENCES `materials` (`material_code`);

--
-- Constraints for table `emp_credentials`
--
ALTER TABLE `emp_credentials`
  ADD CONSTRAINT `emp_credentials_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`emp_id`);

--
-- Constraints for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD CONSTRAINT `fk_material_id` FOREIGN KEY (`material_code`) REFERENCES `materials` (`material_code`),
  ADD CONSTRAINT `fk_purchase_doc` FOREIGN KEY (`purchase_doc_no`) REFERENCES `purchase_orders_list` (`purchase_doc_no`),
  ADD CONSTRAINT `fk_purchase_doc_no` FOREIGN KEY (`purchase_doc_no`) REFERENCES `purchase_orders_list` (`purchase_doc_no`),
  ADD CONSTRAINT `purchase_order_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `sellers` (`s_id`);

--
-- Constraints for table `sales_order`
--
ALTER TABLE `sales_order`
  ADD CONSTRAINT `FK_customer_id` FOREIGN KEY (`Customer_id`) REFERENCES `customers` (`Customer_id`),
  ADD CONSTRAINT `FK_material_code` FOREIGN KEY (`material_code`) REFERENCES `materials` (`material_code`),
  ADD CONSTRAINT `fk_sales_doc_no` FOREIGN KEY (`sales_doc_no`) REFERENCES `sales_orders_list` (`sales_doc_no`),
  ADD CONSTRAINT `sales_order_ibfk_1` FOREIGN KEY (`Customer_id`) REFERENCES `customers` (`Customer_id`);

--
-- Constraints for table `sales_orders_list`
--
ALTER TABLE `sales_orders_list`
  ADD CONSTRAINT `fk_customer_no` FOREIGN KEY (`Customer`) REFERENCES `customers` (`Customer_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
