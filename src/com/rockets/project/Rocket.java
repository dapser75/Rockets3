/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contienela clase tipo cohete con los metodos acelerar y frenar del mismo en funci�n de la potencia 
 * solicitada.
 * 
 */

package com.rockets.project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Rocket {
	private String rocketid;
	private int boosternumber;
	private Long starttime;
	private Long endtime;
	

	private List<Booster> boostersinrocket=new ArrayList<Booster>();	
	private Lock controlthreads = new ReentrantLock();
	
	
	//M�todo de contrucci�n
	public Rocket (String rocketid, int boostersnumber) throws Exception {
		this.rocketid=rocketid;
		this.boosternumber=boostersnumber;
	}
	
	//Chequeamos que se puedan montar propulsores
	public void AddBoosters(List<Booster> boosterlist) throws Exception{
		if(boosternumber == boosterlist.size()) { //Chequeamos que no superemeros el numero m�ximo de cohetes a a�adir
			for (int i=0;i<boosterlist.size();i++) {
				getBoostersinrocket().add(boosterlist.get(i));
			}
		}
		else {
			System.out.println("\nESTE COHETE TIENE QUE MONTAR "+ boosternumber + "PROPULSORES Y QUIERES MONTAR"+ boosterlist.size() );
		}
		
	}
	
	//Metodo para saber si la potencia instantantea m�xima ha llegado a ala potencia total del cohete
/*	public boolean topPowerReached() {
		if (getAllInstantPower() == getAllMaxPower()) return true;
		else return false;
	}*/
	
	//M�todo acelerar
	public void Thrust(int i, int powertarget, Thread t) {
		controlthreads.lock(); //Bloqueamos el hilo para para que solo pueda acelerar un propulsor
		try {			
			if (getAllInstantPower() < powertarget) {
				getBoostersinrocket().get(i).ThrustBooster();
				ViewRocket();
				if (getAllInstantPower() == powertarget) {
					endtime = System.nanoTime();
				}
			}

		}finally {
			endtime = System.nanoTime();
			controlthreads.unlock();//Desbloqueamos el hilo
		}
	}
	
	//M�todo frenar
	public void Brake(int i, int powertarget, Thread t) {
		controlthreads.lock(); //Bloqueamos el hilo para para que solo pueda frenar un propulsor
		if (getAllInstantPower() == powertarget) endtime = System.nanoTime();
		try {			
			if (getAllInstantPower() > powertarget) {
				getBoostersinrocket().get(i).BrakeBooster();
				ViewRocket();
	
			}
		}finally {
			endtime = System.nanoTime();
			controlthreads.unlock();//Desbloqueamos el hilo
		}
	}
	
	public void ViewRocket(){
		System.out.print("\n"+ Thread.currentThread()+"- ROCKET: " +getRocketid()+ "[");
		for(int i=0; i< getBoostersinrocket().size();i++) {
			System.out.print(getBooster(i)); 
		}
		System.out.println("] ----" + getAllInstantPower() + "/" + getAllMaxPower());
	}
	
	
	//*******************************    METODOS GETTERS     *******************************//
	public String getRocketid() {
		return rocketid;
	}
	
	//M�todo para retornar la potencia del propulsor
	public String getBooster(int idbooster) {
		String booster;
			booster=(" ("+ getBoostersinrocket().get(idbooster).getInstantPower() +"/"+   getBoostersinrocket().get(idbooster).getMaxPower()+") ");
	
		return booster;
	}
	
	public Booster getBoosterType(int i) {
		return (getBoostersinrocket().get(i));
	}
	
	//M�todo para retornar la potencia total total del cohete
	public int getAllMaxPower() {
		int allmaxpower=0;
		for (int i = 0 ; i < getBoostersinrocket().size();i++) allmaxpower +=getBoostersinrocket().get(i).getMaxPower();

		return allmaxpower;
	}
	
	//M�todo para retornar la potencia instantanea total del cohete
	public int getAllInstantPower() {
		int allinstantpower=0;
		for (int i = 0 ; i < getBoostersinrocket().size();i++) allinstantpower +=getBoostersinrocket().get(i).getInstantPower();

		return allinstantpower;
	}
	
	public int getBoosterNumber() {
		return boosternumber;
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
