package jxml;

import jxml.Section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SectionHandler {

    private boolean addmode = false;
    private String Filepath = null;
    private HashMap<String, Section> sections = new HashMap<>();

    public SectionHandler(String filepath) throws IOException {
        this.Filepath = filepath;
        File file = new File(this.Filepath);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public void update(){
        try {
            Scanner sc = new Scanner(new File(this.Filepath));
            int counter = 0;
            int start_line = 0;
            int end_line = 0;
            HashMap hashMap = new HashMap();

            while (sc.hasNextLine()) {
                String act_line = sc.nextLine();
                if(act_line.startsWith("</")){
                    end_line = counter;
                    String section_title = act_line.replace("</", "").replace(">","");
                    Section section = new Section(section_title);
                    section.transmitParams(hashMap);
                    sections.put(section_title,section);
                    hashMap.clear();
                    addmode = false;
                }
                if(addmode){
                    String[] res = act_line.split(":");
                    hashMap.put(res[0], res[1]);
                }
                if(act_line.startsWith("<-")){
                     start_line = counter;
                    addmode = true;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void flush(){
        try{
            FileWriter fw = new FileWriter(this.Filepath);
            for(Section section:sections.values()){
                fw.write("<-"+section.getClassname()+">"+"\n");
                section.getParams().forEach((key, value) -> {
                    try {
                        fw.write(key+":"+value+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                fw.write("</"+section.getClassname()+">"+"\n");
            }
            fw.flush();
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Section> getSections() {
        return sections;
    }

    public void addSection(Section section){
        sections.put(section.getClassname(), section);
    }

    public Section getSection(String key){
        return sections.get(key);
    }
    /*
    Checks if a Section is already registered in the SectionHandler.

    _BUG_
    Returns everytime you call it false
    */
    public Boolean containsSection(Section section){
        if(sections.containsKey(section.getClassname())){
            return true;
        }
        return false;
    }

    public void removeSection(Section section){
        sections.remove(section.getClassname());
    }

    /*
    __New Method__ Version pre-v.0.3
    Returns every Section registered in the HashMap
    */
    public HashMap<String, Section> getAllSectionsbyHashMap(){
        return sections;
    }

    /*
    __New Method__ Version pre-v.0.3
    Converts all Sections into a ArrayList
    */
    public ArrayList<Section> getAllSectionsbyArrayList(){
        ArrayList<Section> temp_array = new ArrayList<>();
        sections.forEach((key, section) -> {
            temp_array.add(section);
        });
        return temp_array;
    }
}
