<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Datos del Crédito Capturados</title>
</head>
<body bgcolor="#FDF5E6">
  <h1 align="center">Datos del Crédito Capturados</h1>
  <table border="1" align="center">
    <tr bgcolor="#FFAD00"><th>Nombre del Campo</th><th>Valor del Campo</th></tr>
    <tr><td>Fecha</td><td>${user.fecha}</td></tr>
    <tr><td>Monto</td><td>${user.monto}</td></tr>
    <tr><td>Periodo (meses)</td><td>${user.periodo}</td></tr>
    <tr><td>Moneda</td><td>${user.moneda}</td></tr>
    <tr><td>Cuota</td><td>${user.cuota}</td></tr>
    <tr><td>TEA (%)</td><td>${user.tea}</td></tr>
    <tr><td>Fecha vencimiento</td><td>${user.fechaVencimiento}</td></tr>
  </table>
  <p align="center">El crédito se guardó en la base de datos.</p>
  <p align="center"><a href="userRegistration.htm">Capturar otro crédito</a></p>
</body>
</html>