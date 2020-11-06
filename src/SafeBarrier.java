//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class SafeBarrier extends Barrier {

    int arrived = 0;
    int passed = 0;
    boolean active = false;
    boolean[] hasPassed = new boolean[9];
    boolean barrierReleased;
    
    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {

        if (!active) return;

        if(hasPassed[no]) {
            while (passed > 0) {
                wait();
            }
            hasPassed[no] = false;
        }

        arrived++;

        while (arrived < 9 && !barrierReleased) {
            wait();
        }

        if(!barrierReleased){
            barrierReleased = true;
            notifyAll();
        }

        hasPassed[no] = true;
        arrived--;

        if(arrived == 0) {
            barrierReleased = false;
            notifyAll();
        }
    }

    @Override
    public synchronized void on() {
        active = true;
        barrierReleased = false;
        arrived = 0;
    }

    @Override
    public synchronized void off() {
        active = false;
        barrierReleased = true;
        notifyAll();
    }


    @Override
    // May be (ab)used for robustness testing
    public void set(int k) {
        synchronized (this){
            notifyAll();
        }
    }
}
