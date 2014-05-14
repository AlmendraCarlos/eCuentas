package ar.com.softarte.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.com.softarte.model.MateriaPrima;
import ar.com.softarte.model.Producto;

public class MateriaPrimaValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return MateriaPrima.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "descripcion.required", "es obligatorio.");
		
		MateriaPrima materiaPrima = (MateriaPrima)target;
		
		if(materiaPrima.getTipoMedida().getId() == -1){
			errors.rejectValue("tipoMedida", "tipoMedida.required", "es obligatorio.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medida", "medida.required", "es obligatorio.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "precio.required", "es obligatorio.");
		
	}

}
