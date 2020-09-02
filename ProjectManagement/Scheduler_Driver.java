package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {


    public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

            switch (cmd[0]) {
                case "PROJECT":
                    handle_project(cmd);
                    break;
                case "JOB":
                    handle_job(cmd);
                    break;
                case "USER":
                    handle_user(cmd[1]);
                    break;
                case "QUERY":
                    handle_query(cmd[1]);
                    break;
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();

        print_stats();

    }

    private Trie<User> user_list = new Trie<User>();
    private RBTree<String,Project> project_RB = new RBTree<>();
    private MaxHeap<Job> job_list = new MaxHeap<>();
    private ArrayList<Job> completed_job_list = new ArrayList<>();
//    private Queue<Job> completed_job_queue = new LinkedList<>();
    private ArrayList<Job> jobs_store = new ArrayList<>();
    private ArrayList<Job> checked_jobs_list = new ArrayList<>();

    @Override
    public void run() {
        // till there are JOBS
        schedule();
    }


    @Override
    public void run_to_completion() {
        int size=job_list.max_heap_size();
        while(size>0){
            if(job_list.max_heap_size()>0)
            {
                System.out.println("Running code");
                System.out.println("Remaining jobs: "+ job_list.max_heap_size());
                Job executable_job = job_list.extractMax();
                String related_project_name = executable_job.project_name;
                RedBlackNode<String,Project> project_node = project_RB.search(related_project_name);
                int project_budjet = project_node.value.budjet;
                int job_running_time = executable_job.runtime;
                String job_name = executable_job.name;
                System.out.println("Executing: "+job_name+" from: "+related_project_name);
                while(job_running_time>project_budjet){
                    checked_jobs_list.add(executable_job);
                    System.out.println("Un-sufficient budget.");
                    executable_job = job_list.extractMax();
                    if(executable_job!=null){
                        related_project_name = executable_job.project_name;
                        project_node = project_RB.search(related_project_name);
                        project_budjet = project_node.value.budjet;
                        job_running_time = executable_job.runtime;
                        job_name = executable_job.name;
                        System.out.println("Executing: "+job_name+" from: "+related_project_name);
                    }
                    else{
                        break;
                    }
                }
                if(executable_job!=null){
                    completed_job_list.add(executable_job);
//                    completed_job_queue.add(executable_job);
                    project_node.value.budjet = project_node.value.budjet- job_running_time;
                    System.out.println("Project: "+related_project_name+" budget remaining: "+project_node.value.budjet);
                    System.out.println("System execution completed");
                }
            }
            size=job_list.max_heap_size();
        }
    }

    @Override
    public void handle_project(String[] cmd) {
        String key = cmd[1];
        int priority = Integer.parseInt(cmd[2]);
        int budjet = Integer.parseInt(cmd[3]);
        Project project= new Project(key, priority, budjet);
        project_RB.insert(key,project);
        System.out.println("Creating project");
    }

    @Override
    public void handle_job(String[] cmd) {
        String job_name= cmd[1];
        System.out.println("Creating job");
        String project_name = cmd[2];
        String user_name = cmd[3];
        int runtime =Integer.parseInt(cmd[4]);
        RedBlackNode<String,Project> project_node = project_RB.search(project_name);
        if(project_node!=null){

            TrieNode<User> user_node = user_list.search(user_name);
            if(user_node !=null){
                int priority = project_node.getValue().priority;
                int size_list = jobs_store.size();
                Job job =new Job(job_name, project_name,user_name,runtime,priority,size_list);
                job_list.insert(job);
                jobs_store.add(job);
            }
            else {
                System.out.println("No such user exists: "+user_name);
            }
        }
        else {
            System.out.println("No such project exists. "+project_name);
        }
    }

    @Override
    public void handle_user(String name) {
        System.out.println("Creating user");
        User user_obj = new User(name);
        user_list.insert(name,user_obj);
    }

    @Override
    public void handle_query(String key) {
        System.out.println("Querying");
        if(search_arrayList(completed_job_list,key)){
            System.out.println(key+": COMPLETED");
        }
        else if(search_arrayList(jobs_store,key)){
            System.out.println(key+": NOT FINISHED");
        }
        else
            System.out.println(key+": NO SUCH JOB");
    }

    @Override
    public void handle_empty_line() {
        run();
    }

    @Override
    public void handle_add(String[] cmd) {
        System.out.println("ADDING Budget");
        String project_name = cmd[1];
        int increment = Integer.parseInt(cmd[2]);
        RedBlackNode<String,Project> spot_node = project_RB.search(project_name);
        if(spot_node!=null){
            spot_node.value.budjet+=increment;
        }
        int checked_jobs_list_size = checked_jobs_list.size();
        for(int i=checked_jobs_list_size-1;i>=0;i--){
            Job j= checked_jobs_list.get(i);
            if(j.project_name.equals(project_name)){

                job_list.insert(j);
                checked_jobs_list.remove(i);
            }
        }
    }

    @Override
    public void print_stats() {
        int total_time = 0;
        System.out.println("--------------STATS---------------");
//        int size= completed_job_queue.size();
        int size= completed_job_list.size();
        System.out.println("Total jobs done: "+size);
        /*while(!completed_job_queue.isEmpty()){
            Job finished_job = completed_job_queue.remove();
            int job_running_time = finished_job.runtime;
            String user = finished_job.user_name;
            String job_name = finished_job.name;
            String project_name = finished_job.project_name;
            total_time += job_running_time;
            System.out.println("Job{user='"+user+"', project='"+project_name+"', jobstatus=COMPLETED, execution_time="+job_running_time+", end_time="+total_time+", name='"+job_name+"'}");

        }*/
        for(Job finished_job : completed_job_list){
            int job_running_time = finished_job.runtime;
            String user = finished_job.user_name;
            String job_name = finished_job.name;
            String project_name = finished_job.project_name;
            total_time += job_running_time;
            System.out.println("Job{user='"+user+"', project='"+project_name+"', jobstatus=COMPLETED, execution_time="+job_running_time+", end_time="+total_time+", name='"+job_name+"'}");

        }
        System.out.println("------------------------");
        System.out.println("Unfinished jobs:");
        int size_unfinished = checked_jobs_list.size();
        for (Job unfi_job : checked_jobs_list) {
            int job_running_time = unfi_job.runtime;
            String user = unfi_job.user_name;
            String job_name = unfi_job.name;
            String project_name = unfi_job.project_name;
            System.out.println("Job{user='" + user + "', project='" + project_name + "', jobstatus=REQUESTED, execution_time=" + job_running_time + ", end_time=null, name='" + job_name + "'}");
        }
        System.out.println("Total unfinished jobs: "+ size_unfinished);
        System.out.println("--------------STATS DONE---------------");

    }

    @Override
    public void schedule() {
        if(job_list.max_heap_size()>0)
        {
            System.out.println("Running code");
            System.out.println("Remaining jobs: "+ job_list.max_heap_size());
            Job executable_job = job_list.extractMax();
                String related_project_name = executable_job.project_name;
                RedBlackNode<String,Project> project_node = project_RB.search(related_project_name);
                int project_budjet = project_node.value.budjet;
                int job_running_time = executable_job.runtime;
                String job_name = executable_job.name;
                System.out.println("Executing: "+job_name+" from: "+related_project_name);
                while(job_running_time>project_budjet){
                    checked_jobs_list.add(executable_job);
                    System.out.println("Un-sufficient budget.");
                    executable_job = job_list.extractMax();
                    if(executable_job!=null){
                        related_project_name = executable_job.project_name;
                        project_node = project_RB.search(related_project_name);
                        project_budjet = project_node.value.budjet;
                        job_running_time = executable_job.runtime;
                        job_name = executable_job.name;
                        System.out.println("Executing: "+job_name+" from: "+related_project_name);
                    }
                    else{
                        break;
                    }
                }
                if(executable_job!=null){
                    completed_job_list.add(executable_job);
//                    completed_job_queue.add(executable_job);
                    project_node.value.budjet = project_node.value.budjet- job_running_time;
                    System.out.println("Project: "+related_project_name+" budget remaining: "+project_node.value.budjet);
                    System.out.println("Execution cycle completed");
                }
        }
    }

    public boolean search_arrayList(ArrayList<Job> list, String key){
        int size_completed = list.size();
        if(size_completed!=0)
        {
            for(int i=0;i<size_completed;i++){
                Job j =list.get(i);
                if(key.compareTo(j.name)==0)
                    return true;
            }
        }
        return false;
    }
}
