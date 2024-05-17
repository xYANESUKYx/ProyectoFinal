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
import java.util.ResourceBundle;

import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class SuplementosController implements Initializable {

	//model
	private EventHandler<MouseEvent> onMorning;
	private EventHandler<MouseEvent> onTarde;
	private EventHandler<MouseEvent> onNoche;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onAdd;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;

	//view
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
		
	public SuplementosController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SuplementosView.fxml"));
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
	
    @FXML
    void onMorningClicked(MouseEvent event) {
    	if(onMorning != null) onMorning.handle(event);
    }
	
	public void setonMorning(EventHandler<MouseEvent> onMorning) {
		this.onMorning = onMorning;
	}

    @FXML
    void onTardeClicked(MouseEvent event) {
    	if(onTarde != null) onTarde.handle(event);
    }
    
    public void setonTarde(EventHandler<MouseEvent> onTarde) {
		this.onTarde = onTarde;
	}

    @FXML
    void onNocheClicked(MouseEvent event) {
    	if(onNoche != null) onNoche.handle(event);
    }
    
    public void setonNoche(EventHandler<MouseEvent> onNoche) {
		this.onNoche = onNoche;
	}
    
    @FXML
    void onRutinasClicked(MouseEvent event) {
    	if(onRutinas != null) onRutinas.handle(event);
    }
    
    public void setonRutinas(EventHandler<MouseEvent> onRutinas) {
		this.onRutinas = onRutinas;
	}
    
    @FXML
    void onAddClicked(MouseEvent event) {
    	if(onAdd != null) onAdd.handle(event);
    }
    
    public void setonAdd(EventHandler<MouseEvent> onAdd) {
		this.onAdd = onAdd;
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
	 void onSuplementosClicked(MouseEvent event) {
	 }
	 
	 @FXML
	 void onDietaClicked(MouseEvent event) {
		 if(onDietas != null) onDietas.handle(event);
	 }
	 
	 public void setonDietas(EventHandler<MouseEvent> onDietas) {
		this.onDietas = onDietas;
	}	
}
