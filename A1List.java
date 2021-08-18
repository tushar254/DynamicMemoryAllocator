// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List{

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node

    public A1List(int address, int size, int key){ 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
            
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    { 
        // general DLL insert will happen at some “current” node
        A1List newnode = new A1List(address,size,key);
        newnode.next = this.next;
        newnode.prev = this;
        this.next.prev = newnode;
        this.next = newnode;
        return newnode;
    }

    public boolean Delete(Dictionary d) 
    {
        if(d == null) return false;
        A1List ptr;
        boolean found = false;
        // head and tail sentinel aren't included in traversal
        // so they won't get deleted even if d has (-1,-1,-1)
        for(ptr = this.getFirst();ptr != null;ptr = ptr.getNext()){
            if(ptr.key == d.key && ptr.address == d.address && ptr.size == d.size){
                found = true;
                break;
            }    
        }
        if(found){
            ptr.prev.next = ptr.next;
            ptr.next.prev = ptr.prev;
        	ptr.next = ptr.prev = null;
            return true;
        }
        return false;
    }
    public A1List Find(int k, boolean exact)
    { 
        // it is mentioned that find should start searching from first node!
        A1List ptr;
        if(exact){
            for(ptr = this.getFirst();ptr != null;ptr = ptr.getNext()){
                if(ptr.key == k){
                    return ptr;
                }    
            }
            return null;
        }
        else{
            for(ptr = this.getFirst();ptr != null;ptr = ptr.getNext()){
                if(ptr.key >= k){
                    return ptr;
                }    
            }
            return null;
        }        
    }

    public A1List getFirst()
    {
    	A1List ptr = this;
    	// function called on head
        if(ptr.prev == null){
        	// no node case
            if(ptr.next.next == null)
                return null;
            // if there's a node next to head
            return ptr.next;
        }
        // no node case, function called on tail sentinel
        if(ptr.next == null && ptr.prev.prev == null){
        	return null;
        }
        // function called on any inside node, move back and get to first 
        while(ptr.prev.prev != null){
            ptr = ptr.prev;
        }
        return ptr;
    }

    public A1List getNext() 
    {
    	// if it is the last node we'll return null and not the tail sentinel
    	if(this.next.next == null) 
            return null;
        return this.next;
    }

    public boolean sanity()
    {	
        if(this.cycle())                   // check for a loop in DLL
            return false;
        A1List ptr = this;
        while(ptr.next != null){
    	   if(ptr.next.prev != ptr)      
    	       return false;
            ptr = ptr.next;
        } 
        // at this point ptr.next == null that should be tail sentinel 
        if(ptr.key != -1 || ptr.address != -1 || ptr.size != -1)
            return false;
        ptr = this;  
        while(ptr.prev != null){             
            if(ptr.prev.next != ptr)
                return false;
            ptr = ptr.prev;
        }
        // at this point ptr.prev == null that should be head sentinel
        if(ptr.key != -1 || ptr.address != -1 || ptr.size != -1)
            return false;
        return true;
    }
    private boolean cycle(){
        // fast slow pointer method, returns true if cycle is present 
        // we start from the node calling
    	A1List f = this;
    	A1List s = f;
    	while(f != null && s != null && s.next != null){
            s = s.next;
            f = f.next.next;
            if(f == s) return true;
        }
        return false;
    }   
    public void disp(){
        return;
    }
}


