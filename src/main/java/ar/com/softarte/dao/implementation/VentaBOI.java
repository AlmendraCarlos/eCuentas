package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.VentaBO;
import ar.com.softarte.model.Venta;

@Service  
@Transactional 
public class VentaBOI implements VentaBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(Venta venta) {
	
		hibernateTemplate.saveOrUpdate(venta);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<Venta> list() {
		
		return (List<Venta>) hibernateTemplate.find("from Venta order by id desc");	
	}

	@Override
	public void update(Venta venta) {
		hibernateTemplate.update(venta);		
	}

	@Override
	public void delete(Venta venta) {
		hibernateTemplate.delete(venta);		
	}	
	
	@Override
	public Venta getByID(String ID) {		
		return (Venta) DataAccessUtils.uniqueResult(hibernateTemplate.find("from Venta where id = " + ID ));	
	}
}
