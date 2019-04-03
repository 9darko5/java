package Model;

import java.io.File;


public class Motocikl extends Vozilo {
    
    
    public Motocikl(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image);
    }
    public Motocikl(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int pozicijaX, int pozicijaY, int brojPlatforme) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
    }
    
    public Motocikl()
    {
        
    }
}
