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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AvataresController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onAvatar;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private String avatar = "";
	private String idUser = "";
	
	//view 
    @FXML 
    private ImageView avatar1;

    @FXML
    private ImageView avatar10;

    @FXML
    private ImageView avatar2;

    @FXML
    private ImageView avatar3;

    @FXML
    private ImageView avatar4;

    @FXML
    private ImageView avatar5;

    @FXML
    private ImageView avatar6;

    @FXML
    private ImageView avatar7;

    @FXML
    private ImageView avatar8;

    @FXML
    private ImageView avatar9;

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
		
	public AvataresController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AvatarView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	void seleccionarAvatar(String imagen, String nombre) {
        avatar1.setOpacity(1);
        avatar2.setOpacity(1);
        avatar3.setOpacity(1);
        avatar4.setOpacity(1);
        avatar5.setOpacity(1);
        avatar6.setOpacity(1);
        avatar7.setOpacity(1);
        avatar8.setOpacity(1);
        avatar9.setOpacity(1);
        avatar10.setOpacity(1);
        
		switch (avatar) {
        case "1":
            avatar1.setOpacity(0.5);
            break;
        case "2":
            avatar2.setOpacity(0.5);
            break;
        case "3":
        	avatar3.setOpacity(0.5);
            break;
        case "4":
        	avatar4.setOpacity(0.5);
            break;
        case "5":
        	avatar5.setOpacity(0.5);
            break;
        case "6":
        	avatar6.setOpacity(0.5);
            break;
        case "7":
        	avatar7.setOpacity(0.5);
            break;
        case "8":
        	avatar8.setOpacity(0.5);
            break;
        case "9":
        	avatar9.setOpacity(0.5);
            break;
        case "10":
        	avatar10.setOpacity(0.5);
            break;
        default:
            break;
		}
		
        userImage.setImage(new Image(imagen));
		nombreLabel.setText(nombre);
	}
	
	@FXML
    void onAvatar10Clicked(MouseEvent event) {
		setAvatar("10");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar1Clicked(MouseEvent event) {
		setAvatar("1");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar2Clicked(MouseEvent event) {
		setAvatar("2");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar3Clicked(MouseEvent event) {
		setAvatar("3");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar4Clicked(MouseEvent event) {
		setAvatar("4");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar5Clicked(MouseEvent event) {
		setAvatar("5");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar6Clicked(MouseEvent event) {
		setAvatar("6");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar7Clicked(MouseEvent event) {
		setAvatar("7");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar8Clicked(MouseEvent event) {
		setAvatar("8");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }

    @FXML
    void onAvatar9Clicked(MouseEvent event) {
		setAvatar("9");
    	back();
    	if(onAvatar != null) onAvatar.handle(event);
    }
    
    public void setonAvatar(EventHandler<MouseEvent> onAvatar) {
		this.onAvatar = onAvatar;
	}
	
    void back() {
    	String query = "UPDATE usuarios SET avatar = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, avatar);
            statement.setString(2, idUser);

            statement.executeUpdate();
        } catch (SQLException e) {}
    	
    	if(onBack != null) onBack.handle(null);
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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
