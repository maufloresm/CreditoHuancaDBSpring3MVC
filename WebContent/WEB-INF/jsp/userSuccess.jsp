<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Datos del Crédito y Pagos Capturados</title>
  <style>
    body {
      background-color: #FDF5E6;
      font-family: Arial, Tahoma, sans-serif;
      margin: 30px;
    }
    h1, h2 {
      color: #2F5FBF;
      text-align: center;
    }
    table {
      border-collapse: collapse;
      margin: 15px auto;
      background-color: #FFFFFF;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    th {
      background-color: #FFAD00;
      color: #333333;
      padding: 8px 12px;
      border: 1px solid #C08000;
      text-align: center;
    }
    td {
      padding: 7px 12px;
      border: 1px solid #D3D3D3;
      text-align: left;
    }
    .tabla-pagos th {
      background-color: #4472C4;
      color: white;
      border: 1px solid #2F5FBF;
    }
    .tabla-pagos td {
      text-align: center;
    }
    .alerta-exito {
      background-color: #D4EDDA;
      color: #155724;
      border: 1px solid #C3E6CB;
      padding: 10px;
      border-radius: 4px;
      width: 70%;
      margin: 20px auto;
      text-align: center;
      font-weight: bold;
    }
    .acciones {
      text-align: center;
      margin-top: 25px;
    }
    .acciones a {
      background-color: #4472C4;
      color: white;
      padding: 8px 16px;
      text-decoration: none;
      border-radius: 4px;
      font-weight: bold;
    }
    .acciones a:hover {
      background-color: #2F5FBF;
    }
  </style>
</head>
<body>
  <h1>Datos del Crédito Capturados</h1>
  
  <table border="1" width="50%">
    <tr><th colspan="2">Resumen de Datos del Crédito</th></tr>
    <tr><td width="50%"><b>Fecha</b></td><td>${creditoForm.fecha}</td></tr>
    <tr><td><b>Monto</b></td><td>${creditoForm.monto}</td></tr>
    <tr><td><b>Periodo (meses)</b></td><td>${creditoForm.periodo}</td></tr>
    <tr><td><b>Moneda</b></td><td>${creditoForm.moneda}</td></tr>
    <tr><td><b>Cuota</b></td><td>${creditoForm.cuota}</td></tr>
    <tr><td><b>TEA (%)</b></td><td>${creditoForm.tea}%</td></tr>
    <tr><td><b>Fecha vencimiento</b></td><td>${creditoForm.fechaVencimiento}</td></tr>
  </table>

  <h2>Pagos Realizados en Tarjetas de Crédito</h2>
  <table class="tabla-pagos" border="1" width="75%">
    <thead>
      <tr>
        <th>#</th>
        <th>Fecha de Pago</th>
        <th>Mes</th>
        <th>Tarjeta de Crédito</th>
        <th>Transacción / Detalle</th>
        <th>Gastos</th>
        <th>Pagos</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${creditoForm.pagosTarjetas}" var="pago" varStatus="status">
        <tr>
          <td>${status.count}</td>
          <td>${pago.fecha}</td>
          <td>${pago.mes}</td>
          <td>${pago.tarjeta}</td>
          <td>${pago.transaccion}</td>
          <td>$ ${pago.gastos}</td>
          <td>$ ${pago.pagos}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="alerta-exito">
    ✓ El crédito y todos los pagos de tarjeta fueron almacenados exitosamente en la base de datos MySQL (Inserción en Lote / Multi-Row).
  </div>

  <div class="acciones">
    <a href="userRegistration.htm">Capturar otro crédito</a>
  </div>
</body>
</html>
