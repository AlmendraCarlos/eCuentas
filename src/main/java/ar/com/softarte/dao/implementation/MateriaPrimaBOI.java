package ar.com.softarte.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.MateriaPrimaBO;
import ar.com.softarte.model.MateriaPrima;

@Service  
@Transactional 
public class MateriaPrimaBOI implements MateriaPrimaBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}	 
	
	@Override	
	public void save(MateriaPrima user) {
	
		hibernateTemplate.saveOrUpdate(user);	
	} 
	
	@Override	
	@SuppressWarnings("unchecked")	
	public List<MateriaPrima> list() {
		
		return (List<MateriaPrima>) hibernateTemplate.find("from MateriaPrima order by descripcion asc");	
	}

	@Override
	public void update(MateriaPrima MateriaPrima) {
		hibernateTemplate.update(MateriaPrima);		
	}

	@Override
	public void delete(MateriaPrima MateriaPrima) {
		hibernateTemplate.delete(MateriaPrima);		
	}	
	
	@Override
	public MateriaPrima getByID(String ID) {		
		return (MateriaPrima) DataAccessUtils.uniqueResult(hibernateTemplate.find("from MateriaPrima where id = " + ID ));	
	}
}
