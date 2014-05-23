package ar.com.softarte.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.softarte.dao.HibernateFunctionsBO;
import ar.com.softarte.model.GVentasXMes;


@Controller
public class Home {
	
	@Autowired
	private HibernateFunctionsBO hibernateFunctionsDAO;
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public ModelAndView displayHome() {
        return new ModelAndView("inicio");
    }
	
	@RequestMapping(value = "/getGraficoVentasXMes", method = RequestMethod.GET)
	public @ResponseBody List<GVentasXMes> getGVentasXMes() {
		//prueba de comentario git
		return hibernateFunctionsDAO.list();
		
	}
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
 
		return model;
 
	}
}
