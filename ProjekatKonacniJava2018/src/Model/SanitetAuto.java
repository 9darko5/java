package Model;

import java.io.File;

public class SanitetAuto extends Sanitet {
    
    private int brojVrata;
    
    public SanitetAuto()
    {
        
    }
    public SanitetAuto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int brojVrata) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image);
        this.brojVrata=brojVrata;
    }
    public SanitetAuto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int brojVrata, int pozicijaX, int pozicijaY, int brojPlatforme) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
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
    
    public void setBrojVrata(int brojVrata)
    {
        this.brojVrata=brojVrata;
    }
}
