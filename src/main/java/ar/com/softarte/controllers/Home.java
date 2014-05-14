package ar.com.softarte.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.softarte.dao.HibernateFunctionsBO;
import ar.com.softarte.model.GVentasXMes;


@Controller
public class Home {
	
	@Autowired
	private HibernateFunctionsBO hibernateFunctionsDAO;
	
	@RequestMapping("/home")
	public ModelAndView showMessage() {
		
		return new ModelAndView("inicio");
		
	}
	
	
	@RequestMapping(value = "/getGraficoVentasXMes", method = RequestMethod.GET)
	public @ResponseBody List<GVentasXMes> getGVentasXMes() {
		
		return hibernateFunctionsDAO.list();
		
	}
}
