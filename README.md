# ProjectManagement-RedBlackTree

### Package PriorityQueue: 
##### Class MaxHeap: 

1. In this class, I have implemented a priority queue which is actually a max heap by using array list. I have created shift up and shift down function which shifts the element in account either up or down so that the max heap gets balanced again.  

2. For shifting up, I have compared the element with its parent and if it has more priority than the parent, then swap the position of the node with parent.  

3. Similar type of implementation for the shift down function except for there the node is compared with its child and if it has less priority than the child then swaps the node and the child. 

4. In the insert function, I have first inserted the new node at the end and then I have shifted it up accordingly. 

5. In the extract max function, I have first extracted the maximum priority element which is the second element of the array list(as the first is always null to match the array index with the position of the element in the heap) by swapping it with the last element of the array list. Then I have shifted the new root to the down at its appropriate place. 

6. The size of the max heap is one lower than the size of the array list. 

##### Class Student: 

1. I have overridden the compareTo() function for it which compares the two functions according to its marks. 
2. I have written the toString() function for converting the student object to a string for its printing. 

### Package Trie: 
##### Class TrieNode: 
1. I have created the constructor which creates the array of size 95 to gather its children. I have also written the character, flag and the value related to the node. 
 
##### Class Trie: 
1. I have implemented the trie using the array of size 95 so that all the ASCII values can be stored in it. 
2. In the insert function which takes a word and an object as arguments, I have first searched for each character of the word and moved down accordingly, but when I got nothing then  I have placed the character in their respected indices of the array level by level and at last, I have made the flag the last node true. 
3. In the delete function, I have first searched the word character by character and if it is found, then I have checked its last character flag value. If the flag value is true, then only true is returned else false. 
4. Now if the word is actually found it the trie, then it was also iterating two variables, namely delete_below and delete_char. I shift them down only when the flag is true or the children of the node are more than one because we have to delete after those nodes all the nodes that are the only child belonging to the given characters of the word. We will delete all the levels (related to the given node) of the children nodes after the ‘delete’ node. 
5. In the search function, I simply search level by level. 
6. In the startsWith function, I simply search each character and if all are found then return the node related to the last character. 
