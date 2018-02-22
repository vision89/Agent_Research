package com.gulley.dustin;

import com.gulley.dustin.data.Data;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgentHandler extends BaseAgent {

    ArrayList<BaseAgent> agents;

    public void register(BaseAgent agent) {
        boolean canRegister = true;
        for(BaseAgent ba : agents) {
            if(ba.getId().equals(agent.getId())) {
                canRegister = false;
                break;
            }
        }
        if(canRegister) {
            agent.setHandler(this);
            agents.add(agent);
        }
    }

    void work() {

        for(BaseAgent ba : agents) {
            new Thread(() -> {
                ba.listen();
            }).start();
        }

        QuitListener ql = new QuitListener();
        new Thread(() -> {
            ql.run();
            while(ql.shouldQuit()) {}
            System.exit(1);
        }).start();

    }

    void message(JSONObject obj) {

        for(BaseAgent agent : agents) {
            /**
             * Get by id
             */
            if(obj.has("requesteeId")) {
                if (agent.getId().equals(obj.get("requesteeId"))) {
                    agent.message(obj);
                    break;
                }
            } else {
                /**
                 * Get by agent type
                 */
                if (agent.getAgentType().equals(obj.get("requestee"))) {
                    agent.message(obj);
                    break;
                }
            }
        }

    }

    public AgentHandler(String logfile) {
        super(logfile, Data.AgentType.Handler);
        agents = new ArrayList<BaseAgent>();
    }

}
