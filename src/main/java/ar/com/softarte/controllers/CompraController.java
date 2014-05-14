package ar.com.softarte.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.softarte.dao.MateriaPrimaBO;
import ar.com.softarte.dao.CompraBO;
import ar.com.softarte.functions.Helper;
import ar.com.softarte.model.Compra;
import ar.com.softarte.model.MateriaPrima;

@Controller
public class CompraController {

	@Autowired
	private MateriaPrimaBO materiaPrimaDAO;
	
	@Autowired
	private CompraBO compraDAO;
	
	@RequestMapping(value="/compras", method = RequestMethod.GET)
	public ModelAndView compras() {
		
		ModelMap model = new ModelMap();
		
		model.put("compras", compraDAO.list());
		
		model.put("materiaPrimaList", materiaPrimaDAO.list());
		
		return new ModelAndView("compras", "model", model);
	}
	
	@RequestMapping(value ="/saveCompra", method = RequestMethod.POST)
	public ModelAndView guardarCompra(@ModelAttribute("compra") Compra compra, BindingResult result, ModelMap model) {
		
		if ( result.hasErrors() ) {	
			
			model.put("compras", compraDAO.list());
			
			model.put("compra",compra);
			
			model.put("materiaPrimaList", materiaPrimaDAO.list());
			
			ModelAndView mav = new ModelAndView("compras");
			
			mav.addObject("model",model);
			
			return mav;
		}
		
		compra.setFecha(new Date());
		
		compraDAO.save(compra);
		
		//Actualizo el Stock
		MateriaPrima materiaPrima = materiaPrimaDAO.getByID(String.valueOf(compra.getMateriaPrima().getId()));
		materiaPrima.setMedidaDisponibleStock(materiaPrima.getMedidaDisponibleStock() + compra.getCantidad());
		materiaPrimaDAO.save(materiaPrima);
		
		return this.compras();
	}
	
	@RequestMapping(value = "/removeCompra", method = RequestMethod.GET)
	public String borrarCompra(@RequestParam String ID) {
		
		Compra compra = compraDAO.getByID(ID);
		
		MateriaPrima materiaPrima = compra.getMateriaPrima();
		
		double stock = materiaPrima.getMedidaDisponibleStock() - compra.getCantidad();
		
		if(stock < 0){ 
			materiaPrima.setMedidaDisponibleStock(0.0);
		}else{
			materiaPrima.setMedidaDisponibleStock(stock);
		}
		
		compraDAO.delete(compra);
		
		//Actualizo el Stock
		materiaPrimaDAO.save(materiaPrima);
		
		return "redirect:/compras.htm";
	}
	

	@RequestMapping(value = "/getMateriaPrima", method = RequestMethod.GET)
	public @ResponseBody MateriaPrima getMateriaPrima(@RequestParam String ID) {
		
		MateriaPrima materiaPrima = materiaPrimaDAO.getByID(ID);
		
		materiaPrima.setUnidades(1);
		
		return materiaPrima;
	}
	
	@RequestMapping(value = "/getPrecioXUnidad", method = RequestMethod.GET)
	public @ResponseBody MateriaPrima getPrecioXUnidad(@RequestParam String unidades, @RequestParam String materiaPrimaID) {
		
		MateriaPrima materiaPrima = materiaPrimaDAO.getByID(materiaPrimaID);
		
		materiaPrima = Helper.getPrecioXUnidadMateriaPrima(materiaPrima, Double.parseDouble(unidades));
		
		return materiaPrima;
	}
	
	@RequestMapping(value = "/getPrecioXMedida", method = RequestMethod.GET)
	public @ResponseBody MateriaPrima getPrecioXMedida(@RequestParam String medida, @RequestParam String materiaPrimaID) {
		
		MateriaPrima materiaPrima = materiaPrimaDAO.getByID(materiaPrimaID);
		
		materiaPrima = Helper.getPrecioXMedidaMateriaPrima(materiaPrima, Double.parseDouble(medida));
		
		return materiaPrima;
	}
	
	@RequestMapping(value = "/getMateriaPrimaActualizada", method = RequestMethod.GET)
	public @ResponseBody MateriaPrima getPrecioXMedida(@RequestParam String precio, @RequestParam String medida, @RequestParam String materiaPrimaID, @RequestParam String actualizar) {
		
		MateriaPrima materiaPrima = materiaPrimaDAO.getByID(materiaPrimaID);
		
		double precioActualizado = (materiaPrima.getMedida() * Double.parseDouble(precio)) / Double.parseDouble(medida);
		
		materiaPrima.setPrecio(precioActualizado);
		
		//lo agrego solo porque da error JSon
		materiaPrima.setUnidades(1);
		
		if(actualizar.equals("YES")){
			materiaPrimaDAO.save(materiaPrima);
		}
		
		return materiaPrima;
	}
}
