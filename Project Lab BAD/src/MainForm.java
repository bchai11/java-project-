import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainForm extends Scene implements EventHandler<ActionEvent> {
	VBox viewBox;
	Menu menu = new Menu("Menu");
	MenuItem itemMarket = new MenuItem("Item Market");
	MenuItem cartItem = new MenuItem("Cart Item");
	MenuItem transactionHistory = new MenuItem("Transaction History");
	MenuItem manageUser = new MenuItem("Manage User");
	MenuItem manageItem = new MenuItem("Manage Item");
	MenuItem transaction = new MenuItem("Transaction");
	MenuItem logout = new MenuItem("Logout");
	MainFormListener listener;
	StackPane stackPane = new StackPane();
	ManageUserForm manageUserForm = new ManageUserForm();
	ManageItemForm manageItemForm = new ManageItemForm();
	TransactionForm transactionForm = new TransactionForm();
	ItemMarketForm itemMarketForm = new ItemMarketForm();
	Cart cartItemForm = new Cart();
	TransactionHistoryForm transactionHistoryForm;
	

	public MainForm(MainFormListener listener) {
		super(new VBox(10), 1000, 500);
		viewBox = (VBox) getRoot();
		setupView();
		itemMarket.setOnAction(this);
		cartItem.setOnAction(this);
		transaction.setOnAction(this);
		transactionHistory.setOnAction(this);
		manageUser.setOnAction(this);
		manageItem.setOnAction(this);
		transaction.setOnAction(this);
		logout.setOnAction(this);
		this.listener = listener;
	}
	
	private void setupView() {
		if (Connectir.userRole.equals("user")) {
			menu.getItems().addAll(itemMarket, cartItem, transactionHistory, logout);
		}
		else {
			menu.getItems().addAll(manageUser, manageItem, transaction, logout);
		}
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		Text welcomeText = new Text("Welcome " + Connectir.username);
		welcomeText.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");
		stackPane.getChildren().addAll(welcomeText);
		stackPane.setAlignment(Pos.CENTER);
		stackPane.setMinSize(viewBox.getWidth(), viewBox.getHeight()-menuBar.getHeight());
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(stackPane);
		root.setAlignment(stackPane, Pos.CENTER);
		viewBox.getChildren().add(root);
	}
	
	@Override
	public void handle(ActionEvent e) {
        MenuItem selectedItem = (MenuItem) e.getSource();
        String name = selectedItem.getText();
        switch (name) {
            case "Item Market":
                showItemMarket();
                break;
            case "Cart Item":
            	System.out.println("1");
                showCart();
                break;
            case "Transaction History":
            	System.out.println("1");
            	showTransactionHistory();
                break;
            case "Manage User":
                manageUser();
                break;
            case "Manage Item":
                manageItem();
                break;
            case "Transaction":
                manageTransaction();
                break;
            case "Logout":
                listener.onLogOutTapped();
                break;
        }
    }
	
	private void manageUser() {
		manageUserForm = new ManageUserForm();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(manageUserForm);
	}
	
	private void manageItem() {
		manageItemForm = new ManageItemForm();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(manageItemForm);
	}
	
	private void manageTransaction() {
		transactionForm = new TransactionForm();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(transactionForm);
	}
	
	private void showItemMarket() {
		itemMarketForm = new ItemMarketForm();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(itemMarketForm);
	}
	
	private void showCart() {
		cartItemForm = new Cart();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(cartItemForm);
	}
	
	private void showTransactionHistory() {
		System.out.println("a");
		transactionHistoryForm = new TransactionHistoryForm();
		stackPane.getChildren().remove(0);
		stackPane.getChildren().add(transactionHistoryForm);
	}
}
