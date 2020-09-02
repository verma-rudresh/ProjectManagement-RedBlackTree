package Trie;

import Util.NodeInterface;

public class TrieNode<T> implements NodeInterface<T> {
    TrieNode<T> [] children;
    T value;
    char ch;
    boolean flag =false;
    public TrieNode() {
        this.children = new TrieNode[95];
    }




    public T getValue() {
        return  value;
    }
}