package servicio;

import baseDeDatos.UsuariosJDBCTemplate;
import dominio.User;

public class UserServiceImpl implements UserService {

	@Override
	public void add( User credito ) {

		System.out.println( "Credito agregado correctamente:" );
		System.out.println( "Fecha: " + credito.getFecha() );
		System.out.println( "Monto: " + credito.getMonto() );
		System.out.println( "Periodo (meses): " + credito.getPeriodo() );
		System.out.println( "Moneda: " + credito.getMoneda() );
		System.out.println( "Cuota: " + credito.getCuota() );
		System.out.println( "TEA (%): " + credito.getTea() );
		System.out.println( "Fecha vencimiento: " + credito.getFechaVencimiento() );

		UsuariosJDBCTemplate conn = new UsuariosJDBCTemplate();
		try {
			  conn.insertarU( credito );
		  } catch(Exception e)
		{
			System.out.println("Error NamingException: " + e.toString());
		}
	}
}
