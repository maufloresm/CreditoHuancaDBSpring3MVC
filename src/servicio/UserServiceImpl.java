package servicio;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import baseDeDatos.UsuariosJDBCTemplate;
import dominio.CreditoForm;
import dominio.PagoTarjeta;

public class UserServiceImpl implements UserService {

    private UsuariosJDBCTemplate usuariosJDBCTemplate;

    public void setUsuariosJDBCTemplate(UsuariosJDBCTemplate usuariosJDBCTemplate) {
        this.usuariosJDBCTemplate = usuariosJDBCTemplate;
    }

    private UsuariosJDBCTemplate getDao() {
        if (usuariosJDBCTemplate != null) {
            return usuariosJDBCTemplate;
        }
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Datasource.xml");
            usuariosJDBCTemplate = (UsuariosJDBCTemplate) context.getBean("usuariosJDBCTemplate");
        } catch (Exception e) {
            System.out.println("Error instanciando usuariosJDBCTemplate desde Spring-Datasource.xml: " + e.getMessage());
            usuariosJDBCTemplate = new UsuariosJDBCTemplate();
        }
        return usuariosJDBCTemplate;
    }

    @Override
    public void registrarCredito(CreditoForm credito) {
        System.out.println("--- REGISTRANDO CRÉDITO ---");
        System.out.println("Fecha: " + credito.getFecha());
        System.out.println("Monto: " + credito.getMonto());
        System.out.println("Periodo: " + credito.getPeriodo());
        System.out.println("Moneda: " + credito.getMoneda());
        System.out.println("Cuota: " + credito.getCuota());
        System.out.println("TEA: " + credito.getTea());
        System.out.println("Fecha Vencimiento: " + credito.getFechaVencimiento());

        // Guardar solicitud de crédito
        getDao().guardarCredito(credito);

        // Actualizar pagos en lote (Multi-row)
        if (credito.getPagosTarjetas() != null && !credito.getPagosTarjetas().isEmpty()) {
            guardarPagos(credito.getPagosTarjetas());
        }
    }

    @Override
    public void guardarPagos(List<PagoTarjeta> pagos) {
        System.out.println("--- ACTUALIZANDO PAGOS DE TARJETAS (MULTI-ROW BATCH UPDATE) ---");
        if (pagos != null) {
            for (PagoTarjeta p : pagos) {
                System.out.printf("ID: %d | Tarjeta: %s | Mes: %s | Gastos: %s | Pagos editados: %s\n",
                        p.getId(), p.getTarjeta(), p.getMes(), p.getGastos(), p.getPagos());
            }
            getDao().actualizarPagosBatch(pagos);
        }
    }

    @Override
    public List<PagoTarjeta> obtenerPagos() {
        return getDao().consultaTodosPagos();
    }

    @Override
    public List<PagoTarjeta> obtenerPagosIniciales() {
        return getDao().obtenerPagosIniciales();
    }
}
