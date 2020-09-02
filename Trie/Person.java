package Trie;

public class Person {
    String name;
    String phone_number;
    public Person(String name, String phone_number) {
        this.name =name;
        this.phone_number =phone_number;
    }

    @Override
    public String toString() {
        return "[Name: "+ this.getName()+", Phone="+this.getPhone_number()+"]";
    }

    public String getName() {
        return name;
    }
    public String getPhone_number(){ return phone_number;}
}
