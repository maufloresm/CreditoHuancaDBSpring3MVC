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

-- Tabla de créditos (solicitud del crédito)
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
