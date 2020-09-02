package RedBlack;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {

    private static final boolean RED   = true;
    public static final boolean BLACK = false;
    public RedBlackNode<T,E> root;
    public RBTree(){
        this.root = null;
        //root.parent =null;
    }
    public boolean isBlack(RedBlackNode<T,E> node){
        return node == null || node.color == BLACK;
    }
    public RedBlackNode<T,E> grand(RedBlackNode<T,E> node){
        RedBlackNode<T,E> parent = node.parent;
        if(parent!=null){
            parent=parent.parent;
        }
        return parent;
    }

    public RedBlackNode<T,E> uncle(RedBlackNode<T,E> req_node){
        RedBlackNode<T,E> parent = req_node.parent;
        if(parent!=null)
        {
            RedBlackNode<T,E> grandParent = req_node.parent.parent;
            if(grandParent!=null){
                if(grandParent.left==parent)
                    return grandParent.right;
                else
                    return grandParent.left;
            }
        }
        return null;
    }
    public RedBlackNode<T,E> great_grand(RedBlackNode<T,E> node){
        RedBlackNode<T,E> parent = node.parent;
        if(parent!=null)
        {
            parent = parent.parent;
            if(parent!=null){
                parent=parent.parent;
            }
        }
        return parent;
    }

    public RedBlackNode<T,E> re_Coloring(RedBlackNode<T,E> child){
        if(child==this.root)
        {
            child.color=BLACK;
            return null;
        }
        else if(root.left==child || root.right==child){
            return null ;
        }
        else if(!isBlack(uncle(child))  && !isBlack(child.parent))
        {
            RedBlackNode<T,E> parent = child.parent;
            RedBlackNode<T,E> grandParent = parent.parent;
            grandParent.color = !grandParent.color;
            grandParent.left.color = !grandParent.left.color;
            grandParent.right.color = !grandParent.right.color;
            return re_Coloring(grandParent);
        }
        else if(isBlack(uncle(child)) && !isBlack(child.parent))
            return child;
        return null;
    }


    public RedBlackNode<T,E> re_Structuring(RedBlackNode<T,E> x){
        RedBlackNode<T,E> y = x.parent;
        RedBlackNode<T,E> z = y.parent;
        if(y.left==x && z.left==y){
            RedBlackNode<T,E> t=rotateRight(z);
            t.left=y.left;
            t.color= BLACK;
            t.left.color= RED;
            t.right.color= RED;
            z.parent=t;
            return t;
        }
        else if(y.right==x && z.right==y){
            y=rotateLeft(z);
            y.color= BLACK;
            y.left.color= RED;
            y.right.color= RED;
            return y;
        }
        else if(z.left==y && y.right==x){
            x =rotateLeft(y);
            x.parent=z;
            z.left=x;
            y.parent=x;
            RedBlackNode<T,E> t =rotateRight(z);
            t.left.color= RED;
            t.right.color= RED;
            t.color= BLACK;
            return t;
        }
        else {
            x= rotateRight(y);
            if(x!=null){
                z.right=x;
                x.parent=z;
            }
            RedBlackNode<T,E> t =rotateLeft(z);
            t.left.color= RED;
            t.right.color= RED;
            t.color= BLACK;
            return t;
        }
    }


    public RedBlackNode<T,E> rotateRight(RedBlackNode<T,E> Y){
        RedBlackNode<T,E> X = Y.left;
        if(X!=null){
            RedBlackNode<T,E> T2 = X.right;
            // Perform rotation
            X.right = Y;
            Y.parent=X;
            Y.left = T2;
            if(T2!=null)
            T2.parent=Y;
            return X;
        }
        return null;
    }
    public RedBlackNode<T,E> rotateLeft(RedBlackNode<T,E> x){
        RedBlackNode<T,E> g = x.right;
        RedBlackNode<T,E> T2 = g.left;
        // Perform rotation
        g.left = x;
        x.parent=g;
        x.right = T2;
        if(T2!=null)
        T2.parent=x;
        return g;
    }

    @Override
    public void insert(T key, E value) {
        if(key == null){
            System.out.println("first argument to put() is null");
        }
        if(root == null)
        {
            RedBlackNode<T,E> newNode = new RedBlackNode<>(key, value, BLACK);
            newNode.value_list.add(value);
//            System.out.println("the color of root node "+newNode.key+" is "+newNode.color);
            root = newNode;
            root.parent=null;
            return ;
        }
        boolean isLeftChild = false;
        RedBlackNode<T,E> check_node = root;
        RedBlackNode<T,E> parent = root;
        while(check_node!=null){
            if(key.compareTo(check_node.key)>0){
                parent = check_node;
                check_node=check_node.right;
                isLeftChild=false;
            }
            else if(key.compareTo(check_node.key)<0){
                parent=check_node;
                check_node=check_node.left;
                isLeftChild=true;
            }
            else{
                check_node.value_list.add(value);
                //System.out.println("value inserted corresponding to "+key+" is "+ value);
                break;
            }
        }
        if(check_node==null){
            RedBlackNode<T,E> new_node = new RedBlackNode<>(key,value,RED);
            new_node.value_list.add(value);
//            System.out.println("value inserted corresponding to "+key+" is "+ value+" with initial color RED");
            if(isLeftChild){
                parent.left=new_node;
                new_node.parent=parent;
//                System.out.println("new node is attached at left");
            }
            else {
                parent.right=new_node;
                new_node.parent=parent;
//                System.out.println("new node is attached at right");
            }
            if(new_node.parent.color==RED){
//                System.out.println("Restructuring is required");
            }
            if(!isBlack(uncle(new_node))){
//                    System.out.println("The color of its uncle is RED");
//                    System.out.println("Recoloring is required");
                    RedBlackNode<T,E> stop_node = re_Coloring(new_node);
                    if(stop_node!=null){
//                        System.out.println("Its stop-node-"+stop_node.value+" color-"+stop_node.color);
//                        System.out.println("Stop-node's uncle's color-"+false);
                        RedBlackNode<T,E> grandParent= stop_node.parent.parent;
                        if(grandParent!=root){
                            RedBlackNode<T,E> great_grand = grandParent.parent;
//                            System.out.println("Stop-node's Great-grand -"+great_grand.value);
                                if(great_grand.left==grandParent){
                                    great_grand.left=re_Structuring(stop_node);
//                                    System.out.println("Great-grand.left value-"+great_grand.left.value);
                                }
                                else if(great_grand.right==grandParent){
                                    great_grand.right=re_Structuring(stop_node);
//                                    System.out.println("Great-grand.left value-"+great_grand.left.value);
                                }

                        }
                        else{
                            root=re_Structuring(stop_node);
                            root.color=BLACK;
                            root.parent=null;
                        }

                    }
                    else{
//                        System.out.println("Only recoloring is required");
                    }
                }
            else if(grand(new_node)!=null){
                    RedBlackNode<T,E> grandParent= grand(new_node);
//                    System.out.println("new node's grand parent-"+grandParent.value);
                    if(grandParent!=root){
                        RedBlackNode<T,E> great_grand = great_grand(new_node);
//                        System.out.println("new node's great-grand parent-"+great_grand.value);
                        if(great_grand.left==grandParent){
                            great_grand.left=re_Structuring(new_node);
                            great_grand.left.parent=great_grand;
                        }
                        else if(great_grand.right==grandParent){
                            great_grand.right=re_Structuring(new_node);
                            great_grand.right.parent=great_grand;
                        }
                    }
                    else{
//                        System.out.println("Grand parent is root");
                        root=re_Structuring(new_node);
                        root.color=BLACK;
                        root.parent=null;
//                        System.out.println("The new root-"+root.value);
                    }
            }
        }

    }


    @Override
    public RedBlackNode<T, E> search(T key) {
        if(root==null)
        return null;
        RedBlackNode<T,E> check_node = root;
        while(check_node!=null){
            if(key.compareTo(check_node.key)>0)
                check_node=check_node.right;
            else if(key.compareTo(check_node.key)<0)
                check_node=check_node.left;
            else return check_node;
        }
        return null;
    }
    /*public void display(RedBlackNode<T,E> node){
        if(node==null)
            return;
        RedBlackNode<T,E> parent= node.parent;
        if(parent!=null)
            System.out.println("value-" + node.value+" and color-"+ node.color+" and its parent-"+node.parent.value);
        else
            System.out.println("value-" + node.value+" and color-"+ node.color+" and its parent-null");
        display(node.left);
        display(node.right);
    }*/
}