package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Modelartiestenlijst;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Collin on 2-7-2017.
 */

public class Controller_scherm1 extends Controller {

    @FXML
    private ListView<Modelartiestenlijst.artiestenlijstItem> lijst;
    @FXML
    private TextField text;

    @FXML
    void click(javafx.scene.input.MouseEvent event){

        if (event.getClickCount()==2){
            Modelartiestenlijst.artiestenlijstItem artiestenlijstItem = lijst.getSelectionModel().getSelectedItem();
            if (artiestenlijstItem!=null){
                System.out.println(artiestenlijstItem.getid());

            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("popup.fxml"));
            try{
                loader.load();
            }catch (IOException ex){

            }
            Controller_popup display = loader.getController();
            String naam = artiestenlijstItem.getArtiestnaam();
            int id = artiestenlijstItem.getid();
            display.setNaam(naam);
            display.getid(id);
            display.singles();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
//
//                stage.initModality(Modality.APPLICATION_MODAL);
//

        }

    }

    @FXML
    void knop(ActionEvent event){
        System.out.println();
        String texts = text.getText();

        Modelartiestenlijst modelartiestenlijst=Modelartiestenlijst.getOurInstance();

        lijst.getItems().clear();

        for (Modelartiestenlijst.artiestenlijstItem artiestenlijstItem:modelartiestenlijst.getArtiestenlijst(texts)){
            lijst.getItems().add(artiestenlijstItem);
        }
    }



}
