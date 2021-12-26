package cp3.ass02.graphshortestpath;

public class GraphProfiler {

    // Time
    private long start;
    private long elapsed;
    private long[] avg;
    private int avgCount;

    public static long nsToms(long nano) {
        return nano/1000000;
    }

    public void tic(){
        this.start = System.nanoTime();
    }

    public long toc(){
        elapsed = System.nanoTime() - this.start;
        return elapsed;
    }

    public void avgReset(int n){
        avg = new long[n];
        avgCount = 0;
    }

    public void avgTic(){
        this.start = System.nanoTime();
    }

    public long avgToc(){
        elapsed = System.nanoTime() - this.start;
        avg[avgCount++] = elapsed;
        return elapsed;
    }

    public long avgCalc(){
        long avgTotal = 0;
        for(int i = 0; i < avgCount; i++){
            avgTotal += avg[i];
        }
        return avgTotal / avgCount;
    }


    // Memory
    private long ticMemory;
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes){
        return bytes / MEGABYTE;
    }

    public void ticMem(){
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();

        //Calculate the used memory
        ticMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    public long tocMem(){
        Runtime runtime = Runtime.getRuntime();

        //Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        return memory - ticMemory;
    }

    public void takeOutGarbage() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
    }

}
