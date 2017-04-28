package mx.com.anzen.repository;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import mx.com.anzen.bean.ParametrosSP;
import mx.com.anzen.utilities.DataType;

/**
 * 
 * @author: Angel
 * @category: Persistencia
 * @version: 1.0 (21/02/17) 
 */
@Repository
@Transactional
public class SPRepository extends DataType{
	
	// An EntityManager will be automatically injected from entityManagerFactory setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;
	
	
	// Inicio para formar JSON
	SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SS");    																													// yyyy-MM-dd 

	
	/**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * Tipos de Datos de PARAMETROS
	   * 
	   * PARAMETRO_Integer = 1
	   * PARAMETRO_Float = 2		
	   * PARAMETRO_String = 3
	   * PARAMETRO_Byte = 4, 
	   * PARAMETRO_Short = 5, 
	   * PARAMETRO_Long = 6, 
	   * PARAMETRO_Double = 7, 
	   * PARAMETRO_Boolean = 8, 
	   * PARAMETRO_Character = 9;
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	public StringBuilder getQuerySP(String nameSP, List<ParametrosSP> parametros){
		  
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  
		  try{
			  // registra Stored Procedure, declara y configura parametros de entrada
			  storedProcedure = createStoredProcedure(nameSP, parametros);
			  																																																												System.out.println(String.valueOf("Inicio Respuesta BD(getQuerySP): \t" + time.format(new Date())));			  
			  // execute SP
			  storedProcedure.execute();
			  																																																												System.out.println(String.valueOf("Fin Respuesta BD(getQuerySP): \t\t" + time.format(new Date())));
		  } catch(Exception ex){
			  ex.getMessage();
		  }
		  
		  return convertJSON((ArrayList<?>)storedProcedure.getResultList());
	  }
	  
	  
	  /**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * Tipos de Datos de PARAMETROS
	   * 1.- Enteros
	   * 2.- Decimales
	   * 3.- Texto
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	public Object getCRUDSP(String nameSP, List<ParametrosSP> parametros){
		
		  // Variable
		  Integer result = null;
		  String message = null;
		  Vector<Object> response = new Vector<Object>();
		 
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  
		  try{
			  
			  // registra Stored Procedure, declara y configura parametros de entrada
			  storedProcedure = createStoredProcedure(nameSP, parametros);
			  
			  // registra parametros de salida
			  storedProcedure.registerStoredProcedureParameter("result", Integer.class, ParameterMode.OUT);
			  storedProcedure.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
			  
			  // executa Stored Proceudre
			  storedProcedure.execute();
			  
		      // recupera parametros de salida
			  result = (Integer)storedProcedure.getOutputParameterValue("result");
			  message = (String)storedProcedure.getOutputParameterValue("message");
			  
			  response.add(result);
			  response.add(message);
			  
		  } catch(Exception ex){
			  response.add(-1);
			  response.add(ex.getMessage().toString());
		  }
		  
		  return response;
	  }

	
	/**
	   * Configura los parametros enviados al Stored Procedure
	   * Tipos de Datos de PARAMETROS
	   * 
	   * PARAMETRO_Integer = 1
	   * PARAMETRO_Float = 2		
	   * PARAMETRO_String = 3
	   * PARAMETRO_Byte = 4, 
	   * PARAMETRO_Short = 5, 
	   * PARAMETRO_Long = 6, 
	   * PARAMETRO_Double = 7, 
	   * PARAMETRO_Boolean = 8, 
	   * PARAMETRO_Character = 9;
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return StoredProcedureQuery: Retorna un objeto StoredProcedureQuery configurado con el Stored Procedure a invocar y sus parametros
	   */
	private StoredProcedureQuery createStoredProcedure(String nameSP, List<ParametrosSP> parametros){
		
		// Variable
		StoredProcedureQuery storedProcedure = null;
		
		try{
			
			  // registra Stored Procedure
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);

			  // declara y configura parametros
			  int totalParametros = parametros.size();
			  
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case PARAMETRO_Integer:
			  			parametroInteger = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroInteger);
			  			break;
			  		
			  		case PARAMETRO_Float:
			  			parametroFloat = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroFloat);
			  			break;
			  			
			  		case PARAMETRO_String:
			  			parametroString = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroString);
			  			break;
			  			
			  		case PARAMETRO_Byte:
			  			parametroByte = (Byte) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Byte.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroByte);
			  			break;
			  			
			  		case PARAMETRO_Short:
			  			parametroShort = (Short) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Short.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroShort);
			  			break;
			  			
			  		case PARAMETRO_Long:
			  			parametroLong = (Long) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Long.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroLong);
			  			break;
			  			
			  		case PARAMETRO_Double:
			  			parametroDouble = (Double) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Double.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDouble);
			  			break;
			  			
			  		case PARAMETRO_Boolean:
			  			parametroBoolean = (Boolean) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Boolean.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroBoolean);
			  			break;
			  			
			  		case PARAMETRO_Character:
			  			parametroCharacter = (Character) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Character.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroCharacter);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			
		} catch(Exception ex){
			ex.getMessage();
		}
		
		return storedProcedure;
		
	}
	
	
	  /**
	   * Metodo para generar un JSON del metodo getQuerySP
	   * @param lstConsulta: Lista para ocnvertir en JSON
	   * @return json: Una cadena de texto con el formato JSON de la lista recibida por parametro
	   */
	private StringBuilder convertJSON(ArrayList<?> lstConsulta){
		
		// Se crea el StringBuilder que contendra el JSON completo y se apertura
		StringBuilder json = new StringBuilder();
		json.append("[");

		// Ciclo para concatenar registro por registro del nuevo JSON
		int totalregistros = lstConsulta.size(); 
																																																															System.out.println(String.valueOf("Total Registros para generar JSON: \t" + totalregistros));
																																																															System.out.println(String.valueOf("Inicio --> Genera JSON: \t\t" + time.format(new Date())));
		for(int i=0; i<totalregistros; i++){
			json.append(lstConsulta.get(i));
		
			// Coma que servira para separar registro por registro del array
			if((i+1) != totalregistros){
				json.append(",");
			}
		}		  
		
		// Se cierra la estructra JSON
		json.append("]");
																																																															System.out.println(String.valueOf("Fin --> Genera JSON: \t\t\t" + time.format(new Date())));
		return json;
	}
	
}

