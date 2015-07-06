package antcolony;
import geo.Position;

public class FoodSource{
	
	private int amount;
	private Position position;
	
	public FoodSource(int amount, Position position){
		this.amount=amount;
		this.position=position;
	}
	
	public boolean takeFood(){
		boolean bool=false;
		if(amount>0){
			--amount;
			bool=true;
		}
		return bool;
	}
	
	public Position getPosition(){
		return position;
	}
}
