package application;
import java.util.HashMap;
public class BloodInventory {
	 private HashMap<String, Integer> stock = new HashMap<>();

	    public void addBlood(String group) {
	        stock.put(group, stock.getOrDefault(group, 0) + 1);
	    }

	    public int getUnits(String group) {
	        return stock.getOrDefault(group, 0);
	    }

	    public HashMap<String, Integer> getAllStock() {
	        return stock;
	    }
}
