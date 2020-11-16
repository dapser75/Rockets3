package com.rockets.application;

import java.util.ArrayList;
import java.util.List;
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

	
	//Método para empezar la carrera

	public boolean StartRace(int powertarget) {
		List<Rocket> rockets = repository.GetAllRockets();
		if (CheckPowerOk(powertarget)) {
			for (int i=0;i<rockets.size(); i++) { 
				
				if (rockets.get(i).getAllInstantPower() != powertarget) StartBoosters(powertarget, rockets.get(i));
									
			do {
				
			}while(Thread.activeCount()>1);
			
			}
			return true;
		}
		else return false;
	}//End Start Race
	
	
	//Método para arrancar los diferentes propulsores
	public void StartBoosters(int powertarget, Rocket rocket) {
		for (int j=0; j<rocket.getBoostersinrocket().size();j++) {//Recorrer todo el array de propusores
			if (j==0) rocket.setStartTime(System.nanoTime());
			RunRocket runrocket = new RunRocket(rocket, j,powertarget);
			Thread t = new Thread(runrocket);
			t.start();
		}

	}
	
	
	//Método para chequear 
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


//CLASE RUNNABLE PARA EL PROCESO QUE REALIZAMOS DE FORMA PARALELA.

class RunRocket implements Runnable{
	private Rocket rocket;
	private int powertarget;
	private int boosternumber;
	
	//Constrtuctor de clase
	public RunRocket(Rocket rocket, int boosternumber, int powertarget) {
		this.rocket=rocket;
		this.powertarget=powertarget;
		this.boosternumber=boosternumber;
	}
	
	@Override
	public void run() {
		try {
			while((rocket.getAllInstantPower() != powertarget)) { //Se recorre el bucle hasta que no se alcance la potencia objetivo.
				Thread.sleep(100);
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
	


