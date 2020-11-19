package com.rockets.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rockets.persitence.RocketsRepository;
import com.rockets.project.Booster;
import com.rockets.project.Rocket;

public class RocketsApplication {
	
	private RocketsRepository repository = new RocketsRepository();
	
	//Método para la creación de los cohetes
	public void CreateRockets(String rocketid, int boosternumber, int[] listpowerboosters) throws Exception {
		try {
			Rocket rocket= new Rocket(rocketid,boosternumber);
			rocket.AddBoosters(CreateBooster(boosternumber,listpowerboosters));//añadimos los propulsores
			repository.addRocket(rocket);
		}
		catch (Exception e){
			System.out.println("\nERROR: No se ha podido añadir el cohete. Tipo de error:" + e);
			
		}
	}
	
	//Metodo para añadir los cohetes al repositorio 
	public List<Booster> CreateBooster(int boosternumber,int[] listpowerboosters){
		List<Booster> boostersinrocket = new ArrayList<Booster>();
		for(int i=0; i <boosternumber;i++) {
			boostersinrocket.add(new Booster(listpowerboosters[i]));
		}
		return boostersinrocket;
	}

	//Método para introducir la potencia objetivo de la carrera.
	public void InputPower() {
		Scanner registro = new Scanner(System.in);
		int powertarget=0;
		int controlbucle=0;
		do {
			System.out.println("\n\n-----------------------------------------------------------");
			System.out.println("Introduce la potencia para la carrera(introduce un numero negativo para salir): ");
			try {
				powertarget=registro.nextInt();
				if (powertarget < 0) System.out.println("\n----------------------------  FIN DE LA EJECUCIÓN       ----------------------------"); //Control para no introducir una potencia negativa
				else {
					if (CheckPowerOk(powertarget))				
					{
						controlbucle++;
						StartRace(powertarget);
						do {
							Thread.currentThread();
							Thread.sleep(1800);
							
						}while(Thread.activeCount()>1 || controlbucle==100);
						ShowFinalTimes();//Llamada al método para mostrar los tiempos de cada ccohete
					}
				}
			}catch (Exception e) {
				System.out.println("\nEl valor introducido no es válido.");
				registro.next();
				powertarget=1;
			}
		}while (powertarget >= 0 );
		registro.close();
		
	}//Fin metodo StartRaceMain

	
	//Método para empezar la carrera

	public void StartRace(int powertarget) {
		List<Rocket> rockets = repository.GetAllRockets();
		if (CheckPowerOk(powertarget)) { //Llamada al metodo para chequear que la carrera es posible
			for (int i=0;i<rockets.size(); i++) { 
					if (rockets.get(i).getAllInstantPower() != powertarget) StartBoosters(powertarget, rockets.get(i));
			}
		}
	}//End Start Race
	
	
	//Método para arrancar los diferentes propulsores
	public void StartBoosters(int powertarget, Rocket rocket) {
		for (int j=0; j<rocket.getBoostersinrocket().size();j++) {//Recorrer todo el array de propusores
			if (j==0) rocket.setStartTime(System.nanoTime());
			
			RunRocket runrocket = new RunRocket(rocket, j,powertarget);
			Thread t = new Thread(runrocket); //Creamos los hilos
			
			t.start();
			
		}

	}
	
	
	//Método para chequear si el cohete puede alcanzar la potencia solicitada o la potencia objetivo es la potencia de partida, en ambos casos se aborta la carrera
	
	private boolean CheckPowerOk(int powertarget) {
		List<Rocket> rockets = repository.GetAllRockets();
		boolean control=true;
		for (int i=0; i<rockets.size();i++) {
			if (rockets.get(i).getAllMaxPower() < powertarget) {
				System.out.println("\nEL COHETE " + rockets.get(i).getRocketid() +" NO PUEDE ALCANZAR LA POTENCIA MÁXIMA SOLICITADA. "
						+ "Max power:" + rockets.get(i).getAllMaxPower() + ". Target: " + powertarget);
				System.out.println("\n ----- CARRERA ABORTADA ------");
				control=false;
				break;
			}
			else if(rockets.get(i).getAllInstantPower() == powertarget) {
				System.out.println("\nLA POTENCIA INICIAL Y OBJETIVO ES LA MISMA.");
				System.out.println("\n ----- CARRERA ABORTADA ------");
				control=false;
				break;
			}
			else control=true;
		}
		
		return control;
	}

	
	//Método para mostrar el tiempo final de la carrera
	public void ShowFinalTimes() {
		List<Rocket> rockets = repository.GetAllRockets();
		for (int i=0; i<rockets.size();i++) {
			System.out.println("\nEL TIEMPO DEL COHETE "+rockets.get(i).getRocketid() + "HA SIDO DE: " + (rockets.get(i).getEndtime()-rockets.get(i).getStarttime()) + "ns.");
					
		}
		
	}
}


//CLASE RUNNABLE PARA LOS HILOS, QUE REALIZAMOS DE FORMA PARALELA.

class RunRocket implements Runnable{
	private Rocket rocket;
	private int powertarget;
	private int boosternumber;
	
	//Constructor de clase
	public RunRocket(Rocket rocket, int boosternumber, int powertarget) {
		this.rocket=rocket;
		this.powertarget=powertarget;
		this.boosternumber=boosternumber;
	}
	
	@Override
	public void run() {
		try {
			while((rocket.getAllInstantPower() != powertarget)) { //Se recorre el bucle hasta que no se alcance la potencia objetivo.
				Thread.sleep(10);
				if (rocket.getAllInstantPower() < powertarget) {
					rocket.Thrust(boosternumber, powertarget, Thread.currentThread());
				} 
				else if (rocket.getAllInstantPower() > powertarget) {
					rocket.Brake(boosternumber, powertarget,Thread.currentThread());
				}
			}//End While
		} catch (InterruptedException e) {
			e.printStackTrace();
		} //End Try-catch
				
	}//End run
}//End de clase
	


