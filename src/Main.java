public class Main {
    public static void main(String[] args) {
        SectionList sectionList = new SectionList();
        sectionList.update();
        Section section = sectionList.sections.get("TestName1");
        System.out.println(section.get("asdf"));

    }
}
