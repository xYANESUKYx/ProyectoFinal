package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import dad.LaLagunaUrbanApp.Bd.usuario;

import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class CreateRutinaController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onGuardar;
	private ArrayList<Ejercicio> ejercicio;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private String id_user = "";
	private String id_grupo = "";
	
	//view 
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
		
	public CreateRutinaController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateRutinaView.fxml"));
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
	
	void rellenarTabla(String grupo, String id_user) {
		gridPane.getChildren().clear();
		
		setId_grupo(grupo);
		setId_user(id_user);
		
	    String query = "SELECT * FROM musculos WHERE grupo = '" + grupo + "'";

	    ejercicio = new ArrayList<>();
	    
	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;
	        int cont = 0;

	        while (resultSet.next()) {
	            String name = resultSet.getString("nombre");
            	String id = resultSet.getString("id");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaEjercicio.fxml"));
	            VBox card;
	            try {
	                card = loader.load();
	                Label nameLabel = (Label) card.lookup("#labelMusculo");
	                nameLabel.setText(name.toUpperCase());
	                try {
			            String[] ejercicios = obtenerEjercicios(id);
	
	
		                Label labelEjercicio1 = (Label) card.lookup("#labelEjercicio1");
		                labelEjercicio1.setText(ejercicios[2]);
		                Label labelEjercicio2 = (Label) card.lookup("#labelEjercicio2");
		                labelEjercicio2.setText(ejercicios[5]);
		                
	                    ImageView imageViewEjercicio1 = (ImageView) card.lookup("#imageViewEjercicio1");
	                    Image image = new Image(ejercicios[1]);
	                    imageViewEjercicio1.setImage(image);
	
	                    ImageView imageView = (ImageView) card.lookup("#imageViewEjercicio2");
	                    Image image2 = new Image(ejercicios[4]);
	                    imageView.setImage(image2);

	                    ejercicio.add(new Ejercicio(id, ejercicios[0], "1"));
		                ejercicio.add(new Ejercicio(id, ejercicios[3], "2"));
	                    
	                }catch (Exception e) {}
		            
	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 190;
	                        double rightMargin = 130;
	                        double cornerSize = 30;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
		                            
	        			            String[] ejercicios = obtenerEjercicios(id);
	      
	        		                Label labelEjercicio1 = (Label) card.lookup("#labelEjercicio2");
	        		                labelEjercicio1.setText(ejercicios[5]);
	        	
	        	                    ImageView imageViewEjercicio1 = (ImageView) card.lookup("#imageViewEjercicio2");
	        	                    Image image = new Image(ejercicios[4]);
	        	                    imageViewEjercicio1.setImage(image);
	        	                    
	        	                    for (Ejercicio ejercicio : ejercicio) {
	        	                        if (ejercicio.getId_musculo().equals(id) && ejercicio.getPosicion().equals("2")) {
	        	                        	ejercicio.setId_ejercicio(ejercicios[3]);
	        	                            break; 
	        	                        }
	        	                    }
	        	                
	                            }
	                        else if (clickX >= (cornerX - 400 )&& clickX <= (cornerX - 400 ) + cornerSize &&
		                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
	                        	
		        			        String[] ejercicios = obtenerEjercicios(id);
	                        	
		        		            Label labelEjercicio1 = (Label) card.lookup("#labelEjercicio1");
		        		            labelEjercicio1.setText(ejercicios[2]);
		        	
		        	                ImageView imageViewEjercicio1 = (ImageView) card.lookup("#imageViewEjercicio1");
		        	                Image image = new Image(ejercicios[1]);
		        	                imageViewEjercicio1.setImage(image);
		        	                
		        	                for (Ejercicio ejercicio : ejercicio) {
	        	                        if (ejercicio.getId_musculo().equals(id) && ejercicio.getPosicion().equals("1")) {
	        	                        	ejercicio.setId_ejercicio(ejercicios[0]);
	        	                            break; 
	        	                        }
	        	                    }
		                    }
	                    }
	                });

	                gridPane.add(card, col, row);
	                col++;
	                if (col == 1) {
	                    col = 0;
	                    row++;
	                }
	            } catch (IOException e) {}
	            cont = cont + 2;
	        }
	    } catch (SQLException e) {}
	}

	@FXML
	void onGuardarClicked(MouseEvent event) {
	    Stage ventanaEmergente = new Stage();
	    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
	    ventanaEmergente.setTitle("Guardar rutina");
	    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

	    Label label = new Label("INGRESE EL NOMBRE PARA\nSU NUEVA RUTINA\n  ");
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
		            guardarRutina(textField.getText());
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
            String query = "SELECT COUNT(*) AS count FROM rutinas WHERE nombre = ? AND id_user = ?";
            
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
	
	void guardarRutina(String nombre) {
		int generatedId = -1; 
	    try {
	        String query = "INSERT INTO rutinas (nombre, id_user, privado, grupo) VALUES (?, ?, ?, ?)";

	        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, nombre);
	            statement.setString(2, id_user);
	            statement.setString(3, "1");
	            statement.setString(4, id_grupo);
	            
	            int filasAfectadas = statement.executeUpdate();

	            if (filasAfectadas == 1) {
	                try (ResultSet rs = statement.getGeneratedKeys()) {
	                    if (rs.next()) {
	                    	generatedId = rs.getInt(1); 
	                    	guardarEjercicios(String.valueOf(String.valueOf(generatedId)));
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {}
	        if (onGuardar != null) { onGuardar.handle(null);}
	    }

	void guardarEjercicios(String id) {
	    if (!id.equals("-1")) {
	        for (Ejercicio ejercicio : ejercicio) {
	            if (ejercicio != null) {
	                System.out.println("id_ejercicio: " + ejercicio.getId_ejercicio() + ", posicion: " + ejercicio.getPosicion());

	                String query = "INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio, posicion) VALUES (?, ?, ?)";

	                try (PreparedStatement statement = connection.prepareStatement(query)) {
	                    statement.setString(1, id);
	                    statement.setString(2, ejercicio.getId_ejercicio());
	                    statement.setString(3, ejercicio.getPosicion());

	                    statement.executeUpdate();

	                    System.out.println("Nuevo registro insertado en la tabla 'rutina_ejercicio'.");
	                } catch (SQLException e) {}
	            }
	        }
	    }
	}
    
    public void setonGuardar(EventHandler<MouseEvent> onGuardar) {
		this.onGuardar = onGuardar;
	}
	  
	public String[] obtenerEjercicios(String id) {
        
        String query = "SELECT * FROM ejercicios WHERE musculo = '" + id + "'";
        
        ArrayList<String> ejercicios = new ArrayList<>();
        ArrayList<String> imagenes = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                ejercicios.add(resultSet.getString("nombre"));
                imagenes.add(resultSet.getString("imagen"));
                ids.add(resultSet.getString("id"));
            }
        } catch (SQLException e) {return new String[0]; }

        if (ejercicios.size() < 2) {return new String[0];}

        String[] resultado = new String[6];

        Random random = new Random();
        int index1 = random.nextInt(ejercicios.size());
        int index2;
        do {
            index2 = random.nextInt(ejercicios.size());
        } while (index2 == index1);

        resultado[0] = ids.get(index1);
        resultado[1] = imagenes.get(index1);
        resultado[2] = ejercicios.get(index1);
        
        resultado[3] = ids.get(index2);
        resultado[4] = imagenes.get(index2);
        resultado[5] = ejercicios.get(index2);

        return resultado;
    }
	
    @FXML
    void onBackClicked(MouseEvent event) {
    	if(onBack != null) onBack.handle(event);
    }
    
    public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}

    public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(String id_grupo) {
		this.id_grupo = id_grupo;
	}

	public static class Ejercicio {
        private String id_musculo;
        private String id_ejercicio;
        private String posicion;

        public Ejercicio(String id_musculo, String id_ejercicio, String posicion) {
            this.id_musculo = id_musculo;
            this.id_ejercicio = id_ejercicio;
            this.posicion = posicion;
        }

        public String getId_musculo() {
            return id_musculo;
        }

        public String getId_ejercicio() {
            return id_ejercicio;
        }
        
        public void setId_ejercicio(String id_ejercicio) {
            this.id_ejercicio = id_ejercicio;
        }

        public String getPosicion() {
            return posicion;
        }
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
}
