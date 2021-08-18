// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    private AVLTree getRoot(){
        // returns the sentinel node
        AVLTree ptr = this;
        while(ptr.parent != null){
            ptr = ptr.parent;
        }
        return ptr;
    }
    private int calc(AVLTree node){
        if(node == null)
            return -1;
        int l = calc(node.left);
        int r = calc(node.right);
        if(l > r) return l+1;
        else return r+1;
    }
    private int H(AVLTree node){
        if(node == null)
            return -1;
        else return node.height;
    }
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree newnode = new AVLTree(address,size,key);
        AVLTree ptr = this.getRoot();
        if(ptr.right == null){
            // no node has been added to the sentinel as of now
            ptr.right = newnode;
            newnode.parent = ptr;
            return newnode;
        }
        ptr = ptr.right;            // now we start from actual root
        AVLTree anc = ptr;
        while(ptr != null){
            anc = ptr;
            if(ptr.key < key)
                ptr = ptr.right;
            else if(ptr.key > key)
                ptr = ptr.left;
            // if key is same then check for address
            else{
                if(ptr.address < address) 
                    ptr = ptr.right;
                else ptr = ptr.left;
            }
        }
        if(key <= anc.key){
            anc.left = newnode;
            newnode.parent = anc;
        }
        else{
            anc.right = newnode;
            newnode.parent = anc;
        }
        anc.checkBalance();
        anc.update();
        return newnode;
    }
    private void update(){
        // update heights of the ancestors of 'this'
        AVLTree ptr = this;
        while(ptr.parent != null){
            ptr.height = 1+Math.max(H(ptr.left),H(ptr.right));
            ptr = ptr.parent;
        }
    }

    private void checkBalance(){
        // check balance factors while traversing ancestor nodes
        this.update();
        AVLTree ptr = this;
        int bf = 0;
        while(ptr.parent != null){
            bf = H(ptr.left)-H(ptr.right);
            if(bf < -1 || bf > 1) break;
            ptr = ptr.parent;
        }
        if(bf > 1){
            // left heavy !
            if(H(ptr.left.left) >= H(ptr.left.right)){
                ptr = r_rotate(ptr);
            }
            else{
                ptr.left = l_rotate(ptr.left);
                ptr = r_rotate(ptr);
            }
        }
        else if(bf < -1){
            // right heavy !
            if(H(ptr.right.right) >= H(ptr.right.left)){
                ptr = l_rotate(ptr);
            }
            else{
                ptr.right = r_rotate(ptr.right);
                ptr = l_rotate(ptr);
            }
        }
        else{
            ptr.height = calc(ptr);
        }
    }

    private AVLTree l_rotate(AVLTree node){
        AVLTree rc = node.right;
        rc.parent = node.parent;
        if(node.parent.left == node)
            node.parent.left = rc;
        else node.parent.right = rc;
        node.right = rc.left; 
        if(rc.left != null)
            rc.left.parent = node;
        rc.left =  node;
        node.parent = rc;
        rc.height = calc(rc);
        node.height = calc(node);
        return rc;
    }

    private AVLTree r_rotate(AVLTree node){
        AVLTree lc = node.left;
        lc.parent = node.parent;
        if(node.parent.left == node)
            node.parent.left = lc;
        else node.parent.right = lc;
        node.left = lc.right;
        if(lc.right != null)
            lc.right.parent = node;
        lc.right = node;
        node.parent = lc;
        lc.height = calc(lc);
        node.height = calc(node);
        return lc;
    }
    
    public boolean Delete(Dictionary e)
    { 
        boolean found = false;
        if(e == null)
            return false;
        AVLTree ptr = this.getRoot();
        while(ptr != null){
            if(ptr.key < e.key){
                ptr = ptr.right;
            }
            else if(ptr.key > e.key){
                ptr = ptr.left;
            }
            else{
                if(ptr.address == e.address && ptr.size == e.size){
                    found = true;
                    break;
                }
                else if(ptr.address >= e.address){
                    ptr = ptr.left;
                }
                else{
                    ptr = ptr.right;
                }
            }
        }
        if(!found) return false;
        if(ptr.left != null && ptr.right == null){
            ptr.left.parent = ptr.parent;
            if(ptr.parent.left == ptr)  
                ptr.parent.left = ptr.left;
            else ptr.parent.right = ptr.left;
            ptr.parent.checkBalance();
            ptr.parent.update();
            return true;
        }
        if(ptr.right != null && ptr.left == null){
            ptr.right.parent = ptr.parent;
            if(ptr.parent.left == ptr)  
                ptr.parent.left = ptr.right;
            else ptr.parent.right = ptr.right;
            ptr.parent.checkBalance();
            ptr.parent.update();
            return true;
        }
        if(ptr.left == null && ptr.right == null){      
            if(ptr.parent.left == ptr){
                ptr.parent.left = null;
            }
            else{
                ptr.parent.right = null;
            }
            ptr.parent.checkBalance();
            ptr.parent.update();
            return true;
        }
        // ptr has two children
        AVLTree nxt = ptr.right;
        while(nxt.left != null){
            nxt = nxt.left;
        }
        int k = nxt.key,a = nxt.address,s = nxt.size;
        if(nxt.parent.left == nxt){
            nxt.parent.left = nxt.right;
            if(nxt.right != null)
                nxt.right.parent = nxt.parent;
        }
        if(nxt.parent.right == nxt){
            nxt.parent.right = nxt.right;
            if(nxt.right != null)
                nxt.right.parent = nxt.parent;
        }
        ptr.key = k;
        ptr.address = a;
        ptr.size = s;
        ptr.parent.checkBalance();
        ptr.parent.update();
        return true;
    }
        
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree ptr = this.getRoot();
        ptr = ptr.right;
        if(ptr == null)         // empty tree 
            return null;
        if(exact){
            while(ptr != null){
                if(ptr.key < k)
                    ptr = ptr.right;
                else if(ptr.key > k)
                    ptr = ptr.left;
                else return ptr;
            }
            return null;
        }
        else{
            while(ptr != null){
                if(ptr.key < k)
                    ptr = ptr.right;
                else{ 
                    while(ptr.left != null && ptr.left.key >= k){
                        ptr = ptr.left;
                    }
                    return ptr;
                }
            }
            return null;         
        }
    }

    public AVLTree getFirst()
    { 
        // return the min node(leftmost)
        AVLTree ptr;
        ptr = this.getRoot();   // reached at sentinel node 
        ptr = ptr.right; 
        if(ptr == null)         // empty tree
            return null;
        while(ptr.left != null){
            ptr = ptr.left;
        }
        return ptr;
    }

    public AVLTree getNext()
    {
        //calling from head sentinel, should return null (specified !)
        if(this.parent == null){
            return null;    
        }
        // next is the successor node
        AVLTree ptr = this.successor();
        return ptr;
    }

    private AVLTree successor(){
        if(this.right != null){
            // successor lies in the right subtree
            AVLTree ptr = this.right;
            while(ptr.left != null){
                ptr = ptr.left;
            }
            return ptr;
        }
        // no right subtree !
        else{
            AVLTree ptr = this;
            // check if any ancestor of this is left child of its parent 
            while(ptr.parent != null){
                if(ptr.parent.left == ptr){
                    break;
                }
                ptr = ptr.parent;
            }
            return ptr.parent;
        }
    }
    public boolean sanity()
    { 
        AVLTree ptr = this;
        while(ptr.parent != null){
            ptr = ptr.parent;
            if(ptr.parent.left != ptr || ptr.parent.right != ptr)
                return false;
        }
        if(ptr.key != -1 || ptr.address != -1 || ptr.size != -1){
            return false;
        }
        // ptr is now root
        if(!checkBST(ptr,null,null))
            return false;
        if(!checkAVL(ptr))
            return false;
        return true;
    }
    private boolean checkAVL(AVLTree root){
        if(root == null)
            return true;
        int l = H(root.left);
        int r = H(root.right);
        if((l-r < 2 || r-l < 2) && checkAVL(root.left) && checkAVL(root.right))
            return true;
        return false;
    }
    private boolean checkBST(AVLTree root,AVLTree lf,AVLTree rt){
        if(root == null)
            return true;
        if(lf != null && (root.key < lf.key || (root.key == lf.key && root.address < lf.address)))
            return false;
        if(rt != null && (root.key > rt.key || (root.key == rt.key && root.address >= rt.address)))
            return false;
        return checkBST(root.left,lf,root) && checkBST(root.right,rt,root);
    }

    /*public void disp(){
        AVLTree t = this;
        for(t = this.getFirst();t != null;t = t.getNext()){
            System.out.println("key "+t.key+" height "+t.height);
        }
    }
    public static void main(String[] args) {
        AVLTree t = new AVLTree();
        t.Insert(0,10,0);
        t.Insert(10,10,10);
        t.Insert(20,10,20);
        t.Insert(30,10,30);
        t.Insert(40,10,40);
        t.disp();
        t.Delete(t.getFirst());
        t.disp();
    }*/
}


