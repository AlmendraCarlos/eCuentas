package ar.com.softarte.controllers;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.softarte.dao.IngredientesProductoBO;
import ar.com.softarte.dao.MateriaPrimaBO;
import ar.com.softarte.dao.ProductoBO;
import ar.com.softarte.dao.TipoMedidaBO;
import ar.com.softarte.dao.TipoProductoBO;
import ar.com.softarte.functions.Helper;
import ar.com.softarte.model.IngredientesProducto;
import ar.com.softarte.model.MateriaPrima;
import ar.com.softarte.model.Producto;
import ar.com.softarte.validators.ProductoValidator;

@Controller
public class Productos {

	@Autowired
	private ProductoBO productoDAO;
	
	@Autowired
	private TipoProductoBO tipoProductoDAO;
	
	@Autowired
	private TipoMedidaBO tipoMedidaDAO;
	
	@Autowired
	private MateriaPrimaBO materiaPrimaDAO;
	
	@Autowired
	private IngredientesProductoBO ingredientesDAO;
	
	@Autowired
	private ProductoValidator productoValidator;
	
	@InitBinder(value="formProducto")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(productoValidator);
	}
	
	@RequestMapping(value="/productos", method = RequestMethod.GET)
	public ModelAndView showMessage() {
		
		ModelMap model = new ModelMap();
		
		List<Producto> productos = productoDAO.list();
		
		model.put("productos", Helper.getCostoProductos(productos));
		
		return new ModelAndView("productos", "model", model);
	}
	
	@RequestMapping("/addproducto")
	public ModelAndView agregarProducto() {
		
		ModelMap model = new ModelMap();
		
		model.put("tiposProducto", tipoProductoDAO.list());
		model.put("tiposMedida", tipoMedidaDAO.list());
		
		Producto producto = new Producto();
		producto.setGanancia(100);
		
		model.put("producto",producto);
		
		return new ModelAndView("producto/crear","model",model);
	}
	
	@RequestMapping(value ="/saveProducto", method = RequestMethod.POST)
	public ModelAndView guardarProducto(@ModelAttribute("producto") Producto producto, BindingResult result, ModelMap model) {
		
		productoValidator.validate(producto, result);
		
		if ( result.hasErrors() ) {	
			
			model.put("tiposProducto", tipoProductoDAO.list());
			model.put("tiposMedida", tipoMedidaDAO.list());
			model.put("producto",producto);
			
			ModelAndView mav = new ModelAndView("producto/crear");
			
			mav.addObject("model",model);
			
			return mav;
		}
		
		productoDAO.save(producto);
		
		return new ModelAndView("producto/crear");
	}
	
	@RequestMapping(value = "/removeproducto", method = RequestMethod.GET)
	public String borrarProducto(@RequestParam String ID) {
		
		productoDAO.delete(productoDAO.getByID(ID));
		
		return "redirect:/productos.htm";
	}
	
	@RequestMapping(value = "/editproducto", method = RequestMethod.GET)
	public ModelAndView editerProducto(@RequestParam String ID) {
		
		Producto producto = productoDAO.getByID(ID);
		
		ModelMap model = new ModelMap();
		
		model.put("tiposProducto", tipoProductoDAO.list());
		model.put("tiposMedida", tipoMedidaDAO.list());
		
		model.put("producto",producto);
		
		return new ModelAndView("producto/crear","model",model);
	}
	
	@RequestMapping(value = "/ingredientes", method = RequestMethod.GET)
	public ModelAndView verIngredientes(@RequestParam String ID) {
		
		Producto producto = productoDAO.getByID(ID);
		
		ModelMap model = new ModelMap();
		
		Set<IngredientesProducto> ingredientesProducto = producto.getIngredientesProductos();
		
		model.put("producto", producto);
		model.put("ingredientes", ingredientesProducto);
		model.put("materiaPrimaList", materiaPrimaDAO.list());
		
		return new ModelAndView("producto/ingredientes","model",model);
	}
	
	@RequestMapping(value = "/getTipoMedida", method = RequestMethod.GET)
	public @ResponseBody String tipoMedida(@RequestParam String ID) {
		
		MateriaPrima materia = materiaPrimaDAO.getByID(ID);
		
		String abreviatura = materia.getTipoMedida().getAbreviatura().trim();
		
		return abreviatura;
	}
	
	@RequestMapping(value ="/addIngrediente", method = RequestMethod.POST)
	public ModelAndView agregarIngrediente(@ModelAttribute("formulario") IngredientesProducto ingredienteProducto, BindingResult result, ModelMap modelMap) {
		
		ModelMap model = new ModelMap();
		
		if(ingredienteProducto.getMateriaPrima().getId() == -1){
		
			result.rejectValue("MateriaPrima", "materiaPrima.required", "es obligatorio.");
		
		}else{
			
			if(ingredienteProducto.getCantidad() <= 0){
				
				result.rejectValue("Cantidad", "cantidad.required", "es obligatorio.");
				
			}
		}
		
		if ( result.hasErrors() ) {	
			
			model.put("formulario", ingredienteProducto);

		}else{
			
			ingredientesDAO.save(ingredienteProducto);
			
		}
		
		Producto producto = productoDAO.getByID(String.valueOf(ingredienteProducto.getProducto().getId()));
		
		Set<IngredientesProducto> ingredientesProducto = producto.getIngredientesProductos();
		
		model.put("producto", producto);
		
		model.put("ingredientes", ingredientesProducto);
		
		model.put("materiaPrimaList", materiaPrimaDAO.list());
		
		return new ModelAndView("producto/ingredientes","model",model);
	}
	
	@RequestMapping(value = "/removeingrediente", method = RequestMethod.GET)
	public ModelAndView borrarIngrediente(@RequestParam String ID) {
		
		ModelMap model = new ModelMap();
		
		IngredientesProducto ingredienteProducto = ingredientesDAO.getByID(ID);
		
		String productoID = String.valueOf(ingredienteProducto.getProducto().getId());
		
		
		ingredientesDAO.delete(ingredienteProducto);
		
		
		Producto producto = productoDAO.getByID(productoID);
		
		Set<IngredientesProducto> ingredientesProducto = producto.getIngredientesProductos();
		
		model.put("producto", producto);
		
		model.put("ingredientes", ingredientesProducto);
		
		model.put("materiaPrimaList", materiaPrimaDAO.list());
		
		return new ModelAndView("producto/ingredientes","model",model);
	}
}
