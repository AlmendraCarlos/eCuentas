package ar.com.softarte.controllers;

import java.util.Date;
import java.util.List;

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
import ar.com.softarte.dao.ProductoBO;
import ar.com.softarte.dao.VentaBO;
import ar.com.softarte.functions.Helper;
import ar.com.softarte.model.Producto;
import ar.com.softarte.model.Venta;

@Controller
public class VentaController {

	@Autowired
	private ProductoBO productoDAO;
	
	@Autowired
	private VentaBO ventaDAO;
	
	@Autowired
	private MateriaPrimaBO materiaPrimaDAO;
	
	@RequestMapping(value="/ventas", method = RequestMethod.GET)
	public ModelAndView ventas() {
		
		ModelMap model = new ModelMap();
		
		model.put("ventas", ventaDAO.list());
		
		List<Producto> productos = productoDAO.list();
		
		model.put("productos", Helper.getCostoProductos(productos));
		
		return new ModelAndView("ventas", "model", model);
	}
	
	@RequestMapping(value ="/saveVenta", method = RequestMethod.POST)
	public ModelAndView guardarVenta(@ModelAttribute("venta") Venta venta, BindingResult result, ModelMap model) {
		
		if ( result.hasErrors() ) {	
			
			model.put("ventas", ventaDAO.list());
			
			model.put("venta",venta);
			
			List<Producto> productos = productoDAO.list();
			
			model.put("productos", Helper.getCostoProductos(productos));
			
			ModelAndView mav = new ModelAndView("ventas");
			
			mav.addObject("model",model);
			
			return mav;
		}
		
		venta.setFecha(new Date());
		
		ventaDAO.save(venta);
		
		//Actualizo el Stock
		Helper.actualizarStockXVenta(venta, materiaPrimaDAO,productoDAO);
		
		return this.ventas();
	}
	
	@RequestMapping(value = "/removeVenta", method = RequestMethod.GET)
	public String borrarVenta(@RequestParam String ID) {
		
		Venta venta = ventaDAO.getByID(ID);
		
		Producto producto = venta.getProducto();
		
		double medida = venta.getCantidad();
		
		int cantidad = venta.getUnidades();
		
		ventaDAO.delete(venta);
		
		//Actualizo el Stock
		Helper.actualizarStockXQuitarVenta(producto, cantidad, medida, materiaPrimaDAO);
		
		return "redirect:/ventas.htm";
	}
	
	@RequestMapping(value = "/getProducto", method = RequestMethod.GET)
	public @ResponseBody Producto getProducto(@RequestParam String ID) {
		
		Producto producto = productoDAO.getByID(ID);
		
		//String str = "{\"producto\": { \"descripcion\": \"" + producto.getDescripcion() + "\"}}";  
		
		return Helper.getCostoProducto(producto);
	}
	
	@RequestMapping(value = "/getPrecioXCantidad", method = RequestMethod.GET)
	public @ResponseBody Producto getPrecioXCantidad(@RequestParam String unidades, @RequestParam String cantidad, @RequestParam String productoID) {
		
		Producto producto = productoDAO.getByID(productoID);
		
		producto = Helper.getCostoProducto(producto);
		
		producto = Helper.getCostoXCantidad(producto, Double.parseDouble(cantidad));
		
		producto = Helper.getCostoXUnidad(producto, Integer.parseInt(unidades));
		
		return producto;
	}
	
	@RequestMapping(value = "/getProductos", method = RequestMethod.GET)
	public @ResponseBody List<Producto> getTags(@RequestParam String pName) {
 
		List<Producto> productos = productoDAO.getByName(pName);
		
		return productos;
 
	}
}
