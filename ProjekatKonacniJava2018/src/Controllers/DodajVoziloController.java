package Controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DodajVoziloController implements Initializable{

	@FXML
	private CheckBox rotacija;

	@FXML
	private Button izaberiFotografiju;

	@FXML
	private Button dodajVozilo;

	@FXML
	private TextField nosivost;

	@FXML
	private Label warn;

	@FXML
	private TextField brojMotora;

	@FXML
	private TextField brojSasije;

	@FXML
	private TextField naziv;

	@FXML
	private TextField brojVrata;

	@FXML
	private TextField registarskiBroj;

	@FXML
	private Label registarskiBrojLabel;

	@FXML
	private Label brojVrataLabel;

	@FXML
	private Label brojMotoraLabel;

	@FXML
	private Label rotacijaLabel;

	@FXML
	private Label nazivLabel;

	@FXML
	private Label nosivostLabel;

	@FXML
	private ImageView imageView; 

	@FXML
	private Label fajlPotjeraLabel;

	@FXML
	private Image image;

	@FXML
	private Button potjeraFile;

	File selectedFile;
	File potjera;
	public static String vozilo;

	public static int tempZaPotjeru=0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fajlPotjeraLabel.setVisible(false);
		potjeraFile.setVisible(false);
		if("Motocikl".equals(vozilo)|| 
				"PolicijaMoto".equals(vozilo))
		{
			if("PolicijaMoto".equals(vozilo))
			{
				fajlPotjeraLabel.setVisible(true);
				potjeraFile.setVisible(true);
			}
			nosivost.setVisible(false);
			brojVrata.setVisible(false);
			nosivostLabel.setVisible(false);
			brojVrataLabel.setVisible(false);
		}
		else if("Automobil".equals(vozilo) || "SanitetAuto".equals(vozilo) ||
				"PolicijaAuto".equals(vozilo))
		{
			if("PolicijaAuto".equals(vozilo))
			{
				fajlPotjeraLabel.setVisible(true);
				potjeraFile.setVisible(true);
			}
			nosivost.setVisible(false);
			nosivostLabel.setVisible(false);
		}
		else if("Kombi".equals(vozilo) || "SanitetKombi".equals(vozilo) ||
				"VatrogasnoKombi".equals(vozilo))
		{
			brojVrata.setVisible(false);
			brojVrataLabel.setVisible(false);
		}
		if("Automobil".equals(vozilo) || "Motocikl".equals(vozilo) || "Kombi".equals(vozilo))
		{
			rotacijaLabel.setVisible(false);
			rotacija.setVisible(false);
		}
	}


	public void izaberiFotografijuAction(ActionEvent e)
	{
		FileChooser fc=new FileChooser();
		fc.setInitialDirectory(new File("."+File.separator+"photos"));
		selectedFile=fc.showOpenDialog(null);

		if(selectedFile!=null)
		{
			imageView.setImage(new Image(selectedFile.toURI().toString()));
		}
		else
		{
			warn.setText("File is not valid");
		}
	}

	public void potjeraFileAction(ActionEvent e)
	{

		FileChooser fc=new FileChooser();
		fc.setInitialDirectory(new File("."+File.separator+"potjera"));
		potjera=fc.showOpenDialog(null);
	}

	public void dodajVoziloAction(ActionEvent e) 
	{
		try
		{
			tempZaPotjeru++;
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
			Date vrijemeUlaska = new Date();
			dateFormat.format(vrijemeUlaska);
			if(!AdminOptionsController.ukljucenaKorisnickaAplikacija) {
				if("Kombi".equals(vozilo))
				{

					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText()=="" || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}

					Kombi vv=new  Kombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					vv.setVrijemeUlaska(vrijemeUlaska);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}

				}
				else if("SanitetKombi".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					SanitetKombi vv=new SanitetKombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVrijemeUlaska(vrijemeUlaska);

					vv.setSanitet(true);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("VatrogasnoKombi".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					VatrogasnoKombi vv = new VatrogasnoKombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVrijemeUlaska(vrijemeUlaska);
					vv.setVatrogasno(true);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("Automobil".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					Automobil vv=new Automobil(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Integer.parseInt(brojVrata.getText()));
					vv.setVrijemeUlaska(vrijemeUlaska);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("SanitetAuto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					SanitetAuto vv=new SanitetAuto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Integer.parseInt(brojVrata.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVrijemeUlaska(vrijemeUlaska);
					vv.setSanitet(true);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("PolicijaAuto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					PolicijaAuto vv=new PolicijaAuto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, potjera, Integer.parseInt(brojVrata.getText()));
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVrijemeUlaska(vrijemeUlaska);
					vv.setPolicija(true);
					int n=AdminOptionsController.numPlatform-1;

					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("Motocikl".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					Motocikl vv=new Motocikl(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile);
					vv.setVrijemeUlaska(vrijemeUlaska);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("PolicijaMoto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					PolicijaMoto vv=new PolicijaMoto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, potjera);
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVrijemeUlaska(vrijemeUlaska);
					vv.setPolicija(true);
					int n=AdminOptionsController.numPlatform-1;
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					if(!AdminOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=AdminOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						AdminOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						AdminOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				try {
					dodajVozilo.getScene().getWindow().hide();
					Stage s=new Stage();
					Parent root;
					root = FXMLLoader.load(getClass().getResource("/View/AdminOptions.fxml"));
					Scene scene=new Scene(root);
					s.setScene(scene);
					s.setTitle("Administrator");
					s.getIcons().add(new Image("file:garaza.jpg"));
					s.show();
					s.setResizable(false);
				}
				catch (IOException e1) 
				{
					Logger.getLogger(DodajVoziloController.class.getName()).log(Level.WARNING, "", e1);
					e1.printStackTrace();
				}
			}

			else
			{
				if("Kombi".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					Kombi vv=new  Kombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("SanitetKombi".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					SanitetKombi vv=new SanitetKombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setSanitet(true);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("VatrogasnoKombi".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || nosivost.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					VatrogasnoKombi vv = new VatrogasnoKombi(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Double.parseDouble(nosivost.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setVatrogasno(true);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("Automobil".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					Automobil vv=new Automobil(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Integer.parseInt(brojVrata.getText()));
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("SanitetAuto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					SanitetAuto vv=new SanitetAuto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, Integer.parseInt(brojVrata.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setSanitet(true);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("PolicijaAuto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty() || brojVrata.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					PolicijaAuto vv=new PolicijaAuto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, potjera, Integer.parseInt(brojVrata.getText()));
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setPolicija(true);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("Motocikl".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					Motocikl vv=new Motocikl(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					System.out.println("n="+n);
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				else if("PolicijaMoto".equals(vozilo))
				{
					if(naziv.getText().trim().isEmpty() || brojSasije.getText().trim().isEmpty() || brojMotora.getText().trim().isEmpty() || registarskiBroj.getText().trim().isEmpty())
					{
						emptyFieldAlert();
						return;
					}
					PolicijaMoto vv=new PolicijaMoto(naziv.getText(), Integer.parseInt(brojSasije.getText()), Integer.parseInt(brojMotora.getText()), registarskiBroj.getText(), selectedFile, potjera);
					if(rotacija.isSelected())
						vv.upaliRotaciju();	
					vv.setPolicija(true);
					int n=new Random().nextInt(UserOptionsController.garage.getBrojPlatformi());
					boolean dobarBroj=true;
					int x, y;
					do
					{
						x=new Random().nextInt(10);
						y=new Random().nextInt(8);
						if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
							dobarBroj=false;

					}while(dobarBroj);
					vv.setVrijemeUlaska(vrijemeUlaska);
					//if(tempZaPotjeru%2==0)
					vv.setOznacenoNaPotjernici(true);
					if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
					{
						int br=UserOptionsController.garage.getPlatforme()[0].getBrojVozilaNaPlatformi();
						UserOptionsController.garage.getPlatforme()[0].setBrojVozilaNaPlatformi(++br);
						UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vv, x, y, n);
					}
				}
				try {
					dodajVozilo.getScene().getWindow().hide();
					Stage s=new Stage();
					s.setTitle("Korisnik");
					s.getIcons().add(new Image("file:garaza.jpg"));
					Parent root;
					root = FXMLLoader.load(getClass().getResource("/View/UserOptions.fxml"));
					Scene scene=new Scene(root);
					s.setScene(scene);
					s.show();
					s.setResizable(false);
				}
				catch (IOException e1) 
				{
					Logger.getLogger(DodajVoziloController.class.getName()).log(Level.WARNING, "", e1);
				}

			}
		}
		catch(NumberFormatException e2)
		{
			nepravilanUnosAlert();
		}

	}

	public void emptyFieldAlert()
	{
		Alert alertError = new Alert(Alert.AlertType.WARNING);
		alertError.setHeaderText(null);
		alertError.setContentText("Unesite sve potrebne podatke!");
		alertError.show();
	}
	public void nepravilanUnosAlert()
	{
		Alert alertError = new Alert(Alert.AlertType.WARNING);
		alertError.setHeaderText(null);
		alertError.setContentText("Nepravilan unos brojeva!");
		alertError.show();
	}
}
