import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TransactionHistoryForm extends VBox {
	TableView<Transaction> tableView;
	public TransactionHistoryForm() {
		super();
		setupView();
		super.getChildren().add(tableView);
	}
	
	private void setupView() {
		tableView = new TableView<Transaction>();
		TableColumn<Transaction, ?> transactionIDColumn = new TableColumn<>("transactionID");
		TableColumn<Transaction, ?> transactionDate = new TableColumn<>("Transaction Date");
		TableColumn<Transaction, ?> totalPriceColumn = new TableColumn<>("Total Price");
		TableColumn<Transaction, ?> qtyColumn = new TableColumn<>("Quantity");
		TableColumn<Transaction, ?> itemNameColumn = new TableColumn<>("ItemName");
		TableColumn<Transaction, ?> itemDesColumn = new TableColumn<>("Item Description");
		TableColumn<Transaction, ?> priceColumn = new TableColumn<>("Price");
		transactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemDesColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		tableView.getColumns().addAll(transactionIDColumn,itemNameColumn,itemDesColumn,priceColumn,qtyColumn,totalPriceColumn,transactionDate);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(Connectir.fetchTransactionHistory());
	}
}
