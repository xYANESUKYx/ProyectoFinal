package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddSuplementosController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onSuplemento;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
    private String Suplemento = "";

	//view 

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView userImage;
    
	@FXML
    private GridPane gridPane;

    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddSuplementosController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddSuplementosView.fxml"));
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

		String nombreUser = usuario.getNombre();
		String imagen = usuario.getimagenAvatar();
        nombreLabel.setText(nombreUser);
        userImage.setImage(new Image(imagen));

        comboBox.getItems().add("TODOS");
		comboBox.getSelectionModel().select(0);
        
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			gridPane.getChildren().clear();
			 if ("TODOS".equals(newValue)) {
		            rellenarTabla();
		        } else {
		    String query = "SELECT * FROM efectos WHERE descripcion = '" + newValue + "'";

		    try (Statement statement = connection.createStatement()) {
		        ResultSet resultSet = statement.executeQuery(query);

		        if (resultSet.next()) {
		            String id = resultSet.getString("id");
		            
		            query = "SELECT * FROM efectos_suplementos WHERE id_efecto = '" + id + "'";
		        
				    try (Statement statement2 = connection.createStatement()) {
				        ResultSet resultSet2 = statement.executeQuery(query);
				        
				        List<String> idSuplementos = new ArrayList<>();
				        
				        while (resultSet2.next()) {
				            String id_suplemento = resultSet2.getString("id_suplemento");
				            idSuplementos.add(id_suplemento);
				        }
				    	rellenarTabla(idSuplementos);
				    } 
				 }
		    }catch (SQLException e) {}
		        }});
        	rellenarTabla();
	}
	
	void refrescarDatos(){
		comboBox.getSelectionModel().select(0);
		
	}
	
	void rellenarTabla(List<String> idSuplementos) {
        int row = 0;
        int col = 0;

        for (String id : idSuplementos) {
        
            String query = "SELECT * FROM suplementos WHERE id_suplemento = '" + id + "'";
    
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
    
                while (resultSet.next()) {
                    String id_suplemento = resultSet.getString("id_suplemento");
                    String nombre = resultSet.getString("nombre");
                    String imagen = resultSet.getString("imagen");
    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta.fxml"));
                    VBox card;
                    
                    try {
                        card = loader.load();
                        
                        Label nameLabel = (Label) card.lookup("#nameLabel");
                        nameLabel.setText(nombre);
    
                        ImageView imageView = (ImageView) card.lookup("#imageView");
                        Image image = new Image(imagen);
                        imageView.setImage(image);
                        
                        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                setSuplemento(id_suplemento);
                                if(onSuplemento != null) onSuplemento.handle(event);
                            }
                        });
    
                        card.setStyle("-fx-padding: 5px;");
    
                        gridPane.add(card, col, row);
                        col++;
                        if (col == 3) {
                            col = 0;
                            row++;
                        }
                    } catch (IOException e) {}
                }
            } catch (SQLException e) {}
        }
    }

	void rellenarTabla() {
	    String query = "SELECT * FROM suplementos";

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;

	        while (resultSet.next()) {
	            String nombre = resultSet.getString("nombre");
	            String imagen = resultSet.getString("imagen");

                String id_suplemento = resultSet.getString("id_suplemento");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta.fxml"));
	            VBox card;
	            
	            try {
	                card = loader.load();
	                
	                Label nameLabel = (Label) card.lookup("#nameLabel");
	                nameLabel.setText(nombre);

	                ImageView imageView = (ImageView) card.lookup("#imageView");
	                Image image = new Image(imagen);
	                imageView.setImage(image);
	                
	                card.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                        setSuplemento(id_suplemento);
	                        if(onSuplemento != null) onSuplemento.handle(event);
	                    }
	                });

	                card.setStyle("-fx-padding: 5px;");

	                gridPane.add(card, col, row);
	                col++;
	                if (col == 3) {
	                    col = 0;
	                    row++;
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (SQLException e) {}
	    
	    query = "SELECT * FROM efectos";

	    try (Statement statement = connection.createStatement()) {
	    	 
	    	ResultSet resultSet = statement.executeQuery(query);
	    	
	    	while (resultSet.next()) {
	            String efecto = resultSet.getString("descripcion");
	            comboBox.getItems().add(efecto); 
	        }
	    	
	    } catch (SQLException e) {}
	}
	
	public void setonSuplemento(EventHandler<MouseEvent> onSuplemento) {
		this.onSuplemento = onSuplemento;
	}

	public String getSuplemento() {
		return Suplemento;
	}

	public void setSuplemento(String suplemento) {
		Suplemento = suplemento;
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
	 
	 @FXML
	 void onRutinasClicked(MouseEvent event) {
	    if(onRutinas != null) onRutinas.handle(event);
	 }
	    
	 public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	 }
}
