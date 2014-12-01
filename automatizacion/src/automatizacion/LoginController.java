/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.AwesomeIconsStack;
import de.jensd.fx.fontawesome.Icon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.Glyph;

/**
 *
 * @author NORE
 */
public class LoginController extends BorderPane implements Initializable {

    @FXML
    Label icon;

    @FXML
    Label icon1;

    @FXML
    TextField user;

    private Automatizacion application;

    public void setApp(Automatizacion application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*   Region stackedIcon2 = AwesomeIconsStack.create()
         .add(Icon.create()
         .icon(AwesomeIcon.SQUARE)
         .style("-fx-font-size: 4em; -fx-text-fill: yellowgreen;"))
         .add(Icon.create()
         .icon(AwesomeIcon.APPLE)
         .style("-fx-font-size: 3em; -fx-text-fill: white;")
         );*/
        //user.setText("si funciono");
        Glyph glyph1 = new Glyph("FontAwesome", "SQUARE");
        glyph1.setFontSize(130.0);

        Glyph glyph = new Glyph("FontAwesome", "BEER");
        glyph.setFontSize(80.0);
        glyph.setColor(Color.WHITE);
        icon1.setGraphic(glyph);

        icon.setGraphic(glyph1);
        //  icon.setClip(new Glyph("FontAwesome", "BEER"));

    }

    public void processLogin(ActionEvent event) {
        if (application == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            //     errorMessage.setText("Hello " + userId.getText());
        } else {
            //if (!application.userLogging(userId.getText(), password.getText())){
            if (!application.userLoging("dsd", "sds")) {
                //       errorMessage.setText("Username/Password is incorrect");
            }
        }
    }
}
