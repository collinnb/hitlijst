package sample;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Created by Collin on 2-7-2017.
 */
public class Maincontroller {

    public enum Screen  {SCHERM1, SCHERM2, SCHERM3};

    @FXML
    private BorderPane borderpane;

    protected Screen current_screen;


    public void setScreen(Screen screen){
        if (screen != current_screen){
            String fxmlfilename;


            switch (screen){
                case SCHERM1:
                    fxmlfilename = "artiest";
                            break;
                case SCHERM2:
                    fxmlfilename = "single";
                    break;
                case SCHERM3:
                    fxmlfilename = "top40";
                    break;
                default:
                    throw new Error("Screen unknown or not implented");
            }
            System.out.println("load fxml: "+ fxmlfilename + ".fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfilename+ ".fxml"));
            Parent element = null;

            try{
                element = loader.load();
            } catch (IOException e){
                e.printStackTrace();
                throw new Error("Load fxml failt,");
            }

            Controller c = loader.getController();
            c.setMaincontroller(this);
            current_screen = screen;
            borderpane.setCenter(element);

        }
    }
    @FXML
    void initialize() {
        setScreen(Maincontroller.Screen.SCHERM1);
    }

    @FXML
    void click_artist (ActionEvent event) {
        setScreen(Maincontroller.Screen.SCHERM1);
    }

    @FXML
    void click_singles (ActionEvent event) {
        setScreen(Maincontroller.Screen.SCHERM2);
    }

    @FXML
    void click_top40 (ActionEvent event) {
        setScreen(Maincontroller.Screen.SCHERM3);
    }
}
