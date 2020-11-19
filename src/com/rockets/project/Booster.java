/*@Author: Daniel Arizu Pascual
 * 
 * Date: 10/11/20 
 * 
 * EJERCICIO CORRESPONDIENTE AL MODULO 8 DE THREADS
 * 
 * Esta clase contienela clase tipo propulsor metodos acelerar y frenar del mismo en funci�n de la potencia 
 * solicitada.
 * 
 */

package com.rockets.project;

public class Booster{
	private int maxpower=0; //Potencia m�xima del propulsor
	private int instantpower=0; //Potencia instantanea del propulspor

	//M�todo de creaci�n
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
	
	//****************************    METODOS GETTTERS    *************************
		
	public int getInstantPower() {
		return instantpower;
	}

	
	public int getMaxPower() {
		return maxpower;
	}
	
}
