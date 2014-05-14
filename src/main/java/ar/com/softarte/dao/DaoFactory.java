/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.softarte.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {

    private static DaoFactory instance;

    private synchronized static DaoFactory newInstance() {
        return new DaoFactory();
    }
    
    @SuppressWarnings("unused")
	private BeanFactory factory;

    private DaoFactory() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/mvc-dispatcher-servlet.xml");
        factory = (BeanFactory) ctx;
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = newInstance();
        }
        return instance;
    }

}
