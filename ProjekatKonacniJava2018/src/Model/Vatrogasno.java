/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;

/**
 *
 * @author Darko
 */
public class Vatrogasno extends JavnoVozilo
{
        public Vatrogasno()
	{
		
	}
        
	public Vatrogasno(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image) {
		super(naziv, brojSasije, brojMotora, registarskiBroj, image);
	}
	public Vatrogasno(String naziv, int brojSasije, int brojMotora, String registarskiBroj, File image, int pozicijaX, int pozicijaY, int brojPlatforme) {
		super(naziv, brojSasije, brojMotora, registarskiBroj, image, pozicijaX, pozicijaY, brojPlatforme);
	}
        public Vatrogasno(Vatrogasno vatrogasno){
            super(vatrogasno.getNaziv(), vatrogasno.getBrojSasije(), vatrogasno.getBrojMotora(), vatrogasno.getRegistarskiBroj(), vatrogasno.getImage(), 
                    vatrogasno.getPozicijaX(), vatrogasno.getPozicijaY(), vatrogasno.getBrojPlatforme());
        }
}
