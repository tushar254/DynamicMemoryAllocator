// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree getRoot(){
        // returns the sentinel node
        BSTree ptr = this;
        while(ptr.parent != null){
            ptr = ptr.parent;
        }
        return ptr;
    }
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree newnode = new BSTree(address,size,key);
        BSTree ptr = this.getRoot();
        if(ptr.right == null){
            // no node has been added to the sentinel as of now
            ptr.right = newnode;
            newnode.parent = ptr;
            return newnode;
        }
        ptr = ptr.right;            // now we start from actual root
        while(ptr != null){
            if(ptr.key < key){
                if(ptr.right != null)
                    ptr = ptr.right;
                else{ 
                    ptr.right = newnode;
                    newnode.parent = ptr;
                    break;
                }
            }
            else if(ptr.key > key){
                if(ptr.left != null)
                    ptr = ptr.left;
                else{ 
                    ptr.left = newnode;
                    newnode.parent = ptr;
                    break;
                }
            }
            // if key is same then check for address
            else{
                if(ptr.address < address){
                	if(ptr.right != null)
                    	ptr = ptr.right;
                	else{ 
                    	ptr.right = newnode;
                    	newnode.parent = ptr;
                    	break;
                	}
                }
                else{
                	if(ptr.left != null)
                    	ptr = ptr.left;
                	else{ 
                	   	ptr.left = newnode;
                	   	newnode.parent = ptr;
                	   	break;
                	}
                }
            }
        }
        return newnode;
    }

    public boolean Delete(Dictionary e)
    { 
        boolean found = false;
        if(e == null)
        	return false;
        BSTree ptr = this.getRoot();
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
            return true;
        }
        if(ptr.right != null && ptr.left == null){
            ptr.right.parent = ptr.parent;
            if(ptr.parent.left == ptr)  
                ptr.parent.left = ptr.right;
            else ptr.parent.right = ptr.right;
            return true;
        }
        if(ptr.left == null && ptr.right == null){		
        	if(ptr.parent.left == ptr){
        		ptr.parent.left = null;
        	}
        	else{
        		ptr.parent.right = null;
        	}
            return true;
        }
        // ptr has two children
        BSTree nxt = ptr.right;
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
        return true;
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        BSTree ptr = this.getRoot();
        ptr = ptr.right;
        if(ptr == null)         // empty tree 
            return null;
        if(exact){
            while(ptr != null){
                if(ptr.key < key)
                    ptr = ptr.right;
                else if(ptr.key > key)
                    ptr = ptr.left;
                else return ptr;
            }
            return null;
        }
        else{
            while(ptr != null){
                if(ptr.key < key)
                    ptr = ptr.right;
                else{ 
                	while(ptr.left != null && ptr.left.key >= key){
                		ptr = ptr.left;
                	}
                	return ptr;
                }
            }
            return null;         // if loop ends without returning then ptr = null
        }
    }

    public BSTree getFirst()
    { 
        // return the min node(leftmost)
        BSTree ptr;
        ptr = this.getRoot();   // reached at sentinel node 
        ptr = ptr.right; 
        if(ptr == null)         // empty tree
            return null;
        while(ptr.left != null){
            ptr = ptr.left;
        }
        return ptr;
    }

    public BSTree getNext()
    { 
        //calling from head sentinel, should return null (specified !)
        if(this.parent == null){
            return null;    
        }
        // next is the successor node
        BSTree ptr = this.successor();
        return ptr;
    }

    private BSTree successor(){
        if(this.right != null){
            // successor lies in the right subtree
            BSTree ptr = this.right;
            while(ptr.left != null){
                ptr = ptr.left;
            }
            return ptr;
        }
        // no right subtree !
        else{
            BSTree ptr = this;
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
        BSTree ptr = this;
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
        return true;
    }
    private boolean checkBST(BSTree root,BSTree lf,BSTree rt){
        if(root == null)
            return true;
        if(lf != null && (root.key < lf.key || (root.key == lf.key && root.address < lf.address)))
            return false;
        if(rt != null && (root.key > rt.key || (root.key == rt.key && root.address >= rt.address)))
            return false;
        return checkBST(root.left,lf,root) && checkBST(root.right,rt,root);
    }
    public void disp(){
        BSTree t;
        for(t = this.getFirst();t != null;t = t.getNext()){
            System.out.println("address "+t.address+" size "+t.size+" key "+t.key);
        }
    }
}


 


