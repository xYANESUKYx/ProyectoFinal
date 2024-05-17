package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import dad.LaLagunaUrbanApp.Bd.CryptoUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.concurrent.Task;

public class LoginController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onAcceder;
	private EventHandler<MouseEvent> onAddUser;
	private int id = 0;
	
	//view  
    @FXML
    private ProgressIndicator prIndicator;
    
    @FXML
    private Button btnButton;

    @FXML
    private Label lblLabel;
    
    @FXML
    private Label errorLabel;
    
	@FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public LoginController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblLabel.setMouseTransparent(true);
		passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	onAccederClicked(null);
            }
        });
    }
	
	@FXML
	void onAccederClicked(MouseEvent event) {
	        if (!userField.getText().equals("") && !passwordField.getText().equals("")) {
	            String nombre_user = userField.getText();
	            String password = CryptoUtils.encryptPassword(passwordField.getText());
	            int idUsuario = obtenerIdUsuario(password, nombre_user);

	            if (idUsuario != -1) {
	                errorLabel.setText("");
	            	userField.setVisible(false);
	            	passwordField.setVisible(false);
	        	    prIndicator.setVisible(true);

	        	    Task<Void> task = new Task<Void>() {
	        	        @Override
	        	        protected Void call() throws Exception {
	        	            for (int i = 0; i < 90; i++) {
	        	                Thread.sleep(20);
	        	                updateProgress(i, 99); 
	        	            }
	        	            updateProgress(90, 99);
	        	            return null;
	        	        }
	        	    };
	        	    prIndicator.progressProperty().bind(task.progressProperty());

	        	    task.setOnSucceeded(e -> {
	                errorLabel.setText("");
	                setId(idUsuario);
	            	userField.setVisible(true);
	            	passwordField.setVisible(true);
	        	    prIndicator.setVisible(false);
	                if (onAcceder != null) onAcceder.handle(event);
	        	    });
	        	    new Thread(task).start();
	            } else {
	                errorLabel.setText("Credenciales incorrectas.");
	                userField.setStyle("-fx-border-color: red;");
	                passwordField.setStyle("-fx-border-color: red;");
	            }
	        } else {
	            errorLabel.setText("Rellene todos los campos");
	            if (passwordField.getText().equals("")) {
	                passwordField.setStyle("-fx-border-color: red;");
	            }
	            if (userField.getText().equals("")) {
	                userField.setStyle("-fx-border-color: red;");
	            }
	        }
	}
	
	public int obtenerIdUsuario(String password, String nombreUsuario) {
	    String sql = "SELECT id FROM usuarios WHERE password = ? AND nombre_user = ?";
	    int idUsuario = -1; 
	    
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, password);
	        statement.setString(2, nombreUsuario);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	            idUsuario = resultSet.getInt("id");
	        }
	    } catch (SQLException e) {
	    	
	    	System.err.println("Error al obtener el ID del usuario:");		
	    	System.err.println("*****************************************");    	
	    	System.err.println(e.getMessage());	    	
	    	System.err.println("*****************************************");
        e.printStackTrace();}
	    
	    return idUsuario;
	}

    
    public void setonAcceder(EventHandler<MouseEvent> onAcceder) {
		this.onAcceder = onAcceder;
	}

    @FXML
    void onAddUserClick(MouseEvent event) {
		errorLabel.setText("");
		userField.setStyle("-fx-border-color: white;");
		passwordField.setStyle("-fx-border-color: white;");
    	if(onAddUser != null) onAddUser.handle(event);
    }
    
    public void setonAddUser(EventHandler<MouseEvent> onAddUser) {
		this.onAddUser = onAddUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
