package jxml;

import jxml.Section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public Boolean containsSection(Section section){
        if(section == sections.get(section.getClassname())){
            return true;
        }else{

        }
        return false;
    }

    public void removeSection(Section section){
        sections.remove(section.getClassname());
    }
}
