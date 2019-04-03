package Model;

import Controllers.AdminOptionsController;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Controllers.UserOptionsController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Policija extends JavnoVozilo {
    
    private File spisakRegistarskihBrojeva;
    public List<String> listaRegistarskihTablica=new ArrayList<>();
    public boolean prati;
    
    public Policija(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image);
        this.spisakRegistarskihBrojeva=spisakRegistarskihBrojeva;
    }
    public Policija(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva, int pozicijaX, int pozicijaY, int brojPlatforme) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
        this.spisakRegistarskihBrojeva=spisakRegistarskihBrojeva;
    }
    public Policija(Policija policija)
    {
        super(policija.getNaziv(), policija.getBrojSasije(), policija.getBrojMotora(), policija.getRegistarskiBroj(), policija.getImage(),
                policija.getPozicijaX(), policija.getBuducaPozicijaY(), policija.getBrojPlatforme());
        this.spisakRegistarskihBrojeva=policija.getSpisakRegistarskihBrojeva();
    }
    
    public Policija()
    {
        
    }
    
    public void setPrati(boolean prati)
    {
        this.prati=prati;
    }
    
    public boolean getPrati()
    {
        return prati;
    }
    
    public void setSpisakRegistarskihBrojeva(File spisakRegistarskihBrojeva)
    {
        this.spisakRegistarskihBrojeva=spisakRegistarskihBrojeva;
    }
    
    public File getSpisakRegistarskihBrojeva()
    {
        return spisakRegistarskihBrojeva;
    }
    
    
    @Override
    public void run()
    {
        if(this.getSpisakRegistarskihBrojeva()==null || this.isSudar())
        {
            super.run();
        }
        else
        {
            try
            {
                BufferedReader obj=new BufferedReader(new FileReader(this.getSpisakRegistarskihBrojeva()));
                String line;
                while ((line = obj.readLine()) != null) {
                    String[] regs = line.split("#");
                    for (String string : regs) {
                        listaRegistarskihTablica.add(string);
                    }
                }
            }
            catch (IOException e) {
                Logger.getLogger(Policija.class.getName()).log(Level.WARNING, null, e);
                e.printStackTrace();
            }
            
            
            while(!isOut)
            {
                if(this.getPozicijaX()>1 && this.getPozicijaX()<10 && this.getPozicijaY()==0)
                {
                    this.setPozicijaY(this.getPozicijaY()+1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
                    sleepVoznja();
                    
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[this.getPozicijaX()][this.getPozicijaY()-1]=false;
                    
                    while(this.getPozicijaX()<9)
                    {
                        this.setPozicijaX(this.getPozicijaX()+1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
                        if(this.getPrati()==false)
                            this.setPrati(provjeriLijevo2());
                        sleepVoznja();
                    }
                    
                    while(this.getPozicijaY()<6)
                    {
                        this.setPozicijaY(this.getPozicijaY()+1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
                        sleepVoznja();
                    }
                    while(this.getPozicijaX()>1)
                    {
                        this.setPozicijaX(this.getPozicijaX()-1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                        if(this.getPrati()==false)
                            this.setPrati(provjeriDesno());
                        sleepVoznja();
                    }
                    this.setPozicijaX(this.getPozicijaX()-1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                    sleepVoznja();
                    
                    while(this.getPozicijaY()>0)
                    {
                        this.setPozicijaY(this.getPozicijaY()-1);
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
                        isOut=true;
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
                    }
                    
                }
                
                if (((this.getPozicijaX() > 1 && this.getPozicijaX() < 10) && this.getPozicijaY() == 7) || ((this.getPozicijaX() > 1 && this.getPozicijaX() < 8) && this.getPozicijaY() == 3))
                {
                    this.setPozicijaY(this.getPozicijaY() - 1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
                    sleepVoznja();
                    
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getZauzet()[this.getPozicijaX()][this.getPozicijaY()+1]=false;
                    
                    
                    while (this.getPozicijaX() > 1) {
                        this.setPozicijaX(this.getPozicijaX() - 1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                        if (this.getPrati()==false)
                            this.setPrati(provjeriDesno());
                        sleepVoznja();
                        
                    }
                    this.setPozicijaX(this.getPozicijaX() - 1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                    sleepVoznja();
                    
                    while (this.getPozicijaY() > 0) {
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
                        isOut=true;
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
                    }
                    
                }
                
                if (this.getPozicijaX() > 1 && this.getPozicijaX() < 8 && this.getPozicijaY() == 4)
                {
                    this.setPozicijaY(this.getPozicijaY() + 1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
                    sleepVoznja();
                    
                    
                    while (this.getPozicijaX() < 8) {
                        this.setPozicijaX(this.getPozicijaX() + 1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
                        if (this.getPrati()==false)
                            this.setPrati(provjeriLijevo2());
                        
                        sleepVoznja();
                        
                    }
                    while (this.getPozicijaY() > 2) {
                        this.setPozicijaY(this.getPozicijaY() - 1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()+1);
                        sleepVoznja();
                        
                        
                    }
                    while (this.getPozicijaX() > 1) {
                        this.setPozicijaX(this.getPozicijaX() - 1);
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                        UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                        if (this.getPrati()==false)
                            this.setPrati(provjeriDesno());
                        sleepVoznja();
                        
                        
                    }
                    this.setPozicijaX(this.getPozicijaX() - 1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                    sleepVoznja();
                    
                    while (this.getPozicijaY() > 0) {
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
                        isOut=true;
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
                    }
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
                            Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
                            UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()-1,this.getBrojPlatforme()-1, this, vozilo);
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
                            Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()+1][this.getPozicijaY()-1];
                            UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].intervencija(this.getPozicijaX(), this.getPozicijaY()-1,this.getBrojPlatforme()-1, this, vozilo);
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
                            isOut=true;
                            
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
                        }
                    }
                    catch(InterruptedException e)
                    {
                        Logger.getLogger(Policija.class.getName()).log(Level.WARNING, null, e);
                        e.printStackTrace();
                    }
                }
                
                
            }
        }
    }
    
    public boolean provjeriLijevo2()
    {
        if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()-1] instanceof Vozilo)
        {
            Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()-1];
            if(listaRegistarskihTablica.contains(vozilo.getRegistarskiBroj()))
            {
                if(this.getPozicijaX()<9 && this.getPozicijaY()==1)
                {
                    this.setPozicijaX(this.getPozicijaX()+1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()-1, this.getPozicijaY());
                    
                }
                else
                {
                    this.setPozicijaY(this.getPozicijaY()+1);
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                    UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX(), this.getPozicijaY()-1);
                }
                
                
                vozilo.setUPotjeri(true);
                if(vozilo.getState()==Thread.State.NEW)
                    vozilo.start();
                if(vozilo != null) {
                    try 
                    {
                    	System.out.println("Upis uvidjaja!");
						Files.write(Paths.get("uvidjajPotjera.bin"), ("registarskiBroj:" + vozilo.getRegistarskiBroj() 
								+ "#" + new Date().getTime() + "#" + vozilo.getImage()  + System.getProperty("line.separator")).getBytes(),
									StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					} 
                    catch (IOException e) 
                    {
                        Logger.getLogger(Policija.class.getName()).log(Level.WARNING, null, e);
						e.printStackTrace();
					}
                }
                sleepVoznja();
                return true;
            }
        }
        return false;
    }
    
    public boolean provjeriDesno() //vozilo u 3 ili 7 koloni a policija u 2 ili 6 koloni
    {
        if(UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()+1] instanceof Vozilo)
        {
            Vozilo vozilo=(Vozilo)UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].getPlatform()[this.getPozicijaX()][this.getPozicijaY()+1];
            if(listaRegistarskihTablica.contains(vozilo.getRegistarskiBroj()))
            {
                this.setPozicijaX(this.getPozicijaX()-1);
                UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].postaviVoziloNaZadatoMjestoNaPlatformi(this, this.getPozicijaX(), this.getPozicijaY());
                UserOptionsController.garage.getPlatforme()[this.getBrojPlatforme()-1].obrisiVoziloSaZadatogMjesta(this, this.getPozicijaX()+1, this.getPozicijaY());
                sleepVoznja();
                
                vozilo.setUPotjeri(true);
                if(vozilo.getState()==Thread.State.NEW)
                    vozilo.start();
                if(vozilo != null) {
                    try 
                    {
                    	System.out.println("Upis uvidjaja!");
						Files.write(Paths.get("uvidjajPotjera.bin"), ("registarskiBroj:" + vozilo.getRegistarskiBroj() 
								+ "#" + new Date().getTime() + "#" + vozilo.getImage()  + System.getProperty("line.separator")).getBytes(),
									StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					} 
                    catch (IOException e) 
                    {
                        Logger.getLogger(Policija.class.getName()).log(Level.WARNING, null, e);
						e.printStackTrace();
					}
                }
                sleepVoznja();
                this.upaliRotaciju();
                return true;
            }
        }
        return false;
    }
}
