package dominio;

import java.util.ArrayList;
import java.util.List;

public class CreditoForm {
    // Datos del crédito (formulario superior)
    private Integer id;
    private String fecha;            // Formato YYYY-MM-DD
    private Double monto;
    private Integer periodo;
    private String moneda;
    private Double cuota;
    private Double tea;
    private String fechaVencimiento; // Formato YYYY-MM-DD

    // Tabla de pagos de tarjetas de crédito (MultiRow con auto-crecimiento seguro)
    private List<PagoTarjeta> pagosTarjetas = new ArrayList<PagoTarjeta>() {
        @Override
        public PagoTarjeta get(int index) {
            while (index >= size()) {
                add(new PagoTarjeta());
            }
            return super.get(index);
        }
    };

    public CreditoForm() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Integer getPeriodo() { return periodo; }
    public void setPeriodo(Integer periodo) { this.periodo = periodo; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public Double getCuota() { return cuota; }
    public void setCuota(Double cuota) { this.cuota = cuota; }

    public Double getTea() { return tea; }
    public void setTea(Double tea) { this.tea = tea; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public List<PagoTarjeta> getPagosTarjetas() {
        return pagosTarjetas;
    }

    public void setPagosTarjetas(List<PagoTarjeta> lista) {
        this.pagosTarjetas.clear();
        if (lista != null) {
            this.pagosTarjetas.addAll(lista);
        }
    }
}
