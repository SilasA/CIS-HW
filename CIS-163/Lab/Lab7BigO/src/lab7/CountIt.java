package lab7;

public class CountIt {
    public int linearLoop(int N) {
        int x = 0;
        int count = 0;
        x++;
        while (count < N) {
            x++;
            System.out.println(".");
            x++;
            count++;
        }
        return x;
    }

    public long SnippetNestedLoop(long n) {
        long i, j, x = 0;
        i = 0;
        x++;
        while (i < n) {
            x++;
            x++;
            j = 0;
            x++;
            while (j < n) { x++;
                x++;
                j++; x++;
            }
            x++;
            i++;  x++;
        }
        x++;
        return x;
    }

    public long SnippetLog(long n) {
        long i, j, x = 0;
        i = 1;  x++;
        while (i < n) {
            x++;
            x++;
            i = i * 2;  x++;
        }
        x++;
        return x;
    }

    public long fib(int n)
    {
        if (n < 3) return 1;
        else
        {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static void main(String[] args) {
//        CountIt ex = new CountIt();
//        int N = 150000;
//        long start = System.currentTimeMillis();
//        ex.linearLoop(N);
//        Long endTime = System.currentTimeMillis();
//        System.out.println("\nN = " + N + ". Loops ran in: " + (endTime - start));

//        CountIt r = new CountIt();
//        Long t = System.currentTimeMillis();
//        System.out.println(r.SnippetNestedLoop(50000));
//        System.out.println ("Time:" + (System.currentTimeMillis() - t));

//        CountIt r = new CountIt();
//        Long t = System.currentTimeMillis();
//        System.out.println(r. SnippetLog (Long.MAX_VALUE));
//        System.out.println("Time:" + (System.currentTimeMillis() - t));

        CountIt r = new CountIt();
        Long t = System.currentTimeMillis();
        System.out.println(r.fib(50));
        System.out.println ("Time:" + (System.currentTimeMillis() - t));
    }
}