package ar.com.softarte.model;

public class GVentasXMes {
	
	private Double venta;
	private Double compra;
	private Double ganancia;
	private String fecha;
	
	public GVentasXMes(){
		
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public Double getVenta() {
		return venta;
	}
	public void setVenta(Double venta) {
		this.venta = venta;
	}

	public Double getCompra() {
		return compra;
	}

	public void setCompra(Double compra) {
		this.compra = compra;
	}

	public Double getGanancia() {
		return ganancia;
	}

	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}
}
