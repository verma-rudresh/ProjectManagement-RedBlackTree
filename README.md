# ProjectManagement-RedBlackTree

### Package PriorityQueue: 
#### Class MaxHeap: 
1. In this class, I have implemented a priority queue which is actually a max heap by using array list. I have created shift up and shift down function which shifts the element in account either up or down so that the max heap gets balanced again.  

2. For shifting up, I have compared the element with its parent and if it has more priority than the parent, then swap the position of the node with parent.  

3. Similar type of implementation for the shift down function except for there the node is compared with its child and if it has less priority than the child then swaps the node and the child. 

4. In the insert function, I have first inserted the new node at the end and then I have shifted it up accordingly. 

5. In the extract max function, I have first extracted the maximum priority element which is the second element of the array list(as the first is always null to match the array index with the position of the element in the heap) by swapping it with the last element of the array list. Then I have shifted the new root to the down at its appropriate place. 

6. The size of the max heap is one lower than the size of the array list. 

#### Class Student: 
1. I have overridden the compareTo() function for it which compares the two functions according to its marks.

2. I have written the toString() function for converting the student object to a string for its printing. 

### Package Trie: 
#### Class TrieNode: 
1. I have created the constructor which creates the array of size 95 to gather its children. I have also written the character, flag and the value related to the node. 
 
#### Class Trie: 
1. I have implemented the trie using the array of size 95 so that all the ASCII values can be stored in it.

2. In the insert function which takes a word and an object as arguments, I have first searched for each character of the word and moved down accordingly, but when I got nothing then  I have placed the character in their respected indices of the array level by level and at last, I have made the flag the last node true. 

3. In the delete function, I have first searched the word character by character and if it is found, then I have checked its last character flag value. If the flag value is true, then only true is returned else false. 

4. Now if the word is actually found it the trie, then it was also iterating two variables, namely delete_below and delete_char. I shift them down only when the flag is true or the children of the node are more than one because we have to delete after those nodes all the nodes that are the only child belonging to the given characters of the word. We will delete all the levels (related to the given node) of the children nodes after the ‘delete’ node.

5. In the search function, I simply search level by level. 

6. In the startsWith function, I simply search each character and if all are found then return the node related to the last character. 

7. In the print trie function, we are printing it level by level by using the queue. I have first inserted the root and then null. After that I have inserted the null. After that I popped the root and inserted all its children. This is done in the loop. One element is popped at a time and its children are inserted. When we have popped completely a particular level, then we will again insert the null and the level count is increased by one. We will do so up to the given level and at that time, the queue contains the element of the given level only. So we can easily print them. 

8. To print the complete trie, I have found its depth by using the queue and then printed all the levels of the trie one by one. 

### Package RedBlack: 
#### Class RedBlackNode: 
1. The RedBlack node contains key, value, color, left node, right node, and parent node. It also contains a list storing the values related to that key. 

#### Class RBTree: 
1. I have written the function which takes the node as argument and returns the child of its grandparent which is not the parent of the given node. 

2. The function grandparent returns the parent of the parent and the function parent returns the parent of the given node. 

3. In the recoloring function, I have recolored the parent, uncle, and grandparent and applied a recursion on the grandparent, this way my function has colored the tree up to the required level and then it returns the node above which recoloring is not possible. 

4. In the restructure function, I have used the node returned by the reColoring function and the restructured the trie nodes formed by that node, its parent and the grandparent. 

5. There are four cases for the recoloring namely left_left, left_right, right_left and right_right. For each case, I have returned the structured root of the trie node tree by using the leftRotate and rightRotate function. 

6. The left rotate function rotates the two_node system from left to right. This changes the orientation of the parent and the child. Same for the right rotate function. 

7. In the insert function, I have first simply inserted like the BST and then recolored and restructured the tree accordingly. 

8. The search function is same as that for the binary search tree. 

### Package ProjectManagement: 
#### Class Job: 
1. In this, I have written the user name, project name, runtime, priority, job name, and the count. All these variables are related to the job type object. Two jobs are compared by their priority first and then their count(if the priority is same). 

#### Class Project: 
1. It contains budget and the priority of a project only. 

#### Class User: 
1. It contains the name of the user only. 

#### Class Schedular_Driver: 
1. To implement this class smoothly, I have created a Trie for storing users, RBTree for projects,  MaxHeap for jobs, Array list for finished jobs, a queue for the finished jobs, array list for the total jobs and finally another array list for the jobs which have been checked. 

2. In the handle_project function, I have created a project type object. 

3. In the handle_job function, I have created a job only when the given project name or the user name related to the job is valid. 

4. In the handle_user and handle_query, I have created the given user and solved the query. 

5. For solving the given Query, I have checked the job first in the completed jobs list. If found, then printed found, otherwise I searched it in the 
total_jobs_list, if found there only, then returned not finished. If not present in both lists, then printed no such jobs. 

6. In the handle_add function, I have added the increment to the given project and then shifted all its jobs from the checked list to the max heap so that they can be executed again. 

7. In the schedule() class, I have first extracted the job from the max heap and then checked whether it has less or more run time than its project’s budget. If it is less, then execute it and shift the job to the completed job list. But if it is more, then shift it to the checked jobs list and then extract another job from the list. Repeat this process until the elements are left in the max heap or a satisfying node having run time lower than the budget is found. 

8. In the run to completion, we will execute the schedule() class until the size of the max heap is greater than zero. 

9. For printing the stats, I have popped the element from the completed jobs queue and then taken all its data and printed the combination it the given way. Then taken the array list of the checked jobs which are actually unfinished and printed their details also one by one in the order. 

10. Finally, I have printed some lines to match the format of the output. 
