package TestAgents;

import com.gulley.dustin.*;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        //AgentHandler agentHandler = new AgentHandler(8000, "log_file.txt");
        //agentHandler.listen();

        UserAgent userAgent = new UserAgent("log_file.txt", "user_attributes.json");
        RestaurantAgent restaurantAgent = new RestaurantAgent("log_file.txt");
        AgentHandler agentHandler = new AgentHandler("log_file.txt");
        TransportationAgent transportationAgent = new TransportationAgent("log_file.txt");
        RecommenderAgent recommenderAgent = new RecommenderAgent("log_file.txt");
        agentHandler.register(userAgent);
        agentHandler.register(restaurantAgent);
        agentHandler.register(transportationAgent);
        agentHandler.register(recommenderAgent);
        agentHandler.listen();
    }
}
