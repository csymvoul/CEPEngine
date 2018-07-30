package com.matilda5g.cep;

import com.matilda5g.cep.streamingData.HttpResponseListener;
import com.matilda5g.cep.streamingData.StreamingData;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception {
//        // Create a RulesReader object
//        RulesReader rulesFile = new RulesReader();
//
//        // Set the path of the rules file
//        rulesFile.setRulesFilePath("rules/rules.drl");
//
//        // Get path of the rules file
//        String rulesFilePath = rulesFile.getRulesFile().getPath();
//
//        // Print the rulesFile
//        rulesFile.printRules(rulesFilePath);

        StreamingData netDataStream = new StreamingData("http://83.212.96.238:19999/api/v1/allmetrics?format=json");

//        JSONObject netDataHttpResponse = netDataStream.getHttpResponse();


//        StreamingData cpuUsageStream = new StreamingData("http://83.212.96.238:9090/api/v1/query?query=(sum(rate(node_cpu[1m]))>0.01)");

        netDataStream.StreamHttpRequest();
//        cpuUsageStream.StreamHttpRequest();
    }
}
