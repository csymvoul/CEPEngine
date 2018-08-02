package com.matilda5g.cep.fusionEngine;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.json.JSONException;

import java.io.*;

public class DroolsFusion {
    private File rulesFile;
    private String path;

    public void droolsFusion(Netdata netdata) throws JSONException{
        // KnowledgeBuilder has a collection of DRL files, so our rules can be divided in several files
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        File file = getRulesFile();
        kbuilder.add(ResourceFactory.newFileResource(file.getPath()), ResourceType.DRL);
        if (kbuilder.hasErrors()){
            System.out.println(kbuilder.getErrors().toString());
        }

        // KnowledgeBaseConfiguration is used to set the event processing mode as STREAM
        KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);

        // KnowledgeBase: We created KnowledgeBase considering the connection of DRL files the KnowledgeBuilder has
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(config);
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        //StatefulKnowledgeSession: Once we have our KnowledgeBase we create a Session to use it
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        org.drools.runtime.rule.WorkingMemoryEntryPoint entryPointNetdata = ksession.getWorkingMemoryEntryPoint("Netdata");
//        org.drools.runtime.rule.WorkingMemoryEntryPoint entryPointStoreTwo = ksession.getWorkingMemoryEntryPoint("StoreTwo");

        insertEvent(entryPointNetdata, netdata);
        ksession.fireAllRules();
        ksession.dispose();
//        System.out.println(getRulesFile().getPath());
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
