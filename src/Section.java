import java.util.HashMap;

public class Section {
    String classname = "";
    int start_line, end_line = 0;
    public HashMap params = new HashMap();

    public void setParams(HashMap parameters) {
        params.putAll(parameters);
    }

    public Section(String classname, int start_line, int end_line){
        this.classname = classname;
        this.start_line = start_line;
        this.end_line = end_line;
    }

    public String get(String key){
        return params.get(key).toString();
    }
}
