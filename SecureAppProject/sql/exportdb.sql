CREATE DATABASE  IF NOT EXISTS `project`;
USE `project`;

DROP TABLE IF EXISTS `Staff`;

CREATE TABLE `Staff` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(30) NOT NULL UNIQUE,
  `UserPass` varchar(30) NOT NULL,
  `Pin` varchar(4) NOT NULL default '0000',
  `Designation` varchar(10) NOT NULL,
  `Email` varchar(30) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO `Staff` VALUES 
	(1,'admin','password',1234,'admin',false,'admin@gmail.com'),
    (2,'user','password',1111,'user',false,'user@gmail.com'),
    (3,'super','password',2222,'super',false,'admin@gmail.com');

select * from Staff;
select Designation from Staff where UserName='admin' and UserPass='password';

DROP TABLE IF EXISTS `Books`;
CREATE TABLE `Books` (
    `title` varchar(45) NOT NULL,
    `author` varchar(45) NOT NULL,
    `bookRefID` varchar(45)NOT NULL,
    `year` int(4) NOT NULL,
    `pages` int(6) NOT NULL,
    PRIMARY KEY (`bookRefID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
INSERT INTO `Books` VALUES
('The Witcher','Emliy Blonde','$URJYRLU1','1991','4600'),
('Don Quixote','Miguel de Cervantes','#PPG7Q5E5','1605','928'),
('Lord of the Rings','J.R.R. Tolkien','#BNDGYQO9','1954','1178'),
('Harry Potter and the Sorcerer Stone','J.K. Rowling','@WUAP6A8','1997','223'),
('And Then There Were None','Agatha Christie','#IS78FLI2','1939','272'),
('Alice Adventures in Wonderland','Lewis Carroll','@DQ4HD6S7','1865','200'),
('The Lion, the Witch, and the Wardrobe','C.S. Lewis','#ZX1RMRC5','1950','208'),
('Pinocchio','Carlo Collodi','#KVY0UBM3','1883','224'),
('Catcher in the Rye','J.D. Salinger','#6TV9TW6','1951','277'),
('Anne of Green Gables','L. M. Montgomery','#R1SI1HG4','1908','284');
select * from Books;

DROP TABLE IF EXISTS `IssuedBooks`;
CREATE TABLE `IssuedBooks`(

    `title` varchar(45) NOT NULL,
    `author` varchar(45) NOT NULL,
    `bookRefID` varchar(45)NOT NULL,
    `year` int(4) NOT NULL,
    `pages` int(6) NOT NULL,
    PRIMARY KEY (`bookRefID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
select * from IssuedBooks;