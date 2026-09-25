package validator;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import dominio.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports( Class<?> clazz ) {
		//just validate the User instances
		return User.class.isAssignableFrom( clazz );
     }

	// Regresa true si la cadena es un numero decimal mayor que cero
	private boolean esNumeroPositivo( String valor ) {
		try {
			return new BigDecimal( valor.trim() ).compareTo( BigDecimal.ZERO ) > 0;
		} catch( Exception e ) {
			return false;
		}
	}

	// Regresa true si la cadena tiene formato de fecha yyyy-MM-dd valida
	private boolean esFecha( String valor ) {
		try {
			java.sql.Date.valueOf( valor.trim() );
			return true;
		} catch( Exception e ) {
			return false;
		}
	}

	@Override
	public void validate( Object target, Errors errors ) {

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "fecha", "fecha.required" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "monto", "monto.required" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "periodo", "periodo.required" );
		ValidationUtils.rejectIfEmpty( errors, "moneda", "moneda.required" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "cuota", "cuota.required" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "tea", "tea.required" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "fechaVencimiento", "fechaVencimiento.required" );

		User credito = (User) target;

		if ( !errors.hasFieldErrors( "fecha" ) && !esFecha( credito.getFecha() ))
			errors.rejectValue( "fecha", "fecha.invalida" );

		if ( !errors.hasFieldErrors( "monto" ) && !esNumeroPositivo( credito.getMonto() ))
			errors.rejectValue( "monto", "monto.invalido" );

		if ( !errors.hasFieldErrors( "periodo" ) ) {
			try {
				if ( Integer.parseInt( credito.getPeriodo().trim() ) <= 0 )
					errors.rejectValue( "periodo", "periodo.invalido" );
			} catch( NumberFormatException e ) {
				errors.rejectValue( "periodo", "periodo.invalido" );
			}
		}

		if ( !errors.hasFieldErrors( "moneda" )
				&& !"Soles".equals( credito.getMoneda() ) && !"Dolares".equals( credito.getMoneda() ))
			errors.rejectValue( "moneda", "moneda.required" );

		if ( !errors.hasFieldErrors( "cuota" ) && !esNumeroPositivo( credito.getCuota() ))
			errors.rejectValue( "cuota", "cuota.invalida" );

		if ( !errors.hasFieldErrors( "tea" ) && !esNumeroPositivo( credito.getTea() ))
			errors.rejectValue( "tea", "tea.invalida" );

		if ( !errors.hasFieldErrors( "fechaVencimiento" ) ) {
			if ( !esFecha( credito.getFechaVencimiento() ))
				errors.rejectValue( "fechaVencimiento", "fechaVencimiento.invalida" );
			else if ( !errors.hasFieldErrors( "fecha" )
					&& credito.getFechaVencimiento().trim().compareTo( credito.getFecha().trim() ) <= 0 )
				errors.rejectValue( "fechaVencimiento", "fechaVencimiento.anterior" );
		}
	}
}
