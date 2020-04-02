package application;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
 * @version 3.5
 */
public class PizzaController {
	//Section for size of the Pizza.
	@FXML
	Label pizzaSize;
	@FXML
	ChoiceBox<String> sizeOfPizza = new ChoiceBox<>();

	//Creates a list of Strings of choices for the size of the pizza.
	ObservableList<String> choiceListSize = FXCollections.observableArrayList(
			"Small", "Medium", "Large");

	//Section created for Cheese Choices/
	@FXML
	Label pizzaCheese;
	@FXML
	ChoiceBox<String> cheeseTopping = new ChoiceBox<>();

	//Creates a list of Strings of choices of cheese.
	ObservableList<String> choiceListCheese = FXCollections.observableArrayList(
			"Single", "Double", "Triple");
	
	// CheckBoxes for the Pizza toppings.
	@FXML
	CheckBox pineApple;
	@FXML
	CheckBox greenPepper;
	@FXML
	CheckBox ham;
	@FXML
	CheckBox vegetarian;
	
	// TextField which allows for user input in the amount of Pizza they want to order.
	@FXML private TextField pizzaAmount;
	int numOfPizza;
	
	//Labels, TextFields, TextArea, and labels which represent the total costs.
	@FXML
	private TextField pizzaCost;
	@FXML
	private TextField orderCost;
	@FXML
	private TextField totalCost;
	@FXML
	private TextArea lineOrders;

	//Creates a instance from the Pizza class and from the LineItem Class.
	Pizza pizza;
	LineItem linePizza;

	//Keeps track of orders.
	private ArrayList<String> orders = new ArrayList<>();

	//float variable to keep track of the overall total cost:
	float overallCost;

	//Buttons
	@FXML
	private Button saveButton;

	//If any checkbox for the toppings are selected, and will update the Pizza object accordingly.
	public void setActionToppings() throws IllegalPizza {
		if (pineApple.isSelected())pizza.setPineapple("Single");
		else pizza.setPineapple("None");

		if (ham.isSelected()) pizza.setHam("Single");
		else pizza.setHam("None");

		if (greenPepper.isSelected()) pizza.setGreenPepper("Single");
		else pizza.setGreenPepper("None");

		if (vegetarian.isSelected()) pizza.setVegetarian(true);
		else pizza.setVegetarian(false);

		//Updates the Costs
		updatePizzaCost(pizza);
		float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
		updateOrderCost(costOrder);
	}

	//Updates the Pizza Cost Text Field.
	public void updatePizzaCost(Pizza clonePizza){
		pizzaCost.setText("" + String.format("%.2f", clonePizza.getCost()));
	}

	//Updates the Cost Per Order Text Field.
	public void updateOrderCost(float costOrder){
		orderCost.setText("" + String.format("%.2f", costOrder));
	}

	//Updates the Total Cost in the Text field.
	public void updateTotalCost(float overallCost){
		totalCost.setText("" + String.format("%.2f", overallCost));
	}

	//Calculates the cost per order and rounds it to the nearest second decimal place.
	public float costPerOrder(){
		float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
		return costOrder;
	}

	//Resets all the settings and values back to the default values.
	@FXML
	void clearAll(ActionEvent event) throws IllegalPizza {
		//Resets the Pizza setting to default.
		pizza.setSize("Small");
		pizza.setVegetarian(false);
		pizza.setGreenPepper("None");
		pizza.setPineapple("None");
		pizza.setHam("Single");
		pizza.setCheese("Single");
		linePizza.setNumber(1);

		//Resets the GUI Settings back to default
		lineOrders.setText("Total Cost: ");
		pizzaCost.setText("");
		sizeOfPizza.setValue("Small");
		cheeseTopping.setValue("Single");
		ham.setSelected(true);
		pineApple.setSelected(false);
		greenPepper.setSelected(false);
		vegetarian.setSelected(false);
		pizzaAmount.setText("1");

		//Resets the Cost to Default Values
		updatePizzaCost(pizza);
		updateOrderCost(costPerOrder());
		overallCost = 0;
		updateTotalCost(0);

		//Resets the Arraylist to a blank list.
		orders.clear();
	}

	@FXML
	void initialize() throws IllegalPizza {
		pizza = new Pizza();
		linePizza = new LineItem(pizza);

		//To skip a line inside of the text field
		lineOrders.setText("Total Cost: ");

		// To select the size of Pizza
		sizeOfPizza.setItems(choiceListSize);

		// To select the type of Cheese
		cheeseTopping.setItems(choiceListCheese);

		// Default Pizza
		sizeOfPizza.setValue("Small");
		cheeseTopping.setValue("Single");
		ham.setSelected(true);
		pizzaAmount.setText("1");

		// Does not allow pineapple or green pepper to selected without ham.
		ham.disableProperty().bind(vegetarian.selectedProperty());
		vegetarian.disableProperty().bind(ham.selectedProperty());

		//Displays the cost of one default pizza.
		updatePizzaCost(pizza);
		updateOrderCost(costPerOrder());
		updateTotalCost(overallCost);

		//Listens to when the number of pizzas has changed and automatically changes the total order costs.
		pizzaAmount.textProperty().addListener((observableValue, oldText, newText) ->
		{
			if (newText != null && !newText.isEmpty()) {
				try {
					numOfPizza = Integer.parseInt(newText);
					if(numOfPizza < 1 && numOfPizza > 100){
						pizzaAmount.setPromptText("");
					}else{
						linePizza.setNumber(numOfPizza);
						float costOrder = costPerOrder();
						updateOrderCost(costOrder);
					}
				} catch (NumberFormatException | IllegalPizza e) {
					((StringProperty)observableValue).setValue(oldText);
				}
			}
		});

		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		sizeOfPizza.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pizzaSize = sizeOfPizza.getValue();
    		try {
				pizza.setSize(pizzaSize);
			} catch (IllegalPizza e) {}
    		updatePizzaCost(pizza);
    		updateOrderCost(costPerOrder());
    	});
		
		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		cheeseTopping.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		//Converts the choice of cheese to a string format.
    		String pizzaCheese = cheeseTopping.getValue();
    		//Changes the value of the pizza and updates the Cost display.
    		try {
				pizza.setCheese(pizzaCheese);
			} catch (IllegalPizza e) {}
			pizzaCost.setText("" + String.format("%.2f", pizza.getCost()));
			orderCost.setText("" + String.format("%.2f", linePizza.getCost()));
    	});

		////Updates the display if the user clicks on the save button.
		saveButton.setOnAction(actionEvent -> {
			//If the Pizza amount is empty, It will not print anything.
			if (pizzaAmount.getText().equals(""))
				return;

			//Converts one order to a string format.
			String pizzaOrders = linePizza.toString();

			//Updates the Total Costs.
			float CostOfOrder = costPerOrder();
			overallCost +=  CostOfOrder;
			updateTotalCost(overallCost);

			//Initializes an String used to display the orders and total cost.
			String output = "";

			//Adds the orders to the ArrayList.
			orders.add(pizzaOrders);


			//For each loop adding the orders to the String output variable.
			for(String orderString: orders){
				output += orderString + "\n";
			}
			//Updates the TextField displaying the orders and total costs.
			output += "Total Cost: " + String.format("%.2f", overallCost) + "\n";
			lineOrders.setText(output);
		});
	}
}
