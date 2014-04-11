//------------------------------------------------------------------//
//                      Universidad de Huelva                       //
//          Departamento de Tecnologías de la Información           //
//   Área de Ciencias de la Computación e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//          Compilador del lenguaje Tinto [Versión 0.1]             //
//                                                                  //
//------------------------------------------------------------------//

package tinto.ast.struct;

import java.util.*;

/**
 * Clase que almacena la informaci—n de un programa
 * 
 */
public class Program {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Libreria Principal
	 */
	private Library Main;

	/**
	 * Lista de Librerias necesarias
	 */
	private List<Library> Libs;

	/**
	 * Lista de TODAS LAS LIBRERIAS
	 */
	private List<Library> TODAS;

	
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor de una declaracion de un programa
	 * 
	 */
	public Program() {
		this.Main= null;
		this.Libs = new ArrayList<Library>();
		this.TODAS = new ArrayList<Library>();
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la librer’a principal
	 * @return
	 */
	public Library getMain() {
		return this.Main;
	}
	
	/**
	 * Establece la librer’a principal
	 * @param _main
	 */
	public void setMain(Library _main) {
		this.Main = _main;
	}
	
	/**
	 * Obtiene las librer’as del sistema
	 * @return 
	 */
	public List<Library> getLibraries() {
		return this.Libs;
	}

	/**
	 * A–ade una librer’as al programa
	 * @param _libs
	 */
	public void addLibrary(Library _lib) {
		this.Libs.add(_lib);
	}
	
	/**
	 * A–ade un conjunto de librer’as al programa
	 * @param _libs
	 */
	public void addLibraries(List<Library> _libs) {
		this.Libs.addAll(_libs);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Main: ");
		sb.append(Main.getName());
		sb.append("\n");
		sb.append("Librerias:\n");
		Iterator<Library> it = Libs.iterator();
		while (it.hasNext()) {
			Library aux = it.next();
			sb.append("\t");
			sb.append(((Library)aux).getName());
			sb.append("\n");			
			
			Method[] m = aux.getMethods();
			for (int i=0;i<m.length;i++) {
				sb.append("\t\t");
				sb.append(m[i].getLabel());
				sb.append("\n");			

			}
			
			
			
			
			
			
			
			
			
			
		}
		return sb.toString();
	}
	
	
	public List<Library> getAllLibraries() {
		List<Library> devolver = new LinkedList<Library>();
		
		devolver.add(this.getMain());
		devolver.addAll(this.getLibraries());

		
		
		return devolver;
	}
	
	
	public Library getLibrary(String name) {

		if (name.equalsIgnoreCase(Main.getName())) {
			return Main;
		} else {
			Iterator<Library> it = this.Libs.iterator();
			while (it.hasNext()) {
				Library l = it.next();
				if (l.getName().equalsIgnoreCase(name))
					return l;
			}
		}
		
		return null;
	}
	
	
}
