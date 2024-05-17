package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class AddSuplementoController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onAddSuplemento;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
    private String Suplemento = "";
    private String idUser = "";

	//view 
    @FXML
    private ImageView imageAdd;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private Label labelDescripcion;

    @FXML
    private Label labelMorning;

    @FXML
    private Label labelName;

    @FXML
    private Label labelNoche;

    @FXML
    private Label labelTarde;

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
		
	public AddSuplementoController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddSuplementoView.fxml"));
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
	
	void imagenAdd() {
		 try {
	          String query = "SELECT * FROM suplemento_user WHERE id_suplemento = ? AND id_user = ?";
	          PreparedStatement statement = connection.prepareStatement(query);
	          statement.setString(1, Suplemento);
	          statement.setString(2, idUser);
	          ResultSet resultSet = statement.executeQuery();

	          if (resultSet.next()) {imageAdd.setImage(new Image("/images/quitar.png"));
	          } else {imageAdd.setImage(new Image("/images/ad.png"));}
	            
	          resultSet.close();
	          statement.close();
	      } catch (SQLException e) {}
	    }
	
	void seleccionarSuplemento(Double peso) {
	    imagenAdd();
	    
	    String query = "SELECT * FROM suplementos WHERE id_suplemento = '"+ Suplemento +"'";

	    try (Statement statement = connection.createStatement()) {
	         ResultSet resultSet = statement.executeQuery(query);
	        
	        if (resultSet.next()) {
	            String nombre = resultSet.getString("nombre");
	            labelName.setText(nombre);
	        	
	            String descripcion = resultSet.getString("descripcion");
	            labelDescripcion.setText(descripcion);
	        	
	            String gr_manana = resultSet.getString("gr_manana");
	            
	            if (gr_manana.equals("0")) {
	            	labelMorning.setText("X");
	            	labelMorning.setStyle("-fx-text-fill: red;");

	            }else {
		            labelMorning.setText("" + Math.round(Double.parseDouble(gr_manana) * peso * 10) / 10.0);
	            	labelMorning.setStyle("-fx-text-fill: green;");
	            }
	        	
	            String gr_tarde = resultSet.getString("gr_tarde");
	            
	            if (gr_tarde.equals("0")) {
	            	labelTarde.setText("X");
	            	labelTarde.setStyle("-fx-text-fill: red;");

	            }else {
		            labelTarde.setText("" + Math.round(Double.parseDouble(gr_tarde) * peso * 10) / 10.0);
		            labelTarde.setStyle("-fx-text-fill: green;");
	            }
	        	
	            String gr_noche = resultSet.getString("gr_noche");
	            
	            if (gr_noche.equals("0")) {
	            	labelNoche.setText("X");
	            	labelNoche.setStyle("-fx-text-fill: red;");

	            }else {
		            labelNoche.setText("" + Math.round(Double.parseDouble(gr_noche) * peso * 10) / 10.0);
	            	labelNoche.setStyle("-fx-text-fill: green;");
	            }

	            String imagen = resultSet.getString("imagen");
                Image image = new Image(imagen);
                imageView.setImage(image);

	            String id = resultSet.getString("id_suplemento");
	            
	            query = "SELECT * FROM efectos_suplementos WHERE id_suplemento = '"+ id +"'";
	            
	            try (Statement statement2 = connection.createStatement()) {
	   	         
	    	        ResultSet resultSet2 = statement.executeQuery(query);
	    	        
	    	        while (resultSet2.next()) {
	    	            String efecto = resultSet2.getString("id_efecto");
	    	            
	    	            if(efecto.equals("1")) {imageView1.setImage(new Image("/images/check.png"));}
	    	            else{imageView1.setImage(new Image("/images/x.png"));}
	    	            
	    	            if(efecto.equals("2")) {imageView2.setImage(new Image("/images/check.png"));}
	    	            else{imageView2.setImage(new Image("/images/x.png"));}
	    	            
	    	            if(efecto.equals("3")) {imageView3.setImage(new Image("/images/check.png"));}
	    	            else{imageView3.setImage(new Image("/images/x.png"));}
	    	            
	    	            if(efecto.equals("4")) {imageView4.setImage(new Image("/images/check.png"));}
	    	            else{imageView4.setImage(new Image("/images/x.png"));}
	    	            
	    	            if(efecto.equals("5")) {imageView5.setImage(new Image("/images/check.png"));}
	    	            else{imageView5.setImage(new Image("/images/x.png"));}
	    	            
	    	            if(efecto.equals("6")) {imageView5.setImage(new Image("/images/check.png"));}
	    	            else{imageView6.setImage(new Image("/images/x.png"));}
	    	        }
	    	        
	            }catch (SQLException e) {}
	        }
	        
	    } catch (SQLException e) {}
	}
	
	@FXML
    void onAddClicked(MouseEvent event) {
		if(onAddSuplemento != null) onAddSuplemento.handle(event);
		
		Image image = imageAdd.getImage();
        if (image != null) {
            String nombreImagen = image.getUrl();
            if (nombreImagen.endsWith("ad.png")) {
        		try  {
                	
                    String query = "INSERT INTO suplemento_user (id_suplemento, id_user) VALUES (?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, Suplemento);
                        statement.setString(2, idUser);
                        
                        statement.executeUpdate();
                    }
                } catch (SQLException e) {}

            	imageAdd.setImage(new Image("/images/quitar.png"));
            } else {
                String query = "DELETE FROM suplemento_user WHERE id_suplemento = ? AND id_user = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, Suplemento);
                    statement.setString(2, idUser);

                    statement.executeUpdate();

                } catch (SQLException e) {}
                
            	imageAdd.setImage(new Image("/images/ad.png"));
            }
        }
	}
	
	public void setonAddClicked(EventHandler<MouseEvent> onAddSuplemento) {
		this.onAddSuplemento = onAddSuplemento;
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
	 
	 @FXML
	 void onRutinasClicked(MouseEvent event) {
	    if(onRutinas != null) onRutinas.handle(event);
	 }
	    
	 public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	 }
}
