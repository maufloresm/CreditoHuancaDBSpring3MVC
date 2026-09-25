<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <title>Financiera Huanca</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/estilos.css">
  <style>
    .error {
       color: #ff0000;
       font-style: italic;
       font-size: 12px;
       display: block;
       margin-top: 4px;
     }
  </style>
</head>
<body>
<div class="ventana">
  <div class="titulo">Financiera Huanca</div>
  <div class="tabs">
    <span class="tab activo">Datos del crédito</span>
  </div>
  <div class="contenido">
    <form:form method="POST" commandName="user">
      <div class="fila">
        <span class="btn-etiqueta">Fecha</span>
        <input type="date" name="fecha" lang="es" value="${user.fecha}" required>
        <form:errors path="fecha" cssClass="error" />
      </div>
      <div class="fila">
        <label class="campo">Moneda</label>
        Monto
        <form:input path="monto" size="12" />
        &nbsp;&nbsp;&nbsp;Periodo
        <form:input path="periodo" size="4" />
        meses
        <form:errors path="monto" cssClass="error" />
        <form:errors path="periodo" cssClass="error" />
      </div>
      <div class="fila">
        <form:radiobutton path="moneda" value="Soles" />Soles (S/.)<br>
        <form:radiobutton path="moneda" value="Dolares" />Dólares (US$)
        <form:errors path="moneda" cssClass="error" />
      </div>
      <div class="fila">
        <span class="btn-etiqueta">Cuota</span>
        <form:input path="cuota" size="15" />
        <span class="btn-etiqueta">TEA</span>
        <form:input path="tea" size="6" /> %
        <form:errors path="cuota" cssClass="error" />
        <form:errors path="tea" cssClass="error" />
      </div>
      <div class="fila">
        <span class="btn-etiqueta">Fecha vencimiento</span>
        <input type="date" name="fechaVencimiento" lang="es" value="${user.fechaVencimiento}" required>
        <form:errors path="fechaVencimiento" cssClass="error" />
      </div>
      <div class="aceptar">
        <input type="submit" value="ACEPTAR">
      </div>
    </form:form>
  </div>
</div>
</body>
</html>