package com.matilda5g.cep.fusionEngine;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionPseudoClock;
import org.drools.time.SessionClock;
import org.json.JSONException;

import java.io.*;
import java.util.Calendar;

public class DroolsFusion {
    private File rulesFile;
    private String path;

    public void droolsFusionSession(Netdata netdata) throws JSONException{
        // KnowledgeBuilder has a collection of DRL files, so our rules can be divided in several files
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        File file = getRulesFile();
        kBuilder.add(ResourceFactory.newFileResource(file.getPath()), ResourceType.DRL);
        if (kBuilder.hasErrors()){
            System.out.println(kBuilder.getErrors().toString());
        }

        // KnowledgeBaseConfiguration is used to set the event processing mode as STREAM
        KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);

        // KnowledgeBase: We created KnowledgeBase considering the connection of DRL files the KnowledgeBuilder has
        KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase(config);
        kBase.addKnowledgePackages(kBuilder.getKnowledgePackages());

        // Pseudo Clock Configuration
        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        conf.setOption(ClockTypeOption.get("realtime"));

//        // StatefulKnowledgeSession: Once we have our KnowledgeBase we create a Session to use it
        StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession();

//        // If we have the pseudo clock the above code turns into
//        StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession(conf, null);
//        SessionPseudoClock clock = kSession.getSessionClock();

        // Create Netdata entry point
        WorkingMemoryEntryPoint netdataStream = kSession.getWorkingMemoryEntryPoint("Netdata");
        insertEvent(netdataStream, netdata);

        NetdataBucket netdataBucketObj = new NetdataBucket();
        // Create NetdataBucket entry point
        WorkingMemoryEntryPoint netdataBucket = kSession.getWorkingMemoryEntryPoint("NetdataBucket");
        netdataBucket.insert(netdataBucketObj);
//        netdataStream.insert(netdata);
        kSession.fireAllRules();

        kSession.dispose();
        System.out.println();

//        // get the path if the rules file (debug)
//        System.out.println(getRulesFile().getPath());

//        // print the rules file (debug)
//        printRules(rulesFile.getPath());
    }

    private void insertEvent(org.drools.runtime.rule.WorkingMemoryEntryPoint entryPoint,
                             Netdata netdata) {
        entryPoint.insert(netdata);
    }

    public File getRulesFile(){
        try {
            this.rulesFile = new File(this.path);
            return this.rulesFile;

        } catch(NullPointerException e){
            System.out.println("NullPointerException");
            return null;
        }
    }

    public void printRules(String rulesFilePath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException --> File not found");
        } catch (IOException e) {
            System.out.println("IOException --> IO operation failed or interrupted");
        }
    }

    public void setRulesFilePath(String path) {
        this.path = path;
    }
}
