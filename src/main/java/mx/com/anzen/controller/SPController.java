package mx.com.anzen.controller;

import org.springframework.web.bind.annotation.RestController;

import mx.com.anzen.bean.Libro;
import mx.com.anzen.bean.ParametrosSP;
import mx.com.anzen.bean.States;
import mx.com.anzen.service.SPService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 * @author: Angel
 * @version: 1.0 (21/02/17) 
 */
@RestController
public class SPController {

	// Inyeccion de dependencias del servicio
	@Autowired
	private SPService spService;

	
	
	// Invoke a Stored Procedure (Consultas)  
	@RequestMapping(value = "/JPA/SP/Query/{id}")
	public List<Libro> getQueryBookSP(@PathVariable int id) {
		
		// Nombre SP
		String nombreSP = "spQueryBook";
				
		// Parametros SP
		List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
		ParametrosSP pId = new ParametrosSP(1,"id",id);
		lstParametrosSP.add(pId);
		Object object = lstParametrosSP;
		
		@SuppressWarnings("unchecked")
		List<Libro> lstBook = (List<Libro>) spService.getQuerySP(nombreSP,object);
		return lstBook;
	}
	
	
	// Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	@RequestMapping(value = "/JPA/SP/CRUD/{id}/{autor}/{name}")
	public String getCreateBookSP(@PathVariable int id, @PathVariable String autor, @PathVariable String name) {
		
		// Variables y Constantes
		final int EXITOSO = 0;
		
		// Nombre SP
		String nombreSP = "spInsertBook";
				
		// Parametros SP
		List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
		ParametrosSP pId = new ParametrosSP(1,"id",id);
		ParametrosSP pAuthor = new ParametrosSP(3,"author",autor);
		ParametrosSP pName = new ParametrosSP(3,"name",name);
		lstParametrosSP.add(pId);
		lstParametrosSP.add(pAuthor);
		lstParametrosSP.add(pName);
		
		@SuppressWarnings("unchecked")
		Vector<Object> response = (Vector<Object>)spService.getCRUDSP(nombreSP, lstParametrosSP);
				
		// Validar repsuesta
		if((Integer)response.get(0)==EXITOSO){
			return "Exitoso";
		} else {
			return (String)response.get(1);
		}
		
	}
	
	
	
	// Prueba de performace con SP (PROVISIONAL POR PRUEBAS)
	@RequestMapping(value="/catalogs/retrieveWithSplit/states/{id}")
	public String retrieveStateWithSplit(@PathVariable int id){
		spService.getState(id);
		return "Exitoso";
	}
	
	// Prueba de performace con SP (PROVISIONAL POR PRUEBAS)
	@RequestMapping(value="/catalogs/retrieve/states/{id}")
	public String retrieveState(@PathVariable int id){
		spService.getState(id);
		return "Exitoso";
	}
	
}
