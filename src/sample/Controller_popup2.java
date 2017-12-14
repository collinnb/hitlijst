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
public class Controller_popup2 {
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
    public void setNaam(String titel,String artiest,String weeken,String hoogste) {

        this.titel.setText(titel);
        this.artiest.setText(artiest);
        this.weeken.setText(weeken);
        this.hoogste.setText(hoogste);
    }
    public void getid(int id){
        singleid = id;
    }
    public void singlesinfo(){
        System.out.println();
        int texts = singleid;

        Modelsingleinfo modelsingleinfo=Modelsingleinfo.getOurInstance();

        for (Modelsingleinfo.singleinfoItem singleinfoItem:modelsingleinfo.getsingleinfo(texts)){
            String weeken = ""+singleinfoItem.getWeeken()+"";
            String hoogste = ""+singleinfoItem.getHoogsteb()+"";

            setNaam(singleinfoItem.getSinglenaam(),singleinfoItem.getArtiestnaam(),weeken,hoogste);

        }
    }
    @FXML
    void close(ActionEvent event){
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
