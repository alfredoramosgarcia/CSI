-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 21-11-2022 a las 17:28:34
-- Versión del servidor: 8.0.17
-- Versión de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `richard`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajador`
--

CREATE TABLE `trabajador` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `trabajador`
--

INSERT INTO `trabajador` (`Id`, `Nombre`) VALUES
(16, '12'),
(13, 'Alberto'),
(14, 'Alfredo'),
(1, 'Atlas'),
(3, 'Hiperión'),
(15, 'Jesu'),
(11, 'Manolin'),
(12, 'Pablo'),
(2, 'Pontos');

--
-- Disparadores `trabajador`
--
DELIMITER $$
CREATE TRIGGER `trabajador_bi` BEFORE INSERT ON `trabajador` FOR EACH ROW begin
if NEW.Nombre = '' then
signal sqlstate '45000' set 
message_text = 'Nombre no puede ser cadena vacía.';
end if;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trabajador_bu` BEFORE UPDATE ON `trabajador` FOR EACH ROW begin
if NEW.Nombre = '' then
signal sqlstate '45000' set 
message_text = 'Nombre no puede ser cadena vacía.';
end if;
end
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `trabajador`
--
ALTER TABLE `trabajador`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Nombre` (`Nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `trabajador`
--
ALTER TABLE `trabajador`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;