/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import insidefx.undecorator.Undecorator;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author NORE
 */
public class Automatizacion extends Application {
    
    private Stage stage;
    private Stage main;
    private SplashScreenController spl;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            
            stage = primaryStage;
            stage.setTitle("FXML Login Sample");
            // stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            //stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    //   System.exit(1);
                    // instance.release();
                    System.out.println("HOLA STAGE");
                }
            });
            gotoSplashScreen(stage);
            //  stage.hide();
        } catch (Exception ex) {
            Logger.getLogger(Automatizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gotoSplashScreen(Stage splash) {
        try {
            spl = (SplashScreenController) replaceSceneContent("splashScreen.fxml", splash);
            spl.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Automatizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gotoLogin() {
        try {
            FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), spl.getSplashParent());
            fadeSplash.setFromValue(1.0);
            fadeSplash.setToValue(0.0);
            fadeSplash.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.hide();
                    main = new Stage(StageStyle.UNDECORATED);
                    main.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        
                        @Override
                        public void handle(WindowEvent event) {
                            Crixus.getInstance().release();
                        }
                    });
                    LoginController login;
                    try {
                        login = (LoginController) replaceSceneContent("login.fxml");
                        login.setApp(Automatizacion.this);
                    } catch (Exception ex) {
                        Logger.getLogger(Automatizacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
            fadeSplash.play();
            
        } catch (Exception ex) {
            Logger.getLogger(Automatizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gotoGemma() {
        try {
            GemmaController login = (GemmaController) replaceSceneContent("gemma.fxml");
            Crixus.getInstance().setGemma(login);
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Automatizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Automatizacion.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Automatizacion.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Undecorator undecorator = new Undecorator(main, page);
        undecorator.getStylesheets().add("skin/undecorator.css");
        Scene scene = new Scene(undecorator, 850, 450);
        scene.getStylesheets().add(Automatizacion.class.getResource("main.css").toExternalForm());

        // Transparent scene and stage
        scene.setFill(Color.TRANSPARENT);
        if (!main.isShowing()) {
            main.initStyle(StageStyle.TRANSPARENT);
        }
        main.setScene(scene);
        
        main.sizeToScene();
        main.show();
        return (Initializable) loader.getController();
    }
    
    private Initializable replaceSceneContent(String fxml, Stage splash) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Automatizacion.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Automatizacion.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 600, 400);
        splash.setScene(scene);
        splash.sizeToScene();
        splash.show();
        return (Initializable) loader.getController();
    }
    
    public static void main(String[] args) {
        Application.launch(Automatizacion.class, (java.lang.String[]) null);
    }
    
    public boolean userLoging(String string, String string0) {
        
        gotoGemma();
        return true;
    }
    
    public void afterSplash() {
        gotoLogin();
    }
    
}
