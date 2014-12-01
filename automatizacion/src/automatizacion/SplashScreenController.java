/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NORE
 */
public class SplashScreenController implements Initializable {

    @FXML
    private ProgressBar bar;
    @FXML
    private AnchorPane splashParent;

    private Automatizacion application;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final Task<Integer> task = new Task() {

            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 100; i++) {

                    Thread.sleep(50);
                    updateProgress(i, 100);
                    if (i == 60) {
                        Crixus.getInstance();
                    }
                }
                return null;
            }

        };
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

        task.stateProperty().addListener(new ChangeListener<Worker.State>() {

            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    System.out.println("YA ACABEEEE!!!!!!!!!!!!!!");
                    SplashScreenController.this.application.afterSplash();
                }
            }
        });
    }

    public void setApp(Automatizacion aThis) {
        this.application = aThis;
    }

    public AnchorPane getSplashParent() {
        return splashParent;
    }

}
