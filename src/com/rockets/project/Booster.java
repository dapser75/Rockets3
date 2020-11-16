/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contienela clase tipo propulsor metodos acelerar y frenar del mismo en función de la potencia 
 * solicitada.
 * 
 */

package com.rockets.project;

public class Booster implements Runnable{
	private int maxpower=0; //Potencia máxima del propulsor
	private int instantpower=0; //Potencia instantanea del propulspor

	//Método de creación
	public Booster(int maxpower) {
		this.maxpower=maxpower;
		instantpower=0;
	}
		
	public void ThrustBooster() {
		
		if (instantpower < maxpower) instantpower++;
	}

	public void BrakeBooster() {
		
		if (instantpower > 0) instantpower--;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//****************************    METODOS GETTTERS    *************************
		
	public int getInstantPower() {
		return instantpower;
	}

	
	public int getMaxPower() {
		return maxpower;
	}
	
}
