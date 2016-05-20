-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2016 at 12:36 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chatapplication`
--

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId1` int(11) NOT NULL,
  `UserId2` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`Id`, `UserId1`, `UserId2`) VALUES
(15, 14, 13),
(16, 13, 14),
(17, 13, 16),
(18, 13, 15),
(19, 13, 15),
(20, 17, 13),
(21, 17, 13),
(22, 13, 17);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SenderId` int(11) NOT NULL,
  `ReceiverId` int(11) NOT NULL,
  `Message` mediumtext NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=70 ;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`Id`, `SenderId`, `ReceiverId`, `Message`) VALUES
(65, 14, 13, 'Hi Sandesh'),
(66, 14, 13, 'How are you'),
(67, 13, 14, 'I am fine Ram'),
(68, 13, 17, 'hello dude'),
(69, 17, 13, 'hello');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Id`, `Username`, `Password`, `status`) VALUES
(13, 'sandesh', 'sandesh', 0),
(14, 'ram', 'ram', 0),
(15, 'hari', 'hari', 0),
(16, 'sita', 'sita', 0),
(17, 'sagar', 'sagar', 0);

-- --------------------------------------------------------

--
-- Table structure for table `userinfo`
--

CREATE TABLE IF NOT EXISTS `userinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `StudentId` int(10) NOT NULL,
  `Age` int(3) NOT NULL,
  `Sex` varchar(10) NOT NULL,
  `UserId` int(5) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `userinfo`
--

INSERT INTO `userinfo` (`Id`, `FirstName`, `LastName`, `StudentId`, `Age`, `Sex`, `UserId`) VALUES
(12, 'Sandesh', 'Sharma', 101, 14, 'Male', 13),
(13, 'Ram', 'Kumar', 102, 19, 'Male', 14),
(14, 'Hari', 'Prasad', 103, 21, 'Male', 15),
(15, 'Sita', 'Devkota', 105, 19, 'Female', 16),
(16, 'Sagar', 'Giri', 111, 19, 'Male', 17);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
