package mx.com.anzen.service;

import java.util.List;

import mx.com.anzen.bean.States;

/**
 * 
 * @author: Angel
 * @category: Persistencia
 * @version: 1.0 (21/02/17) 
 */
public interface SPService {
	
	/**
	 * Invoke a Stored Procedure (Consultas)
	 * 
	 * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	 * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	 *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	 *                    
	 * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	 */
	public Object getQuerySP(String nombreSP, Object object);
	
	
	/**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	public Object getCRUDSP(String nombreSP, Object object);
	
	
	
	// Recuperar estado por ID
	public String getState(int id);

}
