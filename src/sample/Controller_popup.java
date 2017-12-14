package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Modelartiestenlijst;
import sample.models.Modelsingleartist;

/**
 * Created by Collin on 5-7-2017.
 */
public class Controller_popup {
    @FXML
    private ListView<Modelsingleartist.singleartistItem> lijst;
    @FXML
    private Button closebtn;
    @FXML
    private Label naam;
    private int artiestid;
    public void setNaam(String naam) {
        this.naam.setText(naam);
    }
    public void getid(int id){
        artiestid = id;
    }
    public void singles(){
        System.out.println();
        int texts = artiestid;

        Modelsingleartist modelsingleartist=Modelsingleartist.getOurInstance();

        lijst.getItems().clear();

        for (Modelsingleartist.singleartistItem singleartistItem:modelsingleartist.getSingleartist(texts)){
            lijst.getItems().add(singleartistItem);
        }
    }
    @FXML
    void close(ActionEvent event){
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
