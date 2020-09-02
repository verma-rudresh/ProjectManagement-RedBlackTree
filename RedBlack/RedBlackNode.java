package RedBlack;

import Util.RBNodeInterface;

import java.util.ArrayList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

    public T key;
    public E value;
    public boolean color;
//    int size;
    List<E> value_list =new ArrayList<>();
    RedBlackNode<T,E> left,right,parent;
//    RedBlackNode<T,E> nullNode = new RedBlackNode<>(null,null,false);
    RedBlackNode(T key,E value, boolean color ){
        //this.size= size;
        this.value = value;
        this.color = color;
        this.key = key;
        this.left = null;
        this.right = null;
    }
    @Override
    public E getValue() {
        return value;
    }

    @Override
    public List<E> getValues() {
        return value_list;
    }
    int compareTo(T key2){
        String s1= (String) key;
        String s2= (String) key2;
        if(s1.compareTo(s2)>0)
            return 1;
        else if(s1.compareTo(s2)<0)
            return -1;
        else return 0;
    }

}
