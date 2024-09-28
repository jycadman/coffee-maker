public class Timer {
    private long timeStart = 0;
    private long timeSet = 0;

    public Timer(){
        timeSet = 0;
        timeStart = System.currentTimeMillis();
    }

    // Resets the timer.
    public void reset(){
        timeStart = System.currentTimeMillis();
    }

    // Sets the time in milliseconds until the timer timeouts.
    public void set(long t){
        timeSet = t;
    }

    // Returns true if timeSet milliseconds of time has past.
    public boolean timeout(){
        return (timeStart + timeSet) < System.currentTimeMillis();
    }


    /*
    Example: Timer to check if four seconds have passed.

    void somefunction(){
        Timer mytimer = new Timer();
        mytimer.set(4000);

        long start = System.currentTimeMillis();
        mytimer.reset();
        while(!mytimer.timeout()){
            System.out.println("notDone");
        }

        long total = (System.currentTimeMillis() - start)/1000;
        System.out.println("done " + total);
    }
     */
}
