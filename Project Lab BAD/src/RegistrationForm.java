import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegistrationForm extends Scene implements EventHandler<ActionEvent> {
	private VBox viewBox;
	private Button loginButton = new Button("Login Page");
	private Button registerButton = new Button("Register");
	private TextField userID;
	private TextField username;
	private TextField emailField;
	private TextField phoneField;
	private PasswordField passwordField;
	private Spinner ageSpinner;
	private RadioButton maleButton;
	private RadioButton femaleButton;
	private RegistrationFormListener listener;
	private Alert alert;
	private ToggleGroup genderGroup;
	
	public RegistrationForm(RegistrationFormListener listener) {
		super(new VBox(10), 300, 500);
		viewBox = (VBox) getRoot();
		setupView();
		this.listener = listener;
	}
	
	private void setupView() {
		Text registerLabel = new Text("Register");
		registerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 28;");
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
		 userID.setText(Connectir.getEligibleUserID());
		 username = new TextField();
		 emailField = new TextField();
		 phoneField = new TextField();
		
		 passwordField = new PasswordField();
		
		 ageSpinner = new Spinner<>(15,70,16);
		
		 maleButton = new RadioButton("Male");
		 maleButton.setToggleGroup(genderGroup);
		 femaleButton = new RadioButton("Female");
		 femaleButton.setToggleGroup(genderGroup);
		
		
		loginButton.setOnAction(this);
		registerButton.setOnAction(this);
		
		HBox radioBox = new HBox(10);
		HBox buttonBox = new HBox(10);
		
		radioBox.getChildren().addAll(maleButton,femaleButton);
		buttonBox.getChildren().addAll(loginButton,registerButton);
		
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
		grid.add(buttonBox, 1, 7);
		
		alert = new Alert(AlertType.NONE);
		viewBox.getChildren().addAll(registerLabel ,grid);
		viewBox.setAlignment(Pos.CENTER);
		viewBox.setSpacing(30);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == loginButton) {
			listener.onBackToLoginTapped();
		}
		else if (event.getSource() == registerButton) {
			checkValidity();
		}
		
	}
	
	private void checkValidity() {
		if (!checkUsername()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Username must be between 5 - 20 characters!");
			alert.show();
		}
		else if (!checkPassword()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Password must be between 5 - 20 characters! and Alphanumeric");
			alert.show();
		}
		else if (!checkEmail()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Invalid email format");
			alert.show();
		}
		else if (!checkPhone()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Phone number must be between 9 - 12 characters!");
			alert.show();
		}
		else if (!checkAge()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Age must be between 17 - 70 Years!");
			alert.show();
		}
		else if (!checkGender()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("REGISTRATION ERROR");
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
			Connectir.registerUser(userID.getText(),
					username.getText(), 
					passwordField.getText(), 
					gender, 
					emailField.getText(),
					phoneField.getText(),
					(int) ageSpinner.getValue(),
					"user");
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Click OK to continue");
			alert.setTitle("REGISTRATION SUCCESS");
			Optional<ButtonType> okButtonOptional = alert.showAndWait();
			if (okButtonOptional.get() == ButtonType.OK) {
				listener.onSuccessRegister();
			}
		}
	}
	
	private boolean checkUsername() {
		return username.getText().length() >= 5 && username.getText().length() <= 20;
	}
	
	private boolean checkPassword() {
		return passwordField.getText().length() >= 5 && passwordField.getText().length() <= 20 && passwordField.getText().matches("^[a-zA-Z0-9]*$");
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
		return age >= 17 && age <= 70;
	}
	
	private boolean checkGender() {
		return femaleButton.isSelected() || maleButton.isSelected();
	}
}
