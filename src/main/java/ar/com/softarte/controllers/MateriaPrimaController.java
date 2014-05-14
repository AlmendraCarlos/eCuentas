package ar.com.softarte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.softarte.dao.MateriaPrimaBO;
import ar.com.softarte.dao.TipoMedidaBO;
import ar.com.softarte.model.MateriaPrima;
import ar.com.softarte.validators.MateriaPrimaValidator;

@Controller
public class MateriaPrimaController {

	@Autowired
	private MateriaPrimaBO materiaPrimaDAO;
	
	@Autowired
	private TipoMedidaBO tipoMedidaDAO;
	
	@Autowired
	private MateriaPrimaValidator materiaPrimaValidator;
	
	@InitBinder(value="materiaPrima")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(materiaPrimaValidator);
	}
	
	@RequestMapping(value="/materiaPrimaList", method = RequestMethod.GET)
	public ModelAndView showMessage() {
		
		ModelMap model = new ModelMap();
		
		model.put("materiaPrimaList", materiaPrimaDAO.list());
		
		return new ModelAndView("materiaPrimaList", "model", model);
	}
	
	@RequestMapping("/addMateriaPrima")
	public ModelAndView agregarMateriaPrima() {
		
		ModelMap model = new ModelMap();
		
		model.put("tiposMedida", tipoMedidaDAO.list());
		
		model.put("materiaPrima", new MateriaPrima());
		
		return new ModelAndView("materia_prima/crear","model",model);
	}
	
	@RequestMapping(value ="/saveMateriaPrima", method = RequestMethod.POST)
	public ModelAndView guardarMateriaPrima(@ModelAttribute("materiaPrima") MateriaPrima materiaPrima, BindingResult result, ModelMap modelMap) {
		
		materiaPrimaValidator.validate(materiaPrima, result);
		
		if ( result.hasErrors() ) {	
			
			modelMap.put("tiposMedida", tipoMedidaDAO.list());
			modelMap.put("materiaPrima",materiaPrima);
			
			ModelAndView mav = new ModelAndView("materia_prima/crear");
			
			mav.addObject("model",modelMap);
			
			return mav;
		}
		
		if(materiaPrima.getMedidaDisponibleStock() == null){
			materiaPrima.setMedidaDisponibleStock(0.0);
		}
		
		if(materiaPrima.getMedidaMinimaStock() == null){
			materiaPrima.setMedidaMinimaStock(0);
		}
		
		materiaPrimaDAO.save(materiaPrima);
		
		return new ModelAndView("materia_prima/crear");
	}
	
	@RequestMapping(value = "/removeMateriaPrima", method = RequestMethod.GET)
	public String borrarMateriaPrima(@RequestParam String ID) {
		
		materiaPrimaDAO.delete(materiaPrimaDAO.getByID(ID));
		
		return "redirect:/materiaPrimaList.htm";
	}
	
	@RequestMapping(value = "/editMateriaPrima", method = RequestMethod.GET)
	public ModelAndView editerMateriaPrima(@RequestParam String ID) {
		
		MateriaPrima MateriaPrima = materiaPrimaDAO.getByID(ID);
		
		ModelMap model = new ModelMap();
		
		model.put("materiaPrima",MateriaPrima);
		model.put("tiposMedida", tipoMedidaDAO.list());
		
		return new ModelAndView("materia_prima/crear","model",model);
	}
}
