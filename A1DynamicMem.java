// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if(blockSize < 1)
            return -1;
        Dictionary i = this.freeBlk.Find(blockSize,false);
        if(i != null){
            this.freeBlk.Delete(i);
            this.allocBlk.Insert(i.address,blockSize,i.address);
            if(i.size > blockSize){
                this.freeBlk.Insert(i.address+blockSize,i.size-blockSize,i.size-blockSize);
            }
            //System.out.println("AllocBlock after alloc");
            //this.allocBlk.disp();
            //System.out.println("FreeBlock after alloc");
            //this.freeBlk.disp();
            return i.address;
        }
        return -1;
    } 
    public int Free(int startAddr){
        Dictionary i = this.allocBlk.Find(startAddr,true);
        if(i != null){
            this.freeBlk.Insert(i.address,i.size,i.size);
            this.allocBlk.Delete(i);
            //System.out.println("AllocBlock after free");
            //this.allocBlk.disp();
            //System.out.println("FreeBlock after free");
            //this.freeBlk.disp();
            return 0;
        }
        return -1;
    }
}