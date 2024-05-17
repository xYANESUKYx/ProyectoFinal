package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.Initializable;

public class VerRutinaOtherController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onPerfil;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onPerfilOther;
	private EventHandler<MouseEvent> onSuplementos;
	private ArrayList<Ejercicio> ejercicio;
	private String id_user = "";
	private String nombre_user = "";
	private String imagen = "";
	private String id_grupo = "";
	private String posicion = "";
	
	//view 
	@FXML
    private GridPane gridPane;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label nombreLabelOther;

    @FXML
    private ImageView userImage;

    @FXML
    private ImageView userImageOther;

    @FXML
    private ScrollPane view;


	public ScrollPane getView() {
		return view;
	}
		
	public VerRutinaOtherController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VerRutinaViewOther.fxml"));
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
	
	void rellenarTabla(String id_rutina, String id_user, String grupo, String nombre, String posicion, String nombreUser, String imagen) {
		this.posicion = posicion;
		
			String query = "SELECT u.id, u.nombre_user, u.avatar FROM usuarios u " +
	                   "INNER JOIN rutinas r ON u.id = r.id_user " +
	                   "WHERE r.id = ?";

		    Map<String, Object> datosUsuario = new HashMap<>();

		    try (PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, id_rutina);
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
		
	    
	     query = "SELECT * FROM musculos WHERE grupo = '" + grupo + "'";

	    ejercicio = new ArrayList<>();
	    
	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        int row = 0;
	        int col = 0;
	        int cont = 0;

	        while (resultSet.next()) {
	            String name = resultSet.getString("nombre");
            	String id = resultSet.getString("id");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjetaEjercicio2.fxml"));
	            VBox card;
	            try {
	                card = loader.load();
	                Label nameLabel = (Label) card.lookup("#labelMusculo");
	                nameLabel.setText(name.toUpperCase());
	                try {
			            String[] ejercicios = obtenerEjercicios(id, id_rutina);
	
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

	  
	public String[] obtenerEjercicios(String id, String id_rutina) {
	    
		String query = "SELECT ejercicios.id, ejercicios.nombre, ejercicios.imagen, rutina_ejercicio.posicion " +
	                   "FROM ejercicios " +
	                   "INNER JOIN rutina_ejercicio ON ejercicios.id = rutina_ejercicio.id_ejercicio " +
	                   "WHERE rutina_ejercicio.id_rutina = '" + id_rutina + "'" +
	                   "AND ejercicios.musculo = '" + id + "'";

	    ArrayList<String> ejercicios = new ArrayList<>();
	    ArrayList<String> imagenes = new ArrayList<>();
	    ArrayList<String> ids = new ArrayList<>();
	    ArrayList<Integer> posiciones = new ArrayList<>();

	    try (Statement statement = connection.createStatement()) {
	        ResultSet resultSet = statement.executeQuery(query);

	        while (resultSet.next()) {
	            ids.add(resultSet.getString("id"));
	            ejercicios.add(resultSet.getString("nombre"));
	            imagenes.add(resultSet.getString("imagen"));
	            posiciones.add(resultSet.getInt("posicion"));
	        }
	    } catch (SQLException e) {return new String[0]; }

	    int numEjercicios = ejercicios.size();
	    if (numEjercicios < 2) {return new String[0];}

	    String[] resultado = new String[6]; 
	    
	    for (int i = 0; i < numEjercicios; i++) {
	        if (posiciones.get(i) == 1) {
	            resultado[0] = ids.get(i);
	            resultado[1] = imagenes.get(i);
	            resultado[2] = ejercicios.get(i);
	        } else if (posiciones.get(i) == 2) {
	            resultado[3] = ids.get(i);
	            resultado[4] = imagenes.get(i);
	            resultado[5] = ejercicios.get(i);
	        }
	    }
	    return resultado;
	}

	public void limpiarGridPane() {
	    gridPane.getChildren().clear();
	}
	
    @FXML
    void onUserClicked(MouseEvent event) {
    	if(posicion.equals("R")) {
    		if(onPerfil != null) onPerfil.handle(event);
    	}else {
    		if(onUsuario != null) onUsuario.handle(event);
    	}
    }
    
    public void setonPerfil(EventHandler<MouseEvent> onPerfil) {
		this.onPerfil = onPerfil;
	}
    
    public void setonUsuario(EventHandler<MouseEvent> onUsuario) {
		this.onUsuario = onUsuario;
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


	@FXML
	void onPerfilClicked(MouseEvent event) {
		 if(onPerfilOther != null) onPerfilOther.handle(event);
	 }
	 
	 public void setonPerfilOther(EventHandler<MouseEvent> onPerfilOther) {
		this.onPerfilOther = onPerfilOther;
	}
}
