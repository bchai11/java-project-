import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ItemMarketForm extends HBox implements EventHandler<ActionEvent> {
	private TableView<Item> tableView;
	private TextField itemID;
	private TextField itemName;
	private TextField desc;
	
	private TextField price;
	private Spinner qtySpinner;

	private Alert alert;

	private Button addToCartButton;
	private Button clearButton;
	private int maxQty;
	
	public ItemMarketForm() {
		super();
		setupView();
	}
	
	public void setupView() {
		Text itemIDLabel = new Text("itemID");
		Text itemNameLabel = new Text("Item Name");
		Text descLabelText = new Text("Item Description");
		Text priceLabel = new Text("price");
		Text QuantityLabel = new Text("Quantity");
		
		 itemID = new TextField();
		 itemID.setEditable(false);
		 itemID.setDisable(true);
		 itemID.setText(Connectir.getEligibleItemId());
		 itemName = new TextField();
		 itemName.setEditable(false);
		 itemName.setDisable(true);
		 desc = new TextField();
		 desc.setEditable(false);
		 desc.setDisable(true);
		 price = new TextField();
		 price.setEditable(false);
		 price.setDisable(true);
		 
		 qtySpinner = new Spinner<>(0,Integer.MAX_VALUE,0);
		
		addToCartButton = new Button("Add To Cart]");
		clearButton = new Button("Clear Form");
		
		
		addToCartButton.setOnAction(this);
		clearButton.setOnAction(this);
		
		addToCartButton.setDisable(true);
		clearButton.setDisable(true);

		HBox buttonBox = new HBox(10);
		
		
		buttonBox.getChildren().addAll(clearButton,addToCartButton);
		buttonBox.setHgrow(addToCartButton, Priority.ALWAYS);
		buttonBox.setHgrow(clearButton, Priority.ALWAYS);
		buttonBox.setAlignment(Pos.CENTER);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
		grid.add(itemIDLabel, 0, 0);
		grid.add(itemNameLabel, 0, 1);
		grid.add(descLabelText, 0, 2);
		grid.add(priceLabel, 0, 3);
		grid.add(QuantityLabel, 0, 4);
		
		grid.add(itemID, 1, 0);
		grid.add(itemName, 1, 1);
		grid.add(desc, 1, 2);
		grid.add(price, 1, 3);
		grid.add(qtySpinner, 1, 4);
		
		alert = new Alert(AlertType.NONE);
		VBox viewBox = new VBox();
		viewBox.getChildren().addAll(grid, buttonBox);
		viewBox.setAlignment(Pos.TOP_CENTER);
		viewBox.setMargin(grid, new Insets(20, 20, 10, 20));
		viewBox.setMargin(buttonBox, new Insets(0, 20, 20, 20));
		buttonBox.setMinWidth(grid.getWidth());
		
		tableView = new TableView<Item>();
		TableColumn<Item, ?> itemIDColomn = new TableColumn<>("itemID");
		itemIDColomn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		TableColumn<Item, ?> itemNameColumn = new TableColumn<>("itemName");
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		TableColumn<Item, ?> passwordColumn = new TableColumn<>("itemDescription");
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
		TableColumn<Item, ?> emailColumn = new TableColumn<>("price");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		TableColumn<Item, ?> phonenumberColumn = new TableColumn<>("quantity");
		phonenumberColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tableView.setItems(Connectir.fetchItem());
		tableView.getColumns().addAll(itemIDColomn,itemNameColumn,passwordColumn,emailColumn,phonenumberColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	clearButton.setDisable(false);
		    	addToCartButton.setDisable(false);
		        configureForm(newSelection);
		    }
		    else {
		    	clearButton.setDisable(true);
		    	addToCartButton.setDisable(true);
		    }
		});
		super.getChildren().addAll(tableView,viewBox);
		super.setHgrow(tableView, Priority.ALWAYS);
		super.setAlignment(Pos.CENTER);
	}

	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == addToCartButton) {
			checkValidity();
		} else if (e.getSource() == clearButton) {
			clearForm();
		}
		
	}
	
	private void configureForm(Item item) {
		itemID.setText(item.getItemID());
		itemName.setText(item.getItemName());
		desc.setText(item.getItemDescription());
		price.setText(""+item.getPrice());
		qtySpinner.getValueFactory().setValue(0);
		maxQty = item.getQuantity();
	}
	
	private void checkValidity() {
		int qty = (int) qtySpinner.getValue();
		if (qty <= 0) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Quantity must be greater than 0!");
			alert.show();
		}
		else if (qty > maxQty) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("The Quantity you input cannot exceed the existing quantity");
			alert.show();
		}
		else {
			Connectir.addToCart(itemID.getText(),(int) qtySpinner.getValue());
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Successfully Add item to cart, Click OK to continue");
			alert.setTitle("SUCCESS");
			Optional<ButtonType> okButtonOptional = alert.showAndWait();
			if (okButtonOptional.get() == ButtonType.OK) {
				updateData();
			}
		}
	}
	
	private void updateData() {
		tableView.setItems(Connectir.fetchItem());
	}
	
	private void clearForm() {
		itemID.setText(null);
		itemName.setText(null);
		desc.setText(null);
		price.setText(null);
		qtySpinner.getValueFactory().setValue(0);
		tableView.getSelectionModel().clearSelection();
	}
}
