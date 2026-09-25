Equipo
Flores Muñoz Mauricio 320236589
Joshua Abel Hurtado Aponte 320176900


PROYECTO CreditoHuancaDBSpring3MVC

Aplicación web hecha con Spring 3 MVC que muestra el formulario de crédito de
Financiera Huanca (fecha, monto, periodo, moneda, cuota, TEA y fecha de
vencimiento), valida los datos capturados y los guarda en una base de datos
MySQL. Al guardar correctamente muestra una página con los datos del crédito.

El proyecto parte del programa base FormularioDBSpring3MVC (formulario de
registro de usuarios con Spring 3 MVC y MySQL) y del formulario del proyecto
CreditoHuanca (Credito.html con Servlet).


REQUISITOS

1. Java JDK 8 (el proyecto está configurado con JavaSE-1.8).
2. Eclipse IDE for Enterprise Java and Web Developers.
3. Apache Tomcat 9 (se probó con apache-tomcat-9.0.100) dado de alta en
   Eclipse como servidor.
4. MySQL Server 5.6 o superior (probado con MySQL 8) y un cliente como
   MySQL Workbench o la consola de mysql.
5. Librerías (ya incluidas en WebContentWEB-INFlib, no hay que descargarlas)
   Spring Framework 3.0 (beans, context, core, web, web.servlet, jdbc, tx,
   expression, asm), mysql-connector-java-8.0.18.jar,
   commons-logging-1.0.4.jar y antlr-runtime-3.0.jar.
6. Un navegador que soporte campos de tipo fecha (Chrome, Edge o Firefox).


ESTRUCTURA DEL PROYECTO

FormularioDBSpring3MVC
    BD Formulario.sql            Script completo de la base test (tablas
                                 actividad, usuario y credito).
    credito.sql                  Script que solo crea la tabla credito.
    README.txt                   Este archivo.
    src
        Spring-Datasource.xml    Lee servicio.properties y crea el bean con
                                 los datos de conexión a MySQL.
        servicio.properties      URL de la base, usuario y contraseña de MySQL.
        messages.properties      Mensajes de error de la validación.
        control
            UserController.java         Muestra el formulario (GET) y recibe
                                        los datos (POST) en userRegistration.htm.
                                        Controla el número de intentos por IP.
            UserSuccessController.java  Muestra la página de éxito
                                        (userSuccess.htm).
        dominio
            User.java            Objeto con los datos del crédito.
            BDMySql.java         Objeto con los datos de conexión.
        validator
            UserValidator.java   Validaciones del formulario de crédito.
        servicio
            UserService.java     Interfaz del servicio.
            UserServiceImpl.java Imprime los datos en consola y llama a la
                                 capa de base de datos.
        baseDeDatos
            UsuariosJDBCTemplate.java  Conexión a MySQL, inserción del crédito
                                       y registro de intentos por IP.
    WebContent
        redirect.jsp             Página de inicio, redirige a
                                 userRegistration.htm.
        styles
            estilos.css          Estilos del formulario de Financiera Huanca.
        WEB-INF
            web.xml              Configura el DispatcherServlet de Spring para
                                 las rutas .htm.
            dispatcher-servlet.xml  Configuración de Spring MVC (vistas,
                                    mensajes, controladores, servicio y
                                    validador).
            lib                 Librerías de Spring y del conector de MySQL.
            jsp
                userForm.jsp     Formulario de crédito.
                userSuccess.jsp  Tabla con los datos del crédito guardado.
                Exceso.jsp       Página que se muestra cuando se exceden los
                                 intentos permitidos.


QUÉ SE AGREGÓ O MODIFICÓ RESPECTO A LA BASE FormularioDBSpring3MVC

1. dominioUser.java
   Se cambiaron los campos del usuario (nombre, edad, password, género, país,
   sobre ti, comunidad, lista de correo) por los del crédito fecha, monto,
   periodo, moneda, cuota, tea y fechaVencimiento.

2. WEB-INFjspuserForm.jsp
   Se reemplazó el formulario de registro por el formulario de crédito de
   Financiera Huanca, con el mismo diseño de Credito.html, pero usando las
   etiquetas form de Spring para mostrar los errores de validación junto a
   cada campo.

3. WebContentstylesestilos.css (archivo nuevo)
   Hoja de estilos copiada del proyecto CreditoHuanca.

4. validatorUserValidator.java
   Nuevas validaciones todos los campos son obligatorios, monto, cuota y TEA
   deben ser números mayores que 0, el periodo debe ser un número entero de
   meses mayor que 0, la moneda debe ser Soles o Dolares, las fechas deben ser
   válidas y la fecha de vencimiento debe ser posterior a la fecha del
   crédito.

5. messages.properties
   Mensajes de error para los nuevos campos.

6. servicioUserServiceImpl.java
   Ahora imprime en la consola los datos del crédito antes de guardarlo.

7. baseDeDatosUsuariosJDBCTemplate.java
   Se modificó el método insertarU para que inserte en la tabla credito con
   un PreparedStatement. Los métodos de conexión y de control de intentos por
   IP (getIntentos y addIntento) se dejaron igual.

8. WEB-INFjspuserSuccess.jsp
   Ahora muestra una tabla con los datos del crédito capturado y un enlace
   para capturar otro crédito.

9. BD Formulario.sql y credito.sql
   Se agregó la tabla credito a la base de datos test
       id                 int, llave primaria, autoincremental
       fecha              date
       monto              decimal(12,2)
       periodo            int
       moneda             varchar(10)
       cuota              decimal(12,2)
       tea                decimal(6,2)
       fecha_vencimiento  date

No se modificaron web.xml, dispatcher-servlet.xml, Spring-Datasource.xml,
UserController.java, UserSuccessController.java ni UserService.java.


CONFIGURACIÓN DE LA BASE DE DATOS

1. Iniciar el servidor de MySQL.
2. Abrir MySQL Workbench (o la consola de mysql) con el usuario root.
3. Ejecutar uno de los dos scripts
   a) BD Formulario.sql si la base test no existe o se quiere crear desde
      cero. Cuidado este script borra y vuelve a crear las tablas actividad,
      usuario y credito, así que se pierden los datos que tengan.
   b) credito.sql si ya existe la base test y solo falta la tabla credito.
4. Verificar con
       USE test;
       SHOW TABLES;


DÓNDE SE PONE EL USUARIO Y LA CONTRASEÑA DE MYSQL

Los datos de conexión están en el archivo srcservicio.properties

    urlBD=jdbcmysqllocalhost3306testserverTimezone=GMT
    user=root
    password=

user es el usuario de MySQL (por defecto root).
password es la contraseña de ese usuario. Si el root de MySQL tiene
contraseña, se escribe después del signo igual, sin espacios ni comillas.
Por ejemplo

    password=miContrasena

Si MySQL usa otro puerto distinto de 3306 o la base tiene otro nombre, se
cambia en urlBD. Después de modificar este archivo hay que hacer
Project, Clean en Eclipse y reiniciar Tomcat para que tome los cambios.


CÓMO SE EJECUTA EN ECLIPSE

1. Abrir Eclipse.
2. File, Import, General, Existing Projects into Workspace y seleccionar la
   carpeta FormularioDBSpring3MVC.
3. Si el proyecto marca error por el servidor, ir a clic derecho sobre el
   proyecto, Properties, Targeted Runtimes y seleccionar Apache Tomcat v9.0.
   Revisar también en Java Build Path que se use un JRE de Java 8 o superior.
4. Crear la base de datos como se explica en CONFIGURACIÓN DE LA BASE DE
   DATOS y poner la contraseña de root en servicio.properties.
5. Project, Clean para compilar el proyecto.
6. Clic derecho sobre el proyecto, Run As, Run on Server y elegir Tomcat 9.
7. Se abre el navegador en
       httplocalhost8080ValidacionFormularioSpring3MVC
   (este es el context root configurado en Eclipse; también se puede entrar
   directo a ...ValidacionFormularioSpring3MVCuserRegistration.htm)
8. Llenar el formulario y dar clic en ACEPTAR.
   Si hay errores se muestran en rojo junto al campo.
   Si todo es correcto se guarda el crédito y se muestra la tabla con los
   datos capturados.
9. Para comprobar que se guardó en MySQL
       SELECT  FROM test.credito;


NOTAS

1. El programa base limita los envíos por dirección IP después de 5 envíos
   correctos en el mismo día muestra la página Exceso.jsp. Para seguir
   probando se puede borrar el registro de la IP con
       DELETE FROM test.actividad;
   o cambiar el valor de MaxIntentos en controlUserController.java.
2. Si en la consola de Eclipse aparece Access denied for user 'root', la
   contraseña de servicio.properties no es correcta.
3. Si aparece Communications link failure, el servidor de MySQL no está
   encendido o el puerto de urlBD es incorrecto.
4. Si aparece Table 'test.credito' doesn't exist, falta ejecutar
   credito.sql.