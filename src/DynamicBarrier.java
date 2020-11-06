//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class DynamicBarrier extends Barrier {

    int arrived = 0;
    int passed = 0;
    int threshold = 9;
    boolean active = false;
    boolean[] hasPassed = new boolean[9];
    boolean newThresholdPending;
    boolean barrierReleased;
    
    public DynamicBarrier(CarDisplayI cd) {
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

        if(arrived >= threshold){
            while(newThresholdPending){
                wait();
            }
        }

        arrived++;

        while (arrived < threshold && !barrierReleased) {
            wait();
        }

        if(passed == 0){
            barrierReleased = true;
            notifyAll();
        }

        hasPassed[no] = true;
        passed++;

        if(passed >= threshold) {
            arrived = 0;
            passed = 0;
            barrierReleased = false;
            notifyAll();
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
    /* Set barrier threshold */
    public synchronized void set(int k) {

        try {

            if(k > threshold && arrived > 0){
                newThresholdPending = true;
                while (arrived > 0) {
                    wait();
                }
                newThresholdPending = false;
            }
            threshold = k;
            notifyAll();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
