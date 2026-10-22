package baseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.naming.NamingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;
import dominio.BDMySql;
import dominio.CreditoForm;
import dominio.PagoTarjeta;
import dominio.User;

public class UsuariosJDBCTemplate extends SimpleJdbcDaoSupport {

    MysqlDataSource ds = null;
    Connection connect = null;
    Statement statement = null;
    BDMySql bdMySql;

    private static final String SQL_INSERT_CREDITO =
            "INSERT INTO credito (fecha, monto, periodo, moneda, cuota, tea, fecha_vencimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_PAGO =
            "UPDATE pago_tarjeta SET pagos = ? WHERE id = ?";

    private static final String SQL_SELECT_PAGOS =
            "SELECT id, fecha, mes, tarjeta, transaccion, gastos, pagos FROM pago_tarjeta ORDER BY id ASC";

    class PagoTarjetaRowMapper implements ParameterizedRowMapper<PagoTarjeta> {
        public PagoTarjeta mapRow(ResultSet rs, int rowNum) throws SQLException {
            PagoTarjeta p = new PagoTarjeta();
            p.setId(rs.getInt("id"));
            p.setFecha(rs.getString("fecha"));
            p.setMes(rs.getString("mes"));
            p.setTarjeta(rs.getString("tarjeta"));
            p.setTransaccion(rs.getString("transaccion"));
            p.setGastos(rs.getDouble("gastos"));
            p.setPagos(rs.getDouble("pagos"));
            return p;
        }
    }

    public Connection getConexion() {
        try {
            if (getDataSource() != null) {
                return getDataSource().getConnection();
            }
            ds = new MysqlDataSource();
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Datasource.xml");
            BDMySql bdMySql = (BDMySql) context.getBean("servicioPropiedades");

            ds.setUrl(bdMySql.getUrlBD());
            ds.setUser(bdMySql.getUser());
            ds.setPassword(bdMySql.getPassword());
            connect = ds.getConnection();
            return connect;
        } catch (SQLException error) {
            System.out.println("Error en getConexion(): " + error.toString());
            return connect;
        }
    }

    public void insertarU(User credito) throws NamingException {
        Connection connect = null;
        PreparedStatement ps = null;
        try {
            connect = getConexion();
            ps = connect.prepareStatement(SQL_INSERT_CREDITO);
            ps.setDate(1, java.sql.Date.valueOf(credito.getFecha().trim()));
            ps.setBigDecimal(2, new java.math.BigDecimal(credito.getMonto().trim()));
            ps.setInt(3, Integer.parseInt(credito.getPeriodo().trim()));
            ps.setString(4, credito.getMoneda());
            ps.setBigDecimal(5, new java.math.BigDecimal(credito.getCuota().trim()));
            ps.setBigDecimal(6, new java.math.BigDecimal(credito.getTea().trim()));
            ps.setDate(7, java.sql.Date.valueOf(credito.getFechaVencimiento().trim()));
            ps.executeUpdate();
        } catch (SQLException error) {
            System.out.println("Error al insertar User: " + error.toString());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException error) { System.out.println("Error Statement: " + error.toString()); }
            try { if (connect != null) connect.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
        }
    }

    public void guardarCredito(CreditoForm credito) {
        SimpleJdbcTemplate sjdbc = getSimpleJdbcTemplate();
        if (sjdbc != null) {
            sjdbc.update(SQL_INSERT_CREDITO,
                    credito.getFecha(),
                    credito.getMonto(),
                    credito.getPeriodo(),
                    credito.getMoneda(),
                    credito.getCuota(),
                    credito.getTea(),
                    credito.getFechaVencimiento());
        } else {
            Connection connect = null;
            PreparedStatement ps = null;
            try {
                connect = getConexion();
                ps = connect.prepareStatement(SQL_INSERT_CREDITO);
                ps.setString(1, credito.getFecha());
                ps.setDouble(2, credito.getMonto() != null ? credito.getMonto() : 0.0);
                ps.setInt(3, credito.getPeriodo() != null ? credito.getPeriodo() : 0);
                ps.setString(4, credito.getMoneda());
                ps.setDouble(5, credito.getCuota() != null ? credito.getCuota() : 0.0);
                ps.setDouble(6, credito.getTea() != null ? credito.getTea() : 0.0);
                ps.setString(7, credito.getFechaVencimiento());
                ps.executeUpdate();
            } catch (SQLException error) {
                System.out.println("Error al guardar CreditoForm: " + error.toString());
            } finally {
                try { if (ps != null) ps.close(); } catch (SQLException error) { System.out.println("Error Statement: " + error.toString()); }
                try { if (connect != null) connect.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
            }
        }
    }

    /**
     * Actualiza en lote (Batch Update) el valor de 'pagos' de cada tarjeta en la tabla pago_tarjeta,
     * exactamente igual que modificaProyecto en el ejemplo MultiRowDBSpring3MVC.
     */
    public void actualizarPagosBatch(List<PagoTarjeta> pagos) {
        if (pagos == null || pagos.isEmpty()) {
            return;
        }

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (PagoTarjeta p : pagos) {
            batchArgs.add(new Object[] {
                p.getPagos() != null ? p.getPagos() : 0.0,
                p.getId()
            });
        }

        SimpleJdbcTemplate sjdbc = getSimpleJdbcTemplate();
        if (sjdbc != null) {
            sjdbc.batchUpdate(SQL_UPDATE_PAGO, batchArgs);
        } else {
            Connection connect = null;
            PreparedStatement ps = null;
            try {
                connect = getConexion();
                connect.setAutoCommit(false);
                ps = connect.prepareStatement(SQL_UPDATE_PAGO);
                for (PagoTarjeta p : pagos) {
                    ps.setDouble(1, p.getPagos() != null ? p.getPagos() : 0.0);
                    ps.setInt(2, p.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
                connect.commit();
            } catch (SQLException error) {
                System.out.println("Error en actualizarPagosBatch: " + error.toString());
                if (connect != null) {
                    try { connect.rollback(); } catch (SQLException ex) { System.out.println("Rollback error: " + ex); }
                }
            } finally {
                try { if (ps != null) ps.close(); } catch (SQLException error) { System.out.println("Error Statement: " + error.toString()); }
                try { if (connect != null) connect.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
            }
        }
    }

    public List<PagoTarjeta> consultaTodosPagos() {
        SimpleJdbcTemplate sjdbc = getSimpleJdbcTemplate();
        if (sjdbc != null) {
            List<PagoTarjeta> lista = sjdbc.query(SQL_SELECT_PAGOS, new PagoTarjetaRowMapper());
            if (lista != null && !lista.isEmpty()) {
                return lista;
            }
        } else {
            List<PagoTarjeta> lista = new ArrayList<PagoTarjeta>();
            Connection connect = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                connect = getConexion();
                stmt = connect.createStatement();
                rs = stmt.executeQuery(SQL_SELECT_PAGOS);
                while (rs.next()) {
                    PagoTarjeta p = new PagoTarjeta();
                    p.setId(rs.getInt("id"));
                    p.setFecha(rs.getString("fecha"));
                    p.setMes(rs.getString("mes"));
                    p.setTarjeta(rs.getString("tarjeta"));
                    p.setTransaccion(rs.getString("transaccion"));
                    p.setGastos(rs.getDouble("gastos"));
                    p.setPagos(rs.getDouble("pagos"));
                    lista.add(p);
                }
                if (!lista.isEmpty()) {
                    return lista;
                }
            } catch (SQLException error) {
                System.out.println("Error al consultar pagos: " + error.toString());
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (stmt != null) stmt.close(); } catch (Exception e) {}
                try { if (connect != null) connect.close(); } catch (Exception e) {}
            }
        }
        return obtenerPagosIniciales();
    }

    public List<PagoTarjeta> obtenerPagosIniciales() {
        List<PagoTarjeta> lista = new ArrayList<PagoTarjeta>();
        lista.add(new PagoTarjeta(1, "2026-01-15", "Enero", "Visa Clásica", "Supermercado", 1500.00, 0.00));
        lista.add(new PagoTarjeta(2, "2026-02-10", "Febrero", "MasterCard Gold", "Electrónicos", 3200.50, 0.00));
        lista.add(new PagoTarjeta(3, "2026-03-05", "Marzo", "American Express", "Boletos de avión", 4800.00, 0.00));
        lista.add(new PagoTarjeta(4, "2026-04-18", "Abril", "Visa Platinum", "Restaurantes", 950.00, 0.00));
        return lista;
    }

    public int getIntentos(String IP) {
        int intentos = 0;
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connect = getConexion();
            String sql = "SELECT intentos, Pintento FROM actividad WHERE IP = ?";
            ps = connect.prepareStatement(sql);
            ps.setString(1, IP);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String fecha = resultSet.getString("Pintento");
                intentos = resultSet.getInt("intentos");
                System.out.println("Conexión IP: " + IP);
                System.out.println("Fecha de último intento: " + fecha);
                System.out.println("No. Intentos actuales: " + intentos);
            }
        } catch (Exception error) {
            System.out.println(error.toString());
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (connect != null) connect.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
        }
        return intentos;
    }

    public void addIntento(String IP) {
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String sql;
        int intentos = 0;
        String fecha = "";
        try {
            connect = getConexion();
            sql = "SELECT intentos, Pintento FROM actividad WHERE IP = ?";
            ps = connect.prepareStatement(sql);
            ps.setString(1, IP);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                fecha = resultSet.getString("Pintento");
                intentos = resultSet.getInt("intentos");
            }
            if (ps != null) ps.close();

            if (intentos == 0) {
                sql = "INSERT INTO actividad (IP, Pintento, intentos) VALUES (?, ?, ?)";
                ps = connect.prepareStatement(sql);
                ps.setString(1, IP);
                ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(3, 1);
                ps.executeUpdate();
            } else {
                Calendar f = new GregorianCalendar();
                String mes = (f.get(Calendar.MONTH) + 1 < 10) ? "0" + (f.get(Calendar.MONTH) + 1) : String.valueOf(f.get(Calendar.MONTH) + 1);
                String dia = (f.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + f.get(Calendar.DAY_OF_MONTH) : String.valueOf(f.get(Calendar.DAY_OF_MONTH));
                String fechaHoy = f.get(Calendar.YEAR) + "-" + mes + "-" + dia;

                if (fechaHoy.compareTo(fecha) == 0) {
                    sql = "UPDATE actividad SET intentos = ? WHERE IP = ?";
                    ps = connect.prepareStatement(sql);
                    ps.setInt(1, intentos + 1);
                    ps.setString(2, IP);
                    ps.executeUpdate();
                } else {
                    sql = "UPDATE actividad SET intentos = ?, Pintento = ? WHERE IP = ?";
                    ps = connect.prepareStatement(sql);
                    ps.setInt(1, 1);
                    ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                    ps.setString(3, IP);
                    ps.executeUpdate();
                }
            }
        } catch (Exception error) {
            System.out.println(error.toString());
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
            try { if (connect != null) connect.close(); } catch (SQLException error) { System.out.println("Error Connect: " + error.toString()); }
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) {
        String resultado = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            resultado = convertToHex(sha1hash);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return resultado;
    }
}
