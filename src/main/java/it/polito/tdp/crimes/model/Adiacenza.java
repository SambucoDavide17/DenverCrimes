package it.polito.tdp.crimes.model;

public class Adiacenza {
	
	private String c1;
	private String c2;
	private double peso;
	
	public Adiacenza(String c1, String c2, double peso) {
		this.c1 = c1;
		this.c2 = c2;
		this.peso = peso;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return c1 + " - " + c2 + " : " + peso;
	}
	
	
}
