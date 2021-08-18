// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment(){
        Dictionary root;
        if(this.type == 2){
            root = new BSTree();         // root of new tree
        }
        else{
            root = new AVLTree();
        }
        Dictionary ptr;
        for(ptr = this.freeBlk.getFirst();ptr != null;ptr = ptr.getNext()){
            // making another tree indexed by address !
            root.Insert(ptr.address,ptr.size,ptr.address);
        }
        ptr = root.getFirst();
        boolean in = false;
        int sz = 0,add = 0;
        while(ptr != null){
            in = false;
            sz = ptr.size;
            add = ptr.address;
            while(ptr.getNext() != null && ((ptr.address + ptr.size) == ptr.getNext().address)){
                in = true;
                sz += ptr.getNext().size;
                ptr.key = ptr.size;           // in freeBlk key = size 
                this.freeBlk.Delete(ptr);
                ptr = ptr.getNext();
            }
            if(in){
                if(ptr.getNext() == null){
                    ptr.key = ptr.size;            
                    this.freeBlk.Delete(ptr);
                }
                this.freeBlk.Insert(add,sz,sz);
            }
            ptr = ptr.getNext();
        }
        root = null; // new dictionary is deleted !
        return;
    }
}