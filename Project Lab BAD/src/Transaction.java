
public class Transaction {
	private String transactionID;
	private String buyer;
	private String itemName;
	private int price;
	private int quantity;
	private int totalPrice;
	private String transactionDate;
	private String desc;
	public Transaction(String transactionID, String buyer, String itemName, int price, int quantity, int totalPrice,
			String transactionDate, String desc) {
		super();
		this.transactionID = transactionID;
		this.buyer = buyer;
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.transactionDate = transactionDate;
		this.desc = desc;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
