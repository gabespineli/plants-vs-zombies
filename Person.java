public class Person {
    private String name;
    private String nationality;

    public Person(String name, String nationality){
        this.name = name;
        this.nationality = nationality;
    }

    public String getName(){
        return name;
    }

    public String getNationality(){
        return nationality;
    }
}
