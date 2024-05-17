package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import java.util.regex.Pattern;

import dad.LaLagunaUrbanApp.Bd.CryptoUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddUserController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}
	
	//model
	private EventHandler<MouseEvent> onAcceder;
	private EventHandler<MouseEvent> onBack;
	private String nombre_user = "";
	private String password = "";
	
	//view  
    @FXML
    private Button btnButton;

    @FXML
    private Label lblLabel;

    @FXML
    private Label errorLabel;
    
	@FXML
    private PasswordField password1Field;

    @FXML
    private PasswordField password2Field;

    @FXML
    private TextField userField;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddUserController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddUserView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblLabel.setMouseTransparent(true);
	}
    
	@FXML
    void onAccederClicked(MouseEvent event) {

		userField.setStyle("-fx-border-color: white;");
		password2Field.setStyle("-fx-border-color: white;");
		password1Field.setStyle("-fx-border-color: white;");
		
		if(!password1Field.getText().equals("") && !password2Field.getText().equals("") && !userField.getText().equals("")) {
			if(password1Field.getText().equals(password2Field.getText())){
				if(password1Field.getText().length() >= 8) {
					if(Pattern.compile("\\d").matcher(password1Field.getText()).find()) {
						if(Pattern.compile("[A-Z]").matcher(password1Field.getText()).find()) {
							if(buscarUsuario(userField.getText())) {
								userField.setStyle("-fx-border-color: red;");
								errorLabel.setText("Usuario no disponible");
							}else {
								setNombre_user(userField.getText());
								setPassword(CryptoUtils.encryptPassword(password1Field.getText()));
								if(onAcceder != null) onAcceder.handle(event);
							}
						}else {
							errorLabel.setText("La contraseña debe contener al menos 1 mayúscula");
							password1Field.setStyle("-fx-border-color: red;");
							password2Field.setStyle("-fx-border-color: red;");
						}
					}else {
						errorLabel.setText("La contraseña debe contener al menos 1 número");
						password1Field.setStyle("-fx-border-color: red;");
						password2Field.setStyle("-fx-border-color: red;");
					}
				}else {
					errorLabel.setText("La contraseña debe contener al menos 8 caracteres");
					password1Field.setStyle("-fx-border-color: red;");
					password2Field.setStyle("-fx-border-color: red;");
				}
			}else {
				errorLabel.setText("Contraseñas distintas");
				password1Field.setStyle("-fx-border-color: red;");
				password2Field.setStyle("-fx-border-color: red;");
			}
		}else {
			errorLabel.setText("Rellene todos los campos");
			if(password1Field.getText().equals("")){password1Field.setStyle("-fx-border-color: red;");}
			if(password2Field.getText().equals("")){password2Field.setStyle("-fx-border-color: red;");}
			if(userField.getText().equals("")){userField.setStyle("-fx-border-color: red;");}
		}
    }
	
	public boolean buscarUsuario(String nombreUsuario) {
	    String sql = "SELECT id FROM usuarios WHERE nombre_user = ?";
	    boolean existe = false; 
	    
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, nombreUsuario);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	        	existe =  true;
	        }
	    } catch (SQLException e) {e.printStackTrace();}
	    
	    return existe;
	}
    
    public void setonAcceder(EventHandler<MouseEvent> onAcceder) {
		this.onAcceder = onAcceder;
	}

    @FXML
    void onBackClicked(MouseEvent event) {
		userField.setStyle("-fx-border-color: white;");
		password2Field.setStyle("-fx-border-color: white;");
		password1Field.setStyle("-fx-border-color: white;");
    	if(onBack != null) onBack.handle(event);
    }

    public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}

	public String getNombre_user() {
		return nombre_user;
	}

	public void setNombre_user(String nombre_user) {
		this.nombre_user = nombre_user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
