package Model;

import Controllers.AdminOptionsController;
import Controllers.UserOptionsController;
import java.io.File;

import Model.interfaces.RotacijaInterface;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavnoVozilo extends Vozilo implements RotacijaInterface {
	
	
	private boolean sanitet=false;
	private boolean policija=false;
	private boolean vatrogasno=false;
	private boolean rotacija;
	private int sudarnaPozicijaX, sudarnaPozicijaY;

	private boolean sudar=false;
	private boolean voziloZaIntervenciju=false;

	public boolean isVoziloZaIntervenciju() {
		return voziloZaIntervenciju;
	}

	public void setVoziloZaIntervenciju(boolean voziloZaIntervenciju) {
		this.voziloZaIntervenciju = voziloZaIntervenciju;
	}

	public boolean isSudar() {
		return sudar;
	}

	public void setSudar(boolean sudar) {
		this.sudar = sudar;
	}

	public int getSudarnaPozicijaX()
	{
		return sudarnaPozicijaX;
	}

	public void setSudarnaPozicijaX(int sudarnaPozicijaX)
	{
		this.sudarnaPozicijaX = sudarnaPozicijaX;
	}

	public int getSudarnaPozicijaY()
	{
		return sudarnaPozicijaY;
	}

	public void setSudarnaPozicijaY(int sudarnaPozicijaY)
	{
		this.sudarnaPozicijaY = sudarnaPozicijaY;
	}
	public JavnoVozilo()
	{

	}
	
	public void setPolicija(boolean policija)
	{
		this.policija=policija;
	}
	
	public boolean getPolicija()
	{
		return policija;
	}
	
	public void setVatrogasno(boolean vatrogasno)
	{
		this.vatrogasno=vatrogasno;
	}
	
	public boolean getVatrogasno()
	{
		return vatrogasno;
	}
	
	public void setSanitet(boolean sanitet)
	{
		this.sanitet=sanitet;
	}
	
	public boolean getSanitet()
	{
		return sanitet;
	}

	public JavnoVozilo(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image) {
		super(naziv, brojSasije, brojMotora, registarskiBroj, image);
		javnoVozilo=true;
	}
	public JavnoVozilo(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int pozicijaX, int pozicijaY, int brojPlatforme) {
		super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
		javnoVozilo=true;
	}

	@Override
	public void upaliRotaciju() {
		rotacija=true;
	}

	@Override
	public void ugasiRotaciju() {
		rotacija=false;
	}
	
	@Override
	public void sleepVoznja()
	{
		try {
		if(this.getPolicija())
		{
			sleep(400);
		}
		else if(this.getVatrogasno())
		{
			sleep(300);
		}
		else 
		{
			sleep(200);
		}
		} catch (InterruptedException e) {
			Logger.getLogger(JavnoVozilo.class.getName()).log(Level.WARNING, null, e);
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		boolean prepisan=true;
		if(!isSudar())
			super.run();
		else
		{
			try {


				//************************** INTERVENCIJA 1 **************************************************
				if((this.getPozicijaY()==0 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==2)
						|| (this.getPozicijaY()==4 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==6))
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());

					sleepVoznja();
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());


					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}

					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==3 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==2
						|| (this.getPozicijaY()==7 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==6))
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()>this.getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==0 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==6)
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					while(this.getPozicijaX()<9)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()<6)
					{
						this.setPozicijaY(this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
						sleepVoznja();
					}
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==7 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==2)
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
					while(this.getPozicijaX()>0)
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()>2)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					this.setPozicijaX(this.getPozicijaX()+1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==4 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==2)
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()<8)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()>2)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaX()>this.getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;

				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==3 && this.getSudarnaPozicijaX()==1 && this.getSudarnaPozicijaY()==6)
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()<9)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()<6)
					{
						this.setPozicijaY(this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
						sleepVoznja();
					}
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					this.setPozicijaX(this.getPozicijaX()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}



				//*******************************INTERVENCIJA 2 ***********************************
				else if((this.getPozicijaY()==0 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==2)
						|| (this.getPozicijaY()==4 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==6))
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()+1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());

					sleepVoznja();
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==3 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==2
						|| (this.getPozicijaY()==7 && this.getPozicijaX()>1 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==6))
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()>this.getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==0 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==6)
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					sleepVoznja();
					while(this.getPozicijaX()<9)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()<6)
					{
						this.setPozicijaY(this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
						sleepVoznja();
					}
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==7 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==2)
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					sleepVoznja();
					while(this.getPozicijaX()>0)
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()>2)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==4 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==2)
				{
					this.setPozicijaY(this.getPozicijaY()+1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()<8)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()>2)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaX()>this.getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;

				}
				else if(this.getPozicijaX()>1 && this.getPozicijaY()==3 && this.getSudarnaPozicijaX()==0 && this.getSudarnaPozicijaY()==6)
				{
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					this.setPozicijaY(this.getPozicijaY()-1);
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
					prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
					sleepVoznja();
					while(this.getPozicijaX()<9)
					{
						this.setPozicijaX(this.getPozicijaX()+1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					while(this.getPozicijaY()<6)
					{
						this.setPozicijaY(this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
						sleepVoznja();
					}
					while(this.getPozicijaX()>getSudarnaPozicijaX())
					{
						this.setPozicijaX(this.getPozicijaX()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(this.isVoziloZaIntervenciju())
						sleep(3000+new Random().nextInt(7000));
					while(this.getPozicijaY()>0)
					{
						this.setPozicijaY(this.getPozicijaY()-1);
						if(prepisan)
							UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
						prepisan=UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
						sleepVoznja();
					}
					if(prepisan)
						UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY());

					super.lock.lock();
					try
					{
						int br=UserOptionsController.garage.getBrojVozilaUGarazi();
						UserOptionsController.garage.setBrojVozilaUGarazi(--br);
					}
					finally
					{
						super.lock.unlock();
					}
					prepisan=true;
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(JavnoVozilo.class.getName()).log(Level.WARNING, null, ex);
				ex.printStackTrace();
			}
		}
	}
}
