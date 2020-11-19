/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contienela clase tipo cohete con los metodos acelerar y frenar del mismo en función de la potencia 
 * solicitada.
 * 
 */

package com.rockets.project;

import java.util.ArrayList;
import java.util.List;


public class Rocket {
	private String rocketid;
	private int boosternumber;
	private Long starttime;
	private Long endtime;
	
	private List<Booster> boostersinrocket=new ArrayList<Booster>();	
	
	//Método de construcción del cohete.
	public Rocket (String rocketid, int boostersnumber) throws Exception {
		this.rocketid=rocketid;
		this.boosternumber=boostersnumber;
	}
	
	//Chequeamos que se puedan montar propulsores
	public void AddBoosters(List<Booster> boosterlist) throws Exception{
		if(boosternumber == boosterlist.size()) { //Chequeamos que no superemeros el numero máximo de cohetes a añadir
			for (int i=0;i<boosterlist.size();i++) {
				getBoostersinrocket().add(boosterlist.get(i));
			}
		}
		else {
			System.out.println("\nESTE COHETE TIENE QUE MONTAR "+ boosternumber + "PROPULSORES Y QUIERES MONTAR"+ boosterlist.size() );
		}
	}
	
		
	//Método acelerar
	public void Thrust(int i, int powertarget, Thread t) {
	//	controlthreads.lock(); //Bloqueamos el hilo para para que solo pueda acelerar un propulsor
		try {			
			if (getAllInstantPower() < powertarget) {
				
				getBoostersinrocket().get(i).ThrustBooster();
			
				ViewRocket(rocketid, i, powertarget);//Llamada al método para mostrar el estado actual de ese propulsor
		
			}
		}finally {
			endtime = System.nanoTime();
	//		controlthreads.unlock();//Desbloqueamos el hilo
		}
	}
	
	//Método frenar
	public void Brake(int i, int powertarget, Thread t) {
		//controlthreads.lock(); //Bloqueamos el hilo para para que solo pueda frenar un propulsor
		if (getAllInstantPower() == powertarget) endtime = System.nanoTime();
		try {			
			if (getAllInstantPower() > powertarget) {
			
				getBoostersinrocket().get(i).BrakeBooster();
			
				ViewRocket(rocketid, i, powertarget);
				
			}
		}finally {
			endtime = System.nanoTime();
		//	controlthreads.unlock();//Desbloqueamos el hilo
		}
	}
	
	public void ViewRocket(String rocketid, int i, int powertarget){
		System.out.println("\n Rocket:" + rocketid + " Propulsor:" + i + " POTENCIA: "+  getBooster(i)+ " POTENCIA TOTAL: " 
				+ getAllInstantPower() + "/" + getAllMaxPower() + " OBJETIVO:" +powertarget);
	}
	
	
	//*******************************    METODOS GETTERS     *******************************//
	public String getRocketid() {
		return rocketid;
	}
	
	//Método para retornar la potencia del propulsor
	public String getBooster(int idbooster) {
		String booster;
			booster=(" ("+ getBoostersinrocket().get(idbooster).getInstantPower() +"/"+   getBoostersinrocket().get(idbooster).getMaxPower()+") ");
	
		return booster;
	}
	
	public Booster getBoosterType(int i) {
		return (getBoostersinrocket().get(i));
	}
	
	//Método para retornar la potencia total total del cohete
	public int getAllMaxPower() {
		int allmaxpower=0;
		for (int i = 0 ; i < getBoostersinrocket().size();i++) allmaxpower +=getBoostersinrocket().get(i).getMaxPower();

		return allmaxpower;
	}
	
	//Método para retornar la potencia instantanea total del cohete
	public int getAllInstantPower() {
		int allinstantpower=0;
		for(Booster b: boostersinrocket) allinstantpower +=b.getInstantPower();
		//for (int i = 0 ; i < getBoostersinrocket().size();i++) allinstantpower +=getBoostersinrocket().get(i).getInstantPower();
		return allinstantpower;
	}
	
	public List<Booster> getBoostersinrocket() {
		return boostersinrocket;
	}

	
	public Long getStarttime() {
		return starttime;
	}

	public Long getEndtime() {
		return endtime;
	}

	//METODOS SETTERS
	public void setStartTime(Long starttime) {
		this.starttime=starttime;
		
	}

}
