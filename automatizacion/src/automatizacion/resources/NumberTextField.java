/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion.resources;

import javafx.scene.control.TextField;

/**
 *
 * @author NORE
 */
public class NumberTextField extends TextField {

    public NumberTextField() {
        this.setPromptText("escribe cantidad");
    }

    @Override
    public void replaceText(int start, int end, String text) {

        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement); //To change body of generated methods, choose Tools | Templates.
    }

}
