//Dummy implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

public class Barrier {
    
    CarDisplayI cd;
    
    protected Barrier() { }

    protected Barrier(CarDisplayI cd) { 
        this.cd = cd;
    }
       
    public static Barrier create(CarDisplayI cd) {
        return new DynamicBarrier(cd);
    }

    public void sync(int no) throws InterruptedException { 
        
    };
    
    public void on() { 
        cd.println("Barrier On not implemented in this version");
    };
    
    public void off() { 
        cd.println("Barrier Off not implemented in this version");
    };
    
    public void set(int k) {
        cd.println("Barrier threshold setting not implemented in this version");
        // This sleep is solely for illustrating how blocking affects the GUI
        try { Thread.sleep(3000); } catch (InterruptedException e) { }
    }
    
}
