package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import dominio.CreditoForm;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreditoForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreditoForm form = (CreditoForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fecha", "required.fecha", "La fecha es obligatoria.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "moneda", "required.moneda", "Especifique el tipo de moneda.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaVencimiento", "required.fechaVencimiento", "La fecha de vencimiento es obligatoria.");

        if (form.getMonto() == null || form.getMonto() <= 0) {
            errors.rejectValue("monto", "invalid.monto", "Ingrese un monto válido mayor a 0.");
        }

        if (form.getPeriodo() == null || form.getPeriodo() <= 0) {
            errors.rejectValue("periodo", "invalid.periodo", "Ingrese un periodo en meses válido.");
        }

        if (form.getCuota() == null || form.getCuota() <= 0) {
            errors.rejectValue("cuota", "invalid.cuota", "Ingrese el valor de la cuota.");
        }

        if (form.getTea() == null || form.getTea() < 0) {
            errors.rejectValue("tea", "invalid.tea", "Ingrese una TEA válida.");
        }
    }
}
