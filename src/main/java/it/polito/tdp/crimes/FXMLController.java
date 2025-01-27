/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Adiacenza arco = boxArco.getValue();
    	if(arco == null) {
    		txtResult.appendText("Selezionare un arco!\n");
    		return;
    	}
    	txtResult.appendText("Percorso di archi più lungo:\n");
    	for(String s: model.percorsoMigliore(arco.getC1(), arco.getC2())) {
    		txtResult.appendText(s + "\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String categoria = boxCategoria.getValue();
    	boxArco.getItems().clear();
    	if(categoria == null) {
    		txtResult.appendText("Selezionare una categoria!\n");
    		return;
    	}
    	Integer mese = boxMese.getValue();
    	if(mese == null) {
    		txtResult.appendText("Selezionare un mese!\n");
    		return;
    	}
    	txtResult.clear();
    	model.creaGrafo(categoria, mese);
    	if(model.getVertici() == null) {
    		txtResult.appendText("Nessun risultato trovato con questa combinazione di categoria e mese!\n");
    		return;
    	}
    	txtResult.appendText("Archi con peso più alto della media:\n");
    	for(Adiacenza a: model.getSopraMedia()){
    		txtResult.appendText(a.toString() + "\n");
    	}
    	boxArco.getItems().addAll(model.getSopraMedia());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().addAll(model.getCategorie());
    	for(int i=1; i<=12; i++) {
    		boxMese.getItems().add(i);
    	}
    }
}
