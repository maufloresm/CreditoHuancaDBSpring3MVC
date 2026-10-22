package control;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import baseDeDatos.UsuariosJDBCTemplate;
import dominio.CreditoForm;
import dominio.PagoTarjeta;
import servicio.UserService;
import validator.UserValidator;

@Controller
public class UserController {

    private static final int MAX_INTENTOS = 5;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @RequestMapping(value = {"/credito.htm", "/userRegistration.htm"}, method = RequestMethod.GET)
    public String showForm(HttpServletRequest request, ModelMap model) {
        String clientIp = request.getRemoteAddr();
        UsuariosJDBCTemplate dao = new UsuariosJDBCTemplate();
        int intentos = dao.getIntentos(clientIp);

        if (intentos >= MAX_INTENTOS) {
            return "Exceso";
        }

        CreditoForm creditoForm = new CreditoForm();
        // Cargar los pagos existentes en la base de datos (o iniciales si está vacía)
        List<PagoTarjeta> filasPagos = userService.obtenerPagos();
        creditoForm.setPagosTarjetas(filasPagos);

        model.addAttribute("creditoForm", creditoForm);
        model.addAttribute("user", creditoForm);

        return "userForm";
    }

    @RequestMapping(value = {"/credito.htm", "/userRegistration.htm"}, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request,
                           @ModelAttribute("creditoForm") CreditoForm creditoForm,
                           BindingResult result,
                           ModelMap model) {

        String clientIp = request.getRemoteAddr();
        UsuariosJDBCTemplate dao = new UsuariosJDBCTemplate();
        int intentos = dao.getIntentos(clientIp);

        if (intentos >= MAX_INTENTOS) {
            return "Exceso";
        }

        userValidator.validate(creditoForm, result);

        if (result.hasErrors()) {
            model.addAttribute("user", creditoForm);
            return "userForm";
        }

        // Registrar crédito y pagos en BD
        userService.registrarCredito(creditoForm);
        dao.addIntento(clientIp);

        model.addAttribute("creditoForm", creditoForm);
        model.addAttribute("user", creditoForm);

        return "userSuccess";
    }
}
