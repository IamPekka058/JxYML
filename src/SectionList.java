import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SectionList {
    boolean addmode = false;
    public HashMap<String,Section> sections = new HashMap<>();
    public void update(){
        try {
            Scanner sc = new Scanner(new File("test.xyml"));
            int counter = 0;
            int start_line = 0;
            int end_line = 0;
            HashMap hashMap = new HashMap();

            while (sc.hasNextLine()) {
                String act_line = sc.nextLine();
                if(act_line.startsWith("</")){
                    end_line = counter;
                    String section_title = act_line.replace("</", "").replace(">","");
                    Section section = new Section(section_title,start_line, end_line);
                    section.setParams(hashMap);
                    sections.put(section_title,section);
                    hashMap.clear();
                    addmode = false;
                }
                if(addmode){
                    String[] res = StringFormat.split(act_line);
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
}
