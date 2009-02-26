package antforage.src;
import sim.engine.SimState;

public class GreedyDecisionMaker extends DecisionMaker{
	 public DecisionInfo getHomeDecision( final SimState state )
     {

     int index;

     if( numInfos == 0 )
         {
         return null;
         }

     for( int i = 0 ; i < numInfos ; i++ )
         {
         processForHomeDecision( info[i] );
         }

     // compute the maximum value
     index = 0;
     for( int i = 0 ; i < numInfos ; i++ )
         if( info[i].profit > info[index].profit )
             index = i;

     int howMany = 0;
     for( int i = 0 ; i < numInfos ; i++ )
         if( info[i].profit == info[index].profit )
             howMany++;

     int x = state.random.nextInt( howMany );
     for( int i = 0 ; i < numInfos ; i++ )
         if( info[i].profit == info[index].profit )
             if( x == 0 )
                 return info[i];
             else
                 x--;
     return null;
 
     }
}
