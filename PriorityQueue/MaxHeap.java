package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
    ArrayList<T> max_priority_queue ;
    public MaxHeap(){
        max_priority_queue= new ArrayList<>();
    }

    public void shiftUp(int index){
        int parent_index=0;
        if(index!=1){
            parent_index = index/2;
            T check_node  = max_priority_queue.get(index);
            T parent_node = max_priority_queue.get(parent_index);
            if(check_node.compareTo(parent_node)>0){
                max_priority_queue.set(index,parent_node);
                max_priority_queue.set(parent_index,check_node);
                shiftUp(parent_index);
            }
        }
    }
    public void shiftDown(int index){
        if(max_priority_queue.size()==3){
            T first= max_priority_queue.get(1);
            T second= max_priority_queue.get(2);
            if(first.compareTo(second)<0){
                max_priority_queue.set(1,second);
                max_priority_queue.set(2,first);
            }
        }
        if(2*index+1<max_priority_queue.size()){
            int rightChildIndex = 2*index+1;
            int leftChildIndex = 2*index;
            T check_nodeValue = max_priority_queue.get(index);
            T rightChildValue = max_priority_queue.get(rightChildIndex);
            T leftChildValue = max_priority_queue.get(leftChildIndex);
            if(check_nodeValue.compareTo(rightChildValue)<0 || check_nodeValue.compareTo(leftChildValue)<0){
                if(leftChildValue.compareTo(rightChildValue)<0){
                    max_priority_queue.set(rightChildIndex,check_nodeValue);
                    max_priority_queue.set(index,rightChildValue);
                    shiftDown(rightChildIndex);
                }
                else{
                    max_priority_queue.set(leftChildIndex,check_nodeValue);
                    max_priority_queue.set(index,leftChildValue);
                    shiftDown(leftChildIndex);
                }
            }
        }
    }
    @Override
    public void insert(T element) {
        if(max_priority_queue.isEmpty()){
            max_priority_queue.add(null);
            max_priority_queue.add(element);
        }
        else{
            max_priority_queue.add(element);
            shiftUp(max_priority_queue.size()-1);
        }
    }

    @Override
    public T extractMax() {
        if(2>max_priority_queue.size())
            return null;
        else
        {
            T max_ele = max_priority_queue.get(1);
            T last_ele =max_priority_queue.get(max_priority_queue.size()-1);
            //System.out.println("last node is "+last_ele.toString());
            max_priority_queue.set(1,last_ele);
            last_ele = max_priority_queue.remove(max_priority_queue.size()-1);
            if(max_priority_queue.size()>2)
            shiftDown(1);
            return max_ele;
        }
    }
    public int max_heap_size(){
        return max_priority_queue.size()-1;
    }

}