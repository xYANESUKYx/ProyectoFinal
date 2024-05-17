package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class CreateDietaController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onGuardar;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onInicio;
	private ArrayList<Comida> comidas;
    
    private int numComidas = 0;
    private int calorias = 0;
    private String id_user;
    private String grupo;

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
		
	public CreateDietaController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateDietaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	void caloriasComidas(int calorias, int comidas) {
		this.calorias = calorias;
		this.numComidas = comidas;
		
		rellenarTabla();
	}
	

	void cargarDatos(usuario usuario) {
		this.id_user = "" + usuario.getId_usuario();
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
	}
	
	

	
	void rellenarTabla() {
	    comidas = new ArrayList<>();
	    
			int x = 0;
	        int row = 0;
	        int col = 0;

	        while (x < numComidas) {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaDieta.fxml"));
	            VBox card;
	            try {
	            	card = loader.load();
	            	
	            	if(x == 0) {
	            		grupo = "desayuno";
	            	}else if(x == 1) {
	            		if(numComidas > 3) {
		            		grupo = "snack";
		            	}else if(numComidas == 3) {
		            		grupo = "almuerzo";
		            	}
	            	}else if(x == 2) {
	            		if(numComidas > 3) {
		            		grupo = "almuerzo";
		            	}else if(numComidas == 3) {
		            		grupo = "cena";
		            	}
	            	}else if(x == 3) {
	            		if(numComidas == 4) {
		            		grupo = "cena";
		            	}else if(numComidas == 5) {
		            		grupo = "merienda";
		            	}
	            	}else if(x == 4) {
	            		grupo = "cena";
	            	}
	            	
		            String[] comidaCard = obtenerComida(grupo);
		        	
		            String grupoCard = grupo;
		            
	                Label labelComida = (Label) card.lookup("#labelComida");
	                labelComida.setText(grupo.substring(0, 1).toUpperCase() + grupo.substring(1));
		        	
		        	
	                Label labelComidas = (Label) card.lookup("#labelComidas");
	                labelComidas.setText(comidaCard[0]);
	                
                    ImageView imageView = (ImageView) card.lookup("#imageView");
                    Image image = new Image(comidaCard[1]);
                    imageView.setImage(image);

	                this.comidas.add(new Comida(comidaCard[2], grupo));
	                
	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 40;
	                        double rightMargin = 20;
	                        double cornerSize = 30;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {

	                            ContextMenu contextMenu = new ContextMenu();
	                            Label label = new Label(comidaCard[0]);
	                            MenuItem item = new MenuItem();
	                            item.setGraphic(label);
	                            contextMenu.getItems().add(item);

	                            contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
	                        }
	                        else if (clickX >= (cornerX - 40) && clickX <= (cornerX - 40) + cornerSize &&
		                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
		                            
		        		            String[] ejercicios = obtenerComida(grupoCard);
		        		        	
		        	                Label labelComida = (Label) card.lookup("#labelComida");
		        	                labelComida.setText(grupoCard.substring(0, 1).toUpperCase() + grupoCard.substring(1));

		        		        	
		        	                Label labelComidas = (Label) card.lookup("#labelComidas");
		        	                labelComidas.setText(ejercicios[0]);
		        	                
		                            ImageView imageView = (ImageView) card.lookup("#imageView");
		                            Image image = new Image(ejercicios[1]);
		                            imageView.setImage(image);
		                            
									for (Comida comidas : comidas) {
										if (comidas.getGrupo().equals(grupoCard)) {
											comidas.setId_comida(ejercicios[2]);
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
	            } catch (IOException e) {System.out.print("Error: " + e.getMessage()); x = 6;}
	            x++;
	        }
	}
	

	public String[] obtenerComida(String grupo) {
        
        String query = "SELECT * FROM comida WHERE grupo = '" + grupo + "'";
        
        ArrayList<String> descripcion = new ArrayList<>();
        ArrayList<String> imagen = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	descripcion.add(resultSet.getString("descripcion"));
            	imagen.add(resultSet.getString("imagen"));
            	id.add(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            System.out.println("\n*******************************\n");
            System.out.println("ERROR1: " + e.getMessage());
            System.out.println("\n*******************************\n");return new String[0];  }

        if (descripcion.size() < 1) {
            System.out.println("\n*******************************\n");
            System.out.println("ERROR2: ");
            System.out.println("\n*******************************\n"); return new String[0];}

        String[] resultado = new String[3];

        Random random = new Random();
        int index1 = random.nextInt(descripcion.size());

        resultado[0] = descripcion.get(index1);
        resultado[1] = imagen.get(index1);
        resultado[2] = id.get(index1);

        return resultado;
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
		                    	System.out.println(String.valueOf(generatedId));
		                    	guardarComidas(String.valueOf(String.valueOf(generatedId)));
		                    }
		                }
		            }
		        }
		    } catch (SQLException e) {}
		        if (onGuardar != null) { onGuardar.handle(null);}
		    }
		void guardarComidas(String id) {
			String posicion = "";
			
		    if (!id.equals("-1")) {
		        for (Comida comida : comidas) {
                	System.out.println(String.valueOf(comida));
                	
                	if (comida.getGrupo().equals("desayuno")) {
                		posicion = "0";
                	}else if (comida.getGrupo().equals("snack")) {
                		posicion = "1";
                	}else if (comida.getGrupo().equals("almuerzo")) {
                		posicion = "2";
                	}else if (comida.getGrupo().equals("merienda")) {
                		posicion = "3";
                	}else if (comida.getGrupo().equals("cena")) {
                		posicion = "4";
                	}
                	
		            if (comida != null) {
		                System.out.println("id comida: " + comida.getId_comida()+ ", grupo: " + comida.getGrupo());

		                String query = "INSERT INTO dieta_comida (id_dieta, id_comida, posicion) VALUES (?, ?, ?)";

		                try (PreparedStatement statement = connection.prepareStatement(query)) {
		                    statement.setString(1, id);
		                    statement.setString(2, comida.getId_comida());
		                    statement.setString(3, posicion);

		                    statement.executeUpdate();

		                    System.out.println("Nuevo registro insertado en la tabla 'rutina_ejercicio'.");
		                } catch (SQLException e) {
		                    System.out.println("ERROR" + e.getMessage());}
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


	public int getCalorias() {
		return calorias;
	}

	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}
	
}
