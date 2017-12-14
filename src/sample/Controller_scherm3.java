package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Modelsinglelijst;
import sample.models.Modeltop40lijst;

import java.io.IOException;

/**
 * Created by Collin on 2-7-2017.
 */
public class Controller_scherm3 extends Controller{
    @FXML
    private ChoiceBox choose;

    @FXML
    private TableView<Modeltop40lijst.top40Item> table;
    @FXML
    private TableColumn<Modeltop40lijst.top40Item, String> positie;
    @FXML
    private TableColumn<Modeltop40lijst.top40Item, String> singel;
    @FXML
    private TableColumn <Modeltop40lijst.top40Item, String> artiest;
    @FXML
    private TextField jaar;
    @FXML
    private TextField week;

    private int top40ortipparade = 0;


    public void initialize() {
        choose.getItems().removeAll(choose.getItems());
        choose.getItems().addAll("top 40", "tip parade");
        choose.getSelectionModel().select("top 40");
    }

@FXML
void click(javafx.scene.input.MouseEvent event){

    if (event.getClickCount()==2){
        Modeltop40lijst.top40Item top40Item = table.getSelectionModel().getSelectedItem();
        if (top40Item!=null){
            System.out.println(top40Item.getSingleid());
            System.out.println(top40Item.getArtiestid());

        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("popup3.fxml"));
        try{
            loader.load();
        }catch (IOException ex){

        }
        Controller_popup3 display3 = loader.getController();
        int singleid = top40Item.getSingleid();
        int artiestid = top40Item.getArtiestid();
        display3.getid(singleid, artiestid);
        display3.singlesinfo();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

}

    @FXML
    void knop(ActionEvent event){
        if (choose.getValue() == "top 40"){
            top40ortipparade = 1;
        } else if (choose.getValue() == "tip parade"){
            top40ortipparade = 2;
        }
        System.out.println(top40ortipparade);
        String jaar2 = jaar.getText();
        String week2 = week.getText();
        int jaar3 = Integer.parseInt(jaar2);
        int week3 = Integer.parseInt(week2);
        Modeltop40lijst modeltop40lijst =Modeltop40lijst.getOurInstance();

        table.getItems().clear();
        positie.setCellValueFactory(new PropertyValueFactory<Modeltop40lijst.top40Item, String>("positie"));
        singel.setCellValueFactory(new PropertyValueFactory<Modeltop40lijst.top40Item, String>("singlenaam"));
        artiest.setCellValueFactory(new PropertyValueFactory<Modeltop40lijst.top40Item, String>("artiestnaam"));
        for (Modeltop40lijst.top40Item top40Item:modeltop40lijst.getArtiestenlijst(jaar3 , week3, top40ortipparade)){
            table.getItems().add(top40Item);

        }
    }
}
