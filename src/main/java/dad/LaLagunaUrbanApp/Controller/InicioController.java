package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class InicioController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onDieta;
	private EventHandler<MouseEvent> onRutina;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onLogo;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onVerRutina;
	private EventHandler<MouseEvent> onDescargarDieta;
	private EventHandler<MouseEvent> onDescargar;
	private EventHandler<MouseEvent> onVerDieta;
	private String nombreRutina = "";
	private String grupoRutina = "";
	private String nombreDieta = "";
	private String idDieta = "";
	private String idRutina = "";

	//view
	@FXML
	private GridPane gridPane;
	
    @FXML
    private GridPane gridPane1;
    
    @FXML
    private Label nombreLabel;
    
    @FXML
    private ImageView logoImage;
    
    @FXML
    private ImageView userImage;
    
	@FXML
    private ScrollPane view;
	
	public ScrollPane getView() {
		return view;
	}
		
	public InicioController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
        rellenarTabla(usuario.getId_usuario());
		rellenarTablaDieta(usuario.getId_usuario());
	}
	
	public void rellenarTablaDieta(int id_user_Actual) {
		gridPane1.getChildren().clear();
	    
	    String query = "SELECT * FROM dietas WHERE privado = '0'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String name = resultSet.getString("nombre");
	            String id_user = resultSet.getString("id_user");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaInicio.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                query = "SELECT avatar FROM usuarios WHERE id = '" + id_user + "'";

	                try (Statement statement2 = connection.createStatement()) {
	        	        ResultSet resultSet2 = statement2.executeQuery(query);

	        	        if (resultSet2.next()) {
	        	            String avatar = resultSet2.getString("avatar");

	                        ImageView imagePrivate = (ImageView) card.lookup("#imageView");
	                        imagePrivate.setImage(new Image(getimagenAvatar(avatar)));
	        	        }
	        	    }
                    card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                    	double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 40;
	                        double rightMargin = 100;
	                        double cornerSize = 40;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
	                        	duplicarRutina(Integer.parseInt(id), Integer.toString(id_user_Actual), "d");
	                		    if(onDescargarDieta != null) onDescargarDieta.handle(null);
	        	                
	                        }else {
	                        	setNombreDieta(name);
	                            setIdDieta(id);
	                            if(onVerDieta != null) onVerDieta.handle(event);
	                        }
	                        
	                    }
	                });
	                
	                gridPane1.add(card, col, row);
	                col++;
	                if (col == 1) {
	                    col = 0;
	                    row++;
	                }
	            } catch (IOException e) {}
	        }
	    } catch (SQLException e) {}
	}
	
	public void rellenarTabla(int id_user_Actual) {
		gridPane.getChildren().clear();
		
	    String query = "SELECT * FROM rutinas WHERE privado = '0'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String grupo = resultSet.getString("grupo");
	            String name = resultSet.getString("nombre");
	            String id_user = resultSet.getString("id_user");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaInicio.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                query = "SELECT avatar FROM usuarios WHERE id = '" + id_user + "'";

	                try (Statement statement2 = connection.createStatement()) {
	        	        ResultSet resultSet2 = statement2.executeQuery(query);

	        	        if (resultSet2.next()) {
	        	            String avatar = resultSet2.getString("avatar");

	                        ImageView imagePrivate = (ImageView) card.lookup("#imageView");
	                        imagePrivate.setImage(new Image(getimagenAvatar(avatar)));
	        	        }
	        	    }
	                
	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 40;
	                        double rightMargin = 100;
	                        double cornerSize = 40;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
	                        	duplicarRutina(Integer.parseInt(id), Integer.toString(id_user_Actual), "r");

	                		    if(onDescargar != null) onDescargar.handle(null);
	        	                
	                        }else {
	                        	setNombreRutina(name);
	                            setGrupoRutina(grupo);
	                            setIdRutina(id);
	                            if(onVerRutina != null) onVerRutina.handle(event);
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
	        }
	    } catch (SQLException e) {}
	}
	
	

	@FXML
    void onFbClicked(MouseEvent event) {
    	try {Desktop.getDesktop().browse(new URI("https://www.facebook.com/p/La-Laguna-Urban-Fitness-100050881971268/?locale=es_ES"));
        } catch (Exception ex) {}
    }

    @FXML
    void onInsClicked(MouseEvent event) {
		try {Desktop.getDesktop().browse(new URI("https://www.instagram.com/lalagunaurbanfitness/?hl=es"));
        } catch (Exception ex) {}
    }
    
	private void clonarEjercicios(int idRutinaOriginal, int idRutinaNueva) throws SQLException {
	    String selectQuery = "SELECT * FROM rutina_ejercicio WHERE id_rutina = ?";
	    String insertQuery = "INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio, posicion) VALUES (?, ?, ?)";

	    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	         PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
	        selectStatement.setInt(1, idRutinaOriginal);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while (resultSet.next()) {
	            int id_ejercicio = resultSet.getInt("id_ejercicio");
	            int posicion = resultSet.getInt("posicion");

	            insertStatement.setInt(1, idRutinaNueva);
	            insertStatement.setInt(2, id_ejercicio);
	            insertStatement.setInt(3, posicion);
	            insertStatement.executeUpdate();
	        }
	    }
	}

	public String getimagenAvatar(String avatar) {

		String imagen = "/avatares/avatar1.png";
        
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

	public void setonVerRutina(EventHandler<MouseEvent> onVerRutina) {
		this.onVerRutina = onVerRutina;
	}
	
	@FXML
    void onDietaClicked(MouseEvent event) {
		if(onDieta != null) onDieta.handle(event);
    }
	
	public void setonDieta(EventHandler<MouseEvent> onDieta) {
		this.onDieta = onDieta;
	}
    
    @FXML
    void onRutinaClicked(MouseEvent event) {
    	if(onRutina != null) onRutina.handle(event);
    }
    
    public void setonRutina(EventHandler<MouseEvent> onRutina) {
		this.onRutina = onRutina;
	}

    @FXML
    void onSuplementosClicked(MouseEvent event) {
    	if(onSuplementos != null) onSuplementos.handle(event);
    }
    
    public void setonSuplementos(EventHandler<MouseEvent> onSuplementos) {
		this.onSuplementos = onSuplementos;
	}
	
    @FXML
    void onLogoClicked(MouseEvent event) {
    	if(onLogo != null) onLogo.handle(event);
    }
    
    public void setonLogo(EventHandler<MouseEvent> onLogo) {
		this.onLogo = onLogo;
	}
    
    @FXML
    void onUserClicked(MouseEvent event) {
		if(onUsuario != null) onUsuario.handle(event);
    }
    
    public void setonUsuario(EventHandler<MouseEvent> onUsuario) {
		this.onUsuario = onUsuario;
	}

	public String getNombreRutina() {
		return nombreRutina;
	}

	public void setNombreRutina(String nombreRutina) {
		this.nombreRutina = nombreRutina;
	}

	public String getGrupoRutina() {
		return grupoRutina;
	}

	public void setGrupoRutina(String grupoRutina) {
		this.grupoRutina = grupoRutina;
	}

	public String getIdRutina() {
		return idRutina;
	}

	public void setIdRutina(String idRutina) {
		this.idRutina = idRutina;
	}	
    
	 public void setonDescargar(EventHandler<MouseEvent> onDescargar) {
		this.onDescargar = onDescargar;
		
	 }private void alerta(String titulo, String frase) {
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
	 public void setonDescargarDieta(EventHandler<MouseEvent> onDescargarDieta) {
		this.onDescargarDieta = onDescargarDieta;
	 }


	    public void setonVerDieta(EventHandler<MouseEvent> onVerDieta) {
			this.onVerDieta = onVerDieta;
		}
	    public String getNombreDieta() {
			return nombreDieta;
		}

		public void setNombreDieta(String nombreDieta) {
			this.nombreDieta = nombreDieta;
		}

		public String getIdDieta() {
			return idDieta;
		}

		public void setIdDieta(String idDieta) {
			this.idDieta = idDieta;
		}
		
		public int duplicarRutina(int idOriginal, String id_user, String dietaOrRutina) {
		    int generatedId = -1; 
		    
		    if(dietaOrRutina.equals("r")){
		    	
		    try{
		        String selectQuery = "SELECT * FROM rutinas WHERE id = ?";

		        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
		            selectStatement.setInt(1, idOriginal);
		            ResultSet resultSet = selectStatement.executeQuery();

		            if (resultSet.next()) {
		                String nombre = resultSet.getString("nombre");
		                String id_grupo = resultSet.getString("grupo");

		                String insertQuery = "INSERT INTO rutinas (nombre, id_user, privado, grupo) VALUES (?, ?, ?, ?)";

		                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
		                    insertStatement.setString(1, nombre + "*"); 
		                    insertStatement.setString(2, id_user);
		                    insertStatement.setString(3, "1"); 
		                    insertStatement.setString(4, id_grupo);

		                    int filasAfectadas = insertStatement.executeUpdate();

		                    if (filasAfectadas == 1) {
		                        try (ResultSet rs = insertStatement.getGeneratedKeys()) {
		                            if (rs.next()) {
		                                generatedId = rs.getInt(1);
		                                clonarEjercicios(idOriginal, generatedId);
		                            }
		                        }
		                    }
		                }
		            }
		        }
		    } catch (SQLException e) {System.out.println("Error" + e.getMessage());}
		    
		    }else {
		    	try{
			        String selectQuery = "SELECT * FROM dietas WHERE id = ?";

			        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
			            selectStatement.setInt(1, idOriginal);
			            ResultSet resultSet = selectStatement.executeQuery();

			            if (resultSet.next()) {
			                String nombre = resultSet.getString("nombre");
			                String num_comidas = resultSet.getString("num_comidas");

			                String insertQuery = "INSERT INTO dietas (nombre, id_user, privado, num_comidas) VALUES (?, ?, ?, ?)";

			                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			                    insertStatement.setString(1, nombre + "*"); 
			                    insertStatement.setString(2, id_user);
			                    insertStatement.setString(3, "1"); 
			                    insertStatement.setString(4, num_comidas);

			                    int filasAfectadas = insertStatement.executeUpdate();

			                    if (filasAfectadas == 1) {
			                        try (ResultSet rs = insertStatement.getGeneratedKeys()) {
			                            if (rs.next()) {
			                                generatedId = rs.getInt(1);
			                                clonarComidas(idOriginal, generatedId);
			                            }
			                        }
			                    }
			                }
			            }
			        }
			    } catch (SQLException e) {System.out.println("Error" + e.getMessage());}
		    }
		    
		    if(dietaOrRutina.equals("r")){
		    	alerta("Rutina copiada", "Se ha copiado la\nrutina con exito ");
		    }else {
		    	alerta("Dieta copiada", "Se ha copiado la\ndieta con exito ");
		    }
		    return generatedId;
		}
		
		private void clonarComidas(int idOriginal, int idNueva) throws SQLException {
		    String selectQuery = "SELECT * FROM dieta_comida WHERE id_dieta = ?";
		    String insertQuery = "INSERT INTO dieta_comida (id_dieta, id_comida	, posicion) VALUES (?, ?, ?)";

		    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
		         PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
		        selectStatement.setInt(1, idOriginal);
		        ResultSet resultSet = selectStatement.executeQuery();

		        while (resultSet.next()) {
		            int id_comida	 = resultSet.getInt("id_comida");
		            int posicion = resultSet.getInt("posicion");

		            insertStatement.setInt(1, idNueva);
		            insertStatement.setInt(2, id_comida	);
		            insertStatement.setInt(3, posicion);
		            insertStatement.executeUpdate();
		        }
		    }catch (Exception e) {System.out.println("Error" + e.getMessage());}
		}
}
