package ReinformentAgent;
import java.awt.Color;
import sim.physics2D.physicalObject.StationaryObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

public class BotHome extends StationaryObject2D{
	public boolean  homeVisible;
	public BotHome(Double2D pos) {
		 this.setCoefficientOfRestitution(1);
	        this.setPose(pos, new Angle(0));
	        this.setShape(new sim.physics2D.shape.Rectangle(10, 10, Color.black));
	        homeVisible=true;
		// TODO Auto-generated constructor stub
	}
}
