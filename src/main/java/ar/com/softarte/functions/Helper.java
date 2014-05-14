package ar.com.softarte.functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ar.com.softarte.dao.MateriaPrimaBO;
import ar.com.softarte.dao.ProductoBO;
import ar.com.softarte.model.IngredientesProducto;
import ar.com.softarte.model.MateriaPrima;
import ar.com.softarte.model.Producto;
import ar.com.softarte.model.Venta;
/*
Comentario de prueba para GIT
*/
public abstract class Helper {

	public static List<Producto> getCostoProductos(List<Producto> productos) {
		
		List<Producto> productosConCosto = new ArrayList<Producto>();
		
		Iterator<Producto> productosIt = productos.iterator();
		
		double costoTotalProducto = 0;
		double costoIngrediente = 0;
		double precioSugerido = 0;
		
		while (productosIt.hasNext()) {
			
			Producto producto = (Producto) productosIt.next();
			
			Set<IngredientesProducto> ingredientes = producto.getIngredientesProductos();
			
			Iterator<IngredientesProducto> ingredientesIt = ingredientes.iterator();
			
			while (ingredientesIt.hasNext()) {
				
				IngredientesProducto ingrediente = (IngredientesProducto) ingredientesIt.next();
				
				costoIngrediente = (ingrediente.getMateriaPrima().getPrecio() * ingrediente.getCantidad()) / ingrediente.getMateriaPrima().getMedida();
				
				costoTotalProducto += costoIngrediente;
				
				costoIngrediente = 0;
			}
			
			precioSugerido = ( costoTotalProducto * (producto.getGanancia() + 100) ) / 100;
			
			producto.setPrecioSugerido(precioSugerido);
			
			producto.setPrecioCosto(costoTotalProducto);
			
			costoTotalProducto = 0;
			precioSugerido = 0;
			
			productosConCosto.add(producto);
			
		}
		
		return productosConCosto;
		
	}
	
	public static Producto getCostoProducto(Producto producto) {
		
		double costoTotalProducto = 0;
		double costoIngrediente = 0;
		double precioSugerido = 0;
			
		Set<IngredientesProducto> ingredientes = producto.getIngredientesProductos();
		
		Iterator<IngredientesProducto> ingredientesIt = ingredientes.iterator();
		
		while (ingredientesIt.hasNext()) {
			
			IngredientesProducto ingrediente = (IngredientesProducto) ingredientesIt.next();
			
			costoIngrediente = (ingrediente.getMateriaPrima().getPrecio() * ingrediente.getCantidad()) / ingrediente.getMateriaPrima().getMedida();
			
			costoTotalProducto += costoIngrediente;
			
			costoIngrediente = 0;
		}
		
		precioSugerido = ( costoTotalProducto * (producto.getGanancia() + 100) ) / 100;
		
		producto.setPrecioSugerido(precioSugerido);
		
		producto.setPrecioCosto(costoTotalProducto);			
		
		return producto;
		
	}
	
	public static Producto getCostoXCantidad(Producto producto,double cantidad) {
		
		double precioSugerido = (cantidad * producto.getPrecioSugerido()) / producto.getMedida();
		
		producto.setMedida(cantidad);
		
		producto.setPrecioSugerido(precioSugerido);
		
		return producto;
		
	}
	
	public static Producto getCostoXUnidad(Producto producto,int unidad) {
		
		double precioSugerido = (unidad * producto.getPrecioSugerido());
		
		producto.setPrecioSugerido(precioSugerido);
		
		return producto;
		
	}
	
	public static MateriaPrima getPrecioXUnidadMateriaPrima(MateriaPrima materiaPrima,double unidades) {
		
		double precio = materiaPrima.getPrecio() * unidades;
		
		double medida = materiaPrima.getMedida() * unidades;
		
		materiaPrima.setMedida(medida);
		
		materiaPrima.setPrecio(precio);
		
		materiaPrima.setUnidades(unidades);
		
		return materiaPrima;
		
	}
	
	public static MateriaPrima getPrecioXMedidaMateriaPrima(MateriaPrima materiaPrima,double medida) {
		
		double precio = (materiaPrima.getPrecio() * medida) / materiaPrima.getMedida();
		
		double unidades =  medida / materiaPrima.getMedida();
		
		materiaPrima.setUnidades(unidades);
		
		materiaPrima.setPrecio(precio);
		
		materiaPrima.setMedida(medida);
		
		return materiaPrima;
		
	}
	
	public static void actualizarStockXVenta(Venta venta, MateriaPrimaBO materiaPrimaDAO, ProductoBO productoDAO) {
		
		Producto producto = productoDAO.getByID(String.valueOf(venta.getProducto().getId()));
		
		Set<IngredientesProducto> ingredientes = producto.getIngredientesProductos();
		
		Iterator<IngredientesProducto> ingredientesIt = ingredientes.iterator();
		
		double stock = 0;
		
		while (ingredientesIt.hasNext()) {
			
			IngredientesProducto ingrediente = (IngredientesProducto) ingredientesIt.next();
			
			MateriaPrima materiaPrima = ingrediente.getMateriaPrima();
			
			if(materiaPrima.getTipoMateriaPrima().getId() == 2){//no es un ingrediente por lo tanto solo descuento por unidad de producto vendido
			
				stock = materiaPrima.getMedidaDisponibleStock() - (ingrediente.getCantidad() * venta.getUnidades());
			
			}else{
				
				stock = materiaPrima.getMedidaDisponibleStock() - (ingrediente.getCantidad() * venta.getCantidad() * venta.getUnidades());
				
			}
			
			if(stock < 0){ 
				materiaPrima.setMedidaDisponibleStock(0.0);
			}else{
				materiaPrima.setMedidaDisponibleStock(stock);
			}
			
			materiaPrimaDAO.save(materiaPrima);
		}
		
	}
	
	public static void actualizarStockXQuitarVenta(Producto producto, int cantidad, double medida, MateriaPrimaBO materiaPrimaDAO) {
		
		Set<IngredientesProducto> ingredientes = producto.getIngredientesProductos();
		
		Iterator<IngredientesProducto> ingredientesIt = ingredientes.iterator();
		
		while (ingredientesIt.hasNext()) {
			
			IngredientesProducto ingrediente = (IngredientesProducto) ingredientesIt.next();
			
			MateriaPrima materiaPrima = ingrediente.getMateriaPrima();
			
			if(materiaPrima.getTipoMateriaPrima().getId() == 2){//no es un ingrediente por lo tanto solo descuento por unidad de producto vendido
				
				materiaPrima.setMedidaDisponibleStock(materiaPrima.getMedidaDisponibleStock() + (ingrediente.getCantidad() * cantidad));
			
			}else{
				
				materiaPrima.setMedidaDisponibleStock(materiaPrima.getMedidaDisponibleStock() + (ingrediente.getCantidad() * medida * cantidad));
				
			}
			
			
			
			materiaPrimaDAO.save(materiaPrima);
		}
		
	}
	
}
