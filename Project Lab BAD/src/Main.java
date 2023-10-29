import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements RegistrationFormListener, LoginFormListener, MainFormListener {
	Stage primaryStage;
	LoginForm loginForm;
	RegistrationForm registrationForm;
	MainForm mainForm;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		loginForm = new LoginForm(this);
		registrationForm = new RegistrationForm(this);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(loginForm);
		primaryStage.show();
	}

	@Override
	public void onBackToLoginTapped() {
		// TODO Auto-generated method stub
		primaryStage.setScene(loginForm);
		primaryStage.setTitle("Login Page");
	}
	
	@Override
	public void onSuccessRegister() {
		// TODO Auto-generated method stub
		primaryStage.setScene(loginForm);
		primaryStage.setTitle("Login Page");
	}

	@Override
	public void onRegisterButtonTapped() {
		// TODO Auto-generated method stub
		primaryStage.setScene(registrationForm);
		primaryStage.setTitle("Register Page");
		
	}

	@Override
	public void onLoginSuccess() {
		// TODO Auto-generated method stub
		mainForm = new MainForm(this);
		primaryStage.setScene(mainForm);
	}
	
	@Override
	public void onLogOutTapped() {
		primaryStage.setScene(loginForm);
		primaryStage.setTitle("Login Page");
	}
}
