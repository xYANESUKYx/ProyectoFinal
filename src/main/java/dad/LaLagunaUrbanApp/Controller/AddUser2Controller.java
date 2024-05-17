package dad.LaLagunaUrbanApp.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddUser2Controller implements Initializable {

	//private Connection connection;
	//public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onAcceder;
	private EventHandler<MouseEvent> onBack;
	private String nombre = "";
	private int edad = 0;
	private Double peso = 0.0;
	private int altura = 0;
	private String avatar = "1";
	private String sexo = "";
	
	//view   
	@FXML
    private TextField alturaText;

    @FXML
    private Button btnButton;

    @FXML
    private TextField edadTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label lblLabel;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField pesoTextField;

    @FXML
    private ComboBox<String> sexoComboBox;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddUser2Controller( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddUser2View.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblLabel.setMouseTransparent(true);
		
		ObservableList<String> opcionesSexo = FXCollections.observableArrayList("Hombre", "Mujer");
		sexoComboBox.setItems(opcionesSexo);
		
		UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 25 && newText.matches("[a-zA-Z\\s]*")) {
                return change;
            } else {
                return null; 
            }
        };
        nombreTextField.setTextFormatter(new TextFormatter<>(filter));
        
        UnaryOperator<TextFormatter.Change> filter2 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}(\\.\\d{0,1})?")) {
                return change;
            } else {
                return null; 
            }
        };
        pesoTextField.setTextFormatter(new TextFormatter<>(filter2));
        
        UnaryOperator<TextFormatter.Change> filter3 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}")) {
                return change;
            } else {
                return null; 
            }
        };
        alturaText.setTextFormatter(new TextFormatter<>(filter3));
    
        UnaryOperator<TextFormatter.Change> filter4 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,2}")) {
                return change;
            } else {
                return null; 
            }
        };
        edadTextField.setTextFormatter(new TextFormatter<>(filter4));
        
	}
    
	@FXML
    void onAccederClicked(MouseEvent event) {

		errorLabel.setText("");
		nombreTextField.setStyle("-fx-border-color: white;");
		edadTextField.setStyle("-fx-border-color: white;");
		sexoComboBox.setStyle("-fx-border-color: white;");
		alturaText.setStyle("-fx-border-color: white;");
		pesoTextField.setStyle("-fx-border-color: white;");
		
		if(!nombreTextField.getText().equals("") && !edadTextField.getText().equals("") && !alturaText.getText().equals("") && 
				!pesoTextField.getText().equals("") && sexoComboBox.getSelectionModel().getSelectedItem() != null) {
			setNombre(nombreTextField.getText());
			setEdad(Integer.parseInt(edadTextField.getText()));
			setSexo(sexoComboBox.getSelectionModel().getSelectedItem().toLowerCase());
			setPeso(Double.parseDouble(pesoTextField.getText()));
			setAltura(Integer.parseInt(alturaText.getText()));
			setAvatar(avatarAleatorio(sexoComboBox.getSelectionModel().getSelectedItem()));
			if(onAcceder != null) onAcceder.handle(event);
		}else {
			errorLabel.setText("Rellene todos los campos");
			if(nombreTextField.getText().equals("")) {nombreTextField.setStyle("-fx-border-color: red;");}
			if(edadTextField.getText().equals("")) {edadTextField.setStyle("-fx-border-color: red;");}
			if(sexoComboBox.getSelectionModel().getSelectedItem() == null) {sexoComboBox.setStyle("-fx-border-color: red;");}
			if(alturaText.getText().equals("")) {alturaText.setStyle("-fx-border-color: red;");}
			if(pesoTextField.getText().equals("")) {pesoTextField.setStyle("-fx-border-color: red;");}
		}
    }

	String avatarAleatorio(String sexo) {
		String avatar = "1";
		
		if(sexo.equals("Mujer")) {
	        avatar = String.valueOf(new Random().nextInt(5) + 1);
	    } else {
	        avatar = String.valueOf(new Random().nextInt(5) + 6);
	    }
		
		return avatar;
	}
	
	
    public void setonAcceder(EventHandler<MouseEvent> onAcceder) {
		this.onAcceder = onAcceder;
	}
	
    @FXML
    void onBackClicked(MouseEvent event) {
		errorLabel.setText("");
		nombreTextField.setStyle("-fx-border-color: white;");
		edadTextField.setStyle("-fx-border-color: white;");
		sexoComboBox.setStyle("-fx-border-color: white;");
		alturaText.setStyle("-fx-border-color: white;");
		pesoTextField.setStyle("-fx-border-color: white;");
		
    	if(onBack != null) onBack.handle(event);
    }

    public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
}
