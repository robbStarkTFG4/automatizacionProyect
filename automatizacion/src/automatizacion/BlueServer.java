/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NORE
 */
public class BlueServer implements Runnable {

    public boolean active = true;
    private boolean doSomething = true;
    private Crixus instance = null;

    @Override
    public void run() {
        instance = Crixus.getInstance();
        while (active) {
            try {
                Thread.sleep(5000);
                if (instance.getGemma() != null) {
                    if (instance.getGemma().piston != null) {
                    /*    if (instance.getGemma().piston.getVastagoState()) {
                            instance.getGemma().piston.setVastagoState(false);

                        } else {
                            instance.getGemma().piston.setVastagoState(true);
                        }*/
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BlueServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
