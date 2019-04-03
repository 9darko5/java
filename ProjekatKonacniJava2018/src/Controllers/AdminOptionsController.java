package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Garage;
import Model.Automobil;
import Model.Kombi;
import Model.Motocikl;
import Model.Policija;
import Model.PolicijaAuto;
import Model.PolicijaMoto;
import Model.SanitetAuto;
import Model.SanitetKombi;
import Model.VatrogasnoKombi;
import Model.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class AdminOptionsController implements Initializable{

	
	public static Handler handler;

	{
		try 
		{
			handler = new FileHandler("log"+File.separator+"error.log");
			Logger.getLogger(AdminOptionsController.class.getName()).addHandler(handler);
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private ComboBox<String> comboBox;
	
	@FXML 
	private ComboBox<Integer> comboBoxNum;
	
	@FXML 
	private ComboBox<Integer> comboBoxNum1;
	
	@FXML 
	private Button dodaj;
	
	@FXML 
	private Button korisnickaAplikacija;
	
	@FXML
	private Button obrisi;
	
	@FXML
	private TableView<Vozilo> tabelaVozila;
	
	@FXML
	private TableColumn<Object, String> naziv;
	
	@FXML
	private TableColumn<Object, Integer> brojSasije;
	
	@FXML
	private TableColumn<Object, Integer> brojMotora;
	
	@FXML
	private TableColumn<Object, String> registarskiBroj;
	
	@FXML
	private TableColumn<Object, Date> vrijemeUlaska;
	
	
	public static boolean prviPut=true;
	public static boolean ukljucenaKorisnickaAplikacija=false;
	public static int numPlatform;
	public static Garage garage;
	int brojPlatformiTxt;
	public static String DIR_SERIALIZATION=new String("garaza.ser");
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		Date date=new Date();
		try 
		{
			//citanje config.properties fajla
			Properties prop=new Properties();
			FileInputStream ip= new FileInputStream("config.properties");
			prop.load(ip);
			brojPlatformiTxt = Integer.parseInt(prop.getProperty("brojPlatformi"));
			
			
			if(prviPut) 
			{
				garage=new Garage(brojPlatformiTxt);
				prviPut=false;
			}
			
			comboBox.getItems().addAll("Automobil", "Kombi", "Motocikl", "PolicijaMoto", 
					"PolicijaAuto", "SanitetAuto", "SanitetKombi", "VatrogasnoKombi");
			naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
			brojSasije.setCellValueFactory(new PropertyValueFactory<>("brojSasije"));
			brojMotora.setCellValueFactory(new PropertyValueFactory<>("brojMotora"));
			registarskiBroj.setCellValueFactory(new PropertyValueFactory<>("registarskiBroj"));
			vrijemeUlaska.setCellValueFactory(new PropertyValueFactory<>("vrijemeUlaska"));
			for(int i=0; i<brojPlatformiTxt; i++)
			{
				comboBoxNum.getItems().add(i+1);
				comboBoxNum1.getItems().add(i+1);
			}
			
			tabelaVozila.setEditable(true);
			
			naziv.setCellFactory(TextFieldTableCell.forTableColumn());
			brojSasije.setCellFactory(TextFieldTableCell.<Object, Integer>forTableColumn(new IntegerStringConverter()));
			brojMotora.setCellFactory(TextFieldTableCell.<Object, Integer>forTableColumn(new IntegerStringConverter()));
			registarskiBroj.setCellFactory(TextFieldTableCell.forTableColumn());
			
			vrijemeUlaska.setCellFactory(column -> {
		        TableCell<Object, Date> cell = new TableCell<Object, Date>() {
		            private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

		            @Override
		            protected void updateItem(Date item, boolean empty) {
		                super.updateItem(item, empty);
		                if(empty) {
		                    setText(null);
		                }
		                else {
		                    this.setText(format.format(item));

		                }
		            }
		        };

		        return cell;
		    });
		}
		
		catch (NumberFormatException | IOException e) {
			Logger.getLogger(AdminOptionsController.class.getName()).log(Level.WARNING, null, e);
			e.printStackTrace();
		}
	}
	
	@FXML
	public void dodajAction(ActionEvent e)
	{
		DodajVoziloController.vozilo=comboBox.getValue().toString();
		numPlatform=Integer.parseInt(comboBoxNum.getValue().toString());		
		
		try {
		dodaj.getScene().getWindow().hide();
		Stage s=new Stage();
                s.setTitle("Prijava");
                s.getIcons().add(new Image("file:garaza.jpg"));
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/View/DodajVozilo.fxml"));
		Scene scene=new Scene(root);
		s.setScene(scene);
		s.show();
		s.setResizable(false);
		}
		catch (IOException e1) 
		{
			Logger.getLogger(AdminOptionsController.class.getName()).log(Level.WARNING, null, e1);
			e1.printStackTrace();
		}
	}
	
	public void prikaziAction(ActionEvent e)
	{
		int n=Integer.parseInt(comboBoxNum1.getValue().toString())-1;
		ObservableList<Vozilo> obLista=FXCollections.observableArrayList();
		List<Vozilo> lista = garage.getPlatforme()[n].getListaVozila();
		for(Object v:lista)
		{
			obLista.add((Vozilo)v);
		}
		tabelaVozila.setItems(obLista);
		tabelaVozila.refresh();
	}
	
	public void korisnickaAplikacijaAction()
	{
		try {
			FileOutputStream fos=new FileOutputStream(DIR_SERIALIZATION);
			ObjectOutputStream obj=new ObjectOutputStream(fos);
			obj.writeObject(garage);
			ukljucenaKorisnickaAplikacija=true;
		
			korisnickaAplikacija.getScene().getWindow().hide();
			
			Stage s=new Stage();
                        s.setTitle("Korisnik");
                        s.getIcons().add(new Image("file:garaza.jpg"));
			FXMLLoader loader=new FXMLLoader();
			Parent root=loader.load(getClass().getResource("/View/UserOptions.fxml"));
			Scene scene=new Scene(root);
			s.setScene(scene);
			s.show();
			s.setResizable(false);
			}
			catch (IOException e1) 
			{
				Logger.getLogger(AdminOptionsController.class.getName()).log(Level.WARNING, null, e1);

				e1.printStackTrace();
			}
	}
	
	public void obrisiAction()
	{
		try
		{
                        Vozilo v=tabelaVozila.getSelectionModel().getSelectedItem();
			ObservableList<Vozilo> productSelected, allProducts;
			allProducts=tabelaVozila.getItems();
			productSelected=tabelaVozila.getSelectionModel().getSelectedItems();
			productSelected.forEach(allProducts::remove);
                        if(v!=null)
                        {
                            int n=Integer.parseInt(comboBoxNum1.getValue().toString())-1;
                            garage.getPlatforme()[n].obrisiVoziloSaZadatogMjesta(v, v.getPozicijaX(), v.getPozicijaY());
                            int br=garage.getPlatforme()[n].getBrojVozilaNaPlatformi();
                            garage.getPlatforme()[n].setBrojVozilaNaPlatformi(--br);
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText(null);
                            alert.setContentText("Prvo zaberi votilo!");
                            alert.show();
                        }
		}
		catch(Exception e)
		{
			Logger.getLogger(AdminOptionsController.class.getName()).log(Level.WARNING, null, e);
		}
	}
	
	public void changeNazivCellEvent(CellEditEvent edittedCell)
	{
		Vozilo voziloSelected=tabelaVozila.getSelectionModel().getSelectedItem();
		voziloSelected.setName(edittedCell.getNewValue().toString());
	}
	
	public void changeBrojMotoraCellEvent(CellEditEvent edittedCell)
	{
		Vozilo voziloSelected=tabelaVozila.getSelectionModel().getSelectedItem();
		voziloSelected.setBrojMotora(Integer.parseInt(edittedCell.getNewValue().toString()));
	}

	public void changeBrojSasijeCellEvent(CellEditEvent edittedCell)
	{
		Vozilo voziloSelected=tabelaVozila.getSelectionModel().getSelectedItem();
		voziloSelected.setBrojSasije(Integer.parseInt(edittedCell.getNewValue().toString()));
	}
	
	public void changeRegistarskiBrojCellEvent(CellEditEvent edittedCell)
	{
		Vozilo voziloSelected=tabelaVozila.getSelectionModel().getSelectedItem();
		voziloSelected.setRegistarskiBroj(edittedCell.getNewValue().toString());
	}
	
	/*
	 * public void changevrijemeUlaskaCellEvent(CellEditEvent edittedCell)
	
	{
		Vozilo voziloSelected=tabelaVozila.getSelectionModel().getSelectedItem();
		voziloSelected.setVrijemeUlaska(edittedCell.getNewValue());
	} 
	*/
}
