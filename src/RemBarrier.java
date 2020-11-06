//Implementation of a basic Barrier class supporting car removal (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class RemBarrier extends Barrier {
    
    public RemBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
    }

    @Override
    public void on() {
    }

    @Override
    public void off() {
    }

    /* Add further methods as needed */
   
    
}
