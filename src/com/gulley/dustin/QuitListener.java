package com.gulley.dustin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Listen for the q key and stop everything
 */
public class QuitListener implements Runnable {

    private boolean keepListening;

    public boolean shouldQuit() {
        return this.keepListening;
    }

    public QuitListener() {
        this.keepListening = true;
    }

    public void run() {

        try {

            String s;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            /**
             * Check if q was pressed
             */
            while(!((s = br.readLine().toUpperCase()).equals("Q"))) {
                System.out.println("Unrecognized command.");
                System.out.println("Please type q to quit.");
            }

            // Q was pressed time to stop
            this.keepListening = false;
            System.out.println("Stopping the agent.");
            br.close();

        } catch(IOException ex) {

            System.out.println("IOException in QuitListener.run: " + ex.toString());
            System.out.println("Stopping the server.");
            ex.printStackTrace();
            System.exit(-1);

        } catch(Exception ex) {

            System.out.println("Exception in QuitListener.run: " + ex.toString());
            System.out.println("Stopping the server.");
            ex.printStackTrace();
            System.exit(-1);

        }

    }

}
