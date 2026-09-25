package dominio;

// Datos del formulario "Financiera Huanca - Datos del credito"
public class User {

	private String fecha;             // yyyy-MM-dd (input type="date")
	private String monto;
	private String periodo;           // en meses
	private String moneda;            // Soles | Dolares
	private String cuota;
	private String tea;               // Tasa Efectiva Anual (%)
	private String fechaVencimiento;  // yyyy-MM-dd

	public String getFecha() {
		return fecha;
	  }
	public String getMonto() {
		return monto;
	  }
	public String getPeriodo() {
		return periodo;
	  }
	public String getMoneda() {
		return moneda;
	  }
	public String getCuota() {
		return cuota;
	  }
	public String getTea() {
		return tea;
	  }
	public String getFechaVencimiento() {
		return fechaVencimiento;
	  }
	public void setFecha( String fecha ) {
		this.fecha = fecha;
	  }
	public void setMonto( String monto ) {
		this.monto = monto;
	  }
	public void setPeriodo( String periodo ) {
		this.periodo = periodo;
	  }
	public void setMoneda( String moneda ) {
		this.moneda = moneda;
	  }
	public void setCuota( String cuota ) {
		this.cuota = cuota;
	  }
	public void setTea( String tea ) {
		this.tea = tea;
	  }
	public void setFechaVencimiento( String fechaVencimiento ) {
		this.fechaVencimiento = fechaVencimiento;
	  }
}