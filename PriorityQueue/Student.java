package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = parseInt;
    }
    @Override
    public String toString(){
        String line = "Student"+"{name='"+this.name+"', marks="+this.marks+"}";
        return line;
    }


    @Override
    public int compareTo(Student student) {
        int check_marks=student.marks;
        if(this.marks>check_marks)
            return 1;
        else if(this.marks<check_marks)
            return -1;
        return 0;
    }

    public String getName() {
        return name;
    }

}
