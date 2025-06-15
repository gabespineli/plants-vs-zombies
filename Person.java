public class Person {
    public Person(String name, String nationality){
        this.NAME = name;
        this.NATIONALITY = nationality;
    }

    public String getName(){ return NAME; }
    public String getNationality(){ return NATIONALITY; }

    @Override
    public String toString(){ return getName() + ", " + getNationality(); }

    private final String NAME;
    private final String NATIONALITY;
}