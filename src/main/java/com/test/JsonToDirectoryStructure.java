package com.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JsonToDirectoryStructure {

	public static void main (String args[]) {
        try {
            String jsonInString =  new ObjectMapper().writerWithDefaultPrettyPrinter()
            		.writeValueAsString(getNode(new File("C:/ILS_Repo/DICOM/"))
            );
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
 
	public static Node getNode(File file){
        if(file.isDirectory()){
        	System.out.println("Directory name: "+ file.getName());
            return new Node(file.getName(), getDirList(file));
        }else{
        	System.out.println("file name: "+ file.getName());
            return new Node(file.getName());
        }
    }


    public static List<Node> getDirList(File file){
        List<Node> nodeList=new ArrayList<>();
        for(File n : file.listFiles()){
            nodeList.add(getNode(n));
        }
        return nodeList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private String label;
        private List<Node> nodes;
        
		public Node(String label) {
			this.label = label;
		}
	}
}
