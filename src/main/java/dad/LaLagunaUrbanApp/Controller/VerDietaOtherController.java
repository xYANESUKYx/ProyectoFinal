package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class VerDietaOtherController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onGuardar;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onPerfilOther;
	private ArrayList<Comida> comidas;
    
    private int numComidas = 0;
    //private int calorias = 0;
    //private String grupo;
	private String id_user = "";
	private String nombre_user = "";
	private String imagen = "";

	//view
    @FXML
    private Label nombreLabelOther;

    @FXML
    private ImageView userImageOther;

	@FXML
    private GridPane gridPane;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView userImage;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public VerDietaOtherController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VerDietaViewOther.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
	}
	
	
	void datosUser(String id_dieta) {

		String query = "SELECT u.id, u.nombre_user, u.avatar FROM usuarios u " +
                "INNER JOIN dietas d ON u.id = d.id_user " +
                "WHERE d.id = ?";

	    Map<String, Object> datosUsuario = new HashMap<>();

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, id_dieta);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            datosUsuario.put("id", resultSet.getString("id"));
	            datosUsuario.put("nombre_user", resultSet.getString("nombre_user"));
	            datosUsuario.put("avatar", resultSet.getString("avatar"));

	            String id = (String) datosUsuario.get("id");
	    	    setId_user(id);
	            
	            String nombreUsuario = (String) datosUsuario.get("nombre_user");
	            nombreLabelOther.setText(nombreUsuario);

	            String avatar = (String) datosUsuario.get("avatar");
	            userImageOther.setImage(new Image(getimagenAvatar(avatar)));
	            
	    		this.nombre_user = nombreUsuario;
	    		this.imagen = getimagenAvatar(avatar);
	        }
	    } catch (SQLException e) {}
	    
		nombreLabelOther.setText(nombre_user);
		
		userImageOther.setImage(new Image(imagen));
	}
	
	public void rellenarTabla(String id_dieta, String nombreDietaString) {
		datosUser(id_dieta);
		
	    gridPane.getChildren().clear();

	    // Consulta para obtener las comidas de la dieta
	    String query = "SELECT c.descripcion, c.grupo, c.imagen " +
	                   "FROM dieta_comida dc " +
	                   "INNER JOIN comida c ON dc.id_comida = c.id " +
	                   "WHERE dc.id_dieta = '" + id_dieta + "'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String descripcion = resultSet.getString("descripcion");
	            String grupo = resultSet.getString("grupo");
	            String imagen = resultSet.getString("imagen");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaVerDieta.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(descripcion);
	                
	                Label grupoLabel = (Label) card.lookup("#grupoLabel");
	                grupoLabel.setText(grupo.substring(0, 1).toUpperCase() + grupo.substring(1));
	                
	                ImageView imageView = (ImageView) card.lookup("#imageView");
	                Image image = new Image(imagen);
	                imageView.setImage(image);

	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                        // Aquí puedes agregar la lógica para manejar el clic en la tarjeta de comida
	                    }
	                });

	                gridPane.add(card, col, row);
	                col++;
	                if (col == 1) {
	                    col = 0;
	                    row++;
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
	    }
	}
	

	
	
	@FXML
	void onInicioClicked(MouseEvent event) {
		if(onInicio != null) onInicio.handle(event);
	}
		 
	public void setonInicio(EventHandler<MouseEvent> onInicio) {
		this.onInicio = onInicio;
	}
	 
	 @FXML
	 void onDietaClicked(MouseEvent event) {
	 	if(onDietas != null) onDietas.handle(event);
	 }
	 
	 public void setonDietas(EventHandler<MouseEvent> onDietas) {
		this.onDietas = onDietas;
	}
	 
	 @FXML
	 void onSuplementosClicked(MouseEvent event) {
		 if(onSuplementos != null) onSuplementos.handle(event);
	 }
	 
	 public void setonSuplementos(EventHandler<MouseEvent> onSuplementos) {
		this.onSuplementos = onSuplementos;
	}  

	 @FXML
	 void onUserClicked(MouseEvent event) {
		 if(onUsuario != null) onUsuario.handle(event);
	 }
	 
	 public void setonUsuario(EventHandler<MouseEvent> onUsuario) {
		this.onUsuario = onUsuario;
	}  
	 
	 @FXML
	 void onRutinasClicked(MouseEvent event) {
			if(onRutinas != null) onRutinas.handle(event);
	 }
		 
	 public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	 }
	 
	 public void setonGuardar(EventHandler<MouseEvent> onGuardar) {
		this.onGuardar = onGuardar;
	}

		@FXML
		void onGuardarClicked(MouseEvent event) {
		    Stage ventanaEmergente = new Stage();
		    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
		    ventanaEmergente.setTitle("Guardar dieta");
		    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

		    Label label = new Label("INGRESE EL NOMBRE PARA\nSU NUEVA DIETA\n  ");
		    label.setTextAlignment(TextAlignment.CENTER);
		    label.setFont(Font.font("DejaVu Sans Condensed", FontWeight.BOLD, 30));
		    label.setTextFill(Color.web("#febc04")); 
		    label.setAlignment(Pos.CENTER);

		    TextField textField = new TextField();
		    textField.setFont(Font.font("Arial", FontWeight.BOLD, 22));
		    textField.setPrefWidth(200);
		    textField.setMaxWidth(200);
		    textField.setPromptText("Nombre"); 
		    
		    textField.textProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue.length() > 9) {
		            textField.setText(oldValue);
		        }
		    });

		    Image image = new Image(getClass().getResourceAsStream("/images/check2.png"));
		    ImageView imageView = new ImageView(image);

		    imageView.setFitWidth(40);
		    imageView.setFitHeight(40);
		    
		    Button botonConfirmar = new Button("", imageView);
		    botonConfirmar.setPrefWidth(30);
		    
		    botonConfirmar.setOnMouseEntered(mouseEvent -> imageView.setOpacity(0.5));
		    botonConfirmar.setOnMouseExited(mouseEvent -> imageView.setOpacity(1.0));

		    botonConfirmar.setStyle("-fx-background-color: black;");
		    
		    botonConfirmar.setOnAction(e -> {
		    	if (textField.getText().length() != 0) {
		    		if(existeRegistro(textField.getText())) {
			    		textField.setStyle("-fx-border-color: red;");
			    	    textField.setPromptText("Nombre en uso"); 
		    		}else {
			            guardarDieta(textField.getText());
			            ventanaEmergente.close();
		    		}
		        }
		    	else {
		    		textField.setStyle("-fx-border-color: red;");
		    	    textField.setPromptText("Inserte un nombre"); 
		    	}
		    });

		    HBox hbox = new HBox(10);
		    hbox.getChildren().addAll(textField, botonConfirmar);
		    hbox.setAlignment(Pos.CENTER);

		    VBox layout = new VBox(10);
		    layout.getChildren().addAll(label, hbox);
		    layout.setAlignment(Pos.CENTER);

		    Scene escena = new Scene(layout, 600, 250);	    
		    layout.setStyle("-fx-background-color: black"); 
		    ventanaEmergente.setScene(escena);

		    ventanaEmergente.showAndWait();
		}
		
		public boolean existeRegistro(String nombre) {
	        boolean existe = false;
	        
	        try {
	            String query = "SELECT COUNT(*) AS count FROM dietas WHERE nombre = ? AND id_user = ?";
	            
	            try (PreparedStatement statement = connection.prepareStatement(query)) {
	                statement.setString(1, nombre);
	                statement.setString(2, id_user);
	                
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        int count = resultSet.getInt("count");
	                        existe = count > 0;
	                    }
	                }
	            }
	        } catch (SQLException e) {}
	        return existe;
	    }
		
		void guardarDieta(String nombre) {
			int generatedId = -1; 
		    try {
		        String query = "INSERT INTO dietas (nombre, id_user, privado, num_comidas) VALUES (?, ?, ?, ?)";

		        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		            statement.setString(1, nombre);
		            statement.setString(2, id_user);
		            statement.setString(3, "1");
		            statement.setString(4, "" + numComidas);
		            
		            int filasAfectadas = statement.executeUpdate();

		            if (filasAfectadas == 1) {
		                try (ResultSet rs = statement.getGeneratedKeys()) {
		                    if (rs.next()) {
		                    	generatedId = rs.getInt(1); 
		                    	guardarComidas(String.valueOf(String.valueOf(generatedId)));
		                    }
		                }
		            }
		        }
		    } catch (SQLException e) {}
		        if (onGuardar != null) { onGuardar.handle(null);}
		    }
		void guardarComidas(String id) {
		    if (!id.equals("-1")) {
		        for (Comida comida : comidas) {
		            if (comida != null) {
		                System.out.println("id comida: " + comida.getId_comida()+ ", grupo: " + comida.getGrupo());

		                String query = "INSERT INTO dieta_comida (id_dieta, id_comida, grupo) VALUES (?, ?, ?)";

		                try (PreparedStatement statement = connection.prepareStatement(query)) {
		                    statement.setString(1, id);
		                    statement.setString(2, comida.getId_comida());
		                    statement.setString(3, comida.getGrupo());

		                    statement.executeUpdate();

		                    System.out.println("Nuevo registro insertado en la tabla 'rutina_ejercicio'.");
		                } catch (SQLException e) {}
		            }
		        }
		    }
		}
		
		
	public static class Comida {  
		private String id_comida;
		private String grupo;

	    public Comida(String id_comida, String grupo) {
	          this.id_comida = id_comida;
	          this.grupo = grupo;
	    }

        public String getId_comida() {
            return id_comida;
        }
        
        public void setId_comida(String id_comida) {
            this.id_comida = id_comida;
        }

        public String getGrupo() {
            return grupo;
        }
        
        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }
	}
	

	@FXML
	void onPerfilClicked(MouseEvent event) {
		 if(onPerfilOther != null) onPerfilOther.handle(event);
	 }
	 
	 public void setonPerfilOther(EventHandler<MouseEvent> onPerfilOther) {
		this.onPerfilOther = onPerfilOther;
	}
	 
	 public String getId_user() {
			return id_user;
		}

		public void setId_user(String id_user) {
			this.id_user = id_user;
		}
		
		public String getImagen() {
			return imagen;
		}

		public void setImagen(String imagen) {
			this.imagen = imagen;
		}

		public String getNombre_user() {
			return nombre_user;
		}

		public void setNombre_user(String nombre_user) {
			this.nombre_user = nombre_user;
		}
		
		public String getimagenAvatar(String avatar) {

			String imagen = "/avatar1.png";
	        
			switch (avatar) {
	        case "1":
	    		imagen = "/avatares/avatar1.png";
	            break;
	        case "2":
	    		imagen = "/avatares/avatar2.png";
	            break;
	        case "3":
	    		imagen = "/avatares/avatar3.png";
	            break;
	        case "4":
	    		imagen = "/avatares/avatar4.png";
	            break;
	        case "5":
	    		imagen = "/avatares/avatar5.png";
	            break;
	        case "6":
	    		imagen = "/avatares/avatar6.png";
	            break;
	        case "7":
	    		imagen = "/avatares/avatar7.png";
	            break;
	        case "8":
	    		imagen = "/avatares/avatar8.png";
	            break;
	        case "9":
	    		imagen = "/avatares/avatar9.png";
	            break;
	        case "10":
	    		imagen = "/avatares/avatar10.png";
	            break;
	        default:
	            break;
			}
			return imagen;
		}
}
