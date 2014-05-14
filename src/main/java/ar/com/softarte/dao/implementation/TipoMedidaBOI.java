package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.TipoMedidaBO;
import ar.com.softarte.model.TipoMedida;

@Service  
@Transactional 
public class TipoMedidaBOI implements TipoMedidaBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(TipoMedida user) {
	
		hibernateTemplate.saveOrUpdate(user);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<TipoMedida> list() {
	
		return (List<TipoMedida>) hibernateTemplate.find("from TipoMedida order by descripcion asc");	
	}

	@Override
	public void update(TipoMedida medida) {
		hibernateTemplate.update(medida);		
	}

	@Override
	public void delete(TipoMedida medida) {
		hibernateTemplate.delete(medida);		
	}	
	
	@Override
	public TipoMedida getByID(String ID) {		
		return (TipoMedida) DataAccessUtils.uniqueResult(hibernateTemplate.find("from TipoMedida where id = " + ID ));	
	}	

}
