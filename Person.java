public class Person {
    public Person(String name, String nationality){
        this.name = name;
        this.nationality = nationality;
    }

    public String getName(){ return name; }
    public String getNationality(){ return nationality; }

    public void setName(String name){ this.name = name; }
    public void setNationality(String nationality){ this.nationality = nationality; }

    public String toString(){ return getName() + ", " + getNationality(); }

    private String name;
    private String nationality;
}
