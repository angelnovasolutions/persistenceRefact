package mx.com.anzen.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.anzen.bean.ParametrosSP;
import mx.com.anzen.bean.States;
import mx.com.anzen.bean.StatesCovertJSON;
import mx.com.anzen.repository.SPRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * @author: Angel
 * @category: Persistencia
 * @version: 1.0 (21/02/17) 
 */
@Service
@Transactional
public class SPServiceImpl implements SPService{

	@Autowired
	private SPRepository spRepository; 

	// Inicio para formar JSON
	SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SS");    // yyyy-MM-dd
	
	
	/**
	 * Invoke a Stored Procedure (Consultas)
	 * 
	 * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	 * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	 *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	 *                    
	 * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getQuerySP(String nombreSP, Object object) {
		
		// Invocar SP para consulta
		//return spRepository.getQuerySP(nombreSP, (List<ParametrosSP>)object);
		return null;
	}

	
	/**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	@SuppressWarnings("unchecked")
	@Override
	public Object getCRUDSP(String nombreSP, Object object) {

		// Invocar SP para insert, update y delete
		return spRepository.getCRUDSP(nombreSP, (List<ParametrosSP>)object);		
	}
	
	

	
	
	@Override
	public String getState(int id) {
		
		// Nombre SP
		String nombreSP = "adb_spr_statesCovertJSON";
								
		// Parametros SP
		List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
		ParametrosSP pId = new ParametrosSP(1,"idState",id);
		lstParametrosSP.add(pId);		
																																									System.out.println(String.valueOf("Inicio FLujo Completo: \t" + time.format(new Date())));		
		// Invocar SP para insert, update y delete
		StringBuilder JSON = spRepository.getQuerySP(nombreSP, lstParametrosSP);

		try{
																																									System.out.println(String.valueOf("Inicio parseo: JSON --> Objeto JAVA: \t" + time.format(new Date())));			
			// Parseador JSON (String to JSONObject)
			JSONParser parser = new JSONParser();
			JSONArray query = (JSONArray) parser.parse(JSON.toString()) ;		
																																									System.out.println(String.valueOf("Fin parseo: JSON --> Objeto JAVA: \t" + time.format(new Date())));
			Iterator<?> key = query.iterator();
			for(int i = 0; i < 1; i++);
			{
				// Se parsea JSONArray a JSONObject por registro y asi poder recuperar cada uno de los elementos por registro
				JSONObject ob = (JSONObject) parser.parse(key.next().toString());
				System.out.println(ob.get("idStateCode") + " - " + ob.get("state"));
			}
																																									System.out.println(String.valueOf("Fin FLujo Completo: \t" + time.format(new Date())));		
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return "Successful";
	}
}
