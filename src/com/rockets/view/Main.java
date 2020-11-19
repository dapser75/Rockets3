/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contiene el main principal, en ella también se cargan los datos iniciales del ejercicio.
 * 
 */

package com.rockets.view;

import com.rockets.application.RocketsApplication;


public class Main {

	private static RocketsApplication controller=new  RocketsApplication();


	public static void main(String[] args) throws Exception {
		
		InitialDates();
		controller.InputPower(); //Llamada al método para iniciar la carrera.
		
	}
	
	//Método para crear los cohetes con las potencias definidas en el ejercicio
	public static void InitialDates() throws Exception {
		int[] listpowerboosters1= {10,30,80};
		controller.CreateRockets("32WESSDS",3,listpowerboosters1);
		int[] listpowerboosters2= {30,40,50,50,30,10};
		controller.CreateRockets("LDSFJA32",6,listpowerboosters2);
	}
	
	
	
	

}
