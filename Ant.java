package antcolony;

import geo.Position;
import antcolony.simulation.Simulation;
import antcolony.FoodSource;
import java.util.List;
import java.util.LinkedList;

public class Ant{
	private Position p;
	private boolean hasFood;
	private double excitement;
	private int out;
	private Simulation sim;
	private static final int TIME_TO_GO_HOME=15;
	private Position antPosition;

	public Ant(Simulation sim, Position p){
		this.p=p;
		this.sim=sim;
		this.antPosition=new Position(p.getX(),p.getY());
		this.out=0;
		this.excitement=1;
		this.hasFood=false;
	}
	
	public Position getPosition(){
		return antPosition;
	}
	
	public boolean isCarryingFood(){
		return hasFood;
	}
	
	public void moveTowardsColony(){
		if(hasFood){
			sim.putPheromoneOn(antPosition,excitement);
			excitement*=0.9;
		}
		antPosition.towards(sim.getColonyPosition());
	}
	
	public void wander(){
		LinkedList<Position> list =antPosition.neighbours(sim);
		double maxPheromone=0;
		Position max=null;
		for(Position e: list){
			double currentPheromone=sim.pheromoneOn(e)+sim.random.nextDouble()*0.1;
			if(currentPheromone>maxPheromone){
				maxPheromone=currentPheromone;
				max=e;
			}
		}
		antPosition.towards(max);
	}
	
	public void simulateStep(){
		if(isCarryingFood() && antPosition.samePosition(sim.getColonyPosition())){
			sim.foodDelivered++;
			hasFood=false;
			excitement=0;
			out=0;
		}else if(TIME_TO_GO_HOME<=out && antPosition.samePosition(sim.getColonyPosition())){
			out=0;
			excitement=0;
		}else if(isCarryingFood() || TIME_TO_GO_HOME<=out){
			moveTowardsColony();
		}else if(TIME_TO_GO_HOME>out){
			wander();
			out++;
			FoodSource fs = sim.foodPresent(antPosition);
			if(fs!=null && fs.takeFood()){
				hasFood=true;
				excitement=1;
			}
			
		}
	}
}
