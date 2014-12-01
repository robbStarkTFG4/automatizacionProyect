/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author NORE
 */
public class ReadOnlyCommands {

    public SimpleBooleanProperty[] states = new SimpleBooleanProperty[6];

    public ReadOnlyCommands() {
        for (int i = 0; i < states.length; i++) {
            int lis = i;
            states[i] = new SimpleBooleanProperty(false);
            states[i].addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    switch (lis) {
                        case 0:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston.setVastagoState(newValue);
                                }
                            });

                            break;
                        case 1:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston2.setVastagoState(newValue);
                                }
                            });
                            break;
                        case 2:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston3.setVastagoState(newValue);
                                }
                            });
                            break;
                        case 3:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston4.setVastagoState(newValue);
                                }
                            });
                            break;
                        case 4:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston5.setVastagoState(newValue);
                                }
                            });
                            break;
                        case 5:
                            System.out.println("LISTENER " + lis);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Crixus.getInstance().getGemma().piston6.setVastagoState(newValue);
                                }
                            });
                            break;
                    }
                }

            });
        }
    }

    public void readRegisters() {

        try {
            if (Crixus.getInstance().getModbus().getMaster().isInitialized()) {
                boolean[] vals = Crixus.getInstance().getModbus().readDiscreteInputTest(
                        ModBus.SLAVE_ADDRESS, 0, 5);

                for (int i = 0; i < states.length; i++) {
                    if (!(vals[i] == states[i].getValue())) {
                        states[i].set(vals[i]);
                    }
                }
            }
        } catch (Exception e) {

        }

    }

}
