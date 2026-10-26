-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         12.0.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para gestion_nominas
CREATE DATABASE IF NOT EXISTS `gestion_nominas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `gestion_nominas`;

-- Volcando estructura para tabla gestion_nominas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `sexo` char(1) DEFAULT NULL CHECK (`sexo` in ('H','M')),
  `categoria` int(11) DEFAULT NULL CHECK (`categoria` between 1 and 10),
  `anyos` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla gestion_nominas.empleados: ~3 rows (aproximadamente)
INSERT INTO `empleados` (`id`, `nombre`, `dni`, `sexo`, `categoria`, `anyos`) VALUES
	(30, 'James Cosling', '32000232G', 'M', 2, 7),
	(31, 'Ada Lovelace', '32000031R', 'H', 2, 2),
	(32, 'ale', '32000041A', 'H', 10, 5);

-- Volcando estructura para tabla gestion_nominas.nominas
CREATE TABLE IF NOT EXISTS `nominas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empleado_id` int(11) NOT NULL,
  `sueldo` double NOT NULL,
  `fecha_calculo` date DEFAULT curdate(),
  PRIMARY KEY (`id`),
  KEY `empleado_id` (`empleado_id`),
  CONSTRAINT `nominas_ibfk_1` FOREIGN KEY (`empleado_id`) REFERENCES `empleados` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla gestion_nominas.nominas: ~3 rows (aproximadamente)
INSERT INTO `nominas` (`id`, `empleado_id`, `sueldo`, `fecha_calculo`) VALUES
	(26, 30, 105000, '2026-10-26'),
	(27, 31, 80000, '2026-10-26'),
	(28, 32, 255000, '2026-10-26');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
