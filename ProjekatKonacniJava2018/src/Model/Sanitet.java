package Model;

import java.io.File;

public class Sanitet extends JavnoVozilo {
    
    
    public Sanitet()
    {
        
    }
    
    public Sanitet(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image);
    }
    public Sanitet(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int pozicijaX, int pozicijaY, int brojPlatforme) {
        super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
    }
    
    public Sanitet(Sanitet sanitet){
        super(sanitet.getNaziv(), sanitet.getBrojSasije(), sanitet.getBrojMotora(), sanitet.getRegistarskiBroj(), sanitet.getImage(),
                sanitet.getPozicijaX(), sanitet.getPozicijaY(), sanitet.getBrojPlatforme());
    }
}
