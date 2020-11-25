//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 30, 2020

class DynamicBarrier extends Barrier {

    int arrived;
    int threshold = 9;

    boolean active;
    boolean newThresholdPending;
    boolean barrierReleased;
    boolean oldThresholdReleased;

    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {

        if (!active) return;

        while(barrierReleased || (newThresholdPending && oldThresholdReleased)){
            wait();
        }

        arrived++;

        while (arrived < threshold && !barrierReleased) {
            wait();
        }

        if(!barrierReleased){
            barrierReleased = true;
            oldThresholdReleased = true;
            notifyAll();
        }

        arrived--;

        if(arrived == 0) {
            barrierReleased = false;
            notifyAll();
        }
    }

    @Override
    public synchronized void on() {
        if(!active) {
            active = true;
            barrierReleased = false;
            oldThresholdReleased = false;
        }
    }

    @Override
    public synchronized void off() {
        if(active) {
            active = false;
            barrierReleased = true;
            oldThresholdReleased = true;
            notifyAll();
        }
    }

    @Override
    /* Set barrier threshold */
    public synchronized void set(int k) {
        try {
            if(k > threshold && arrived > 0){
                newThresholdPending = true;
                oldThresholdReleased = false;
                while (!oldThresholdReleased) {
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
