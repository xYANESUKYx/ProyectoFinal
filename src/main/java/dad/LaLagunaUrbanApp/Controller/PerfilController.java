package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import java.util.List;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class PerfilController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onAvatares;
	private EventHandler<MouseEvent> onAjustes;
	private EventHandler<MouseEvent> onVerRutina;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onVerDieta;
	private EventHandler<MouseEvent> onChangePrivado;
	private EventHandler<MouseEvent> onDelete;
	private EventHandler<MouseEvent> onDeleteDieta;
	private String nombreRutina = "";
	private String grupoRutina = "";
	private String idRutina = "";
	private String nombreDieta = "";
	private String idDieta = "";
	private String id_user = "", imagen = "";
	private List<HBox> tarjetas = new ArrayList<>();

	//view
    @FXML
    private ImageView ajustesImage;

    @FXML
    private GridPane gridPane;
    
    @FXML
    private GridPane gridPane1;

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
		
	public PerfilController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PerfilView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void cargarDatos(String id_user, String imagen, boolean other, String nombre) {
		this.id_user = id_user;
		this.imagen = imagen;
		
		userImage.setImage(new Image(imagen));
		
		if(other) {
			ajustesImage.setVisible(false);
			nombreLabel.setText(nombre);
			rellenarTablaOther();
			rellenarTablaDietas();
		}else {
			ajustesImage.setVisible(true);
			rellenarTabla();
			rellenarTablaDietas();
		}
	}
	
	public void cargarDatos(usuario usuario) {
		
		String id_user = "" + usuario.getId_usuario();
		String imagen = usuario.getimagenAvatar();
		String nombre = usuario.getNombre();
		
		this.id_user = id_user;
		this.imagen = imagen;
		
		userImage.setImage(new Image(imagen));
		
		ajustesImage.setVisible(true);
		nombreLabel.setText(nombre);
		rellenarTabla();
	}
	
	public void rellenarTablaOther() {
		gridPane.getChildren().clear();
	    
	    String query = "SELECT * FROM rutinas WHERE id_user = '" + id_user + "' AND privado = '0'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String grupo = resultSet.getString("grupo");
	            String name = resultSet.getString("nombre");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaPerfil.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                ImageView imageView = (ImageView) card.lookup("#imageView");
	                imageView.setImage(new Image(this.imagen));
	                
                    ImageView imagePrivate = (ImageView) card.lookup("#imagePrivate");
                    imagePrivate.setVisible(false);
	                
                    ImageView imagePrivate1 = (ImageView) card.lookup("#imagePrivate1");
                    imagePrivate1.setVisible(false);

	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
                        	setNombreRutina(name);
                            setGrupoRutina(grupo);
                            setIdRutina(id);
                            if(onVerRutina != null) onVerRutina.handle(event);
	                    }
	                });
	                
                    tarjetas.add(card);
	                
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
	
	public void rellenarTabla() {
		gridPane.getChildren().clear();
	    
	    String query = "SELECT * FROM rutinas WHERE id_user = '" + id_user + "'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String name = resultSet.getString("nombre");
	            String grupo = resultSet.getString("grupo");
	            String privado = resultSet.getString("privado");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaPerfil.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                ImageView imageView = (ImageView) card.lookup("#imageView");
	                imageView.setImage(new Image(this.imagen));
	                
                    ImageView imagePrivate = (ImageView) card.lookup("#imagePrivate");
                    Image imageP = new Image(privado.equals("1")? "/images/private.png":"/images/public.png");
                    imagePrivate.setImage(imageP);

	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 40;
	                        double rightMargin = 90;
	                        double rightMargin2 = 160;
	                        double cornerSize = 40;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerX2 = cardWidth - rightMargin2 - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {

	                        	alerta(id, name, "r");
	        	                
	                        }else if (clickX >= cornerX2 && clickX <= cornerX2 + cornerSize &&
		                            clickY >= cornerY && clickY <= cornerY + cornerSize) {

		                        	String rutaImagen = imagePrivate.getImage().getUrl();
		                        	
		                        	if(rutaImagen.endsWith("private.png")) {
		                        		imagePrivate.setImage(new Image("/images/public.png"));
		                        		cambiarPrivacidad(id, "0", "r");
		                        	}else {
		                        		imagePrivate.setImage(new Image("/images/private.png"));
		                        		cambiarPrivacidad(id, "1", "r");
		                        	}
		        	                
		                        }else {
		                        	setNombreRutina(name);
		                            setGrupoRutina(grupo);
		                            setIdRutina(id);
		                            if(onVerRutina != null) onVerRutina.handle(event);
	                        }
	                    }
	                });
	                
                    tarjetas.add(card);
	                
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
	
	public void rellenarTablaDietas() {
		gridPane1.getChildren().clear();
	    
	    String query = "SELECT * FROM dietas WHERE id_user = '" + id_user + "'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String name = resultSet.getString("nombre");
	            String privado = resultSet.getString("privado");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaPerfil.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                ImageView imageView = (ImageView) card.lookup("#imageView");
	                imageView.setImage(new Image(this.imagen));
	                
                    ImageView imagePrivate = (ImageView) card.lookup("#imagePrivate");
                    Image imageP = new Image(privado.equals("1")? "/images/private.png":"/images/public.png");
                    imagePrivate.setImage(imageP);

	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 40;
	                        double rightMargin = 90;
	                        double rightMargin2 = 160;
	                        double cornerSize = 40;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerX2 = cardWidth - rightMargin2 - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {

	                        	alerta(id, name, "d");
	        	                
	                        }else if (clickX >= cornerX2 && clickX <= cornerX2 + cornerSize &&
		                            clickY >= cornerY && clickY <= cornerY + cornerSize) {

		                        	String rutaImagen = imagePrivate.getImage().getUrl();
		                        	
		                        	if(rutaImagen.endsWith("private.png")) {
		                        		imagePrivate.setImage(new Image("/images/public.png"));
		                        		cambiarPrivacidad(id, "0", "d");
		                        	}else {
		                        		imagePrivate.setImage(new Image("/images/private.png"));
		                        		cambiarPrivacidad(id, "1", "d");
		                        	}
		        	                
		                        }else {
		                        	setNombreDieta(name);
		                            setIdDieta(id);
		                            if(onVerDieta != null) onVerDieta.handle(event);
	                        }
	                    }
	                });
	                
                    tarjetas.add(card);
	                
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
	public void cambiarPrivacidad(String idUsuario, String privacidad, String dietaOrRutina) {
		String query = "";
		if(dietaOrRutina.equals("r")) {
			 query = "UPDATE rutinas SET privado = '" + privacidad + "' WHERE id = ?";
		}else {
			 query = "UPDATE dietas SET privado = '" + privacidad + "' WHERE id = ?";
		}
		
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, idUsuario);
	        statement.executeUpdate();
		    if(onChangePrivado != null) onChangePrivado.handle(null);
	    } catch (SQLException e) {}
	}

	public void setonVerRutina(EventHandler<MouseEvent> onVerRutina) {
		this.onVerRutina = onVerRutina;
	}
	
	@FXML
	void onAjustesClicked(MouseEvent event) {
		if(onAjustes != null) onAjustes.handle(event);
	}

	public void setonAjustes(EventHandler<MouseEvent> onAjustes) {
		this.onAjustes = onAjustes;
	}
	
	@FXML
	void onAvatarClicked(MouseEvent event) {
		if(onAvatares != null) onAvatares.handle(event);
	}
    
    public void setonAvatares(EventHandler<MouseEvent> onAvatares) {
		this.onAvatares = onAvatares;
	}
    
    private void alerta(String id, String nombre, String dietaOrRutina) {
	    Stage ventanaEmergente = new Stage();
	    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
	    ventanaEmergente.setTitle("Eliminar " + (dietaOrRutina.equals("r")? "rutina":"dieta"));
	    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

	    Label label = new Label("¿Desea eliminar la\n" + (dietaOrRutina.equals("r")? "rutina":"dieta") + " " + nombre + "?\n ");
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
            String query = "";
	    	if(dietaOrRutina.equals("r")) {
	    		query = "DELETE FROM rutinas WHERE id = ?";
	    	}else {
	    		query = "DELETE FROM dietas WHERE id = ?";
	    	}

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.executeUpdate();

                rellenarTabla();
            } catch (SQLException ex) {}
	    	ventanaEmergente.close();

	    	if(dietaOrRutina.equals("r")) {
			    if(onDelete != null) onDelete.handle(null);
	    	}else {
			    if(onDeleteDieta != null) onDeleteDieta.handle(null);
	    	}
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
	 void onRutinasClicked(MouseEvent event) {
	    if(onRutinas != null) onRutinas.handle(event);
	 }
	    
	 public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	 }
	    
	 public void setonDelete(EventHandler<MouseEvent> onDelete) {
		this.onDelete = onDelete;
	 }
	    
	 public void setonDeleteDieta(EventHandler<MouseEvent> onDeleteDieta) {
		this.onDeleteDieta = onDeleteDieta;
	 }
	    
	 public void setonChangePrivado(EventHandler<MouseEvent> onChangePrivado) {
		this.onChangePrivado = onChangePrivado;
	 }

	 @FXML
	 void onUserClicked(MouseEvent event) {
	 }

	    public void setonVerDieta(EventHandler<MouseEvent> onVerDieta) {
			this.onVerDieta = onVerDieta;
		}

}
