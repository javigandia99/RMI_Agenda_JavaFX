-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-01-2020 a las 16:25:10
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agenda`
--
CREATE DATABASE IF NOT EXISTS `agenda` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `agenda`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacts`
--
-- Creación: 04-01-2020 a las 18:06:38
--

DROP TABLE IF EXISTS `contacts`;
CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `surname` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `telephone` int(16) NOT NULL,
  `movil` int(16) NOT NULL,
  `ref_user` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- RELACIONES PARA LA TABLA `contacts`:
--   `ref_user`
--       `users` -> `username`
--

--
-- Volcado de datos para la tabla `contacts`
--

INSERT INTO `contacts` (`id`, `name`, `surname`, `telephone`, `movil`, `ref_user`) VALUES
(9, 'Susana', 'Diaz', 654122345, 876987098, 'w'),
(10, 'Jaime', 'Casado', 54321678, 916709832, '1234'),
(11, 'we', 'we', 7654, 321098, 'w'),
(12, 'Prueba', 'final', 90876543, 654321789, 'w'),
(13, 'prueba', 'de javi', 987654321, 123456789, 'javi'),
(14, 'prueba', 'de qwerty', 987654321, 123456776, 'qwerty'),
(15, 'de qwerty', 'prueba 2', 98765432, 67890543, 'qwerty'),
(16, 'movil no ', 'cambiar (igual)', 123456789, 4321, 'w'),
(19, 'prueba', 'de xx', 654321789, 914356778, 'xx'),
(20, 'movil clave', 'no func si change', 987654324, 646122321, 'w'),
(21, 'movil clave', 'movil fijo', 987650987, 656433212, 'w'),
(25, 'Marta', 'Velazquez', 915678745, 632123545, 'Pedro1234'),
(26, 'Pedro', 'Camacho Ortega', 917894321, 676544578, 'Pedro1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--
-- Creación: 17-12-2019 a las 11:46:23
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `name` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `surname` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- RELACIONES PARA LA TABLA `users`:
--

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`username`, `password`, `name`, `surname`) VALUES
('1234', '1234', 'Prueba ', '1'),
('javi', '54321', 'Javi', 'Gandia'),
('Pedro1234', 'pedro1234', 'Pedro', 'Camacho Ortega'),
('qwerty', 'qwerty', 'qwerty', 'qwerty'),
('w', 'w', 'Prueba', '2');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
