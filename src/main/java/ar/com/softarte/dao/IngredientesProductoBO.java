package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.IngredientesProducto;


public interface IngredientesProductoBO {
	
		void save(IngredientesProducto ingrediente);
		
		void update(IngredientesProducto ingrediente);
		
		void delete(IngredientesProducto ingrediente);
		
		List<IngredientesProducto> list();

		IngredientesProducto getByID(String ID);
		
}
