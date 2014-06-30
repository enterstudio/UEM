-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2014 at 03:35 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `uem_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking_bay`
--

CREATE TABLE IF NOT EXISTS `parking_bay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(100) NOT NULL COMMENT 'Unique id to define the sensor location for the parking bay',
  `parking_lot_id` int(11) NOT NULL COMMENT 'Foreign key to relate parking bay to a parking lot',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'State of parking bay: True for bay is occupied, False for bay is vacant',
  PRIMARY KEY (`id`),
  KEY `parking_lot_id` (`parking_lot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Table containing the information related to individual parking bays' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `parking_lot`
--

CREATE TABLE IF NOT EXISTS `parking_lot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT 'The name of the defined parking lot',
  `parking_available` int(11) NOT NULL COMMENT 'The amount of parking bays available within the parking lot',
  `parking_occupied` int(11) NOT NULL COMMENT 'The amount of parking bays currently occupied by vehicles within the parking lot',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Table storing information regarding separate parking lots' AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parking_bay`
--
ALTER TABLE `parking_bay`
  ADD CONSTRAINT `parking_bay_ibfk_1` FOREIGN KEY (`parking_lot_id`) REFERENCES `parking_lot` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
