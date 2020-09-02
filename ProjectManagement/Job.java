package ProjectManagement;



public class Job implements Comparable<Job> {
    String name;
    String project_name;
    String user_name;
    int runtime;
    int priority;
    int count;
    public Job(String name, String project_name, String user_name, int runtime, int priority,int count){
        this.user_name =user_name;
        this.project_name = project_name;
        this.name = name;
        this.runtime = runtime;
        this.priority = priority;
        this.count=count;
    }
    @Override
    public int compareTo(Job job) {
        if(this.priority>job.priority)
            return 1;
        else if(this.priority<job.priority)
            return -1;
        else {
            if(this.count>job.count)
                return -1;
            else return 1;
        }
    }
}