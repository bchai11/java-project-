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

public class ManageItemForm extends HBox implements EventHandler<ActionEvent> {
	private TableView<Item> tableView;
	private TextField itemID;
	private TextField itemName;
	private TextField desc;
	
	private Spinner priceSpinner;
	private Spinner qtySpinner;

	private Alert alert;
	
	private Button updateButton;
	private Button deleteButton;
	private Button insertButton;
	private Button clearButton;
	
	public ManageItemForm() {
		super();
		setupView();
	}
	
	public void setupView() {
		Text itemIDLabel = new Text("User ID");
		Text itemNameLabel = new Text("Item Name");
		Text descLabelText = new Text("Item Description");
		Text priceLabel = new Text("price");
		Text QuantityLabel = new Text("Quantity");
		
		 itemID = new TextField();
		 itemID.setEditable(false);
		 itemID.setDisable(true);
		 itemID.setText(Connectir.getEligibleItemId());
		 itemName = new TextField();
		 desc = new TextField();
		
		 priceSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0, 1000);
		 qtySpinner = new Spinner<>(0,Integer.MAX_VALUE,0);
		
		updateButton = new Button("Update Item");
		deleteButton = new Button("Delete Item");
		insertButton = new Button("Insert Item");
		clearButton = new Button("Clear Form");
		
		
		updateButton.setOnAction(this);
		deleteButton.setOnAction(this);
		insertButton.setOnAction(this);
		clearButton.setOnAction(this);
		
		updateButton.setDisable(true);
		deleteButton.setDisable(true);

		HBox buttonBox = new HBox(10);
		
		
		buttonBox.getChildren().addAll(insertButton,updateButton,deleteButton);
		buttonBox.setHgrow(deleteButton, Priority.ALWAYS);
		buttonBox.setHgrow(updateButton, Priority.ALWAYS);
		buttonBox.setHgrow(insertButton, Priority.ALWAYS);
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
		grid.add(priceSpinner, 1, 3);
		grid.add(qtySpinner, 1, 4);
		
		alert = new Alert(AlertType.NONE);
		VBox viewBox = new VBox();
		viewBox.getChildren().addAll(grid, buttonBox, clearButton);
		viewBox.setAlignment(Pos.TOP_CENTER);
		viewBox.setMargin(grid, new Insets(20, 20, 10, 20));
		viewBox.setMargin(buttonBox, new Insets(0, 20, 10, 20));
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
		    	insertButton.setDisable(true);
		    	deleteButton.setDisable(false);
		    	updateButton.setDisable(false);
		        configureForm(newSelection);
		    }
		    else {
		    	insertButton.setDisable(false);
		    	deleteButton.setDisable(true);
		    	updateButton.setDisable(true);
		    }
		});
		super.getChildren().addAll(tableView,viewBox);
		super.setHgrow(tableView, Priority.ALWAYS);
		super.setAlignment(Pos.CENTER);
	}

	@Override
	public void handle(ActionEvent e) {
		if(tableView.getSelectionModel() == null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("FAILED");
			alert.setHeaderText(null);
			alert.setContentText("Please Select Data You want to delete");
			alert.show();
		} else if(e.getSource() == updateButton) {
			checkValidity("update");
		} else if (e.getSource() == deleteButton) {
			deleteItem();
		} else if (e.getSource() == insertButton) {
			checkValidity("insert");
		} else if (e.getSource() == clearButton) {
			clearForm();
		}
		
	}
	
	private void configureForm(Item item) {
		itemID.setText(item.getItemID());
		itemName.setText(item.getItemName());
		desc.setText(item.getItemDescription());
		priceSpinner.getValueFactory().setValue(item.getPrice());
		qtySpinner.getValueFactory().setValue(item.getQuantity());
	}
	
	private void checkValidity(String trigger) {
		if (!checkItemName()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Item Must be 5 - 100 chars, contains atleast 2 word and have : to seperate game name and item name");
			alert.show();
		}
		else if (!checkDesc()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Item Description must be between 10 – 200 characters.");
			alert.show();
		}
		else if (!checkPrice()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Price must be greater than 0!");
			alert.show();
		}
		else if (!checkQty()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Quantity must be greater than 0!");
			alert.show();
		}
		else {
			if (trigger.equals("insert")) {
				Connectir.registerItem(itemID.getText(),
						itemName.getText(),
						desc.getText(), 
						(int) priceSpinner.getValue(), 
						(int) qtySpinner.getValue());
			}
			else {
				Connectir.updateItem(itemID.getText(),
						itemName.getText(),
						desc.getText(), 
						(int) priceSpinner.getValue(), 
						(int) qtySpinner.getValue());
			}
			
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Add Item Successfully, Click OK to continue");
			alert.setTitle("SUCCESS");
			Optional<ButtonType> okButtonOptional = alert.showAndWait();
			if (okButtonOptional.get() == ButtonType.OK) {
				updateData();
			}
		}
	}
	
	private boolean checkItemName() {
		String itemName = this.itemName.getText();
		String[] words = itemName.split("\\s+");
        boolean isValid = true;
		
		if (itemName.length() < 5 && itemName.length() > 100) {
			isValid = false;
		}
		else if (!itemName.contains(":")) {
			isValid = false;
		}
		else if (words.length < 2){
			 isValid = false;
		}
		return isValid;
	}
	
	private boolean checkDesc() {
		String descString = desc.getText();
		return descString.length() >= 10 && descString.length() <= 200;
	}
	
	private boolean checkPrice() {
		int price = (int) priceSpinner.getValue();
		return price > 0;
	}
	
	private boolean checkQty() {
		int price = (int) qtySpinner.getValue();
		return price > 0;
	}
	
	private void deleteItem() {
		Connectir.deleteItem(itemID.getText());
		alert.setAlertType(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Click OK to continue");
		alert.setTitle("DELETE SUCCESS");
		Optional<ButtonType> okButtonOptional = alert.showAndWait();
		if (okButtonOptional.get() == ButtonType.OK) {
			updateData();
		}
	}
	
	private void updateData() {
		tableView.setItems(Connectir.fetchItem());
	}
	
	private void clearForm() {
		itemID.setText(Connectir.getEligibleItemId());
		itemName.setText(null);
		desc.setText(null);
		priceSpinner.getValueFactory().setValue(0);
		qtySpinner.getValueFactory().setValue(0);
		tableView.getSelectionModel().clearSelection();
	}
}
