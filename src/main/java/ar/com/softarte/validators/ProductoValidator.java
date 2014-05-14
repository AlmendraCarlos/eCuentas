package ar.com.softarte.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.com.softarte.model.Producto;

public class ProductoValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Producto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "descripcion.required", "es obligatorio.");
		
		Producto producto = (Producto)target;
		
		if(producto.getTipoMedida().getId() == -1){
			errors.rejectValue("tipoMedida", "tipoMedida.required", "es obligatorio.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medida", "medida.required", "es obligatorio.");
		
		if(producto.getTipoProducto().getId() == -1){
			errors.rejectValue("tipoProducto", "tipoProducto.required", "es obligatorio.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ganancia", "ganancia.required", "es obligatorio.");
	}

}
