package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.models.Modelsingleartist;
import sample.models.Modelsingleinfo;

/**
 * Created by Collin on 5-7-2017.
 */
public class Controller_popup3 {
    @FXML
    private ListView lijst;
    @FXML
    private Button closebtn;
    @FXML
    private Label titel;
    @FXML
    private Label artiest;
    @FXML
    private Label weeken;
    @FXML
    private Label hoogste;

    private int singleid;
    private int artiestid;
    public void setNaam(String titel,String artiest,String weeken,String hoogste) {

        this.titel.setText(titel);
        this.artiest.setText(artiest);
        this.weeken.setText(weeken);
        this.hoogste.setText(hoogste);
    }
    public void getid(int id,int id2){
        singleid = id;
        artiestid = id2;
    }
    public void singlesinfo(){
        System.out.println();
        int texts = singleid;
        int artiest = artiestid;

        Modelsingleinfo modelsingleinfo=Modelsingleinfo.getOurInstance();

        for (Modelsingleinfo.singleinfoItem singleinfoItem:modelsingleinfo.getsingleinfo(texts)){
            String weeken = ""+singleinfoItem.getWeeken()+"";
            String hoogste = ""+singleinfoItem.getHoogsteb()+"";

            setNaam(singleinfoItem.getSinglenaam(),singleinfoItem.getArtiestnaam(),weeken,hoogste);

        }
        Modelsingleartist modelsingleartist=Modelsingleartist.getOurInstance();
        lijst.getItems().clear();

        for (Modelsingleartist.singleartistItem singleartistItem:modelsingleartist.getSingleartist(artiest)){

            lijst.getItems().add(singleartistItem);

        }

    }
    @FXML
    void close(ActionEvent event){
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
