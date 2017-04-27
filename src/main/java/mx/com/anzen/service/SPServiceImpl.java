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
		//String nombreSP = "adb_spr_statesTest";
//		String nombreSP = "adb_spr_camposportransacciones";
		
		
		String nombreSP = "adb_spr_statesCovertJSON";
		
//		if(tipoSP == 1){
//			nombreSP = "adb_spr_statesCovertJSONWithSplit";
//			System.out.println("nombreSP: " + nombreSP);
//		} else {
//			nombreSP = "adb_spr_statesCovertJSON";
//			System.out.println("nombreSP: " + nombreSP);
//		}
		
						
		// Parametros SP
		List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
		ParametrosSP pId = new ParametrosSP(1,"idState",id);
		lstParametrosSP.add(pId);

		
// Inicia Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Inicio FLujo Completo: \t" + time.format(new Date())));
		
		// Invocar SP para insert, update y delete
		StringBuilder JSON = spRepository.getQuerySP(nombreSP, lstParametrosSP);
				
// Inicia Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Inicio Respuesta Parseo Objeto JAVA(Parseo): \t" + time.format(new Date())));


try{
		// Parseador JSON
		JSONParser parser = new JSONParser();

		// Se parsea String a JSONObject
//		JSONObject jsonObject = (JSONObject) parser.parse(JSON.toString());
		JSONArray query = (JSONArray) parser.parse(JSON.toString()) ;
		
		Iterator<?> key = query.iterator();
		
		
		for(int i = 0; i < query.size(); i++){
		//while (key.hasNext()) {
//            String k = key.next().toString();
//            System.out.println("Key : " + k); //+ ", value : " + objects.getString(k));
            
            
            JSONObject ob = (JSONObject) parser.parse(key.next().toString());
      /*  
            System.out.println("----------------------------------------------Registro " + (i+1) + "-------------------------------------------------------");
            
            System.out.println("idStateCode: " + ob.get("idStateCode"));
            System.out.println("numHabitnates: " + ob.get("numHabitnates"));
            System.out.println("state: " + ob.get("state"));
            System.out.println("presupuesto: " + ob.get("presupuesto"));
            System.out.println("pais: " + ob.get("pais"));
            System.out.println("referencia1: " + ob.get("referencia1"));
            System.out.println("referencia2: " + ob.get("referencia2"));
            System.out.println("referencia3: " + ob.get("referencia3"));
            System.out.println("referencia4: " + ob.get("referencia4"));
            System.out.println("referencia5: " + ob.get("referencia5"));
            System.out.println("referencia6: " + ob.get("referencia6"));
            System.out.println("referencia7: " + ob.get("referencia7"));
            System.out.println("referencia8: " + ob.get("referencia8"));
            System.out.println("referencia9: " + ob.get("referencia9"));
            System.out.println("referencia10: " + ob.get("referencia10"));
            
            */
        }
		
		
		
		
		// Fin Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
		System.out.println(String.valueOf("Fin Respuesta Parseo Objeto JAVA(Parseo): \t\t" + time.format(new Date())));
		        
		        
		// Inicia Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
		System.out.println(String.valueOf("Fin FLujo Completo: \t" + time.format(new Date())));
		
		
		
		//Se obtienen los campos del JSON
//		JSONArray query = (JSONArray) jsonObject.get("convert");
	
//		for(int i=0; i<query.size(); i++){
			
			
//			System.out.println(query);
			
			
			// Se obtiene campo por campo de cada seccion (header, operation, footer)
			//JSONObject row = (JSONObject)parser.parse(query.get(i).toString());						
			
			// Se obtiene la validacion de cada campo
			//JSONObject validation = (JSONObject)row.get("validation");
			
//			// Se añade el nuevo campo a la lista
//			fileFields = new FileFields((String)row.get("nombre"), (String)row.get("longField"), (String)row.get("start"), (String)row.get("end"), (String)row.get("format"),
//					new ValidationFields((Long)validation.get("integer"), (Long)validation.get("decimal")));
//			lstCampos.add(fileFields);
			
//		}
		
		System.out.println("Si llega");
//		System.out.println(jsonObject);
		//System.out.println(query);
		
} catch(Exception ex){
	System.out.println(ex.getMessage());
}


/*
		// Parsear JSON a Map --> List
		JSONParser parser = new JSONParser();
        parser.addTypeHint(".convert[]", StatesCovertJSON.class);
        
        Map<String, List<StatesCovertJSON>> result1 = parser.parse(Map.class, JSON.toString());

// Fin Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Fin Respuesta Parseo Objeto JAVA(Parseo): \t\t" + time.format(new Date())));
        
        
// Inicia Respuesta Parseo Objeto JAVA (PROVISIONAL POR PRUEBAS)
System.out.println(String.valueOf("Fin FLujo Completo: \t" + time.format(new Date())));


        
        for (Entry<String, List<StatesCovertJSON>> entry : result1.entrySet()) {
            for (StatesCovertJSON states : entry.getValue()) {
            	System.out.println("VALUE(ID) :->" + states.getIdStateCode());
                System.out.println("VALUE(State) :->" + states.getState());
                System.out.println("VALUE(NumHabitnates) :->" + states.getNumHabitnates());
                System.out.println("VALUE(Presupuesto) :->" + states.getPresupuesto());
                System.out.println("VALUE(NumHabitnates) :->" + states.getPais());
            }
        }
*/		
		
		return "Successful";
	}
}
