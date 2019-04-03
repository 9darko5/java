package Model;

import java.io.File;

public class VatrogasnoKombi extends Vatrogasno {
	
	private double nosivost;
	
	public VatrogasnoKombi()
	{
		
	}
	
	public VatrogasnoKombi(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, double nosivost) 
	{
		super(naziv, brojSasije, brojMotora, registarskiBroj, image);
		this.nosivost=nosivost;
	}
	public VatrogasnoKombi(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, double nosivost, int pozicijaX, int pozicijaY, int brojPlatforme) 
	{
		super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
		this.nosivost=nosivost;
	}
	
	public double getNosivost()
	{
		return nosivost;
	}
	
	public void setNosivost(double nosivost)
	{
		this.nosivost=nosivost;
	}
	
	@Override
	public String toString()
	{
		return super.toString()+System.getProperty("line.separator")+"Nosivost: "+this.nosivost;
	}
}
