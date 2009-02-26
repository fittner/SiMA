package TutorialApp;
import sim.engine.*;
import sim.util.*;

public class BigParticle extends Particle implements Proxiable{
	// we can't "turn off" setRandomize by making it protected or whatnot.
    // but we can tell SimpleProperties to use a proxy of our invention
    // rather than querying us directly.  The proxy class MUST be public;
    // and if it's to be used in our model, it must be Serializable.
    // Also remember that if it's a non-static inner class, and we care
    // about cross-platform serialization, it needs to have a serialversionUID,
    // as well as its inclosing class!
    public class MyProxy implements java.io.Serializable
        {
        public int getXDir() { return xdir; }
        public int getYDir() { return ydir; }
        // because we are a non-static inner class
        static final long serialVersionUID = -2815745192429358605L;
        }
        
    // because we contain a non-static inner class
    static final long serialVersionUID = 7720089824883511682L;

    public Object propertiesProxy()
        {
        return new MyProxy();
        }
 
    public BigParticle(int xdir, int ydir) { super(xdir,ydir); }

    public void step(SimState state)
        {
        // hard-code me to be non-randomized
        randomize = false;
        super.step(state);
        }
}
