package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.TipoProducto;


public interface TipoProductoBO {
	
		void save(TipoProducto producto);
		
		void update(TipoProducto producto);
		
		void delete(TipoProducto producto);
		
		List<TipoProducto> list();

		TipoProducto getByID(String ID);
		
}
