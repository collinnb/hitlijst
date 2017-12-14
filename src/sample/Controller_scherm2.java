package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Modelartiestenlijst;
import sample.models.Modelsinglelijst;

import java.io.IOException;

/**
 * Created by Collin on 2-7-2017.
 */
public class Controller_scherm2 extends Controller {
    @FXML
    private TableView <Modelsinglelijst.singlelijstItem> table;
    @FXML
    private TableColumn <Modelsinglelijst.singlelijstItem, String> singel;
    @FXML
    private TableColumn <Modelsinglelijst.singlelijstItem, String> artiest;
    @FXML
    private TextField text;

    @FXML
    void click(javafx.scene.input.MouseEvent event){

        if (event.getClickCount()==2){
            Modelsinglelijst.singlelijstItem singlelijstItem = table.getSelectionModel().getSelectedItem();
            if (singlelijstItem!=null){
                System.out.println(singlelijstItem.getId());

            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("popup2.fxml"));
            try{
                loader.load();
            }catch (IOException ex){

            }
            Controller_popup2 display2 = loader.getController();
            int id = singlelijstItem.getId();
            display2.getid(id);
            display2.singlesinfo();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }

    }

    @FXML
    void knop(ActionEvent event){
        System.out.println();
        String texts = text.getText();

        Modelsinglelijst modelsinglelijst =Modelsinglelijst.getOurInstance();

        table.getItems().clear();
        singel.setCellValueFactory(new PropertyValueFactory<Modelsinglelijst.singlelijstItem, String>("singlenaam"));
        artiest.setCellValueFactory(new PropertyValueFactory<Modelsinglelijst.singlelijstItem, String>("artiestnaam"));
        for (Modelsinglelijst.singlelijstItem singlelijstItem:modelsinglelijst.getArtiestenlijst(texts)){
            table.getItems().add(singlelijstItem);

        }
    }
}
