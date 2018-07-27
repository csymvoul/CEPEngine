package com.matilda5g.cep.fusionEngine;

import java.io.*;

public class RulesReader {
    private File rulesFile;
    private String path;

    public File getRulesFile(){
        try {
            this.rulesFile = new File(this.path);
            return this.rulesFile;

        } catch(NullPointerException e){
            System.out.println("NullPointerException --> No ");
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
