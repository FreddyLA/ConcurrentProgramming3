//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class SafeBarrier extends Barrier {

    int arrived = 0;
    int passed = 0;
    boolean active = false;
    boolean[] hasPassed = new boolean[9];
    
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

        while (arrived < 9) {
            wait();
        }

        if(passed == 0){
            notifyAll();
        }

        hasPassed[no] = true;
        passed++;

        if(passed == 9) {
            passed = 0;
            arrived = 0;
        }
    }

    @Override
    public synchronized void on() {
        active = true;
    }

    @Override
    public synchronized void off() {
        active = false;
        arrived = 0;
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
