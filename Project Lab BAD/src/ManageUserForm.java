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

public class ManageUserForm extends HBox implements EventHandler<ActionEvent> {
	private TableView<User> tableView;
	private TextField userID;
	private TextField username;
	private TextField emailField;
	private TextField phoneField;
	private TextField passwordField;
	private Spinner ageSpinner;
	private RadioButton maleButton;
	private RadioButton femaleButton;
	private RegistrationFormListener listener;
	private Alert alert;
	private ToggleGroup genderGroup;
	private Button updateButton;
	private Button deleteButton;
	
	public ManageUserForm() {
		super();
		setupView();
	}
	
	public void setupView() {
		Text userIdLabel = new Text("User ID");
		Text usernameLabel = new Text("Username");
		Text passwordLabel = new Text("Password");
		Text emailLabel = new Text("Email");
		Text phoneLabel = new Text("Phone Number");
		Text ageLabel = new Text("Age");
		Text GenderLabel = new Text("Gender");
		genderGroup = new ToggleGroup();
		
		 userID = new TextField();
		 userID.setEditable(false);
		 userID.setDisable(true);
		 username = new TextField();
		 username.setEditable(false);
		 username.setDisable(true);
		 emailField = new TextField();
		 phoneField = new TextField();
		
		 passwordField = new TextField();
		 passwordField.setEditable(false);
		 passwordField.setDisable(true);
		
		 ageSpinner = new Spinner<>(15,70,16);
		
		 maleButton = new RadioButton("Male");
		 maleButton.setToggleGroup(genderGroup);
		 femaleButton = new RadioButton("Female");
		 femaleButton.setToggleGroup(genderGroup);
		updateButton = new Button("Update User");
		deleteButton = new Button("Delete User");
		
		
		updateButton.setOnAction(this);
		deleteButton.setOnAction(this);
		
		HBox radioBox = new HBox(10);
		HBox buttonBox = new HBox(10);
		
		radioBox.getChildren().addAll(maleButton,femaleButton);
		buttonBox.getChildren().addAll(updateButton,deleteButton);
		buttonBox.setHgrow(deleteButton, Priority.ALWAYS);
		buttonBox.setHgrow(updateButton, Priority.ALWAYS);
		buttonBox.setAlignment(Pos.CENTER);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
		grid.add(userIdLabel, 0, 0);
		grid.add(usernameLabel, 0, 1);
		grid.add(passwordLabel, 0, 2);
		grid.add(emailLabel, 0, 3);
		grid.add(phoneLabel, 0, 4);
		grid.add(ageLabel, 0, 5);
		grid.add(GenderLabel, 0, 6);
		
		grid.add(userID, 1, 0);
		grid.add(username, 1, 1);
		grid.add(passwordField, 1, 2);
		grid.add(emailField, 1, 3);
		grid.add(phoneField, 1, 4);
		grid.add(ageSpinner, 1, 5);
		grid.add(radioBox, 1, 6);
		
		alert = new Alert(AlertType.NONE);
		VBox viewBox = new VBox();
		viewBox.getChildren().addAll(grid, buttonBox);
		viewBox.setAlignment(Pos.TOP_CENTER);
		viewBox.setMargin(grid, new Insets(20, 20, 10, 20));
		viewBox.setMargin(buttonBox, new Insets(0, 20, 20, 20));
		buttonBox.setMinWidth(grid.getWidth());
		tableView = new TableView<User>();
		TableColumn<User, ?> userIDColomn = new TableColumn<>("userID");
		userIDColomn.setCellValueFactory(new PropertyValueFactory<>("userID"));
		TableColumn<User, ?> usernameColumn = new TableColumn<>("username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<User, ?> passwordColumn = new TableColumn<>("password");
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		TableColumn<User, ?> emailColumn = new TableColumn<>("email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<User, ?> phonenumberColumn = new TableColumn<>("phoneNumber");
		phonenumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		TableColumn<User, ?> ageColumn = new TableColumn<>("age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		TableColumn<User, ?> genderColumn = new TableColumn<>("gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tableView.setItems(Connectir.fetchUser());
		tableView.getColumns().addAll(userIDColomn,usernameColumn,passwordColumn,emailColumn,phonenumberColumn,ageColumn,genderColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        configureForm(newSelection);
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
			checkValidity();
		} else if (e.getSource() == deleteButton) {
			deleteUser();
		}
		
	}
	
	private void configureForm(User user) {
		userID.setText(user.getUserID());
		username.setText(user.getUsername());
		passwordField.setText(user.getPassword());
		emailField.setText(user.getEmail());
		phoneField.setText(user.getPhoneNumber());
		ageSpinner.getValueFactory().setValue(user.getAge());
		
		if (user.getGender().equals("Male")) {
			maleButton.setSelected(true);
		}
		else {
			femaleButton.setSelected(true);
		}
	}
	
	private void checkValidity() {
		if (!checkPhone()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("UPDATE FAILED");
			alert.setHeaderText(null);
			alert.setContentText("Phone number must be between 9 - 12 characters!");
			alert.show();
		}
		else if (!checkAge()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("UPDATE FAILED");
			alert.setHeaderText(null);
			alert.setContentText("Age must be between 17 - 60 Years!");
			alert.show();
		}
		else if (!checkGender()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("UPDATE FAILED");
			alert.setHeaderText(null);
			alert.setContentText("Gender must be selected");
			alert.show();
		}
		else {
			String gender;
			if (maleButton.isSelected()) {
				gender = "Male";
			}
			else {
				gender = "Female";
			}
			Connectir.updateUser(userID.getText(),
					gender, 
					emailField.getText(),
					phoneField.getText(),
					(int) ageSpinner.getValue());
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Click OK to continue");
			alert.setTitle("UPDATE SUCCESS");
			Optional<ButtonType> okButtonOptional = alert.showAndWait();
			if (okButtonOptional.get() == ButtonType.OK) {
				updateData();
			}
		}
	}
	
	private boolean checkEmail() {
		boolean isValid = true;
		String email = emailField.getText();
		
		if (email.startsWith("@") || !email.contains("@") || !email.endsWith(".com")) {
			isValid = false;
		}
		return isValid;
	}
	
	private boolean checkPhone() {
		return phoneField.getText().length() >= 9 && phoneField.getText().length() <= 12;
	}
	
	private boolean checkAge() {
		int age = (int) ageSpinner.getValue();
		return age >= 17 && age <= 60;
	}
	
	private boolean checkGender() {
		return femaleButton.isSelected() || maleButton.isSelected();
	}
	
	private void deleteUser() {
		Connectir.deleteUser(userID.getText());
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
		tableView.setItems(Connectir.fetchUser());
	}
}
