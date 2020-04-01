package application;

import javafx.scene.control.TextArea;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * This class is to handle the backend for the GUI.
 * <p>
 * An Interactive GUI that enables users to change the Pizza through ChoiceBoxes, CheckBoxes, and Buttons.
 * The class updates once the user presses the Save Order button and updates the cost of the individual pizza 
 * as well as the total cost of the order.
 * Then it displays it in the Line Order Section of the GUI.
 * <p>
 * 
 * @author Patrick Li
 * @version 1.0
 */
public class PizzaController {
	
	//Section for size of the Pizza.
	@FXML Label pizzaSize;
	@FXML ChoiceBox<String> sizeOfPizza = new ChoiceBox<>();

	ObservableList<String> choiceListSize = FXCollections.observableArrayList(
			"Small", "Medium", "Large");

	//Section created for Cheese Choices/
	@FXML Label pizzaCheese;
	@FXML ChoiceBox<String> cheeseTopping = new ChoiceBox<>();

	// Creates a list of Strings of choices of cheese.
	ObservableList<String> choiceListCheese = FXCollections.observableArrayList(
			"Single", "Double", "Triple");
	
	// CheckBoxes for the Pizza toppings.
	@FXML CheckBox pineApple;
	@FXML CheckBox greenPepper;
	@FXML CheckBox ham;
	@FXML CheckBox vegetarian;
	
	// Slider and Label for the amount of Pizza the user wants to order.
	@FXML private Slider pizzaAmount = new Slider(1, 100, 1);
	@FXML private Label lblPizzaAmount;
	int numOfPizza;
	
	// Labels and a TextArea for each individual's pizzas cost and total order cost.
	@FXML private Label pizzaCost;
	@FXML private Label orderCost;
	@FXML private TextArea lineOrders;
	
	// Creates a instance from the Pizza class and from the LineItem Class.
	Pizza pizza;
	LineItem linePizza;
	
	// To updates the Pizza updated. and updates the cost of the Pizza and the total cost of the order.
	public void saveOrderButtonPushed() throws IllegalPizza {
		// Displays the number of Pizza to the Line Order Section.
		String pizzaOrders = linePizza.toString();
		lineOrders.appendText(pizzaOrders + "\n");
	}	
	
	//If any checkbox for the toppings are selected, and will update the Pizza object accordingly.
	public void setActionToppings() throws IllegalPizza {
		if (pineApple.isSelected()) {
			pizza.setPineapple("Single");
		}else {
			pizza.setPineapple("None");
		}
		
		if (ham.isSelected()) {
			pizza.setHam("Single");
		}else {
			pizza.setHam("None");
		}
		
		if (greenPepper.isSelected()) {
			pizza.setGreenPepper("Single");
		}else {
			pizza.setGreenPepper("None");
		}
		
		pizzaCost.setText("" + pizza.getCost());
		orderCost.setText("" + linePizza.getCost());
	
	}

	@FXML
	void initialize() throws IllegalPizza {
		
		pizza = new Pizza();
		linePizza = new LineItem(pizza);

		// Does not allow pineapple or green pepper to selected without ham.
		pineApple.disableProperty().bind(ham.selectedProperty().not());
		greenPepper.disableProperty().bind(ham.selectedProperty().not());
		ham.disableProperty().bind(Bindings.or(pineApple.selectedProperty(), greenPepper.selectedProperty()));

		/**
		// Changes the label and sets pizzaOrder depending on the number the slider moves on.
		lblPizzaAmount.setText("" + (int)pizzaAmount.getValue());
		pizzaAmount.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		lblPizzaAmount.setText("" + newVal.intValue());
    		numOfPizza = newVal.intValue();
    		
    		try {
				linePizza.setNumber(numOfPizza);
			} catch (IllegalPizza e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if(numOfPizza < 1) {
    			float totalOrderCost = linePizza.getCost();
    			orderCost.setText("$" + totalOrderCost);
    			
    		}else {
    			float totalOrderCost = linePizza.getCost();
    			orderCost.setText("$" + totalOrderCost);
    		}
    		
    	});
		*/
		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		sizeOfPizza.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pizzaSize = sizeOfPizza.getValue().toString();
    		
    		try {
				pizza.setSize(pizzaSize);
			} catch (IllegalPizza e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		pizzaCost.setText("" + pizza.getCost());
    		orderCost.setText("" + linePizza.getCost());
    		
    	});
		
		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		cheeseTopping.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pizzaCheese = cheeseTopping.getValue().toString();
    		
    		try {
				pizza.setCheese(pizzaCheese);
			} catch (IllegalPizza e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		pizzaCost.setText("" + pizza.getCost());
    		orderCost.setText("" + linePizza.getCost());
    		
    	});
		
		
		// To select the size of Pizza
		sizeOfPizza.setItems(choiceListSize);
		sizeOfPizza.setValue("Small");
		
		// To select the type of Cheese
		cheeseTopping.setItems(choiceListCheese);
		cheeseTopping.setValue("Single");
		
		// Handles when user selects Ham
		ham.setSelected(true);
	}
}
