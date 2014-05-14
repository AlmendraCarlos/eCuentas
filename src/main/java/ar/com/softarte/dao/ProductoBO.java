package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.Producto;


public interface ProductoBO {
	
		void save(Producto producto);
		
		void update(Producto producto);
		
		void delete(Producto producto);
		
		List<Producto> list();
		
		List<Producto> getByName(String name);

		Producto getByID(String ID);
		
}
