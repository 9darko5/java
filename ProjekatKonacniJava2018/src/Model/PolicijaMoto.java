package Model;

import java.io.File;


public class PolicijaMoto extends Policija {
    
    public PolicijaMoto()
    {
        
    }
    
    public PolicijaMoto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva)
    {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, spisakRegistarskihBrojeva);
    }
    public PolicijaMoto(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, File spisakRegistarskihBrojeva, int pozicijaX, int pozicijaY, int brojPlatforme)
    {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, spisakRegistarskihBrojeva, pozicijaX, pozicijaY, brojPlatforme);
    }
    
}
