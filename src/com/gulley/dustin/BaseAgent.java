package com.gulley.dustin;
import com.gulley.dustin.data.Data;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.UUID;

/**
 * Abstract class for creating simple agents
 */
public abstract class BaseAgent {

    protected String logfile;
    protected UUID id;
    protected AgentHandler handler;
    protected Data.AgentType agentType;

    /**
     * Get this agent type
     * @return
     */
    public Data.AgentType getAgentType() {
        return this.agentType;
    }

    /**
     * Set the handler agent
     * @param handler
     */
    public void setHandler(AgentHandler handler) {
        this.handler = handler;
    }

    /**
     * Get the agents id
     * @return
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the logfile
     * @return
     */
    protected String getLogfile() {
        return logfile;
    }

    /**
     * Override to receive requests
     * @param obj
     */
    abstract void message(JSONObject obj);

    /**
     * Get the contents of a supplied file
     * @param filename
     * @return
     */
    protected StringBuilder getContentsOfFile(String filename) {

        StringBuilder sb = new StringBuilder("");
        try {
            Scanner dataFile = new Scanner(new File(filename));
            while (dataFile.hasNextLine()) {
                sb.append(dataFile.nextLine());
            }
            dataFile.close();
        } catch(FileNotFoundException ex) {
            System.out.println("FileNotFoundException Error in UserAgent.getContentsOfFile: " + ex.toString());
            ex.printStackTrace();
        } catch(Exception ex) {
            System.out.println("Exception Error in UserAgent.getContentsOfFile: " + ex.toString());
            ex.printStackTrace();
        }
        return sb;

    }

    /**
     * Creates a JSONObject from a stringbuilder
     * @param str
     * @return
     */
    protected JSONObject stringBuilderToJSON(StringBuilder str) {
        return new JSONObject(str.toString());
    }

    /**
     * Override to do agent work
     */
    abstract void work();

    /**
     * Start the agent and listen
     */
    public void listen() {

        try {

            /**
             * Do agent work
             */
            while(true) {
                this.work();
                try {
                    Thread.sleep(60000);
                } catch(InterruptedException ex) {
                    System.out.println("IOException in BaseAgent.listen: " + ex.toString());
                    System.out.println("Stopping the agent.");
                    ex.printStackTrace();
                    System.exit(-1);
                }

            }

        } catch(Exception ex) {

            System.out.println("Exception in BaseAgent.listen: " + ex.toString());
            System.out.println("Stopping the agent.");
            ex.printStackTrace();
            System.exit(-1);

        }
    }

    /**
     * Create the base agent
     * @param logfile
     */
    public BaseAgent(String logfile, Data.AgentType agentType) {
        this.logfile = logfile;
        this.agentType = agentType;
        this.id = java.util.UUID.randomUUID();
    }

}
