Equipo:
Flores Muñoz Mauricio 320236589
Joshua Abel Hurtado Aponte 320176900


Tarea 3
Tomamos el formulario de crédito de la Tarea 2 y le agregamos una tabla de pagos de tarjetas de crédito en la parte inferior. 
Al presionar "ACEPTAR", la aplicación guarda los datos del crédito y actualiza los pagos que el usuario capturó 

Cambios:

1. Base de datos (Joshua):
   - Creamos la tabla `pago_tarjeta` (id, fecha, mes, tarjeta, transaccion, gastos, pagos).
   - NO tiene llave foránea ni relación directa con la tabla credito.
   - Se precargaron 4 registros de ejemplo con sus gastos correspondientes.

2. Objeto contenedor (Mauricio):
   - Creamos la clase `PagoTarjeta.java` para representar cada fila de la tabla.
   - En `CreditoForm.java` metimos una lista (`List<PagoTarjeta>`) para envolver tanto los campos del formulario de crédito como las múltiples filas de pagos en un solo objeto.
   - Ajustamos la lista para que crezca de forma automática cuando Spring MVC hace el amarre (binding) de datos.

3. Formulario y vista (Joshua):
   - En `userForm.jsp` pusimos la tabla de tarjetas debajo del formulario. Las columnas de Fecha, Mes, Tarjeta, Concepto y Gastos vienen precargadas y fijas; la única columna que el usuario edita es la de "Pagos".
   - En `userSuccess.jsp` agregamos la tabla para mostrar los pagos que se acaban de registrar junto al resumen del crédito.

4. Procesamiento en lote (Mauricio):
   - implementamos UPDATE
   - Con esto se actualiza el monto de pagos de cada tarjeta por su ID sin duplicar filas al recargar la página.

5. Controlador (Mauricio):
   - En la petición GET carga los pagos que están en la base de datos para mostrarlos en la tabla.
   - En la petición POST valida el formulario, guarda el crédito en la tabla `credito`, actualiza las filas de pagos en lote en `pago_tarjeta` y manda la información a la vista de éxito.


Como ejecutarlo:

1. En MySQL Workbench o consola, corre el script `credito.sql`.
2. Revisa que tu usuario y contraseña en `src/servicio.properties` sean correctos.
3. En Eclipse dale a "Project -> Clean" y corre el proyecto en Tomcat 9 ("Run As -> Run on Server").
4. Llena los datos del crédito, escribe los montos en la columna "Pagos" de las tarjetas y dale a "ACEPTAR".
