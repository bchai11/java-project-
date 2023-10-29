import java.security.KeyStore.PrivateKeyEntry;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class LoginForm extends Scene implements EventHandler<ActionEvent> {
	private VBox viewBox;
	Button loginButton;
	Button registerButton;
	Text titleText;
	TextField username;
	PasswordField password;
	Text usernameText;
	Text passwordText;
	Alert alert = new Alert(AlertType.NONE);
	LoginFormListener listener;
	
	public LoginForm(LoginFormListener listener) {
		super(new VBox(),300, 300);
		viewBox = (VBox) getRoot();
		setupView();
		this.listener = listener;
	}
	
	private void setupView() {
		loginButton = new Button();
		loginButton.setText("Login");
		loginButton.setOnAction(this);
		
		registerButton = new Button();
		registerButton.setText("Register Account Page");
		registerButton.setOnAction(this);
		
		titleText = new Text();
		titleText.setText("Login");
		titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 28;");
		
		username = new TextField();
		
		usernameText = new Text();
		usernameText.setText("Username");
		
		password = new PasswordField();
		
		passwordText = new Text();
		passwordText.setText("Password");
		
		VBox stack = new VBox();
		stack.setAlignment(Pos.CENTER);
		HBox buttonStack = new HBox();
		buttonStack.setSpacing(10.0);
		buttonStack.getChildren().addAll(registerButton, loginButton);
		GridPane grid = new GridPane();
				
		grid.setMinSize(300, 200);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		
		grid.add(usernameText, 0 , 0);
		grid.add(username, 1 , 0);
		grid.add(passwordText, 0 , 1);
		grid.add(password, 1 , 1);
		grid.add(buttonStack, 1, 2);
		stack.getChildren().addAll(titleText, grid);
		
		viewBox.getChildren().add(stack);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == loginButton) {
			if (checkValidity()) {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("LOGIN SUCCESS");
				alert.setContentText("Click OK to Continue");
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					listener.onLoginSuccess();
				}
			}
		}
		else if(event.getSource() == registerButton) {
			listener.onRegisterButtonTapped();
		}
	}
	
	private boolean checkValidity() {
		boolean isValid = true;
		
		if (username.getText().isBlank() || password.getText().isBlank()) {
			isValid = false;
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("LOGIN FAILED");
			alert.setContentText("Username or Passwrod cannot be null!");
		}
		
		if (!Connectir.checkUser(username.getText(), password.getText())) {
			isValid = false;
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("LOGIN FAILED");
			alert.setContentText("Wrong Username or Passwrod");
		}
		return isValid;
	}
}
