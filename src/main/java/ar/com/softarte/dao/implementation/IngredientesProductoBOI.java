package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.IngredientesProductoBO;
import ar.com.softarte.model.IngredientesProducto;

@Service  
@Transactional 
public class IngredientesProductoBOI implements IngredientesProductoBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(IngredientesProducto user) {
	
		hibernateTemplate.saveOrUpdate(user);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<IngredientesProducto> list() {
		
		return (List<IngredientesProducto>) hibernateTemplate.find("from IngredientesProducto");	
	}

	@Override
	public void update(IngredientesProducto MateriaPrima) {
		hibernateTemplate.update(MateriaPrima);		
	}

	@Override
	public void delete(IngredientesProducto MateriaPrima) {
		hibernateTemplate.delete(MateriaPrima);		
	}	
	
	@Override
	public IngredientesProducto getByID(String ID) {		
		return (IngredientesProducto) DataAccessUtils.uniqueResult(hibernateTemplate.find("from IngredientesProducto where id = " + ID ));	
	}
}
