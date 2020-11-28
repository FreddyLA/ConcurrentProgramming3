//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class SafeBarrier extends Barrier {

    int arrived;
    boolean active;
    boolean barrierReleased;
    
    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {

        if (!active) return;

        while(barrierReleased){
            wait();
        }

        arrived++;

        while (arrived < 9 && !barrierReleased) {
            wait();
        }

        if(!barrierReleased){
            barrierReleased = true;
            notifyAll();
        }

        arrived--;

        if(arrived == 0 && active) {
            barrierReleased = false;
            notifyAll();
        }
    }

    @Override
    public synchronized void on() {
        if(!active) {
            active = true;
            barrierReleased = false;
        }
    }

    @Override
    public synchronized void off() {
        if(active) {
            active = false;
            barrierReleased = true;
            notifyAll();
        }
    }


    @Override
    // May be (ab)used for robustness testing
    public void set(int k) {
        synchronized (this){
            notifyAll();
        }
    }
}
