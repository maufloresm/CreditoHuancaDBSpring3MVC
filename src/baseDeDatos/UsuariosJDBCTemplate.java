package baseDeDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.naming.NamingException;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;  // Version mysql-connector-java-5.1.17-bin
import com.mysql.cj.jdbc.MysqlDataSource;                // Version mysql-connector-java-8.0.18

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.MessageDigest; 
import java.util.Calendar;
import java.util.GregorianCalendar;
import dominio.User;
import dominio.BDMySql;

public class UsuariosJDBCTemplate {

    MysqlDataSource ds = null;
    
    Connection connect = null;
    Statement statement = null;
    BDMySql bdMySql;
        

   public Connection getConexion () {
	   
	   MysqlDataSource ds = null;	   
	   Connection connect = null;
	   
	   try {
          ds = new MysqlDataSource();

          ApplicationContext context = new ClassPathXmlApplicationContext( "Spring-Datasource.xml" );	            
          BDMySql bdMySql = (BDMySql)context.getBean( "servicioPropiedades" );
       	            
          ds.setUrl( bdMySql.getUrlBD() );
          ds.setUser( bdMySql.getUser() );
          ds.setPassword( bdMySql.getPassword() ); 
          connect = ds.getConnection();
          return connect ;
	   } 
       catch (SQLException error) 
         { 
     	   System.out.println( error.toString() );
     	    return connect ;
         }          
   }
    
   public void insertarU( User credito ) throws NamingException
	   {
	        Connection connect = null;
	        PreparedStatement ps = null;
	        try
	        {
	            connect = getConexion();

	            String sql = "insert into credito ( fecha, monto, periodo, moneda, cuota, tea, fecha_vencimiento ) values ( ?, ?, ?, ?, ?, ?, ? )";
	            ps = connect.prepareStatement( sql );
	            ps.setDate( 1, java.sql.Date.valueOf( credito.getFecha().trim() ));
	            ps.setBigDecimal( 2, new java.math.BigDecimal( credito.getMonto().trim() ));
	            ps.setInt( 3, Integer.parseInt( credito.getPeriodo().trim() ));
	            ps.setString( 4, credito.getMoneda() );
	            ps.setBigDecimal( 5, new java.math.BigDecimal( credito.getCuota().trim() ));
	            ps.setBigDecimal( 6, new java.math.BigDecimal( credito.getTea().trim() ));
	            ps.setDate( 7, java.sql.Date.valueOf( credito.getFechaVencimiento().trim() ));
	            ps.executeUpdate();
	        }
	        catch (SQLException error)
	          {
	        	System.out.println( error.toString() );
	          } finally {
	            try { if ( ps != null ) ps.close(); }
	              catch (SQLException error) { System.out.println( "Error Statement : " + error.toString()); }
	            try { if ( connect != null ) connect.close(); }
	              catch (SQLException error) { System.out.println ("Error Connect :" + error.toString()); }
	        }
	   }

	   public int getIntentos( String IP ) 
	     {
		   	int intentos = 0;
	        Connection connect = null;
	        try 
	        {	            
	            connect = getConexion();
	 		    String sql = "Select intentos, Pintento from actividad where IP= ?";
	            PreparedStatement ps = connect.prepareStatement( sql );
	            ps.setString( 1, IP );
	            ResultSet resultSet = ps.executeQuery();
	            while ( resultSet.next() ) 
	             {
	                String fecha = resultSet.getString( "Pintento" );
	                intentos = resultSet.getInt( "intentos" );
	                System.out.println( "Conexi�n IP: " + IP );
	                System.out.println( "Fecha de �ltimo intento: " + fecha );
	                System.out.println( "No. Intentos actuales: " + intentos );
	             }
	        } 
	        catch (Exception error)
	          { 
	        	System.out.println( error.toString() );
	          } 
	        finally 
	          {
	            try { connect.close(); } 
	            catch (SQLException error) 
	             { System.out.println( "Error Connect :" + error.toString() ); }
	          }
	        return intentos;
	     }
	   
	   public void addIntento( String IP ) {
		   
	        Connection connect = null;
	        PreparedStatement ps = null;
	        String sql;
	        int intentos = 0;
	        String fecha = "";
	        try 
	        {	            
	            connect = getConexion();
	 		    sql = "Select intentos, Pintento from actividad where IP= ?";
	            ps = connect.prepareStatement( sql );
	            ps.setString( 1, IP );
	            ResultSet resultSet = ps.executeQuery();
	            while (resultSet.next()) {
	                fecha = resultSet.getString( "Pintento" );
	                intentos = resultSet.getInt( "intentos" );
	                System.out.println( "Total de intentos: " + intentos );
	            }	            	           
	            if (intentos == 0 )
	              {
	            	sql = "insert into actividad ( IP, Pintento, intentos ) values ( ?, ?, ? )";
	            	ps = connect.prepareStatement( sql );
	            	ps.setString( 1, IP );
	            	ps.setDate( 2, new java.sql.Date(System.currentTimeMillis() ));
	            	ps.setInt( 3, 1 );
	            	ps.executeUpdate();		
	            	System.out.println( "Conexi�n IP: " + IP + " con 1 intento");
	              }
	            else
	              {
	            	Calendar f = new GregorianCalendar();
	            	String mes = "";
	            	String dia = "";
	            	if( f.get(Calendar.MONTH) + 1 < 10)
	            		mes = "0" + ( f.get(Calendar.MONTH) + 1 );	            	
	            	else
	            		mes = String.valueOf( f.get(Calendar.MONTH) + 1);
	            	
	            	if( f.get(Calendar.DAY_OF_MONTH) < 10)
	            	    dia = "0" + ( f.get(Calendar.DAY_OF_MONTH) );	            	
	            	else
	            	    dia = String.valueOf( f.get(Calendar.DAY_OF_MONTH) );
	            	  
	            	String fechaHoy= f.get(Calendar.YEAR) + "-" + mes + "-" + dia;
	            	System.out.println( fechaHoy + " " + fecha );
	            	if( fechaHoy.compareTo(fecha) == 0 )
	            	 {
		            	sql="update actividad set intentos = ? where IP = ?";
		            	ps = connect.prepareStatement(sql );
		            	ps.setInt( 1, intentos + 1);
		            	ps.setString( 2, IP );
		            	ps.executeUpdate();	
		            	System.out.println( "Otro intento el mismo d�a ..." );
	            	 }
	            	else
	            	 {	
	            		sql="update actividad set intentos = ?, Pintento = ? where IP = ?";
		            	ps = connect.prepareStatement(sql);
		            	ps.setInt( 1, 1 );
		            	ps.setDate( 2, new java.sql.Date(System.currentTimeMillis()) );
		            	ps.setString( 3, IP );
		            	ps.executeUpdate();	
		            	System.out.println( "Un intento en otro d�a ... va 1" );
	            	 }
	            }     
	        } catch (Exception error) { System.out.println(error.toString()); }
	          finally {
	        	try { ps.close(); } catch (SQLException error) { System.out.println("Error Connect :"+error.toString()); }
	            try { connect.close(); } catch (SQLException error) { System.out.println("Error Connect :"+error.toString()); }
	          }
		 } 
	   
	    private static String convertToHex( byte[] data ) { 
	    	
	        StringBuffer buf = new StringBuffer();
	        for( int i = 0; i < data.length; i++ ) { 
	            int halfbyte = ( data[i] >>> 4 ) & 0x0F;
	            int two_halfs = 0;
	            do { 
	                if ( (0 <= halfbyte) && (halfbyte <= 9) ) 
	                  buf.append((char) ('0' + halfbyte));
	                else 
	                  buf.append((char) ('a' + (halfbyte - 10)));
	                halfbyte = data[i] & 0x0F;
	            } while( two_halfs ++ < 1 );
	        } 
	        return buf.toString();
	    } 
 
	    public static String SHA1( String text )  {
	    
	       String resultado = null;
	    try { 
	      MessageDigest md;
  	      md = MessageDigest.getInstance( "SHA-1" );
	      byte[] sha1hash; 
	      md.update( text.getBytes( "iso-8859-1" ), 0, text.length() );
	      sha1hash = md.digest();
          System.out.println( "Password Bytes: " + sha1hash );  
          System.out.println( "Password String con New String: " + new String( sha1hash ) );            
          System.out.println( "Password String con convertToHex : " + convertToHex( sha1hash ) );	      
//	      resultado = new String( sha1hash );
	      resultado = convertToHex( sha1hash );	  	      
	    }
	    catch( Exception e )
	      {
	        System.out.println( "Error: " + e.toString() );
	      }
	        return resultado;
	}
}