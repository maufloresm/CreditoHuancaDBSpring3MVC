<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <title>Financiera Huanca - Solicitud y Pagos de Tarjeta</title>
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
    .tabla-pagos {
       width: 100%;
       border-collapse: collapse;
       margin-top: 10px;
       font-size: 13px;
    }
    .tabla-pagos th {
       background-color: #4472C4;
       color: white;
       padding: 8px 5px;
       text-align: center;
       border: 1px solid #2F5FBF;
    }
    .tabla-pagos td {
       padding: 7px 5px;
       border: 1px solid #CCCCCC;
       background-color: #FFFFFF;
       text-align: center;
    }
    .col-editable input[type="number"] {
       width: 90%;
       padding: 4px 6px;
       border: 2px solid #4472C4;
       background-color: #FFFFEE;
       font-weight: bold;
       font-family: inherit;
       text-align: right;
    }
    .subtitulo-tabla {
       margin-top: 25px;
       margin-bottom: 5px;
       font-weight: bold;
       color: #2F5FBF;
       border-bottom: 2px solid #4472C4;
       padding-bottom: 4px;
    }
    .indicacion {
       font-size: 11px;
       color: #555555;
       margin-bottom: 8px;
    }
  </style>
</head>
<body>
<div class="ventana" style="width: 880px;">
  <div class="titulo">Financiera Huanca</div>
  <div class="tabs">
    <span class="tab activo">Datos del crédito y Pagos de Tarjetas</span>
  </div>
  <div class="contenido">
    <form:form method="POST" action="credito.htm" modelAttribute="creditoForm">
      <div class="fila">
        <span class="btn-etiqueta">Fecha</span>
        <input type="date" name="fecha" lang="es" value="${creditoForm.fecha}" required>
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
        <form:radiobutton path="moneda" value="Soles" />Soles (S/.)&nbsp;&nbsp;
        <form:radiobutton path="moneda" value="Dolares" />Dólares (US$)
        <form:errors path="moneda" cssClass="error" />
      </div>
      <div class="fila">
        <span class="btn-etiqueta">Cuota</span>
        <form:input path="cuota" size="15" />
        &nbsp;&nbsp;
        <span class="btn-etiqueta">TEA</span>
        <form:input path="tea" size="6" /> %
        <form:errors path="cuota" cssClass="error" />
        <form:errors path="tea" cssClass="error" />
      </div>
      <div class="fila">
        <span class="btn-etiqueta">Fecha vencimiento</span>
        <input type="date" name="fechaVencimiento" lang="es" value="${creditoForm.fechaVencimiento}" required>
        <form:errors path="fechaVencimiento" cssClass="error" />
      </div>

      <!-- TABLA DE PAGOS DE TARJETA DE CRÉDITO DEBAJO DEL FORMULARIO (MULTI-ROW) -->
      <div class="subtitulo-tabla">Tabla de Pagos de Tarjetas de Crédito</div>
      <div class="indicacion">Datos precargados. Ingrese y edite los valores en la columna <b>Pagos</b>:</div>
      <table class="tabla-pagos">
        <thead>
          <tr>
            <th width="5%">#</th>
            <th width="14%">Fecha</th>
            <th width="13%">Mes</th>
            <th width="20%">Tarjeta de Crédito</th>
            <th width="24%">Transacción</th>
            <th width="12%">Gastos</th>
            <th width="12%">Pagos (Editar)</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${creditoForm.pagosTarjetas}" var="pago" varStatus="status">
            <tr>
              <td>
                ${status.count}
                <input type="hidden" name="pagosTarjetas[${status.index}].id" value="${pago.id}" />
                <input type="hidden" name="pagosTarjetas[${status.index}].fecha" value="${pago.fecha}" />
                <input type="hidden" name="pagosTarjetas[${status.index}].mes" value="${pago.mes}" />
                <input type="hidden" name="pagosTarjetas[${status.index}].tarjeta" value="${pago.tarjeta}" />
                <input type="hidden" name="pagosTarjetas[${status.index}].transaccion" value="${pago.transaccion}" />
                <input type="hidden" name="pagosTarjetas[${status.index}].gastos" value="${pago.gastos}" />
              </td>
              <td>${pago.fecha}</td>
              <td>${pago.mes}</td>
              <td>${pago.tarjeta}</td>
              <td>${pago.transaccion}</td>
              <td>$ ${pago.gastos}</td>
              <td class="col-editable">
                <input type="number" step="0.01" name="pagosTarjetas[${status.index}].pagos" value="${pago.pagos}" required />
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="aceptar" style="margin-top: 20px;">
        <input type="submit" value="ACEPTAR">
      </div>
    </form:form>
  </div>
</div>
</body>
</html>
