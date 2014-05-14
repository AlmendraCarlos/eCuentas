package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.MateriaPrima;


public interface MateriaPrimaBO {
	
		void save(MateriaPrima MateriaPrima);
		
		void update(MateriaPrima MateriaPrima);
		
		void delete(MateriaPrima MateriaPrima);
		
		List<MateriaPrima> list();

		MateriaPrima getByID(String ID);
		
}
