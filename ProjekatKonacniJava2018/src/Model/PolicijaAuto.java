package Model;

import java.io.File;


public class PolicijaAuto extends Policija{
    
    private int brojVrata;
    
    public PolicijaAuto()
    {
        
    }
    
    public PolicijaAuto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva, int brojVrata)
    {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, spisakRegistarskihBrojeva);
        this.brojVrata=brojVrata;
    }
    
    public PolicijaAuto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva, int brojVrata, int pozicijaX, int pozicijaY, int brojPlatforme)
    {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, spisakRegistarskihBrojeva, pozicijaX, pozicijaY, brojPlatforme);
        this.brojVrata=brojVrata;
    }
    
    @Override
    public String toString()
    {
        return super.toString()+System.getProperty("line.separator")+"Broj vrata: "+this.getBrojVrata();
    }
    
    public int getBrojVrata()
    {
        return brojVrata;
    }
}
