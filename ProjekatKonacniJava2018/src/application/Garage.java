package application;

import Model.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Garage implements Serializable {
    
    private int brojPlatformi;
    private Platforma[] platforme;
    public static int[] brojSlobodnihMjestaPoPlatformi;
    private int brojVozilaUGarazi;
    
    public Lock lock=new ReentrantLock();
    public Condition cond=lock.newCondition();
    public Garage(int brojPlatformi)
    {
        this.platforme=new Platforma[brojPlatformi];
        Platforma.sudar=new boolean[brojPlatformi];
        for(int i=0; i<brojPlatformi; i++)
        {
            platforme[i]=new Platforma();
            platforme[i].sudar[i]=false;
        }
        this.brojPlatformi=brojPlatformi;
        brojSlobodnihMjestaPoPlatformi=new int[brojPlatformi];
        this.brojVozilaUGarazi=0;
        for(int i=0; i<brojPlatformi; i++)
        {
            brojSlobodnihMjestaPoPlatformi[i]=28;
            platforme[i].setBrojVozilaNaPlatformi(0);
        }
    }
    
    public Garage(Garage garage)
    {
        this.platforme=garage.getPlatforme();
        this.brojPlatformi=garage.getBrojPlatformi();
        this.brojSlobodnihMjestaPoPlatformi=garage.brojSlobodnihMjestaPoPlatformi;
        this.brojVozilaUGarazi=garage.brojVozilaUGarazi;
    }
    
    public boolean postaviVoziloNaSlobodnoMjestoNaPlatformi(Vozilo vozilo, int brojPlatforme)
    {
        
        if(brojSlobodnihMjestaPoPlatformi[brojPlatforme]>0)
        {
            vozilo.setBrojPlatforme(brojPlatforme+1);
            platforme[brojPlatforme].postaviVoziloNaSlobodnoMjesto(vozilo);
            //System.out.println(brojSlobodnihMjestaPoPlatformi[brojPlatforme-1]);
            brojVozilaUGarazi++;
            return true;
        }
        return false;
    }
    
    public boolean postaviVoziloNaZadatoMjesto(Vozilo vozilo, int x, int y, int brojPlatforme)
    {
        vozilo.setBrojPlatforme(brojPlatforme+1);
        if(platforme[brojPlatforme].postaviVoziloNaZadatoMjestoNaPlatformi(vozilo, x, y))
            return true;
        return false;
    }
    
    public boolean postojiVoziloNaMjestu(int x, int y, int n)
    {
        if(platforme[n].getPlatform()[x][y] instanceof Vozilo)
            return true;
        return false;
    }
    public int getBrojVozilaUGarazi()
    {
        return brojVozilaUGarazi;
    }
    
    public void setBrojVozilaUGarazi(int br)
    {
        this.brojVozilaUGarazi=br;
    }
    
    public String pisiPlatformuUString(int izabranaPlatforma) {
        String[][] matricaVozila=new String[10][8];
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                
                if((i>1 && j==0)||(i>1&&j==7)||(i>1&&i<8&&j==3)||(i>1&&i<8&&j==4))
                {
                    if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof String ||
                            platforme[izabranaPlatforma].getPlatform()[i][j]==null)
                    {
                        matricaVozila[i][j]="  *  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof PolicijaMoto ||
                            platforme[izabranaPlatforma].getPlatform()[i][j] instanceof PolicijaAuto)
                    {
                        matricaVozila[i][j]="  P  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof SanitetAuto ||
                            platforme[izabranaPlatforma].getPlatform()[i][j] instanceof SanitetKombi)
                    {
                        matricaVozila[i][j]="  H  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof VatrogasnoKombi)
                    {
                        matricaVozila[i][j]="  F  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof Vozilo)
                    {
                        matricaVozila[i][j]="  V  ";
                    }
                    else
                    {
                        matricaVozila[i][j]="     ";
                    }
                }
                else
                {
                    if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof PolicijaMoto ||
                            platforme[izabranaPlatforma].getPlatform()[i][j] instanceof PolicijaAuto)
                    {
                        matricaVozila[i][j]="  P  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof SanitetAuto ||
                            platforme[izabranaPlatforma].getPlatform()[i][j] instanceof SanitetKombi)
                    {
                        matricaVozila[i][j]="  H  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof VatrogasnoKombi)
                    {
                        matricaVozila[i][j]="  F  ";
                    }
                    else if(platforme[izabranaPlatforma].getPlatform()[i][j] instanceof Vozilo)
                    {
                        matricaVozila[i][j]="  V  ";
                    }
                    else
                    {
                        matricaVozila[i][j]="     ";
                    }
                }
            }
        
        String s=new String();
        for(int i=0; i<10; i++)
        {
            if(i!=0)
                s+=System.getProperty("line.separator");
            for(int j=0; j<8; j++)
                s+=matricaVozila[i][j];
        }
        //System.out.println(s);
        return s;
    }
    
    public List<Vozilo> getListaVozilaSaPlatforme(int n)
    {
        return platforme[n].getVozilaSaPlatforme();
    }
    
    public Platforma[] getPlatforme()
    {
        return this.platforme;
    }
    
    public void setPlatforme(Platforma[] platforme)
    {
        this.platforme=platforme;
    }
    
    public void setBrojPlatformi(int brojPlatformi)
    {
        this.brojPlatformi=brojPlatformi;
    }
    
    public int getBrojPlatformi()
    {
        return brojPlatformi;
    }
    
}
