package ar.com.softarte.dao;

import java.util.List;

import ar.com.softarte.model.TipoMedida;


public interface TipoMedidaBO {
	
		void save(TipoMedida medida);
		
		void update(TipoMedida medida);
		
		void delete(TipoMedida medida);
		
		List<TipoMedida> list();

		TipoMedida getByID(String ID);
		
}
