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
	
	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;
	
	// Inicio para formar JSON
	SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SS");    // yyyy-MM-dd 
	
	  
	  /**
	   * Invoke a Stored Procedure (Consultas)
	   * 
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
    @SuppressWarnings("rawtypes")
	public StringBuilder getQuerySP(String nameSP, List<ParametrosSP> parametros){
		  
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  //String JSON = "";
		  
		  StringBuilder sb = new StringBuilder();
		  
		  try{
			  // Se invoca el SP
			   storedProcedure = entityManager.createStoredProcedureQuery(nameSP);

			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros
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
  
		      
			  
			// Parametros de salida
			  //storedProcedure.registerStoredProcedureParameter("json", java.sql.Clob.class, ParameterMode.OUT);
			  
			  
// Inicia Respuesta BD (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Inicio Respuesta BD(getQuerySP): \t" + time.format(new Date())));
			  
			  // execute SP
			  storedProcedure.execute();
			  
// Fin Respuesta BD (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Fin Respuesta BD(getQuerySP): \t\t" + time.format(new Date())));
			  
			  
			//get the result
			//java.sql.Clob reslt = (java.sql.Clob)storedProcedure.getOutputParameterValue("json");
			
			
			//sb = clobToString(reslt);
			  
			  
		  } catch(Exception ex){
			  System.out.println(ex.getMessage());
		  }

	      //return JSON;
		  
		// Convertir consulta de base de datos en JSON (PROVISIONAL POR PRUEBAS)
//		  if(tipoSP == 1){
//			  return convertJSONWithSplit((ArrayList)storedProcedure.getResultList());
//				
//		  } else {
//			  return convertJSON((ArrayList)storedProcedure.getResultList());
//			  //return sb;
//		  }
		  
		  
		  StringBuilder sbTesting = convertJSON((ArrayList)storedProcedure.getResultList());  
		  
		  return sbTesting;
		  
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
		  StoredProcedureQuery storedProcedure = null;
		  Integer parametroEntero = 0;
		  Float parametroDecimal = 0F;
		  String parametroTexto = "";
		  Integer reslt = null;
		  String message = null;
		  Vector<Object> response = new Vector<Object>();
		  
		  // Constantes
		  final int ENTEROS = 1, DECIMALES=2, TEXTO=3;
		  
		  try{
			  // Se invoca el SP
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);
			  
			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros de Entrada
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case ENTEROS:
			  			parametroEntero = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroEntero);
			  			break;
			  		
			  		case DECIMALES:
			  			parametroDecimal = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDecimal);
			  			break;
			  			
			  		case TEXTO:
			  			parametroTexto = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroTexto);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			  
			  // Parametros de salida
			  storedProcedure.registerStoredProcedureParameter("reslt", Integer.class, ParameterMode.OUT);
			  storedProcedure.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
			  
			  // execute SP
			  storedProcedure.execute();
			  
		      // get the result
			  reslt = (Integer)storedProcedure.getOutputParameterValue("reslt");
			  message = (String)storedProcedure.getOutputParameterValue("message");
			  
			  response.add(reslt);
			  response.add(message);
			  
			  
			  
		  } catch(Exception ex){
			  response.add(-1);
			  response.add(ex.getMessage().toString());
			  
		  }
		  
		  return response;
	  }


	  /**
	   * Metodo para generar un JSON del metodo getQuerySP a partir del patro Key : Value con el metodo Split
	   * @param lstConsulta: Lista para ocnvertir en JSON
	   * @return json: Una cadena de texto con el formato JSON de la lista recibida por parametro
	   */
	@SuppressWarnings({ "unused", "rawtypes" })
	private StringBuilder convertJSONWithSplit(ArrayList<?> lstConsulta){

// Inicia JSON (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Inicio JSON(convconvertJSONWithSplitertJSON): \t" + time.format(new Date())));
		
		
		StringBuilder json = new StringBuilder();
		  json.append("{\"convert\": [");
		  
		  int totalregistros = lstConsulta.size();
// Inicia JSON (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Total Registros(convconvertJSONWithSplitertJSON): \t" + totalregistros));
		  for(int i=0; i<totalregistros; i++){
			  
			  json.append("{");
			  int totalCampos = ((Object[])lstConsulta.get(i)).length;
			  
			  for(int j=0; j<totalCampos; j++){
				  String[] aux = ((String)((Object[])lstConsulta.get(i))[j]).split(":");
				  json.append("\"").append(aux[0]).append("\": ").append("\"").append(aux[1]).append("\"");
				  
				  if((j+1) != totalCampos){
					  json.append(",");
				  }
			  }
			  json.append("}");
			  
			  if((i+1) != totalregistros){
				  json.append(",");
			  }
		  }
		  
		  json.append("]}");
		  
		  
// Fin JSON (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Fin JSON(convertJSONWithSplit): \t\t" + time.format(new Date())));
		
		  return json;
	  }
	
	
	
	
	  /**
	   * Metodo para generar un JSON del metodo getQuerySP
	   * @param lstConsulta: Lista para ocnvertir en JSON
	   * @return json: Una cadena de texto con el formato JSON de la lista recibida por parametro
	   */
	private StringBuilder convertJSON(ArrayList<?> lstConsulta){
	
// Inicia JSON (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Inicio JSON(convertJSON): \t" + time.format(new Date())));
		
		
		// Se crea el StringBuilder que contendra el JSON completo
		StringBuilder json = new StringBuilder();
		
		// Se declara la apertura del objeto array(convert) JSON
		json.append("[");
//		json.append("{ \"convert\" : { \"fields\" : [");
		
		// Ciclo para concatenar registro por registro del nuevo JSON
		int totalregistros = lstConsulta.size(); 
// Total Registros (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Total Registros(convertJSON): \t" + totalregistros));
		
		for(int i=0; i<totalregistros; i++){
			json.append(lstConsulta.get(i));
		
			// Coma que servira para separar registro por registro del array
			if((i+1) != totalregistros){
				json.append(",");
			}
		}
		  
		// Se cierra la estructra JSON
		json.append("]");
//		json.append("]}}");
		
		
// Fin JSON (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Fin JSON(convertJSON): \t\t" + time.format(new Date())));
//System.out.print("json");
//System.out.println(json);

		return json;
	  }
	
	
	  
	  
	  /**
	   * Metodo para generar la lista que retornara el metodo getQuerySP
	   * @param lstConsulta: Lista de consulta nativa del SP
	   * @return lstResponse: Lista que sera retornada por el metodo getQuerySP con los campos casteados
	   */
	  @SuppressWarnings("rawtypes")
	  public ArrayList<?> responseArrayList(ArrayList lstConsulta){
		  
		  // Se obtiene total de registros de la columna
		  int totalregistros = lstConsulta.size();
		  
		  // Se crea lista donde se van a guarda rlos registros
		  ArrayList<ArrayList<?>> lstCampos = new ArrayList<ArrayList<?>>();
		  
		  // Ciclo para recorrer cada registro de la consulta
		  for(int i=0; i<totalregistros; i++){
			  
			  // Lista para guardar los campos casteados por registro
			  ArrayList<Comparable> lstAux = new ArrayList<>();
			  int totalCampos = ((Object[])lstConsulta.get(i)).length;
			  
			  // Se itera cada registro de la consulta para guardar y castear el tipo de dato a regresar en la nueva lista 
			  for(int j=0; j<totalCampos; j++){
				   
				  if(((Object[])lstConsulta.get(i))[j] == null){
					  lstAux.add(null);
					  break;
				  } 
				  
				  if(((Object[])lstConsulta.get(i))[j] instanceof String){
						lstAux.add((String)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Byte) {
						lstAux.add((Byte)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Short) {
						lstAux.add((Short)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Integer) {
						lstAux.add((Integer)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Long) {
						lstAux.add((Long)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Float) {
						lstAux.add((Float)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Double) {
						lstAux.add((Double)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Boolean) {
						lstAux.add((Boolean)((Object[])lstConsulta.get(i))[j]);
				  }else if (((Object[])lstConsulta.get(i))[j] instanceof Character) {
						lstAux.add((Character)((Object[])lstConsulta.get(i))[j]);
				  }

			  }
			  
			  // Se guarda el registro de cada iteracion con los campos casteados
			  lstCampos.add(lstAux);
		  }
		  
		  
		  return lstCampos; 
	  }
	  
	  
	  
	  @SuppressWarnings("unused")
	private StringBuilder clobToString(Clob data) {
		    StringBuilder sb = new StringBuilder();
		    try {
		        Reader reader = data.getCharacterStream();
		        BufferedReader br = new BufferedReader(reader);

		        String line;
		        while(null != (line = br.readLine())) {
		            sb.append(line);
		        }
		        br.close();
		    } catch (SQLException e) {
		        // handle this exception
		    } catch (IOException e) {
		        // handle this exception
		    }
		    return sb;
		}
	  
	  
}

