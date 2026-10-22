package servicio;

import java.util.List;
import dominio.CreditoForm;
import dominio.PagoTarjeta;

public interface UserService {
    void registrarCredito(CreditoForm credito);
    void guardarPagos(List<PagoTarjeta> pagos);
    List<PagoTarjeta> obtenerPagos();
    List<PagoTarjeta> obtenerPagosIniciales();
}
