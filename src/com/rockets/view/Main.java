/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contiene el main principal, en ella tambi�n se cargan los datos iniciales del ejercicio.
 * 
 */

package com.rockets.view;

import java.util.Scanner;

import com.rockets.application.RocketsApplication;


public class Main {


	private static RocketsApplication controller=new  RocketsApplication();
	
	static Scanner registro = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		
		InitialDates();
		StartRaceMain();
		
	}
	
	//M�todo para crear los cohetes con las potencias definidas en el ejercicio
	public static void InitialDates() throws Exception {
		int[] listpowerboosters1= {10,30,80};
		controller.CreateRockets("32WESSDS",3,listpowerboosters1);
		int[] listpowerboosters2= {30,40,50,50,30,10};
		controller.CreateRockets("LDSFJA32",6,listpowerboosters2);
		
	}
	
	//M�todo para introducir la potencia objetivo de la carrera.
	public static void StartRaceMain() {
		
		int powertarget=0;
		do {
			System.out.println("\n\n-----------------------------------------------------------");
			System.out.println("Introduce la potencia para la carrera(introduce un numero negativo para salir): ");
			try {
				powertarget=registro.nextInt();
				if (powertarget < 0) System.out.println("\n----------------------------  FIN DE LA EJECUCI�N       ----------------------------"); //Control para no introducir una potencia negativa
				else {
					if (controller.StartRace(powertarget)) controller.ShowFinalTimes();
				}

			}catch (Exception e) {
				System.out.println("\nEl valor introducido no es v�lido.");
				powertarget=1;
			}
		}while (powertarget >= 0 );
		
	}
	
	

}
