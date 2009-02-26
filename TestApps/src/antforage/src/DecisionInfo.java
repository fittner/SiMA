package antforage.src;
import java.awt.*;

public class DecisionInfo implements java.io.Serializable {
	public Point position;
    public int orientation;
    public double homePheromoneAmount;
    public double foodPheromoneAmount;

    // to be computed from homePheromoneAmount and foodPheromoneAmount based on goal (go to home or to food)
    public double profit;

    public DecisionInfo() { position = new Point(); }
}
