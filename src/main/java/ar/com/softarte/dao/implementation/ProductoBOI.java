package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.ProductoBO;
import ar.com.softarte.model.Producto;

@Service  
@Transactional 
public class ProductoBOI implements ProductoBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(Producto user) {
	
		hibernateTemplate.saveOrUpdate(user);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<Producto> getByName(String name) {
		
		return (List<Producto>) hibernateTemplate.find("from Producto where upper(descripcion) like '%" + name.toUpperCase() + "%' order by descripcion asc");	
	}
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<Producto> list() {
		
		return (List<Producto>) hibernateTemplate.find("from Producto order by tipoProducto.descripcion,descripcion asc");	
	}

	@Override
	public void update(Producto producto) {
		hibernateTemplate.update(producto);		
	}

	@Override
	public void delete(Producto producto) {
		hibernateTemplate.delete(producto);		
	}	
	
	@Override
	public Producto getByID(String ID) {		
		return (Producto) DataAccessUtils.uniqueResult(hibernateTemplate.find("from Producto where id = " + ID ));	
	}
}
