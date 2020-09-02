package Trie;
import java.util.LinkedList;
import java.util.Queue;
public class Trie<T> implements TrieInterface {
    TrieNode<T> root;
    public Trie(){
        this.root = new TrieNode();
    }




    @Override
    public boolean insert(String word, Object value) {
        TrieNode<T> check_Node = root;
        int Size = word.length();
        for(int i =0; i<Size;i++){
            char c= word.charAt(i);
            //TrieNode<T> new_Node ;
            int index = find_Index(c);
            if(check_Node.children[index]==null){
                TrieNode<T> new_Node = new TrieNode();//create a new node
                new_Node.ch=c;
                check_Node.children[index]=new_Node;    //insert this node into the corresponding place in the array
                check_Node = new_Node;                  //now, shift the checking node to this node for further check...
            }else{
                check_Node=check_Node.children[index];
                //if already present, then shift the check node to the child in concern.
            }
        }
        check_Node.flag = true;
        check_Node.value = (T) value;
        return true;
    }

    public int find_Index(char c){
        int index= c-' ';
        return index;
    }

    @Override
    public boolean delete(String word) {
        if(word ==null || word.length()==0)
            return false;
        TrieNode deleteBelow = null;
        char deleteChar = '\0';
        // Search to ensure word is present
        TrieNode parent = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            int index1= find_Index(cur);
            TrieNode child = parent.children[index1]; // Check if having a TrieNode associated with 'cur'
            if (child == null) { // null if 'word' is way too long or its prefix doesn't appear in the Trie
                System.out.println("ERROR DELETING");
                return false;
            }
            int count_non_empty=0;
            for(int j =0;j<95;j++){
                if(parent.children[j]!=null)
                    count_non_empty++;
            }
            if (count_non_empty > 1 || parent.flag) { // Update 'deleteBelow' and 'deleteChar'
                deleteBelow = parent;
                deleteChar = cur;
            }

            parent = child;
        }
        if (!parent.flag) { // word isn't in trie
            System.out.println("ERROR DELETING");
            return false;
        }
        int count_empty=0;
        for(int i =0;i<95;i++){
            if(parent.children[i]==null)
                count_empty++;
        }
        if (count_empty==95) {
            int index = find_Index(deleteChar);
            //System.out.println("Starting of delete is "+ deleteChar);
            assert deleteBelow != null;
            deleteBelow.children[index]= null;
        } else {
            parent.flag = false; // Delete word by mark it as not the end of a word
        }
        System.out.println("DELETED");
        return true;

    }

    @Override
    public TrieNode search(String word) {
        TrieNode check_node = startsWith(word);
        if(check_node==null){
            return null;
        }else{
            if(check_node.flag)
                return check_node;
        }

        return null;
    }

    @Override
    public TrieNode startsWith(String prefix) {
        TrieNode check_Node = root;
        for(int i=0; i<prefix.length(); i++){
            char c= prefix.charAt(i);
            int index = find_Index(c);
            if(check_Node.children[index]!=null){
                check_Node = check_Node.children[index];
            }else{
                return null;
            }
        }

        if(check_Node==root)
            return null;

        return check_Node;
    }


    @Override
    public void printTrie(TrieNode trieNode) {
        if(trieNode.flag){
            Person person = (Person) trieNode.getValue();
            String line= "[Name: " + person.getName()+", Phone="+person.getPhone_number()+"]";
            System.out.println(line);
        }
        for(int i=0;i<95;i++){
            if(trieNode.children[i]!=null){
                printTrie(trieNode.children[i]);
            }
        }
    }

    public int find_Depth(TrieNode root){
        int current_level = 0;
        Queue<TrieNode> queue = new LinkedList<>() ;
        queue.add(root);
        queue.add(null);
        while(!queue.isEmpty()) {
            TrieNode<T> t = queue.remove();
            if (t == null && !queue.isEmpty()) {
                queue.add(null);
                current_level++;
            }
            else if(t!=null){
                for( int i = 0; i< 95; i++){
                    if (t.children[i] != null)
                        queue.add(t.children[i]);
                }
            }
        }
        return current_level;
    }


    @Override
    public void printLevel(int level) {
        int current_level = 0;
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);
        while (!queue.isEmpty()) {
            TrieNode<T> t = queue.remove();
            if (t == null && !queue.isEmpty()) {
                queue.add(null);
                current_level += 1;
                if (current_level == level)
                    break;
            } else if (t != null) {
                for (int i = 0; i < 95; i++) {
                    if (t.children[i] != null)
                        queue.add(t.children[i]);
                }
            }
        }
        String line = "Level " + level + ":";
        if (level == 1 + find_Depth(this.root))
            System.out.println(line);
        else {
            line+=" ";
            int s = queue.size();
            char [] array = new char[s-1];
            for (int i = 0; i < s - 1; i++) {
            TrieNode t = queue.remove();
            if (t != null) {
                    array[i]=t.ch;
                }
            }
            for (int j = 1; j < s-1; j++) {
                for (int k = 0; k < s-1-j; k++) {
                    if (array[k] >= array[k+1]) {
                        char temp = array[k];
                        array[k] = array[k+1];
                        array[k+1] = temp;
                    }
                }
            }
            for(int i=0;i<s-1;i++){
                if(array[i]!=' '){
                if(i!=s-2)
                    line+=array[i]+",";
                else line+=array[i];
                }
            }
            System.out.println(line);
        }
    }

    @Override
    public void print() {
        System.out.println("-------------");
        System.out.println("Printing Trie");
        int levels = find_Depth(this.root);
        for(int i=1;i<=levels+1;i++){
            printLevel(i);
        }
        System.out.println("-------------");
    }

    }
