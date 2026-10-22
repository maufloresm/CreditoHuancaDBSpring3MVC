package dominio;

public class PagoTarjeta {
    private int id;
    private String fecha;
    private String mes;
    private String tarjeta;
    private String transaccion;
    private Double gastos;
    private Double pagos;

    public PagoTarjeta() {}

    public PagoTarjeta(int id, String fecha, String mes, String tarjeta, String transaccion, Double gastos, Double pagos) {
        this.id = id;
        this.fecha = fecha;
        this.mes = mes;
        this.tarjeta = tarjeta;
        this.transaccion = transaccion;
        this.gastos = gastos;
        this.pagos = pagos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }

    public String getTarjeta() { return tarjeta; }
    public void setTarjeta(String tarjeta) { this.tarjeta = tarjeta; }

    public String getTransaccion() { return transaccion; }
    public void setTransaccion(String transaccion) { this.transaccion = transaccion; }

    public Double getGastos() { return gastos; }
    public void setGastos(Double gastos) { this.gastos = gastos; }

    public Double getPagos() { return pagos; }
    public void setPagos(Double pagos) { this.pagos = pagos; }
}
