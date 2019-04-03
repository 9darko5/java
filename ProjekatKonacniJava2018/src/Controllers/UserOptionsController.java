package Controllers;

import static Controllers.AdminOptionsController.DIR_SERIALIZATION;
import application.*;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserOptionsController implements Initializable {
    
    
    
    @FXML
    private Button pokreniSimulaciju;
    
    @FXML
    private Button dodajUGarazu;
    
    @FXML
    private Button zavrsiSimulacijuButton;
    
    @FXML
    private TextField textField;
    
    @FXML
    private ComboBox platform;
    
    @FXML
    private TextArea[] textAreas;
    
    @FXML
    private VBox vb;
    
    @FXML
    private Label labela;
    
    public static Garage garage;
    public static int prviPut=0;
    public int brojUnosa=0;
    public static int minBroj;
    
    public static int tempp=0;
    public static Lock lock=new ReentrantLock();
    public static Condition condEvidencija=lock.newCondition();
    public static boolean zavrsenIzlazak=false;
    public boolean[] temp;
    public volatile static List<NaplatnaEvidencija> evidencijaNaplate=new ArrayList<>();
    
    
    public static String[] listaVozilaRandom=new String[] {
        new String("Kombi"),
        new String("Automobil"),
        new String("Motocikl"),
        new String("PolicijaMoto"),
        new String("PolicijaAuto"),
        new String("SanitetAuto"),
        new String("SanitetKombi"),
        new String("VatrogasnoKombi")
    };
    
    @FXML
    public void pokreniSimulacijuAction(ActionEvent event) {
        
        if(prviPut==1) {
            minBroj=Integer.parseInt(textField.getText().toString());
            prviPut++;
        }
        if(minBroj>garage.getBrojVozilaUGarazi())
        {
            //System.out.println("Broj vozila u garazi: "+garage.brojVozilaUGarazi());
            if(brojUnosa<minBroj-garage.getBrojVozilaUGarazi())
            {
                //System.out.println("Broj unosa: "+brojUnosa);
                DodajVoziloController.vozilo=listaVozilaRandom[new Random().nextInt(8)];
                brojUnosa++;
                try {
                    pokreniSimulaciju.getScene().getWindow().hide();
                    Stage s=new Stage();
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("/View/DodajVozilo.fxml"));
                    Scene scene=new Scene(root);
                    s.setScene(scene);
                    s.show();
                    s.setResizable(false);
                }
                catch (IOException e1)
                {
                    Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, e1);
                    e1.printStackTrace();
                }
            }
            else
            {
                brojUnosa=0;
            }
        }
        else
        {
            if(garage.getBrojPlatformi()<minBroj)
                prviPut=1;
            dodajUGarazu.setDisable(false);
            List<Vozilo> listaZaIzvoz=new ArrayList<>();
            int brojVozilaZaIzvoz;
            for(int i=0; i<garage.getBrojPlatformi(); i++)
            {
                brojVozilaZaIzvoz=(garage.getPlatforme()[i].getListaVozila().size()*40)/100;
                for(int j=0; j<brojVozilaZaIzvoz; j++)
                {
                    int k=new Random().nextInt(garage.getPlatforme()[i].getListaVozila().size());
                    listaZaIzvoz.add(garage.getPlatforme()[i].getListaVozila().get(k));
                }
            }
            
            for(int i=0; i<listaZaIzvoz.size(); i++)
            {
                Vozilo vozilo=listaZaIzvoz.get(i);
                listaZaIzvoz.remove(vozilo);
                vozilo.setIzvozenje(true);
                
                try {
                    if(vozilo.getState()==Thread.State.NEW)
                        vozilo.start();
                }
                catch(Exception e)
                {
                    Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, e);
                    System.out.println("");
                }
            }
        }
    }
    
    @FXML
    public void dodajUGarazuAction(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Date vrijemeUlaska = new Date();
        dateFormat.format(vrijemeUlaska);
        tempp++;
        Vozilo vozilo;
        int temp=new Random().nextInt(100);
        if(temp<30)
        {
            if(temp<8)
                vozilo=new PolicijaAuto();
            else if(temp<15)
                vozilo=new PolicijaMoto();
            else if(temp==20)
                vozilo=new VatrogasnoKombi();
            else if(temp==25)
                vozilo=new SanitetKombi();
            else
                vozilo=new SanitetAuto();
        }
        else if(temp<70)
            vozilo=new Automobil();
        else if(temp<90)
            vozilo=new Kombi();
        else
            vozilo=new Motocikl();
        vozilo.setPozicijaX(1);
        vozilo.setPozicijaY(0);
        vozilo.setBrojPlatforme(1);
        vozilo.setParkiran(false);
        vozilo.setVrijemeUlaska(vrijemeUlaska);
     
        if(garage.getBrojVozilaUGarazi()!=garage.getBrojPlatformi()*28)
        {
            Vozilo.lock.lock();
            try
            {
                int br=garage.getBrojVozilaUGarazi();
                garage.setBrojVozilaUGarazi(++br);
            }
            finally
            {
                Vozilo.lock.unlock();
            }
            vozilo.start();
        }
        else
            fullGarageAlert();
    }
    
    @FXML
    public void zavrsiSimulacijuAction(ActionEvent e)
    {
        
        dodajUGarazu.setVisible(false);
        pokreniSimulaciju.setVisible(false);
        zavrsiSimulacijuButton.setVisible(false);
        
        new Thread(()-> {
            boolean dobarBroj=false;
            boolean temp=false;
            while(!dobarBroj)
            {
                for(int i=0; i<garage.getBrojPlatformi(); i++)
                {
                    temp=provjeriStanjeNiti(i);
                    if(!temp)
                    {
                        break;
                    }
                }
                if(temp)
                {
                    dobarBroj=true;
                }
            }
            try {
                
                String st=new String();
                System.out.println("evidencija: "+evidencijaNaplate.size());
                st+="registarski broj,vrijeme (u minutama),cijena"+System.getProperty("line.separator");
                lock.lock();
                for(NaplatnaEvidencija ne : evidencijaNaplate)
                {
                    st+=ne.regBroj+","+ne.vrijemeUMinutama+","+ne.cijena+System.getProperty("line.separator");
                }
                lock.unlock();
                Files.write(Paths.get("naplata.csv"), st.getBytes(), StandardOpenOption.CREATE);
            } catch (IOException ex) {
                Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, ex);
                ex.printStackTrace();
            }
            FileOutputStream fos;
            try 
            {
                fos = new FileOutputStream(DIR_SERIALIZATION);
                
                ObjectOutputStream obj=new ObjectOutputStream(fos);
                obj.writeObject(garage);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, ex);
                ex.printStackTrace();
            }
            Platform.runLater(() -> {
                krajSimulacijeAlert();
            });
        }).start();
        
    }
    
    @FXML
    public void izaberiPlatformuAction(ActionEvent event) {
        
        int izabranaPlatforma=Integer.valueOf(platform.getValue().toString())-1;
        vb.getChildren().removeAll(textAreas);
        textAreas[izabranaPlatforma].setText(garage.pisiPlatformuUString(izabranaPlatforma));
        vb.getChildren().addAll(textAreas[izabranaPlatforma]);
        
        new Thread(()-> {
            while(true)
            {
                try {
                    Thread.sleep(100);
                }
                catch(Exception e)
                {
                    Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, e);
                    e.printStackTrace();
                }
                try
                {
                    Platform.runLater(() -> {
                        textAreas[izabranaPlatforma].setText(garage.pisiPlatformuUString(izabranaPlatforma));
                    });
                    
                }
                catch(Exception e)
                {
                    Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, e);
                    e.printStackTrace();
                }
            }
        }
        ).start();
        
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(prviPut==0)
        {
            dodajUGarazu.setDisable(true);
            prviPut++;
            try {
                ObjectInputStream obj = new  ObjectInputStream(new BufferedInputStream(new FileInputStream(AdminOptionsController.DIR_SERIALIZATION)));
                garage=new Garage((Garage)obj.readObject());
                System.out.println("Broj vozzzzz: "+garage.getBrojVozilaUGarazi());
            } catch (ClassNotFoundException | IOException e) {
                Logger.getLogger(UserOptionsController.class.getName()).log(Level.WARNING, null, e);
                e.printStackTrace();
            }
        }
        else
        {
            textField.setText(String.valueOf(minBroj));
        }
        for(int i=0; i<garage.getBrojPlatformi(); i++)
            platform.getItems().add(i+1);
        textAreas=new TextArea[garage.getBrojPlatformi()];
        for(int i=0; i<garage.getBrojPlatformi(); i++) {
            textAreas[i]=new TextArea();
            textAreas[i].setPrefHeight(200);
            textAreas[i].setPrefWidth(224);
        }
        vb.getChildren().addAll(textAreas[0]);
        int x;
        int y;
        int n;
        for(int i=17; i>0; i--)
        {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            Date vrijemeUlaska = new Date();
            dateFormat.format(vrijemeUlaska);
            boolean dobarBroj=true;
            int temp=new Random().nextInt(100);
            if(temp<40)
            {
                Policija voziloo;
                if(temp<20)
                {
                    voziloo=new PolicijaAuto();
                    voziloo.setPolicija(true);
                    voziloo.setSpisakRegistarskihBrojeva(new File("."+File.separator+"potjera"+File.separator+"potjera.txt"));
                }
                else
                {
                    voziloo=new PolicijaMoto();
                    voziloo.setPolicija(true);
                    voziloo.setSpisakRegistarskihBrojeva(new File("."+File.separator+"potjera"+File.separator+"potjera.txt"));
                }
                do
                {
                    n=new Random().nextInt(2);
                    x=new Random().nextInt(10);
                    y=new Random().nextInt(8);
                    if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
                        dobarBroj=false;
                    
                }while(dobarBroj);
                voziloo.setVrijemeUlaska(vrijemeUlaska);
                if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,n))
                {
                    Vozilo.lock.lock();
                    try
                    {
                        int br=garage.getBrojVozilaUGarazi();
                        garage.setBrojVozilaUGarazi(++br);
                    }
                    finally
                    {
                        Vozilo.lock.unlock();
                    }
                    UserOptionsController.garage.postaviVoziloNaZadatoMjesto(voziloo, x, y, 0);
                }
            }
            else {
                Vozilo vozilo;
                if(temp<45) 
                {
                    vozilo=new VatrogasnoKombi();
                   ((VatrogasnoKombi)vozilo).setVatrogasno(true);
                }
                else if(temp<50)
                {
                    vozilo=new SanitetKombi();
                    ((SanitetKombi)vozilo).setSanitet(true);
                }
                else if(temp<55)
                {
                    vozilo=new SanitetAuto();
                    ((SanitetAuto)vozilo).setSanitet(true);
                }
                else if(temp<70)
                    vozilo=new Automobil();
                else if(temp<90)
                    vozilo=new Kombi();
                else
                    vozilo=new Motocikl();
                do
                {
                    n=new Random().nextInt(2);
                    x=new Random().nextInt(10);
                    y=new Random().nextInt(8);
                    if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
                        dobarBroj=false;
                    
                }while(dobarBroj);
                if(temp>15)
                {
                    vozilo.setVrijemeUlaska(vrijemeUlaska);
                    vozilo.setRegistarskiBroj("123");
                    
                    if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,0))
                    {
                        Vozilo.lock.lock();
                        try
                        {
                            int br=garage.getBrojVozilaUGarazi();
                            garage.setBrojVozilaUGarazi(++br);
                        }
                        finally
                        {
                            Vozilo.lock.unlock();
                        }
                        UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vozilo, x, y, 0);
                    }
                }
            }
        }
        for(int i=17; i>0; i--)
        {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            Date vrijemeUlaska = new Date();
            dateFormat.format(vrijemeUlaska);
            boolean dobarBroj=true;
            int temp=new Random().nextInt(100);
            if(temp<40)
            {
                Policija voziloo;
                if(temp<20)
                {
                    voziloo=new PolicijaAuto();
                    voziloo.setPolicija(true);
                    voziloo.setSpisakRegistarskihBrojeva(new File("."+File.separator+"potjera"+File.separator+"potjera.txt"));
                }
                else
                {
                    voziloo=new PolicijaMoto();
                    voziloo.setPolicija(true);
                    voziloo.setSpisakRegistarskihBrojeva(new File("."+File.separator+"potjera"+File.separator+"potjera.txt"));
                }
                do
                {
                    n=new Random().nextInt(2);
                    x=new Random().nextInt(10);
                    y=new Random().nextInt(8);
                    if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
                        dobarBroj=false;
                    
                }while(dobarBroj);
                voziloo.setVrijemeUlaska(vrijemeUlaska);
                if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,1))
                {
                    Vozilo.lock.lock();
                    try
                    {
                        int br=garage.getBrojVozilaUGarazi();
                        garage.setBrojVozilaUGarazi(++br);
                    }
                    finally
                    {
                        Vozilo.lock.unlock();
                    }
                    UserOptionsController.garage.postaviVoziloNaZadatoMjesto(voziloo, x, y, 1);
                }
            }
            else {
                Vozilo vozilo;
                if(temp<45) 
                {
                    vozilo=new VatrogasnoKombi();
                   ((VatrogasnoKombi)vozilo).setVatrogasno(true);
                }
                else if(temp<50)
                {
                    vozilo=new SanitetKombi();
                    ((SanitetKombi)vozilo).setSanitet(true);
                }
                else if(temp<55)
                {
                    vozilo=new SanitetAuto();
                    ((SanitetAuto)vozilo).setSanitet(true);
                }
                else if(temp<70)
                    vozilo=new Automobil();
                else if(temp<90)
                    vozilo=new Kombi();
                else
                    vozilo=new Motocikl();
                do
                {
                    n=new Random().nextInt(2);
                    x=new Random().nextInt(10);
                    y=new Random().nextInt(8);
                    if((x>1 && y==0)||(x>1&&y==7)||(x>1&&x<8&&y==3)||(x>1&&x<8&&y==4))
                        dobarBroj=false;
                    
                }while(dobarBroj);
                if(temp>15)
                {
                    vozilo.setVrijemeUlaska(vrijemeUlaska);
                    vozilo.setRegistarskiBroj("123");
                    if(!UserOptionsController.garage.postojiVoziloNaMjestu(x,y,1))
                    {
                        Vozilo.lock.lock();
                        try
                        {
                            int br=garage.getBrojVozilaUGarazi();
                            garage.setBrojVozilaUGarazi(++br);
                        }
                        finally
                        {
                            Vozilo.lock.unlock();
                        }
                        UserOptionsController.garage.postaviVoziloNaZadatoMjesto(vozilo, x, y, 1);
                    }
                }
            }
        }
    }
    
    public boolean provjeriStanjeNiti(int brojPlatforme)
    {
        for(int i=0; i<10; i++)
            for(int j=0; j<8; j++)
            {
                if(UserOptionsController.garage.getPlatforme()[brojPlatforme].getPlatform()[i][j] instanceof Vozilo)
                {
                    Vozilo v=(Vozilo)UserOptionsController.garage.getPlatforme()[brojPlatforme].getPlatform()[i][j];
                    if(v.getState()==Thread.State.BLOCKED || v.getState()==Thread.State.RUNNABLE || v.getState()==Thread.State.TIMED_WAITING
                            || v.getState()==Thread.State.WAITING)
                        return false;
                }
            }
        return true;
    }
    public static  void upisiUEvidenciju(String registarskiBroj, long vrijemeUMinutama, double cijena)
    {
        evidencijaNaplate.add(new NaplatnaEvidencija(registarskiBroj, vrijemeUMinutama, cijena));
    }
    
    public void fullGarageAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Garaza je popunjena!");
        alert.show();
    }
    
    public void krajSimulacijeAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Simulacija zavrsena!");
        alert.show();
    }
}

class NaplatnaEvidencija
{
    String regBroj;
    long vrijemeUMinutama;
    double cijena;
    
    public NaplatnaEvidencija(String regBroj, long vrijemeUMinutama, double cijena) {
        this.regBroj = regBroj;
        this.vrijemeUMinutama=vrijemeUMinutama;
        this.cijena = cijena;
    }
}