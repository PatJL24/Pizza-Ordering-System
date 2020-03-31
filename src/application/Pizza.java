package application;


import java.io.Serializable;
/**
 * This class is part of the solution for assignment 3. The class represents a pizza, containing
 * the size, the amount of cheese, pineapple, green pepper and ham. It can also produce the cost 
 * of that pizza using hard-coded prices.
 * @author Alan McLeod
 * @version 1.3
 */
public class Pizza implements Serializable {

	private static final long serialVersionUID = 1856146538106631512L;
	private String size;
	private String cheese;
	private String pineapple;
	private String greenPepper;
	private String ham;
	
	private final float SMALL_COST = 7.00F;	// Includes one cheese
	private final float MEDIUM_COST = 9.00F;
	private final float LARGE_COST = 11.00F;
	private final float COST_PER_TOPPING = 1.50F;
	
	public void setSize(String size) throws IllegalPizza {
		if (size == null)
			throw new IllegalPizza("Size not provided!");
		if (size.equals("Small") || size.equals("Medium") || size.equals("Large"))
			this.size = size;
		else
			throw new IllegalPizza("Illegal size!");		
	} // end setSize
	
	public void setCheese(String cheese) throws IllegalPizza {
		if (cheese == null)
			throw new IllegalPizza("Cheese not provided!");
		if (cheese.equals("Single") || cheese.equals("Double") || cheese.equals("Triple"))
			this.cheese = cheese;
		else
			throw new IllegalPizza("Illegal cheese!");		
	} // end setCheese
	
	public void setPineapple(String pineapple) throws IllegalPizza {
		if (pineapple == null)
			throw new IllegalPizza("Pineapple not provided!");
		if (pineapple.equals("None") || pineapple.equals("Single"))
			this.pineapple = pineapple;
		else
			throw new IllegalPizza("Illegal pineapple!");		
	} // end setPineapple
	
	public void setGreenPepper(String greenPepper) throws IllegalPizza {
		if (greenPepper == null)
			throw new IllegalPizza("Green pepper not provided!");
		if (greenPepper.equals("None") || greenPepper.equals("Single"))
			this.greenPepper = greenPepper;
		else
			throw new IllegalPizza("Illegal green pepper!");		
	} // end setGreenPepper
	
	public void setHam(String ham) throws IllegalPizza {
		if (ham == null)
			throw new IllegalPizza("Ham not provided!");
		if (ham.equals("None") || ham.equals("Single"))
			this.ham = ham;
		else
			throw new IllegalPizza("Illegal ham!");		
	} // end setHam
	
	/**
	 * The Pizza constructor.
	 * @param size Must be "Small", "Medium" or "Large".
	 * @param cheese Must be "Single", "Double" or "Triple".
	 * @param pineapple Must be "None" or "Single".
	 * @param greenPepper Must be "None" or "Single".
	 * @param ham Must be "None" or "Single".
	 * @throws IllegalPizza If any of the parameters are not legal or if ham is "None" and
	 * pineapple or greenPepper is not "None".
	 */
	public Pizza(String size, String cheese, String pineapple, String greenPepper, String ham) throws IllegalPizza {
		setSize(size);
		setCheese(cheese);
		setPineapple(pineapple);
		setGreenPepper(greenPepper);
		setHam(ham);
		if (this.ham.equals("None") && (!this.pineapple.equals("None") || !this.greenPepper.equals("None")))
			throw new IllegalPizza("You must have ham with your pineapple or green pepper!");
	} // end full parameter constructor
	
	/**
	 * The default Pizza constructor.
	 * @throws IllegalPizza Should not be thrown.
	 */
	public Pizza() throws IllegalPizza {
		this("Small", "Single", "None", "None", "Single");
	} // end default constructor
	
	private float getSizeCost(String size) {
		switch (size) {
		case "Small":
			return SMALL_COST;
		case "Medium":
			return MEDIUM_COST;
		}
		return LARGE_COST;
	} // end getSizeCost
	
	private int translateTopping(String topping) {
		switch (topping) {
		case "None":
			return 0;
		case "Single":
			return 1;
		case "Double":
			return 2;
		}
		return 3;
	} // end translateTopping
	
	/**
	 * Returns the cost of the current Pizza object.
	 * @return The cost in dollars.  No tax.
	 */
	public float getCost() {
		float cost = getSizeCost(size);
		cost += ((translateTopping(cheese) - 1) + translateTopping(pineapple) + 
				  translateTopping(greenPepper) + translateTopping(ham)) * COST_PER_TOPPING;
		return cost;
	} // end getCost
	
	/**
	 * Returns a string representation of the current object.
	 * @return A string description of the current Pizza object.
	 */
	public String toString() {
		String out = size + " pizza, " + cheese + " cheese";
		if (pineapple.equals("Single"))
			out += ", pineapple";
		if (greenPepper.equals("Single"))
			out += ", green pepper";
		if (ham.equals("Single"))
			out += ", ham";
		out += String.format(". Cost: $%.2f each.", getCost());
		return out;
	} // end toString
	
	/**
	 * Tests to see if the current object is equal to the supplied Pizza object. Equality is
	 * defined as all attributes being exactly equal.
	 * @param other The supplied object for comparison.
	 * @return false if the objects are not equal or the supplied object is not a Pizza, true
	 * otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof Pizza) {
			Pizza otherP = (Pizza)other;
			return size.equals(otherP.size) && cheese.equals(otherP.cheese) && 
				   pineapple.equals(otherP.pineapple) && greenPepper.equals(otherP.greenPepper) && 
				   ham.equals(otherP.ham);
		}
		return false;
	} // end equals
	
	/**
	 * Creates and returns a clone of the current Pizza object.
	 * @return A deep copy or clone of the current object. 
	 */
	public Pizza clone() {
		Pizza outP = null;
		try {
			outP = new Pizza(size, cheese, pineapple, greenPepper, ham);
		} catch (IllegalPizza e) {
		}
		return outP;
	} // end clone
	
} // end Pizza class