import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Cart extends VBox implements EventHandler<ActionEvent> {
	TableView<Item> tableView;
	Button removeButton;
	Button checkoutButton;
	ObservableList<Item> items;
	String selectedItem;
	Alert alert;
	public Cart() {
		super();
		setupView();
	}
	
	private void setupView() {
		
		removeButton = new Button("Remove From Cart");
		checkoutButton = new Button("Checkout");
		
		removeButton.setOnAction(this);
		checkoutButton.setOnAction(this);
		
		removeButton.setDisable(true);
		
		tableView = new TableView<Item>();
		TableColumn<Item, ?> transactionIDColumn = new TableColumn<>("itemID");
		TableColumn<Item, ?> totalPriceColumn = new TableColumn<>("Total Price");
		TableColumn<Item, ?> qtyColumn = new TableColumn<>("Quantity");
		TableColumn<Item, ?> itemNameColumn = new TableColumn<>("Item Name");
		TableColumn<Item, ?> itemDesColumn = new TableColumn<>("Item Description");
		TableColumn<Item, ?> priceColumn = new TableColumn<>("Price");
		transactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemDesColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		tableView.getColumns().addAll(transactionIDColumn,itemNameColumn,itemDesColumn,priceColumn,qtyColumn,totalPriceColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		reloadTable();
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	selectedItem = newSelection.getItemID();
		    	removeButton.setDisable(false);
		    }
		    else {
		    	removeButton.setDisable(true);
		    }
		});
		
		
		HBox hBox = new HBox(10);
		hBox.setMinWidth(1000);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(removeButton,checkoutButton);
		super.getChildren().addAll(tableView,hBox);
		super.setSpacing(20);
	}
	
	private void reloadTable() {
		items = Connectir.fetchCart();
		tableView.setItems(items);
		if (tableView.getItems().size() <= 0) {
			checkoutButton.setDisable(true);
		}
		else {
			checkoutButton.setDisable(false);
		}
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == removeButton) {
			removeFromCart();
		}
		else {
			checkout();
		}
	}
	
	private void removeFromCart() {
		Connectir.removeFromCart(selectedItem);
		alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Successfully remove items, click OK to continue");
		alert.setTitle("Success");
		Optional<ButtonType> resultOptional = alert.showAndWait();
		if (resultOptional.get() == ButtonType.OK) {
			reloadTable();
		}
	}
	
	private void checkout() {
		for (Item item : items) {
			if (item.getQuantity() > Connectir.getItemQuantity(item.getItemID())) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("The item you ordered ("+item.getItemName()+") with itemID  ("+item.getItemID()+") is out of stock");
				alert.setTitle("ERROR");
				alert.show();
			}
			else {
				alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Successfully checkout items with item name ("+item.getItemName()+") with itemID  ("+item.getItemID()+") click OK to Continue");
				alert.setTitle("Success");
				alert.show();
				Connectir.removeFromCart(item.getItemID());
				Connectir.reduceQty(item.getQuantity(), item.getItemID());
			}
		}
		
	}
}
