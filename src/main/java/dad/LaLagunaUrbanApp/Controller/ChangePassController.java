package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.regex.Pattern;
import dad.LaLagunaUrbanApp.Bd.CryptoUtils;
import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class ChangePassController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}
	
	//model
	private EventHandler<MouseEvent> onSalir;
	private EventHandler<MouseEvent> onChangeUser;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	//private EventHandler<MouseEvent> onAdd;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onBorrarCuenta;
	private String id_user = "";
	private String name_user = "";

	//view
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private Button passButton;

    @FXML
    private PasswordField password1Field;

    @FXML
    private PasswordField password2Field;

    @FXML
    private PasswordField password3Field;

    @FXML
    private Button userButton;

    @FXML
    private TextField userField;

    @FXML
    private ImageView userImage;

    @FXML
    private ScrollPane view;
	
	public ScrollPane getView() {
		return view;
	}
		
	public ChangePassController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChangePassView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        
		passButton.setDisable(true);
		userButton.setDisable(true);
		
		password2Field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.compile("\\d").matcher(newValue).find()) {
                    label1.setStyle("-fx-text-fill: green !important;");
                    label1.setText("✅ Mínimo 1 número");
                } else {
                    label1.setStyle("-fx-text-fill: red !important;");
                    label1.setText("❌ Mínimo 1 número");
                }
                if (Pattern.compile("[A-Z]").matcher(newValue).find()) {
                    label2.setStyle("-fx-text-fill: green !important;");
                    label2.setText("✅ Mínimo 1 mayúscula");
                } else {
                    label2.setStyle("-fx-text-fill: red !important;");
                    label2.setText("❌ Mínimo 1 mayúscula");
                }
                if (newValue.length() >= 8) {
                    label3.setStyle("-fx-text-fill: green !important;");
                    label3.setText("✅ Mínimo 8 caracteres");
                } else {
                    label3.setStyle("-fx-text-fill: red !important;");
                    label3.setText("❌ Mínimo 8 caracteres");
                }
            }
        });
		
        password1Field.textProperty().addListener((obs, oldText, newText) -> {
        	
        	if(label1.getStyle().contains("-fx-text-fill: green !important;") &&
				    label2.getStyle().contains("-fx-text-fill: green !important;") &&
				    label3.getStyle().contains("-fx-text-fill: green !important;") &&
				    !password1Field.getText().equals("")&&
				    !password3Field.getText().equals("")) {

            	passButton.setDisable(false);
				
            }else {
            	passButton.setDisable(true);
            }
        });
		
        password2Field.textProperty().addListener((obs, oldText, newText) -> {
        	
        	if(label1.getStyle().contains("-fx-text-fill: green !important;") &&
				    label2.getStyle().contains("-fx-text-fill: green !important;") &&
				    label3.getStyle().contains("-fx-text-fill: green !important;") &&
				    !password1Field.getText().equals("")&&
				    !password3Field.getText().equals("")) {

            	passButton.setDisable(false);
				
            }else {
            	passButton.setDisable(true);
            }
        });
		
        password3Field.textProperty().addListener((obs, oldText, newText) -> {
        	
        	if(label1.getStyle().contains("-fx-text-fill: green !important;") &&
				    label2.getStyle().contains("-fx-text-fill: green !important;") &&
				    label3.getStyle().contains("-fx-text-fill: green !important;") &&
				    !password1Field.getText().equals("")&&
				    !password3Field.getText().equals("")) {

            	passButton.setDisable(false);
				
            }else {
            	passButton.setDisable(true);
            }
        });
		
		userField.textProperty().addListener((obs, oldText, newText) -> {
			if(newText.equals("") || userField.getText().equals(name_user)) {
				userButton.setDisable(true);
			}else {
				userButton.setDisable(false);
			}
		});
	}
	void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
		userField.setText(usuario.getNombre_user());
        
        this.name_user = usuario.getNombre_user();
        this.id_user = usuario.getId_usuario() + "";
		
		password1Field.setStyle("");
		password2Field.setStyle("");
		password3Field.setStyle("");
		userField.setStyle("");
		
		password1Field.setText("");
		password2Field.setText("");
		password3Field.setText("");
	}

    @FXML
    void onPassClicked(MouseEvent event) {
    	String password = "";
	    String query = "SELECT password FROM usuarios WHERE id = '" + id_user + "'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        if (resultSet.next()) {
	            password = resultSet.getString("password");
	        }
	        
	   } catch (SQLException e) {}
    	
    	if(password1Field.getText().equals(CryptoUtils.decryptPassword(password))) {
    		if(password2Field.getText().equals(password3Field.getText())) {

    			String querySelect = "SELECT password FROM usuarios WHERE id = ?";
    			String queryUpdate = "UPDATE usuarios SET password = ? WHERE id = ?";

    			try (PreparedStatement selectStatement = connection.prepareStatement(querySelect);
    			     PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {

    			    selectStatement.setString(1, id_user);
    			    ResultSet resultSet = selectStatement.executeQuery();

    			    if (resultSet.next()) {
    			        updateStatement.setString(1, CryptoUtils.encryptPassword(password2Field.getText()));
    			        updateStatement.setString(2, id_user);
    			        updateStatement.executeUpdate();
    			    } 

    			} catch (SQLException e) { e.printStackTrace(); }

    			alerta("Cambio de contraseña", "La contraseña se ha\nmodificado correctamente.\n");

                label1.setText(" ");
                label2.setText(" ");
                label3.setText(" ");
                
    			password1Field.setStyle("");
    			password2Field.setStyle("");
    			password3Field.setStyle("");
    			
        		password1Field.setText("");
        		password2Field.setText("");
        		password3Field.setText("");
        		
    		}else {
        		alerta("Error", "Error!! Las contraseñas\nno coinciden.\n");
        		password2Field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        		password3Field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        	}
    	}else {
    		alerta("Error", "Error!! La contraseña\nactual no es correcta.\n");
    		password1Field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

    	}
    }

    public void validarContraseñas() {
        String password2 = password2Field.getText();
        
        String regexLongitud = ".{8,}";
        String regexMayuscula = "(?=.*[A-Z])";
        String regexMinuscula = "(?=.*[a-z])";
        String regexNumero = "(?=.*\\d)";

        boolean cumpleLongitud = Pattern.matches(regexLongitud, password2);
        boolean cumpleMayuscula = Pattern.matches(regexMayuscula, password2);
        boolean cumpleMinuscula = Pattern.matches(regexMinuscula, password2);
        boolean cumpleNumero = Pattern.matches(regexNumero, password2);

        if (!cumpleLongitud || !cumpleMayuscula || !cumpleMinuscula || !cumpleNumero) {
            if (!cumpleLongitud) {
                password2Field.setStyle("-fx-border-color: red;");
            }
            if (!cumpleMayuscula) {
                password2Field.setStyle("-fx-border-color: red;");
            }
            if (!cumpleMinuscula) {
                password2Field.setStyle("-fx-border-color: red;");
            }
            if (!cumpleNumero) {
                password2Field.setStyle("-fx-border-color: red;");
            }
        } else {
            password2Field.setStyle("");
        }
    }
    
    @FXML
    void onSalirClicked(MouseEvent event) {
		if(onSalir != null) onSalir.handle(event);
    }
    
    public void setonSalir(EventHandler<MouseEvent> onSalir) {
		this.onSalir = onSalir;
	}

    @FXML
    void onChangeUserClicked(MouseEvent event) {
        password2Field.setStyle("-fx-border-color: white;");
    	
    	String newUserName = userField.getText();
        String userId = id_user; 

        String querySelect = "SELECT nombre_user FROM usuarios WHERE nombre_user = ?";
        String queryUpdate = "UPDATE usuarios SET nombre_user = ? WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(querySelect);
             PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {

        	selectStatement.setString(1, newUserName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
        		alerta("Error", "Error!! Ya existe un usuario\ncon ese nombre.\n");
        		userField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            } else {
                updateStatement.setString(1, newUserName);
                updateStatement.setString(2, userId);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
            		alerta("Cambio de nombre", "Nombre de usuario actualizado\nexitosamente.\n ");
            		userField.setStyle("");
            		setName_user(newUserName);
                } 
            }

        } catch (SQLException e) {}
		if(onChangeUser != null) onChangeUser.handle(event);
    }
    
    public void setonChangeUser(EventHandler<MouseEvent> onChangeUser) {
		this.onChangeUser = onChangeUser;
	}

	private void alerta(String titulo, String frase) {
		    Stage ventanaEmergente = new Stage();
		    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
		    ventanaEmergente.setTitle(titulo);
		    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

		    Label label = new Label(frase);
		    label.setTextAlignment(TextAlignment.CENTER);
		    label.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 30));
		    label.setTextFill(Color.web("#febc04")); 
		    label.setAlignment(Pos.CENTER);

		    Button botonCancelar = new Button("Okey");
		    
		    botonCancelar.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 20));
		    
		    botonCancelar.setStyle("-fx-background-color: #febc04;");

		    botonCancelar.setOnAction(e -> {
		    	ventanaEmergente.close();
		    });

		    HBox hbox = new HBox(10);
		    hbox.getChildren().addAll(botonCancelar);
		    hbox.setAlignment(Pos.CENTER);

		    VBox layout = new VBox(10);
		    layout.getChildren().addAll(label, hbox);
		    layout.setAlignment(Pos.CENTER);

		    Scene escena = new Scene(layout, 600, 250);
		    layout.setStyle("-fx-background-color: black"); 
		    ventanaEmergente.setScene(escena);

		    ventanaEmergente.showAndWait();
	    }

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}


	public String getName_user() {
		return name_user;
	}

	public void setName_user(String name_user) {
		this.name_user = name_user;
	}

    @FXML
    void onUserClicked(MouseEvent event) {
    	if(onUsuario != null) onUsuario.handle(event);
    }
    
    public void setonUsuario(EventHandler<MouseEvent> onUsuario) {
		this.onUsuario = onUsuario;
	}

    @FXML
    void onInicioClicked(MouseEvent event) {
	 	if(onInicio != null) onInicio.handle(event);
	 }
	 
	 public void setonInicio(EventHandler<MouseEvent> onInicio) {
		this.onInicio = onInicio;
	}
	 
	 
	 @FXML
	 void onSuplementosClicked(MouseEvent event) {
		 if(onSuplementos != null) onSuplementos.handle(event);
	 }
	 
	 public void setonSuplementos(EventHandler<MouseEvent> onSuplementos) {
		this.onSuplementos = onSuplementos;
	}
	 
	 @FXML
	 void onDietaClicked(MouseEvent event) {
		 if(onDietas != null) onDietas.handle(event);
	 }
	 
	 public void setonDietas(EventHandler<MouseEvent> onDietas) {
		this.onDietas = onDietas;

	 }

	 @FXML
	 void onRutinasClicked(MouseEvent event) {
		 if(onRutinas != null) onRutinas.handle(event);
	 }
	 
	 public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;

	 }
	 
	 @FXML
	 void onBorrarCuentaClicked(MouseEvent event) {
		 alertaBorrarCuenta(id_user);
	 }
	 
	 public void setonBorrarCuenta(EventHandler<MouseEvent> onBorrarCuenta) {
		this.onBorrarCuenta = onBorrarCuenta;

	 }
	 
	 private void alertaBorrarCuenta(String id) {
		    Stage ventanaEmergente = new Stage();
		    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
		    ventanaEmergente.setTitle("Eliminar cuenta");
		    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

		    Label label = new Label("¿Seguro que desea eliminar\nsu cuenta?\n ");
		    label.setTextAlignment(TextAlignment.CENTER);
		    label.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 30));
		    label.setTextFill(Color.web("#febc04")); 
		    label.setAlignment(Pos.CENTER);

		    Button botonCancelar = new Button("Cancelar");
		    Button botonConfirmar = new Button("Eliminar");
		    
		    botonCancelar.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 20));
		    botonConfirmar.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 20));
		    
		    botonCancelar.setStyle("-fx-background-color: #febc04;");
		    botonConfirmar.setStyle("-fx-background-color: #febc04;");

		    botonConfirmar.setOnAction(e -> {
		    	
		    	eliminarDatosUsuario(id_user);
	            
		    	ventanaEmergente.close();

				if(onBorrarCuenta != null) onBorrarCuenta.handle(null);
		    });
		    
		    botonCancelar.setOnAction(e -> {
		    	ventanaEmergente.close();
		    });

		    HBox hbox = new HBox(10);
		    hbox.getChildren().addAll(botonCancelar, botonConfirmar);
		    hbox.setAlignment(Pos.CENTER);

		    VBox layout = new VBox(10);
		    layout.getChildren().addAll(label, hbox);
		    layout.setAlignment(Pos.CENTER);

		    Scene escena = new Scene(layout, 600, 250);
		    layout.setStyle("-fx-background-color: black"); 
		    ventanaEmergente.setScene(escena);
		    ventanaEmergente.showAndWait();
	    }
	 
	 public void eliminarDatosUsuario(String id) {
		    PreparedStatement statement = null;
		    try {

		        // Eliminar registros de la tabla "suplemento_user"
		        String deleteSuplementoUserQuery = "DELETE FROM suplemento_user WHERE id_user = ?";
		        statement = connection.prepareStatement(deleteSuplementoUserQuery);
		        statement.setString(1, id);
		        statement.executeUpdate();

		        // Eliminar registros de la tabla "rutina_ejercicio"
		        String deleteRutinaEjercicioQuery = "DELETE FROM rutina_ejercicio WHERE id_rutina IN (SELECT id FROM rutinas WHERE id_user = ?)";
		        statement = connection.prepareStatement(deleteRutinaEjercicioQuery);
		        statement.setString(1, id);
		        statement.executeUpdate();
		        
		        // Eliminar registros de la tabla "rutinas" y "rutina_ejercicio"
		        String deleteRutinasQuery = "DELETE FROM rutinas WHERE id_user = ?";
		        statement = connection.prepareStatement(deleteRutinasQuery);
		        statement.setString(1, id);
		        statement.executeUpdate();

		        // Eliminar registros de la tabla "usuarios"
		        String deleteUsuariosQuery = "DELETE FROM usuarios WHERE id = ?";
		        statement = connection.prepareStatement(deleteUsuariosQuery);
		        statement.setString(1, id);
		        statement.executeUpdate();

		    } catch (SQLException ex) {ex.printStackTrace(); // Manejo de la excepción, puedes cambiarlo según tus necesidades
		    } finally {
		        if (statement != null) {
		            try {statement.close();
		            } catch (SQLException ex) {}
		        }
		    }
		    alerta("Cuenta eliminada", "Su cuenta ha sido\neliminada con exito.\n ");
		}

	 
}
