-- MySQL dump 10.13  Distrib 5.6.22, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Create schema ProyectosEscolares
--

CREATE DATABASE IF NOT EXISTS test;
USE test;
--
-- Table structure for table `actividad`
--

DROP TABLE IF EXISTS `actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actividad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `IP` varchar(15) DEFAULT NULL,
  `Pintento` date DEFAULT NULL,
  `intentos` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `edad` varchar(3) NOT NULL,
  `password` varchar(40) NOT NULL,
  `genero` varchar(2) DEFAULT NULL,
  `pais` varchar(2) NOT NULL,
  `about` varchar(200) NOT NULL,
  `comunidad` varchar(30) NOT NULL,
  `lista` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=522 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `credito` (formulario Financiera Huanca)
--

DROP TABLE IF EXISTS `credito`;
CREATE TABLE `credito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `monto` decimal(12,2) NOT NULL,
  `periodo` int(11) NOT NULL,
  `moneda` varchar(10) NOT NULL,
  `cuota` decimal(12,2) NOT NULL,
  `tea` decimal(6,2) NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `pago_tarjeta`
--

DROP TABLE IF EXISTS `pago_tarjeta`;
CREATE TABLE `pago_tarjeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` varchar(20) DEFAULT NULL,
  `mes` varchar(20) DEFAULT NULL,
  `tarjeta` varchar(50) DEFAULT NULL,
  `transaccion` varchar(100) DEFAULT NULL,
  `gastos` decimal(12,2) DEFAULT 0.00,
  `pagos` decimal(12,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Script para crear la base de datos y todas las tablas necesarias
CREATE DATABASE IF NOT EXISTS test;
USE test;

-- Tabla para control de intentos por IP
CREATE TABLE IF NOT EXISTS `actividad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `IP` varchar(15) DEFAULT NULL,
  `Pintento` date DEFAULT NULL,
  `intentos` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabla de créditos (solicitud del crédito - Formulario principal)
CREATE TABLE IF NOT EXISTS `credito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `monto` decimal(12,2) NOT NULL,
  `periodo` int(11) NOT NULL,
  `moneda` varchar(10) NOT NULL,
  `cuota` decimal(12,2) NOT NULL,
  `tea` decimal(6,2) NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabla de pagos de tarjetas de crédito (MultiRow - SIN llave foránea hacia credito)
CREATE TABLE IF NOT EXISTS `pago_tarjeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` varchar(20) DEFAULT NULL,
  `mes` varchar(20) DEFAULT NULL,
  `tarjeta` varchar(50) DEFAULT NULL,
  `transaccion` varchar(100) DEFAULT NULL,
  `gastos` decimal(12,2) DEFAULT 0.00,
  `pagos` decimal(12,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Datos precargados en la tabla de pagos (Fecha, Mes, Tarjeta, Transacción y Gastos)
INSERT INTO `pago_tarjeta` (`id`, `fecha`, `mes`, `tarjeta`, `transaccion`, `gastos`, `pagos`) VALUES
(1, '2026-01-15', 'Enero', 'Visa Clásica', 'Supermercado', 1500.00, 0.00),
(2, '2026-02-10', 'Febrero', 'MasterCard Gold', 'Electrónicos', 3200.50, 0.00),
(3, '2026-03-05', 'Marzo', 'American Express', 'Boletos de avión', 4800.00, 0.00),
(4, '2026-04-18', 'Abril', 'Visa Platinum', 'Restaurantes', 950.00, 0.00)
ON DUPLICATE KEY UPDATE `fecha`=VALUES(`fecha`), `mes`=VALUES(`mes`), `tarjeta`=VALUES(`tarjeta`), `transaccion`=VALUES(`transaccion`), `gastos`=VALUES(`gastos`);
