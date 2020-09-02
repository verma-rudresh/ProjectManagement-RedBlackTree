package ProjectManagement;

public class User implements Comparable<User> {

    String name;
    User(String user){
        this.name=user;
    }
    @Override
    public int compareTo(User user)
    {
        if(this.name.compareTo(user.name)>0)
            return 1;
        else if(this.name.compareTo(user.name)<0)
            return -1;
        return 0;
    }
}
