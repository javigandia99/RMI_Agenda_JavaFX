-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-01-2020 a las 20:49:15
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

DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `id` int(11) NOT NULL,
  `name` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `surname` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `telephone` int(16) NOT NULL,
  `movil` int(16) NOT NULL,
  `ref_user` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `contacts`
--

INSERT INTO `contacts` (`id`, `name`, `surname`, `telephone`, `movil`, `ref_user`) VALUES
(1, 'Javier', 'admin', 916704567, 678905436, ''),
(4, 'Marta', 'Velazquez', 987654321, 635678321, ''),
(5, 'Pedro', 'Sanchez', 980765234, 654789098, ''),
(6, 'ty', 'nuevo', 123456789, 987654321, ''),
(7, 'wert', 'wertyu', 12349876, 6543222, ''),
(8, 'Pablo', 'Motos', 12345432, 654321987, ''),
(9, 'Susana', 'Diaz', 654122345, 876987098, 'w'),
(10, 'Jaime', 'Casado', 54321678, 916709832, '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `name` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `surname` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`username`, `password`, `name`, `surname`) VALUES
('1234', '1234', 'Prueba ', '1'),
('Pedro1234', '1234', 'Pedro', 'Camacho Ortega'),
('qwerty', 'qwerty', 'qwerty', 'qwerty'),
('w', 'w', 'Prueba', '2');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contacts`
--
ALTER TABLE `contacts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
