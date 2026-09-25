package control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import baseDeDatos.UsuariosJDBCTemplate;
import dominio.User;
import servicio.UserService;
import validator.UserValidator;


@Controller
@RequestMapping( "/userRegistration.htm" )
@SessionAttributes("user")

public class UserController {

	private UserService userService;
	private UserValidator userValidator;
 
	@Autowired
	public UserController( UserService userService, UserValidator userValidator ) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	 
	@RequestMapping( method = RequestMethod.GET )
	public String showUserForm( ModelMap model, HttpServletRequest request ) {
		User user = new User();
		model.addAttribute( "user", user );
		return "userForm";
	}

	@RequestMapping( method = RequestMethod.POST )
	public String onSubmit( @ModelAttribute("user") User user, BindingResult result , HttpServletRequest request ) 
	 {
		int MaxIntentos = 6;
		userValidator.validate( user, result );
		if ( result.hasErrors() ) 
			return "userForm";
	   else {
			
			String formulario = "";
			final String userIpAddress = request.getRemoteAddr();
	        System.out.println( "User IpAddress: " + userIpAddress );
	        UsuariosJDBCTemplate reg = new UsuariosJDBCTemplate();
	        reg.addIntento( userIpAddress );
	        if ( reg.getIntentos(userIpAddress) >= MaxIntentos )
	            formulario = "Exceso";	          
	        else
	          {
	        	userService.add( user );
	        	formulario = "redirect:userSuccess.htm";
	          }			
			return formulario;
		 }
	}
}

