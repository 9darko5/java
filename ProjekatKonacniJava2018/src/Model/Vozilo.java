package Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controllers.AdminOptionsController;
import Controllers.UserOptionsController;
import application.Platforma;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.Alert;

public class Vozilo extends Thread implements Serializable {


	private String naziv;
	private int brojSasije;
	private int brojMotora;
	private String registarskiBroj;
	private File image;
	public int pozicijaX, pozicijaY;
	public int buducaPozicijaX, buducaPozicijaY;




	private Date vrijemeUlaska;
	private Date vrijemeIzlaska;


	private int brojPlatforme;

	public static Lock lock = new ReentrantLock();
	

	//pomocni flegovi
	
	boolean isOut=false;
	boolean javnoVozilo=false;
	boolean izvozenje=false;
	boolean parkiran=true;
	boolean dalje=true;
	boolean oznacenoNaPotjernici=false;
	boolean uPotjeri=false;

	public Vozilo()
	{

	}

	public Vozilo(String naziv, int brojSasije, int brojMotora, String registarskiBroj,File image, int pozicijaX, int pozicijaY, int brojPlatforme)
	{
		this.naziv=naziv;
		this.brojSasije=brojSasije;
		this.brojMotora=brojMotora;
		this.registarskiBroj=registarskiBroj;
		this.image=image;
		this.pozicijaX=pozicijaX;
		this.pozicijaY=pozicijaY;
		this.brojPlatforme=brojPlatforme;
	}
	public Vozilo(String naziv, int brojSasije, int brojMotora, String registarskiBroj,File image)
	{
		this.naziv=naziv;
		this.brojSasije=brojSasije;
		this.brojMotora=brojMotora;
		this.registarskiBroj=registarskiBroj;
		this.image=image;
	}

	public String getNaziv()
	{
		return naziv;
	}

	public void setNaziv(String naziv)
	{
		this.naziv=naziv;
	}

	public int getBrojSasije()
	{
		return brojSasije;
	}

	public void setBrojSasije(int brojSasije)
	{
		this.brojSasije=brojSasije;
	}

	public int getBrojMotora() 
	{
		return brojMotora;
	}

	public void setBrojMotora(int brojMotora)
	{
		this.brojMotora=brojMotora;
	}

	public String getRegistarskiBroj()
	{
		return registarskiBroj;
	}

	public void setRegistarskiBroj(String registarskiBroj)
	{
		this.registarskiBroj=registarskiBroj;
	}

	public void setPozicijaX(int pozicijaX)
	{
		this.pozicijaX=pozicijaX;
	}
	public int getPozicijaX()
	{
		return pozicijaX;
	}

	public void setPozicijaY(int pozicijaY)
	{
		this.pozicijaY=pozicijaY;
	}
	public int getPozicijaY()
	{
		return pozicijaY;
	}

	//------
	public void setBuducaPozicijaX(int buducaPozicijaX)
	{
		this.buducaPozicijaX=buducaPozicijaX;
	}
	public int getBuducaPozicijaX()
	{
		return buducaPozicijaX;
	}

	public void setBuducaPozicijaY(int buducaPozicijaY)
	{
		this.buducaPozicijaY=buducaPozicijaY;
	}
	public int getBuducaPozicijaY()
	{
		return buducaPozicijaY;
	}

	public void setBrojPlatforme(int brojPlatforme)
	{
		this.brojPlatforme=brojPlatforme;
	}

	public int getBrojPlatforme()
	{
		return brojPlatforme;
	}

	@Override
	public String toString()
	{
		return "Naziv: "+naziv+", broj sasije: "+brojSasije+", broj motora: "+brojMotora+", registarski broj: "+registarskiBroj+System.getProperty("line.separator"); 
	}

	public File getImage()
	{
		return image;
	}

	public void setImage(File image)
	{
		this.image=image;
	}

	public void setVrijemeUlaska(Date vrijemeUlaska)
	{
		this.vrijemeUlaska=vrijemeUlaska;
	}

	public Date getVrijemeUlaska()
	{
		return vrijemeUlaska;
	}

	public void setVrijemeIzlaska(Date vrijemeIzlaska)
	{
		this.vrijemeIzlaska=vrijemeIzlaska;
	}

	public Date getVrijemeIzlaska()
	{
		return vrijemeIzlaska;
	}

	public void setOznacenoNaPotjernici(boolean oznacenoNaPotjernici)
	{
		this.oznacenoNaPotjernici=oznacenoNaPotjernici;
	}

	public boolean getOznacenoNaPotjernici()
	{
		return oznacenoNaPotjernici;
	}

	public void setIzvozenje(boolean izvozenje)
	{
		this.izvozenje=izvozenje;
	}

	public boolean getIzvozenje()
	{
		return izvozenje;
	}

	public void setParkiran(boolean parkiran)
	{
		this.parkiran=parkiran;
	}

	public boolean getParkiran()
	{
		return parkiran;
	}

	public void setUPotjeri(boolean uPotjeri)
	{
		this.uPotjeri=uPotjeri;
	}

	public boolean getUPotjeri()
	{
		return uPotjeri;
	}
	public Lock getLock() 
	{
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}


	@Override
	public void run() 
	{



		//********************IZVOZ U POTJERI********************************
		if(uPotjeri)
		{
			try {
				//System.out.println("x="+this.getPozicijaX()+" y="+this.getPozicijaY());
				sleep(1000);
			} catch (InterruptedException e) {
				Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
				e.printStackTrace();
			}
			//System.out.println("isOutPotjera="+isOut+"\t");
			while(!isOut)
			{       
				//System.out.println("X="+this.getPozicijaX()+" Y="+this.getPozicijaY());
				//System.out.println("Brp: "+this.getBrojPlatforme());
				if(this.getPozicijaX()>1 && this.getPozicijaY()==0)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==1)
				{
					while(this.getPozicijaX()<9)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()+1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						sleepVoznja();
					}
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaX(this.getPozicijaX()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaX(this.getPozicijaX()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					sleepVoznja();

				}
				else if(this.getPozicijaY()==2)
				{
					if(this.getPozicijaX()>0)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());

						sleepVoznja();
					}
					else if(this.getPozicijaX()==0)
					{
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();

						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());
						if(this.getBrojPlatforme()>1)
						{
							this.setBrojPlatforme(this.getBrojPlatforme()-1);
							this.setPozicijaY(7);
							this.setPozicijaX(0);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());

						}
						else if(this.getBrojPlatforme()==1)
						{

							DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
							Date vrijemeIzlaska = new Date();
							dateFormat.format(vrijemeIzlaska);
							this.setVrijemeIzlaska(vrijemeIzlaska);
							naplati();
							//System.out.println("Kraj potjere!");

							isOut=true;
							this.setIzvozenje(false);
							lock.lock();
							try 
							{
								int br=UserOptionsController.garage.getBrojVozilaUGarazi();
								UserOptionsController.garage.setBrojVozilaUGarazi(--br);
							} 
							finally 
							{
								lock.unlock();
							}  
						}
						
					}
				}
				else if(this.getPozicijaY()==3 && this.getPozicijaX()>1 && this.getPozicijaX()<8)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==4 && this.getPozicijaX()>1 && this.getPozicijaX()<8)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);

					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==6)
				{
					if(this.getPozicijaX()>0)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						sleepVoznja();
					}
					else if(this.getPozicijaX()==0)
					{
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme() [this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
					}
				}
				else if(this.getPozicijaY()==7 && this.getPozicijaX()>1)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
				}
				else if(this.getPozicijaX()==0 && this.getPozicijaY()==7)
				{
					try
					{
						if(provjeriDaLiJeSudar2())
						{ 
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try{
								while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
							} 
							finally 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
							}
							Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()-1,this.getBrojPlatforme()-1, this, v1);     
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
							} finally 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();  
							}
						}

						while(this.getPozicijaY()>3)
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY() - 1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
							sleepVoznja();
						}
						if(provjeriDaLiJeSudar2())
						{ 
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try{
								while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
							} 
							finally 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
							}
							Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()-1,this.getBrojPlatforme()-1, this, v1);     
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
							} finally 
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();  
							}
						}
						while(this.getPozicijaY()>0)
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY() - 1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
							sleepVoznja();
						}
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());
						if(this.getBrojPlatforme()>1)
						{
							this.setBrojPlatforme(this.getBrojPlatforme()-1);
							this.setPozicijaY(7);
							this.setPozicijaX(0);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());

						}
						else if(this.getBrojPlatforme()==1)
						{

							DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
							Date vrijemeIzlaska = new Date();
							dateFormat.format(vrijemeIzlaska);
							this.setVrijemeIzlaska(vrijemeIzlaska);
							naplati();
							//System.out.println("Kraj potjere!");

							isOut=true;
							lock.lock();
							try 
							{
								int br=UserOptionsController.garage.getBrojVozilaUGarazi();
								UserOptionsController.garage.setBrojVozilaUGarazi(--br);
							} 
							finally 
							{
								lock.unlock();
							}  
						}
					}   
					catch(InterruptedException e)
					{
						Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
						e.printStackTrace();
					}
				}
			}

		}



		//*******************IZVOZ IZ GARAZE**********************************
		if(izvozenje)
		{

			while(!isOut)
			{
				//System.out.println("X="+this.getPozicijaX()+" Y="+this.getPozicijaY());
				//System.out.println("Brp: "+this.getBrojPlatforme());
				if(this.getPozicijaX()>1 && this.getPozicijaY()==0)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==1)
				{
					while(this.getPozicijaX()<9)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()+1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						sleepVoznja();
					}
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaX(this.getPozicijaX()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					sleepVoznja();
					sudarNegdje();
					this.setPozicijaX(this.getPozicijaX()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					sleepVoznja();

				}
				else if(this.getPozicijaY()==2)
				{
					if(this.getPozicijaX()>0)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());

						sleepVoznja();
					}
					else if(this.getPozicijaX()==0)
					{       
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());


						if(this.getBrojPlatforme()>1)
						{
							this.setBrojPlatforme(this.getBrojPlatforme()-1);
							this.setPozicijaX(0);
							this.setPozicijaY(7);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							sleepVoznja();
						}
						else
						{
							isOut=true;
							lock.lock();
							int br=UserOptionsController.garage.getBrojVozilaUGarazi();
							UserOptionsController.garage.setBrojVozilaUGarazi(--br);
							lock.unlock();
							this.setIzvozenje(false);
							DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
							Date vrijemeIzl = new Date();
							dateFormat.format(vrijemeIzl);
							this.setVrijemeIzlaska(vrijemeIzl);
							naplati();
							//System.out.println("Kraj izvoza!");
						}
					}
				}
				else if(this.getPozicijaY()==3 && this.getPozicijaX()>1 && this.getPozicijaX()<8)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==4 && this.getPozicijaX()>1 && this.getPozicijaX()<8)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);

					sleepVoznja();
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==6)
				{
					if(this.getPozicijaX()>0)
					{
						sudarNegdje();
						this.setPozicijaX(this.getPozicijaX()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						sleepVoznja();
					}
					else if(this.getPozicijaX()==0)
					{
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);

						sleepVoznja();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();

						try {
							if(provjeriDaLiJeSudar2())
							{ 
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try {
									while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
										UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
								}
								Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()-1,this.getBrojPlatforme()-1, this, v1);     
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try 
								{
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();                                                            }

							}
						} catch (InterruptedException ex) {
							Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, ex);
							ex.printStackTrace();
						}

						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();
					}
				}
				else if(this.getPozicijaY()==7 && this.getPozicijaX()>1)
				{
					sudarNegdje();
					this.setPozicijaY(this.getPozicijaY()-1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
				}
				else if(this.getPozicijaY()==7 && this.getPozicijaX()==0)
				{
					try {
						if(provjeriDaLiJeSudar2())
						{
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try {
								while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
							} finally {
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
							}
							Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()+1,this.getBrojPlatforme()-1, this, v1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
							try
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
							} finally {
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();  
							}
						}
						sudarNegdje();
						this.setPozicijaY(this.getPozicijaY()-1);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						sleepVoznja();
					} catch (InterruptedException ex) {
						Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, ex);
						ex.printStackTrace();

					}
				}


			}
		}




		//****************************** UVOZ U GARAZU ************************
		else if(!parkiran)
		{
			//System.out.println("Pocetak uvoza");
			while(parkiran==false)
			{
				sudarNegdje();
				
				if(this.getPozicijaX()==1 && this.getPozicijaY()==0) // provjera za prvi dio platforme
				{
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					sleepVoznja();
					pronadjiSlobodnoMjestoPrviDio();
					if(dalje) //ako u prvom dijelu nema slobodnih mjesta
					{
						try {
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							if(provjeriDaLiJeSudar())
							{ 
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try {
									while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
										UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
								}
								Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()+1];
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()+1,this.getBrojPlatforme()-1, this, v1);     
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try 
								{
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();                                                            }

							}

							this.setPozicijaY(this.getPozicijaY()+2);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-2);
							sleepVoznja();
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();

						} catch (InterruptedException ex) {
							Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, ex);
							ex.printStackTrace();
						}
					}
					else // ako je pronadjeno slobodno mjesto u prvom dijelu prvog dijela platforme
					{
						if(this.getBuducaPozicijaY()==0) // ako je pronadjeno slobodno mjesto u prvom dijelu prvog dijela platforme
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();

							while(this.getBuducaPozicijaX()>this.getPozicijaX())
							{
								sudarNegdje();
								this.setPozicijaX(this.getPozicijaX()+1);
								//System.out.println("obj "+UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].platform[this.getPozicijaX()][this.getPozicijaY()]);
								UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
								sleepVoznja();
							}
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()-1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
							parkiran=true;
							//System.out.println("budiciX="+this.getBuducaPozicijaX()+", buduciY="+this.getBuducaPozicijaY());
							sleepVoznja();
						}
						else if(this.getBuducaPozicijaY()==3) // ako je pronadjeno slobodno mjesto u drugom dijelu prvog dijela platforme
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							while(this.getBuducaPozicijaX()>this.getPozicijaX())
							{
								sudarNegdje();
								this.setPozicijaX(this.getPozicijaX()+1);
								UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
								sleepVoznja();
							}
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							parkiran=true;
							//System.out.println("budiciX="+this.getBuducaPozicijaX()+", buduciY="+this.getBuducaPozicijaY());
							sleepVoznja();
						}
					}
				}	
				else if(this.getPozicijaX()==1 && this.getPozicijaY()==4)
				{
					pronadjiSlobodnoMjestoDrugiDio();
					if(dalje) //ako nije pronadjeno slobodno mjesto u drugom dijelu platforme
					{
						try {
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							if(provjeriDaLiJeSudar())
							{
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try {
									while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
										UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
								}
								Vozilo v1=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()+1];
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()+1, this.getBrojPlatforme()-1, this, v1);     
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
								try 
								{
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1]=false;
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.signalAll();
								} finally {
									UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();                                                            }

							}

							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+2);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-2);
							sleepVoznja();
							sudarNegdje();
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());
							if(UserOptionsController.garage.getBrojPlatformi()>=this.getBrojPlatforme()+1)
								{
									this.setBrojPlatforme(getBrojPlatforme()+1);
									this.setPozicijaX(1);
									this.setPozicijaY(0);
								}
						} catch (InterruptedException ex) {
							Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, ex);
							ex.printStackTrace();
						}

					}
					else 
					{
						if(this.getBuducaPozicijaY()==4) //ako je pronadjeno sobodno mjesto u prvom dijelu drugog dijela
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();

							while(this.getBuducaPozicijaX()>this.getPozicijaX())
							{
								sudarNegdje();
								this.setPozicijaX(this.getPozicijaX()+1);
								UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
								sleepVoznja();
							}
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()-1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
							parkiran=true;
							sleepVoznja();
						}
						else if(this.getBuducaPozicijaY()==7) // ako je pronadjeno slobodno mjesto u drugom dijelu drugog dijela platforme
						{
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							while(this.getBuducaPozicijaX()>this.getPozicijaX())
							{
								sudarNegdje();
								this.setPozicijaX(this.getPozicijaX()+1);
								UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
								UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
								sleepVoznja();
							}
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							sleepVoznja();
							sudarNegdje();
							this.setPozicijaY(this.getPozicijaY()+1);
							UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
							parkiran=true;
							//System.out.println("budiciX="+this.getBuducaPozicijaX()+", buduciY="+this.getBuducaPozicijaY());
							this.setBuducaPozicijaX(1);
							this.setBuducaPozicijaY(0);
							sleepVoznja();
						}
					}
				}
				else //ako nema slobodnog mjesta na platformi
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					UserOptionsController.garage.postaviVoziloNaZadatoMjesto(this, this.getPozicijaX(), this.getPozicijaY(), this.getBrojPlatforme()-1);
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					if(UserOptionsController.garage.getBrojPlatformi()>=getBrojPlatforme()+1)
						{
							this.setBrojPlatforme(getBrojPlatforme()+1);
							this.setPozicijaX(1);
							this.setPozicijaY(0);
						}
				}


			}
			//System.out.println("Kraj parkiranja!");
		}
	}

	public void sleepVoznja() 
	{
		try
		{
			sleep(500);
		}
		catch(InterruptedException e)
		{
			Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
			e.printStackTrace();
		}
	}

	public synchronized void pronadjiSlobodnoMjestoPrviDio()
	{
		for(int i=this.getPozicijaX(); i<10; i++)//provjera za prvi dio prvog dijela platforme
		{
			if("SLOBODAN".equals(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[i][this.getPozicijaY()]) && 
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()]==false)
			{
				UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()]=true;
				this.setBuducaPozicijaX(i);
				this.setBuducaPozicijaY(this.getPozicijaY());
				dalje=false;
				return;
			}
		}

		for(int i=this.getPozicijaX(); i<8; i++) //provjera za drugi dio prvog dijela pletforme
		{
			if("SLOBODAN".equals(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[i][this.getPozicijaY()+3]) && 
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()+3]==false)
			{
				UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()+3]=true;
				this.setBuducaPozicijaX(i);
				this.setBuducaPozicijaY(this.getPozicijaY()+3);
				dalje=false;
				return;
			}
		}
	}

	public synchronized boolean provjeriPotjernicu1()
	{
		if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()-1][this.getPozicijaY()-1] instanceof Vozilo)
		{
			Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()-1][this.getPozicijaY()-1];
			//System.out.println("----------> "+vozilo.getOznacenoNaPotjernici());
			if(vozilo.getOznacenoNaPotjernici())
			{
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	public synchronized boolean provjeriPotjernicu2()
	{
		if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()+1] instanceof Vozilo)
		{
			Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()+1];
			//System.out.println("----------> "+vozilo.getOznacenoNaPotjernici());
			if(vozilo.getOznacenoNaPotjernici())
			{
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
					e.printStackTrace();
				}
				vozilo.setIzvozenje(true);
				return true;
			}
		}
		return false;
	}

	public boolean provjeriDaLiJeSudar() throws InterruptedException
	{
		//System.out.println("Provjera SUDARA!");
		if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()+1] instanceof Vozilo)
		{
			int r=new Random().nextInt(100);
			System.out.println("SUDAR 1: "+r);
			if(r<10)
			{
				System.out.println("Sudar na platformi "+this.getBrojPlatforme());
				return true; 
			}
		}
		return false; 
	}

	public boolean provjeriDaLiJeSudar2() 
	{
		//System.out.println("Provjera SUDARA!");
		if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1] instanceof Vozilo)
		{
			int r=new Random().nextInt(100);
			System.out.println("SUDAR 2: "+r);
			if(r<30)
			{
				System.out.println("Sudar na platformi "+this.getBrojPlatforme());
				return true; 
			}
		}
		return false; 
	}

	public void sudarNegdje()
	{
		UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.lock();
		try
		{
			while(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].sudar[this.getBrojPlatforme()-1])
				UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].condSudar.await();
		}
		catch(InterruptedException e)
		{
			Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, null, e);
			e.printStackTrace();
		}
		finally
		{
			UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].lock.unlock();
		}
	}

	public synchronized void pronadjiSlobodnoMjestoDrugiDio()
	{
		for(int i=this.getPozicijaX(); i<8; i++)//provjera za prvi dio drugog dijela platforme
		{
			if("SLOBODAN".equals(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[i][this.getPozicijaY()]) && 
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()]==false)
			{
				UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()]=true;
				this.setBuducaPozicijaX(i);
				this.setBuducaPozicijaY(this.getPozicijaY());
				dalje=false;
				return;
			}
		}

		for(int i=this.getPozicijaX(); i<10; i++) //provjera za drugi dio drugog dijela pletforme
		{
			if("SLOBODAN".equals(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[i][this.getPozicijaY()+3]) && 
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()+3]==false)
			{
				UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[i][this.getPozicijaY()+3]=true;
				this.setBuducaPozicijaX(i);
				this.setBuducaPozicijaY(this.getPozicijaY()+3);
				dalje=false;
				return;
			}
		}
	}


	public void naplati()
	{
		int cijena=0;
		long vrijemeUMinutama=0;


		if(javnoVozilo)
		{
			cijena+=0;
			long temp=this.getVrijemeIzlaska().getTime()-this.getVrijemeUlaska().getTime();
			vrijemeUMinutama=temp / (60 * 500) % 60;
		}
		else 
		{
			int dan=0;
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
			Date vrijemeIzl = new Date();
			dateFormat.format(vrijemeIzl);
			this.setVrijemeIzlaska(vrijemeIzl);
			//System.out.println("izlazak: "+this.getVrijemeIzlaska().getTime());
			long temp=this.getVrijemeIzlaska().getTime()-this.getVrijemeUlaska().getTime();

			vrijemeUMinutama=temp / (60 * 500) % 60;
			if(vrijemeUMinutama<=60)
				cijena+=1;
			else if(vrijemeUMinutama<=3*60)
				cijena+=2;
			else if(vrijemeUMinutama<=24*60)
				cijena+=8;
			else
			{
				dan=(int)vrijemeUMinutama/(24*60);
				long vrijemeUMinutamaPlusDani=(vrijemeUMinutama/dan)%60;
				cijena+=dan*8;
				if(vrijemeUMinutamaPlusDani<=60)
					cijena+=1;
				else if(vrijemeUMinutamaPlusDani<=3*60)
					cijena+=2;
				else if(vrijemeUMinutamaPlusDani<=24*60)
					cijena+=8;
			}

		}
		//System.out.println("reg broj: "+registarskiBroj+", vrijeme u min: "+vrijemeUMinutama+", cijena: "+cijena);
		UserOptionsController.upisiUEvidenciju(registarskiBroj, vrijemeUMinutama, cijena);   		
	}

	public void fullGarageAlert()
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText("Garaza je popunjena!");
		alert.show();
	}
}




