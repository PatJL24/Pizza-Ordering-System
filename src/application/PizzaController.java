package application;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

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
 * @version 2.5
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
	
	// TextField which allows for user input in the amount of Pizza they want to order.
	@FXML private TextField pizzaAmount;
	int numOfPizza;
	
	// Labels and a TextArea for each individual's pizzas cost and total order cost.
	@FXML private Label pizzaCost;
	@FXML private Label orderCost;
	@FXML private Label totalCost;
	@FXML private TextArea lineOrders;
	
	// Creates a instance from the Pizza class and from the LineItem Class.
	Pizza pizza;
	LineItem linePizza;


	//float variable to keep track of the overall total cost:
	float overallCost;

	// To updates the Pizza updated. and updates the cost of the Pizza and the total cost of the order.
	public void saveOrderButtonPushed() throws IllegalPizza {
		// Displays the number of Pizza to the Line Order Section.
		String pizzaOrders = linePizza.toString();
		float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
		lineOrders.appendText(pizzaOrders + " Order Cost: $"
				+ String.format("%.2f", costOrder) + ".\n");

		overallCost +=  costOrder;
		totalCost.setText("" + String.format("%.2f", overallCost));
	}	
	
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

		pizzaCost.setText("" + String.format("%.2f", pizza.getCost()));
		float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
		orderCost.setText("" + String.format("%.2f", costOrder));
	}

	//Resets all the settings and values back to the default values.
	@FXML
	void clearAll(ActionEvent event) throws IllegalPizza {
		//Resets the Amount of Pizza back to 1.
		pizzaAmount.clear();

		//Resets the Pizza setting to default.
		pizza.setSize("Small");
		pizza.setVegetarian(false);
		pizza.setGreenPepper("None");
		pizza.setPineapple("None");
		pizza.setHam("Single");
		pizza.setCheese("Single");

		//Resets the GUI Settings back to default
		lineOrders.setText("Orders: \n");
		sizeOfPizza.setValue("Small");
		cheeseTopping.setValue("Single");
		ham.setSelected(true);
		pineApple.setSelected(false);
		greenPepper.setSelected(false);
		vegetarian.setSelected(false);

		overallCost = 0;
		totalCost.setText("" + String.format("%.2f", overallCost));
	}

	@FXML
	void initialize() throws IllegalPizza {
		pizza = new Pizza();
		linePizza = new LineItem(pizza);

		//To skip a line inside of the text field
		lineOrders.setText("Orders: \n");

		// To select the size of Pizza
		sizeOfPizza.setItems(choiceListSize);

		// To select the type of Cheese
		cheeseTopping.setItems(choiceListCheese);

		// Default Pizza
		sizeOfPizza.setValue("Small");
		cheeseTopping.setValue("Single");
		ham.setSelected(true);

		// Does not allow pineapple or green pepper to selected without ham.
		ham.disableProperty().bind(vegetarian.selectedProperty());
		vegetarian.disableProperty().bind(ham.selectedProperty());

		//Displays the cost of one default pizza.
		pizzaCost.setText("" + String.format("%.2f", pizza.getCost()));
		orderCost.setText("" + String.format("%.2f", linePizza.getCost()));
		totalCost.setText("" + String.format("%.2f", overallCost));

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
						float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
						orderCost.setText("" + String.format("%.2f", costOrder));
					}
				} catch (NumberFormatException | IllegalPizza e) {
					((StringProperty)observableValue).setValue(oldText);
				}
			}else{
				numOfPizza = 1;
				try {
					linePizza.setNumber(numOfPizza);
				} catch (IllegalPizza illegalPizza) {
					illegalPizza.printStackTrace();
				}
				float costOrder = (float) (Math.round(linePizza.getCost() * 100.0) / 100.0);
				orderCost.setText("" + String.format("%.2f", costOrder));
			}
		});

		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		sizeOfPizza.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pizzaSize = sizeOfPizza.getValue();
    		
    		try {
				pizza.setSize(pizzaSize);
			} catch (IllegalPizza e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		pizzaCost.setText("" + String.format("%.2f", pizza.getCost()));
    		orderCost.setText("" + String.format("%.2f", linePizza.getCost()));
    	});
		
		// Listens to see if the choice box has been changed and updates the Pizza object accordingly.
		cheeseTopping.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pizzaCheese = cheeseTopping.getValue();
    		try {
				pizza.setCheese(pizzaCheese);
			} catch (IllegalPizza e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pizzaCost.setText("" + String.format("%.2f", pizza.getCost()));
			orderCost.setText("" + String.format("%.2f", linePizza.getCost()));
    	});
	}
}
