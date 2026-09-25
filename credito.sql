-- Ejecuta solo este script si ya tienes creada la base `test`
USE test;

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