package Model;

import java.io.File;

public class Automobil extends Vozilo {
    
    private int brojVrata;
    
    public Automobil()
    {
        
    }
    public Automobil(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int brojVrata) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image);
        this.brojVrata=brojVrata;
    }
    
    public Automobil(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int brojVrata, int pozicijaX, int pozicijaY, int brojPlatforme) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
        this.brojVrata=brojVrata;
    }
    
    public Automobil(Automobil a)
    {
        super(a.getNaziv(),a.getBrojSasije(), a.getBrojMotora(), a.getRegistarskiBroj(), a.getImage(), a.pozicijaX, a.pozicijaY, a.getBrojPlatforme());
        this.brojVrata=a.brojVrata;
    }
    public int getBrojVrata()
    {
        return brojVrata;
    }
    
    @Override
    public String toString()
    {
        return super.toString()+"\nBroj vrata: "+this.getBrojVrata();
    }
}
