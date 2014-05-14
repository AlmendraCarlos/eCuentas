package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.TipoProductoBO;
import ar.com.softarte.model.TipoProducto;

@Service  
@Transactional 
public class TipoProductoBOI implements TipoProductoBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(TipoProducto user) {
	
		hibernateTemplate.saveOrUpdate(user);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<TipoProducto> list() {
	
		return (List<TipoProducto>) hibernateTemplate.find("from TipoProducto order by descripcion asc");	
	}

	@Override
	public void update(TipoProducto producto) {
		hibernateTemplate.update(producto);		
	}

	@Override
	public void delete(TipoProducto producto) {
		hibernateTemplate.delete(producto);		
	}	
	
	@Override
	public TipoProducto getByID(String ID) {		
		return (TipoProducto) DataAccessUtils.uniqueResult(hibernateTemplate.find("from TipoProducto where id = " + ID ));	
	}	

}
