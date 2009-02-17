package test;

public class PrimativeCast {

    private int loopCount =  java.lang.Integer.MAX_VALUE / 2;
    private int testCount = 1;
    
    long ci = 0;
    long cs = 0;
    long cis = 0;
    long csi = 0;
    long cfd = 0;
    long cdf = 0;

    long ab = 0;
    long as = 0;
    long ai = 0;
    long al = 0;
    long af = 0;
    long ad = 0;    
    
    long mb = 0;
    long ms = 0;
    long mi = 0;
    long ml = 0;
    long mf = 0;
    long md = 0;    
    
    long db = 0;
    long ds = 0;
    long di = 0;
    long dl = 0;
    long df = 0;
    long dd = 0;    

    
   
    public PrimativeCast() {
        System.out.println( "--------------------------------" );

        for( int i = 0, n = testCount; i < n; i++ )
        {
            System.out.println( "Test Run: " + (i+1) + " / "+testCount );
           
            ci += intControl();
            cs += shortControl();
            cis += castIntToShort();
            csi += castShortToInt();
            cfd += castFloatToDouble();
            cdf += castDoubleToFloat();
            
            ab += addBytes();
            as += addShorts();
            ai += addInts();
            al += addLongs();
            af += addFloats();
            ad += addDoubles();                 
            
            mb += multiplyBytes();
            ms += multiplyShorts();
            mi += multiplyInts();
            ml += multiplyLongs();
            mf += multiplyFloats();
            md += multiplyDoubles();        

            db += divBytes();
            ds += divShorts();
            di += divInts();
            dl += divLongs();
            df += divFloats();
            dd += divDoubles();                 
        }
                       
        System.out.println( "--------------------------------" );
        
        ci /= testCount;
        cs /= testCount;
        cis /= testCount;
        csi /= testCount;
        cdf /= testCount;
        cfd /= testCount;
        
        System.out.println("int to int (control): " + ci);
        System.out.println("short to short (control): " + cs);
        System.out.println("int to short (simple cast): " + cis);
        System.out.println("float to double (simple automatic cast): " + cfd);
        System.out.println("double to float (simple cast): " + cdf);
        
        System.out.println( "--------------------------------" );
        
        ab /= testCount;
        as /= testCount;
        ai /= testCount;
        al /= testCount;
        af /= testCount;
        ad /= testCount;   
                
        System.out.println("add bytes: " + ab );   
        System.out.println("add shorts: " + as );
        System.out.println("add ints: " + ai );
        System.out.println("add longs: " + al );
        System.out.println("add floats: " + af );
        System.out.println("add doubles: " + ad );

        System.out.println( "--------------------------------" );
        
        mb /= testCount;
        ms /= testCount;
        mi /= testCount;
        ml /= testCount;
        mf /= testCount;
        md /= testCount;          

        System.out.println("multiplying bytes: " + mb );
        System.out.println("multiplying shorts: " + ms );
        System.out.println("multiplying ints: " + mi );
        System.out.println("multiplying longs: " + ml );
        System.out.println("multiplying floats: " + mf );
        System.out.println("multiplying doubles: " + md );
        
        System.out.println( "--------------------------------" );
        
        db /= testCount;
        ds /= testCount;
        di /= testCount;
        dl /= testCount;
        df /= testCount;
        dd /= testCount;          

        System.out.println("div bytes: " + db );
        System.out.println("div shorts: " + ds );
        System.out.println("div ints: " + di );
        System.out.println("div longs: " + dl );
        System.out.println("div floats: " + df );
        System.out.println("div doubles: " + dd );
 
        System.out.println( "--------------------------------" );
        System.out.println( "--------------------------------" );        
        
    }
   
    private int intControl() {
        int testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            int result = testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
    private int shortControl() {
        short testShort = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            short result = (short)testShort;
            testShort++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
   
    private int castIntToShort() {
        int testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            short result = (short)testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int castShortToInt() {
        short testShort = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            int result = testShort;
            testShort++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
    private int castFloatToDouble() {
        float testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            double result = testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }    
   
    private int castDoubleToFloat() {
    	double testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	float result = (float)testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    } 
    
    private int addBytes() {
        byte test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            byte result = (byte)(test + test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int addShorts() {
        short test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            short result = (short)(test + test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
        
       
        return duration;
    }
   
    private int addInts() {
        int testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            int result = testInt + testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
        
       
        return duration;
    }
   
    private int addLongs() {
        long test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            long result = test + test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
        
       
        return duration;
    }

    private int addFloats() {
        float test = 0.0f;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            float result = test + test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
        
       
        return duration;
    }

    private int addDoubles() {
        double test = 0.0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            double result = test + test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }

    
    
    private int multiplyBytes() {
        byte test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            byte result = (byte)(test * test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
    private int multiplyShorts() {
        short test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            short result = (short)(test * test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int multiplyInts() {
        int testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            int result = testInt * testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int multiplyLongs() {
        long test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            long result = test * test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int multiplyFloats() {
        float test = 0.0f;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            float result = test * test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int multiplyDoubles() {
        double test = 0.0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
            double result = test * test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
    private int divBytes() {
        byte test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (test==0) {test=1;}
            byte result = (byte)(test / test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    
    private int divShorts() {
        short test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (test==0) {test=1;}        	
            short result = (short)(test / test);
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int divInts() {
        int testInt = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (testInt==0) {testInt=1;}
            int result = testInt / testInt;
            testInt++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int divLongs() {
        long test = 0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (test==0) {test=1;} 
            long result = test / test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int divFloats() {
        float test = 0.0f;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (test==0.0f) {test=1.0f;} 
            float result = test / test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
   
    private int divDoubles() {
        double test = 0.0;
       
        long start = System.currentTimeMillis();
        for (int i = 0, n = loopCount; i < n; i++) {
        	if (test==0.0) {test=1.0;} 
            double result = test / test;
            test++;
            result++;
        }
        long end = System.currentTimeMillis();
        int duration = (int)(end - start);
       
        return duration;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        new PrimativeCast();
    }

} 