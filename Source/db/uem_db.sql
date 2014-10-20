-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 19, 2014 at 11:11 PM
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
-- Table structure for table `bay_log`
--

CREATE TABLE IF NOT EXISTS `bay_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(8) DEFAULT NULL,
  `stype` varchar(100) DEFAULT NULL,
  `event` varchar(100) DEFAULT NULL,
  `time_of_change` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Logging of changes made to parking bays' AUTO_INCREMENT=5 ;

--
-- Dumping data for table `bay_log`
--

INSERT INTO `bay_log` (`id`, `identifier`, `stype`, `event`, `time_of_change`) VALUES
(1, '0010', 'STATE', 'false', '2014-10-18 18:10:01'),
(2, '0010', 'STATE', 'false', '2014-10-18 18:10:39'),
(3, '0000', 'STATE', 'false', '2014-10-28 00:00:00'),
(4, '0001', 'STATE', 'false', '2014-10-01 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `parking_bay`
--

CREATE TABLE IF NOT EXISTS `parking_bay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(8) NOT NULL COMMENT 'Unique id to define the sensor location for the parking bay',
  `parking_lot_id` int(11) NOT NULL COMMENT 'Foreign key to relate parking bay to a parking lot',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'State of parking bay: True for bay is occupied, False for bay is vacant',
  `time_of_change` datetime NOT NULL COMMENT 'Records the date and time when a state change has occurred',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifier` (`identifier`),
  KEY `parking_lot_id` (`parking_lot_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Table containing the information related to individual parking bays' AUTO_INCREMENT=57 ;

--
-- Dumping data for table `parking_bay`
--

INSERT INTO `parking_bay` (`id`, `identifier`, `parking_lot_id`, `state`, `time_of_change`) VALUES
(3, '0010', 1, 1, '2014-10-18 18:10:39'),
(4, '0011', 1, 0, '2014-08-13 09:00:00'),
(6, '0100', 1, 1, '2014-10-02 17:18:52'),
(7, '0101', 1, 1, '2014-10-02 17:18:52'),
(8, '0110', 1, 1, '2014-10-02 17:18:52'),
(10, '1101', 1, 1, '2014-10-02 17:18:52'),
(11, '1011', 1, 1, '2014-10-02 17:18:52'),
(21, '0111', 1, 1, '2014-10-03 03:13:00'),
(44, '0000', 1, 1, '2014-10-19 02:07:43'),
(45, '0001', 1, 1, '2014-10-19 02:09:10');

-- --------------------------------------------------------

--
-- Table structure for table `parking_bay_ui`
--

CREATE TABLE IF NOT EXISTS `parking_bay_ui` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(11) NOT NULL DEFAULT '0' COMMENT 'x coordinate for positioning',
  `y` int(11) NOT NULL DEFAULT '0' COMMENT 'y coordinate for positioning',
  `rotation` double NOT NULL DEFAULT '0',
  `parking_bay_id` int(11) NOT NULL COMMENT 'Reference to parking_bay element',
  PRIMARY KEY (`id`),
  UNIQUE KEY `parking_bay_id` (`parking_bay_id`),
  UNIQUE KEY `parking_bay_id_2` (`parking_bay_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=246 ;

--
-- Dumping data for table `parking_bay_ui`
--

INSERT INTO `parking_bay_ui` (`id`, `x`, `y`, `rotation`, `parking_bay_id`) VALUES
(3, 200, 14, 0, 3),
(4, 248, 14, 0, 4),
(8, 296, 14, 0, 6),
(9, 100, 170, 180, 7),
(10, 149, 170, 180, 8),
(15, 198, 170, 180, 10),
(16, 248, 170, 180, 11),
(171, 298, 170, 180, 21),
(224, 102, 14, 0, 44),
(227, 152, 14, 0, 45);

-- --------------------------------------------------------

--
-- Table structure for table `parking_lot`
--

CREATE TABLE IF NOT EXISTS `parking_lot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT 'The name of the defined parking lot',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Table storing information regarding separate parking lots' AUTO_INCREMENT=2 ;

--
-- Dumping data for table `parking_lot`
--

INSERT INTO `parking_lot` (`id`, `name`) VALUES
(1, 'test_lot');

-- --------------------------------------------------------

--
-- Table structure for table `personnel`
--

CREATE TABLE IF NOT EXISTS `personnel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL COMMENT 'Assigned username for login purposes',
  `password` varchar(100) DEFAULT NULL COMMENT 'Assigned password for login purpose',
  `name` varchar(100) NOT NULL COMMENT 'First name of person',
  `surname` varchar(100) NOT NULL COMMENT 'Last name of person',
  `tel` varchar(10) NOT NULL COMMENT 'Contact number of person for administrative purpose',
  `email` varchar(100) NOT NULL COMMENT 'E-mail address of person for administrative purpose',
  `level` enum('0','2','5') DEFAULT '0' COMMENT 'Security clearance level of person: ''0'' user level rights; ''2'' security personnel rights; ''5'' administrative rights',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Table containing data to grant access to system functionality for personnel' AUTO_INCREMENT=4 ;

--
-- Dumping data for table `personnel`
--

INSERT INTO `personnel` (`id`, `username`, `password`, `name`, `surname`, `tel`, `email`, `level`) VALUES
(1, 'admin', 'admin', 'admin', '', '', '', '5'),
(2, 'security', 'security', '', '', '', '', '2'),
(3, 'user', 'user', '', '', '', '', '0');

-- --------------------------------------------------------

--
-- Table structure for table `personnel_lot_manager`
--

CREATE TABLE IF NOT EXISTS `personnel_lot_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personnel_id` int(11) DEFAULT NULL COMMENT 'Foreign key to personnel table',
  `parking_lot_id` int(11) DEFAULT NULL COMMENT 'Foreign key to parking_lot table',
  PRIMARY KEY (`id`),
  KEY `personnel_id` (`personnel_id`),
  KEY `parking_lot_id` (`parking_lot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Table relating multiple personnel to multiple parking lots' AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parking_bay`
--
ALTER TABLE `parking_bay`
  ADD CONSTRAINT `parking_bay_ibfk_1` FOREIGN KEY (`parking_lot_id`) REFERENCES `parking_lot` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `parking_bay_ui`
--
ALTER TABLE `parking_bay_ui`
  ADD CONSTRAINT `parking_bay_ui_ibfk_1` FOREIGN KEY (`parking_bay_id`) REFERENCES `parking_bay` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `personnel_lot_manager`
--
ALTER TABLE `personnel_lot_manager`
  ADD CONSTRAINT `personnel_lot_manager_ibfk_1` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `personnel_lot_manager_ibfk_2` FOREIGN KEY (`parking_lot_id`) REFERENCES `parking_lot` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
