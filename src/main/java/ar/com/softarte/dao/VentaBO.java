package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.Venta;


public interface VentaBO {
	
		void save(Venta venta);
		
		void update(Venta venta);
		
		void delete(Venta venta);
		
		List<Venta> list();

		Venta getByID(String ID);
		
}
