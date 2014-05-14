package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.CompraBO;
import ar.com.softarte.model.Compra;

@Service  
@Transactional 
public class CompraBOI implements CompraBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(Compra compra) {
	
		hibernateTemplate.saveOrUpdate(compra);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<Compra> list() {
		
		return (List<Compra>) hibernateTemplate.find("from Compra order by id desc");	
	}

	@Override
	public void update(Compra compra) {
		hibernateTemplate.update(compra);		
	}

	@Override
	public void delete(Compra compra) {
		hibernateTemplate.delete(compra);		
	}	
	
	@Override
	public Compra getByID(String ID) {		
		return (Compra) DataAccessUtils.uniqueResult(hibernateTemplate.find("from Compra where id = " + ID ));	
	}
}
