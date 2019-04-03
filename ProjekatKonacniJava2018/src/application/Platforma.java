package application;

import Model.*;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Platforma implements Serializable {
    
    private Object[][] platform;
    private List<Vozilo> listaVozila=new ArrayList<>();
    private boolean zauzet[][]=new boolean[10][8];
    private int brojVozilaNaPlatformi=0;
    
    public static Lock lock=new ReentrantLock(true);
    public static Condition condSudar=lock.newCondition();
    public Condition cond=lock.newCondition();
    
    public boolean postavljanje=false;
    public static boolean[] sudar;
    public Platforma()
    {
        platform = new Object[10][8];
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                zauzet[i][j]=false;
                if((i>1 && j==0)||(i>1&&j==7)||(i>1&&i<8&&j==3)||(i>1&&i<8&&j==4))
                {
                    platform[i][j]="SLOBODAN";
                }
                else
                    platform[i][j]=null;
                
            }
        brojVozilaNaPlatformi=0;
    }
    
    
    
    
    public int brojSlobodnihMjestaNaPlatformi()
    {
        int brojSlobodnihMjesta=0;
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                if((i>1 && j==0)||(i>1&&j==7)||(i>1&&i<8&&j==3)||(i>1&&i<8&&j==4))
                {
                    if("SLOBODAN".equals(platform[i][j]))
                        brojSlobodnihMjesta++;
                }
            }
        return brojSlobodnihMjesta;
    }
    public void obrisiVoziloSaZadatogMjesta(Vozilo v, int x, int y)
    {
        if(!v.isAlive())
        {
            listaVozila.remove(v);
        }
        if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
        {
            Garage.brojSlobodnihMjestaPoPlatformi[v.getBrojPlatforme()-1]++;
            platform[x][y]="SLOBODAN";
            zauzet[x][y]=false;
        }
        else
        {
            platform[x][y]=null;
        }
    }
    
    public synchronized void postaviVoziloNaSlobodnoMjesto(Vozilo vozilo)
    {
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                if((i>1 && j==0)||(i>1&&j==7)||(i>1&&i<8&&j==3)||(i>1&&i<8&&j==4))
                {
                    if("SLOBODAN".equals(platform[i][j]))
                    {
                        Garage.brojSlobodnihMjestaPoPlatformi[vozilo.getBrojPlatforme()-1]--;
                        vozilo.setPozicijaX(i);
                        vozilo.setPozicijaY(j);
                        platform[i][j]=vozilo;
                        brojVozilaNaPlatformi++;									
                        listaVozila.add(vozilo);
                        return;
                    }
                }
            }
    }
    
    public boolean postaviVoziloNaZadatoMjestoNaPlatformi(Vozilo vozilo, int x, int y)
    {
        if(vozilo.isAlive())
        {
            if(platform[x][y]==null)
            {
                vozilo.setPozicijaX(x);
                vozilo.setPozicijaY(y);
                platform[x][y]=vozilo;
                return true;
                
            }
            else if("SLOBODAN".equals(platform[x][y]))
            {
            	brojVozilaNaPlatformi++;
                Garage.brojSlobodnihMjestaPoPlatformi[vozilo.getBrojPlatforme()-1]--;
                vozilo.setPozicijaX(x);
                vozilo.setPozicijaY(y);
                platform[x][y]=vozilo;
                listaVozila.add(vozilo);
                return true;
            }
            else if(vozilo instanceof JavnoVozilo)
            {
                vozilo.setPozicijaX(x);
                vozilo.setPozicijaY(y);
                return false;
            }
            else if(platform[x][y] instanceof Vozilo)
            {
            	 /*lock.lock();
                try
                {
                    while(postavljanje)
                    {
                        try {
                            cond.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, ex);
                        }
                    }
                
                
                postavljanje=true;
                vozilo.setPozicijaX(x);
                vozilo.setPozicijaY(y);
                
                
                    postavljanje=false;
                    cond.signal();
                } finally {
                    lock.unlock();
                }*/
            	try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
	                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
	                e.printStackTrace();
				}
            	vozilo.setPozicijaX(x);
                vozilo.setPozicijaY(y);
                platform[x][y]=vozilo;
                return true;
            }
            
        }
        else if("SLOBODAN".equals(platform[x][y]))
        {
        	brojVozilaNaPlatformi++;
            Garage.brojSlobodnihMjestaPoPlatformi[vozilo.getBrojPlatforme()-1]--;
            vozilo.setPozicijaX(x);
            vozilo.setPozicijaY(y);
            platform[x][y]=vozilo;
            listaVozila.add(vozilo);
            return true;
        }
        return false;
    }
    
    public List<Vozilo>  getVozilaSaPlatforme()
    {
        return listaVozila;
    }
    public void pisiStanja()
    {
        for(int i=0; i<10; i++) {
            System.out.println("\n");
            for(int j=0; j<8; j++)
            {
                if(platform[i][j] instanceof Vozilo)
                {
                    Vozilo v=(Vozilo)platform[i][j];
                    System.out.print(" "+v.getState());
                }
                
            }
        }
        
    }
    
    @Override
    public String toString()
    {
        String s=new String();
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                if(platform[i][j] instanceof Automobil)
                {
                    Automobil v=(Automobil)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof PolicijaAuto)
                {
                    PolicijaAuto v=(PolicijaAuto)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof SanitetAuto)
                {
                    SanitetAuto v=(SanitetAuto)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof Kombi)
                {
                    Kombi v=(Kombi)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof SanitetKombi)
                {
                    SanitetKombi v=(SanitetKombi)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof VatrogasnoKombi)
                {
                    VatrogasnoKombi v=(VatrogasnoKombi)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof Motocikl)
                {
                    Motocikl v=(Motocikl)platform[i][j];
                    s+="\n"+v.toString();
                }
                else if(platform[i][j] instanceof PolicijaMoto)
                {
                    PolicijaMoto v=(PolicijaMoto)platform[i][j];
                    s+="\n"+v.toString();
                }
            }
        return s;
    }
    
    public Policija pronadjiPoliciju()
    {
        for(int i=0; i<this.getListaVozila().size(); i++)
        {
            if(this.getListaVozila().get(i) instanceof Policija && this.getListaVozila().get(i).getState()==Thread.State.NEW)
            {
                return (Policija)this.getListaVozila().get(i);
            }
        }
        return null;
    }
    
    public Vatrogasno pronadjiVatrogasno()
    {
        for(int i=0; i<this.getListaVozila().size(); i++)
        {
            if(this.getListaVozila().get(i) instanceof Vatrogasno && this.getListaVozila().get(i).getState()==Thread.State.NEW)
            {
                return (Vatrogasno)this.getListaVozila().get(i);
            }
        }
        return null;
    }
    
    public Sanitet pronadjiSanitet()
    {
        for(int i=0; i<this.getListaVozila().size(); i++)
        {
            if(this.getListaVozila().get(i) instanceof Sanitet && this.getListaVozila().get(i).getState()==Thread.State.NEW)
            {
                return (Sanitet)this.getListaVozila().get(i);
            }
        }
        return null;
    }
    
    public void intervencija(int sudarX, int sudarY, int brojPlatforme, Vozilo v1, Vozilo v2) 
    {
        sudar[brojPlatforme]=true;
        Policija policija=pronadjiPoliciju();
        Vatrogasno vatrogasno =pronadjiVatrogasno();
        Sanitet sanitet=pronadjiSanitet();
        
        if(sanitet!=null)
        {
            sanitet.upaliRotaciju();
            sanitet.setSudar(true);
            sanitet.setSudarnaPozicijaX(sudarX);
            sanitet.setSudarnaPozicijaY(sudarY);
            sanitet.start();
            try {
				sanitet.join();
			} catch (InterruptedException e) {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
				e.printStackTrace();
			}
        }
        
        if(vatrogasno!=null)
        {
            vatrogasno.upaliRotaciju();
            vatrogasno.setSudar(true);
            vatrogasno.setSudarnaPozicijaX(sudarX);
            vatrogasno.setSudarnaPozicijaY(sudarY);
            vatrogasno.start();
            try {
				vatrogasno.join();
			} catch (InterruptedException e) {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
				e.printStackTrace();
			}
        }
        
        if(policija!=null)
        {
            policija.upaliRotaciju();
            policija.setSudar(true);
            policija.setVoziloZaIntervenciju(true);
            policija.setSudarnaPozicijaX(sudarX);
            policija.setSudarnaPozicijaY(sudarY);
            policija.start();
            try {
                if(v1 != null) {
                    Files.write(Paths.get(System.getProperty("user.home") + "/uvidjajSudara.bin"), ("registarskiBroj:" + v1.getRegistarskiBroj() 
                    		+ "#" + new Date().getTime() + "#" + v1.getImage()  + System.getProperty("line.separator")).getBytes(),
                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                if(v2 != null) {
                    Files.write(Paths.get(System.getProperty("user.home") + "/uvidjajSudara.bin"), ("registarskiBroj:" + v1.getRegistarskiBroj() 
                    		+ "#" + new Date().getTime() + "#" + v1.getImage()  + System.getProperty("line.separator")).getBytes(),
                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
				policija.join();
			} 
            catch (InterruptedException e) 
            {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
				e.printStackTrace();
			} 
            catch (IOException e) 
            {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
			}
        }
        
        if(policija==null)
        {
            try
            {
            	if(v1 != null) {
                    Files.write(Paths.get(System.getProperty("user.home") + "/uvidjajSudara.bin"), ("registarskiBroj:" + v1.getRegistarskiBroj() 
                    		+ "#" + new Date().getTime() + "#" + v1.getImage()  + System.getProperty("line.separator")).getBytes(),
                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                if(v2 != null) {
                    Files.write(Paths.get(System.getProperty("user.home") + "/uvidjajSudara.bin"), ("registarskiBroj:" + v1.getRegistarskiBroj() 
                    		+ "#" + new Date().getTime() + "#" + v1.getImage()  + System.getProperty("line.separator")).getBytes(),
                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                System.out.println("Cekanje milicije!");
                Thread.sleep(3000+new Random().nextInt(7000));
            }
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, ex);
                ex.printStackTrace();
            } 
            catch (IOException e) {
                Logger.getLogger(Platforma.class.getName()).log(Level.WARNING, null, e);
				e.printStackTrace();
			}
        }
    }
    
    public Object[][] getPlatform()
    {
        return platform;
    }
    
    public void setPlatform(Object[][] platform)
    {
        this.platform=platform;
    }
    
    public boolean[][] getZauzet()
    {
        return zauzet;
    }
    
    public void setZauzet(boolean[][] zauzet)
    {
        this.zauzet = zauzet;
    }
    
    public void setListaVozila(List<Vozilo> listaVozila)
    {
        this.listaVozila=listaVozila;
    }
    
    public List<Vozilo> getListaVozila()
    {
        return listaVozila;
    }
    
    public int getBrojVozilaNaPlatformi()
    {
        return brojVozilaNaPlatformi;
    }
    
    public void setBrojVozilaNaPlatformi(int brojVozilaNaPlatformi)
    {
        this.brojVozilaNaPlatformi = brojVozilaNaPlatformi;
    }
}
