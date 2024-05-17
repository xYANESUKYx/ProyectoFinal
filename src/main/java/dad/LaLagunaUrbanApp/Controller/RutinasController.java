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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class RutinasController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}
	
	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onRutina;
	private EventHandler<MouseEvent> onVerRutina;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onDelete;
	private String nombreRutina = "";
	private String grupoRutina = "";
	private String idRutina = "";
	private String idUser = "";

	//view 
    @FXML
    private ImageView userImage;
    
    @FXML
    private Label nombreLabel;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridpane;

    @FXML
    private ScrollPane view;
    
    @FXML
    private AnchorPane anchorPane;

	public ScrollPane getView() {
		return view;
	}
		
	public RutinasController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RutinasView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	}
	
	void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
		rellenarTabla("" + usuario.getId_usuario());
	}
	
	void rellenarTabla(String id_user) {
		idUser = id_user;
		gridpane.getChildren().clear();
		
	    String query = "SELECT * FROM rutinas WHERE id_user = '" + id_user + "'";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String name = resultSet.getString("nombre");
	            String id = resultSet.getString("id");
	            String grupo = resultSet.getString("grupo");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta4.fxml"));
	            VBox card;
	            try {
	                card = loader.load();
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                    	
	                        double clickX = event.getX();
	                        double clickY = event.getY();
	                        
	                        double cardWidth = card.getWidth();
	                        
	                        double topMargin = 10;
	                        double rightMargin = 40;
	                        double cornerSize = 40;

	                        double cornerX = cardWidth - rightMargin - cornerSize;
	                        double cornerY = topMargin;

	                        if (clickX >= cornerX && clickX <= cornerX + cornerSize &&
	                            clickY >= cornerY && clickY <= cornerY + cornerSize) {
	                        	alerta(id, name);
	                        }else {
	                        	setNombreRutina(name);
	                            setGrupoRutina(grupo);
	                            setIdRutina(id);
	                            if(onVerRutina != null) onVerRutina.handle(event);
	                        }
	                    }
	                });
	                
	                gridpane.add(card, col, row);
	                row++;
	                if (row == 1) {
	                    row = 0;
	                    col++;
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        try {
	            FXMLLoader loaderAdd = new FXMLLoader(getClass().getResource("/fxml/tarjetaAdd.fxml"));
	            VBox cardAdd = loaderAdd.load();

	            cardAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(onRutina != null) onRutina.handle(event);
                    }
                });
	            
	            gridpane.add(cardAdd, col, row);

	        } catch (IOException e) {}
	    } catch (SQLException e) {}
	}
	
	public void setonVerRutina(EventHandler<MouseEvent> onVerRutina) {
		this.onVerRutina = onVerRutina;
	}
	
	public void setonRutina(EventHandler<MouseEvent> onRutina) {
		this.onRutina = onRutina;
	}
	
    @FXML
    void onBackClicked(MouseEvent event) {
    	if(onBack != null) onBack.handle(event);
    }
    
    public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}

    @FXML
    void onUserClicked(MouseEvent event) {
    	if(onUsuario != null) onUsuario.handle(event);
    }
    
    public void setonUsuario(EventHandler<MouseEvent> onUsuario) {
		this.onUsuario = onUsuario;
	}
    
    @FXML
    void onLeftClicked(MouseEvent event) {
    	double newPosition = scrollPane.getHvalue() - 450 / scrollPane.getWidth();
    	scrollPane.setHvalue(newPosition);
    }

    @FXML
    void onRightClicked(MouseEvent event) {
    	double newPosition = scrollPane.getHvalue() + 450 / scrollPane.getWidth();
    	scrollPane.setHvalue(newPosition);
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

	 private void alerta(String id, String nombre) {
		    Stage ventanaEmergente = new Stage();
		    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
		    ventanaEmergente.setTitle("Eliminar rutina");
		    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

		    Label label = new Label("¿Desea eliminar la\nrutina " + nombre + "?\n ");
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
		        String deleteRutinaQuery = "DELETE FROM rutinas WHERE id = ?";
		        String deleteRutinaEjercicioQuery = "DELETE FROM rutina_ejercicio WHERE id_rutina = ?";

		        try (
		             PreparedStatement deleteRutinaStatement = connection.prepareStatement(deleteRutinaQuery);
		             PreparedStatement deleteRutinaEjercicioStatement = connection.prepareStatement(deleteRutinaEjercicioQuery)) {

		            deleteRutinaStatement.setString(1, id);
		            deleteRutinaStatement.executeUpdate();

		            deleteRutinaEjercicioStatement.setString(1, id);
		            deleteRutinaEjercicioStatement.executeUpdate();

		            rellenarTabla(idUser);

		        } catch (SQLException ex) {}

			    if(onDelete != null) onDelete.handle(null);
		        ventanaEmergente.close();
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
	    
	 public void setonDelete(EventHandler<MouseEvent> onDelete) {
		this.onDelete = onDelete;
	 }
}
