package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.Compra;


public interface CompraBO {
	
		void save(Compra compra);
		
		void update(Compra compra);
		
		void delete(Compra compra);
		
		List<Compra> list();

		Compra getByID(String ID);
		
}
