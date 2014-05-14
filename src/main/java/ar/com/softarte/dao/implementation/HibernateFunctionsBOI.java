package ar.com.softarte.dao.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.softarte.dao.HibernateFunctionsBO;
import ar.com.softarte.model.GVentasXMes;

@Service
public class HibernateFunctionsBOI implements HibernateFunctionsBO {
	
	private HibernateTemplate hibernateTemplate;
		
	public void setSessionFactory(SessionFactory sessionFactory){
	
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);	
	}
	
	@Transactional
	public List<GVentasXMes> list() {
		
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		
		List<GVentasXMes> gList = new ArrayList<GVentasXMes>();
		
		//session.beginTransaction();
		
		Query query = session.createSQLQuery("select * from VentasXMes()");
			 
		List result = query.list();
		
		for(int i=0; i < result.size(); i++){
			
			Object[] resultado = (Object[]) result.get(i);
			
			GVentasXMes gVentasXMes = new GVentasXMes();			
			gVentasXMes.setVenta((Double) resultado[0]);
			gVentasXMes.setCompra((Double) resultado[1]);
			gVentasXMes.setFecha((String) resultado[2]);
			
			gVentasXMes.setGanancia( (gVentasXMes.getVenta() - gVentasXMes.getCompra()) >= 0 ? (gVentasXMes.getVenta() - gVentasXMes.getCompra()) : 0.0  );
			
			gList.add(gVentasXMes);
		}

		//session.getTransaction().commit();
		
		return gList;	
	}
	
}
