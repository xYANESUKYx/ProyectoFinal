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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class SuplementosHoraController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
    private String Suplemento = "";
    private String idUser = "";
    private String hora = "";
    private Double peso = 0.0;

	//view
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView imageView;

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
		
	public SuplementosHoraController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SuplementosHoraView.fxml"));
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
	
	public void rellenarTabla(String hora, Double peso) {
		this.hora = hora;
		this.peso = peso;
		
		gridPane.getChildren().clear();
		rellenarDatosTabla(hora);
	    
	    String gr_hora = hora.equals("morning")?"gr_manana":hora.equals("tarde")?"gr_tarde":"gr_noche";
	    
	    String query = "SELECT s.nombre, s.id_suplemento, s.imagen, s." + gr_hora + " " +
	               "FROM suplementos s " +
	               "INNER JOIN suplemento_user su ON s.id_suplemento = su.id_suplemento " +
	               "WHERE su.id_user = '" + idUser + "' AND s." + gr_hora + " != 0";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String name = resultSet.getString("nombre");
	            String id = resultSet.getString("id_suplemento");
	            String imagen = resultSet.getString("imagen");
	            String gramos = resultSet.getString(gr_hora);

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta2.fxml"));
	            HBox card;
	            try {
	                card = loader.load();
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(name);
	                
	                Label grLabel = (Label) card.lookup("#grLabel");
	                DecimalFormat df = new DecimalFormat("#.0");
	                String numeroRedondeado = df.format(Double.parseDouble(gramos) * peso);
	                grLabel.setText(numeroRedondeado + "gr");
	                
                    ImageView imageView = (ImageView) card.lookup("#imageView");
                    Image image = new Image(imagen);
                    imageView.setImage(image);

	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        	setSuplemento(id);
                        	alerta(name, name);
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
	

	private void alerta(String nombre, String id) {
	    Stage ventanaEmergente = new Stage();
	    ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
	    ventanaEmergente.setTitle("Eliminar suplemento");
	    ventanaEmergente.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

	    Label label = new Label("¿Desea eliminar la\n" + nombre + " de sus suplementos?\n ");
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
            String query = "DELETE FROM suplemento_user WHERE id_suplemento = ? AND id_user = ?";

            try ( 
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, Suplemento);
                statement.setString(2, idUser);

                int filasAfectadas = statement.executeUpdate();

                if (filasAfectadas > 0) {System.out.println("Registro eliminado");} 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rellenarTabla(hora, peso);
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
    void onRutinasClicked(MouseEvent event) {
    	if(onRutinas != null) onRutinas.handle(event);
    }
    
    public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	}
	
	public  void rellenarDatosTabla(String horario) {
		
		if(horario.equals("morning")) {
			//titleLabel.setText("MAÑANA");

		    Image nuevaImagen = new Image(getClass().getResourceAsStream("/images/mananaImg.png"));
		    imageView.setImage(nuevaImagen);
		    
		} else if(horario.equals("tarde")) {
			//titleLabel.setText("TARDE");

		    Image nuevaImagen = new Image(getClass().getResourceAsStream("/images/tardeImg.png"));
		    imageView.setImage(nuevaImagen);
		    
		} else if(horario.equals("noche")) {
			//titleLabel.setText("NOCHE");

		    Image nuevaImagen = new Image(getClass().getResourceAsStream("/images/nocheImg.png"));
		    imageView.setImage(nuevaImagen);
		}
	}

	public String getSuplemento() {
		return Suplemento;
	}

	public void setSuplemento(String suplemento) {
		Suplemento = suplemento;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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
