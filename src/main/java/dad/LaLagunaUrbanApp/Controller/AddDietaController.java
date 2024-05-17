package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddDietaController implements Initializable {

	//model
	private EventHandler<MouseEvent> onGenerarDieta;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private EventHandler<MouseEvent> onInicio;
	private String calorias = "0";
	private String numComidas = "0";
	
	//view 
	@FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Spinner<Integer> spinnerCalorias;

    @FXML
    private ImageView userImage;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddDietaController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddDietaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 5));
		spinnerCalorias.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1500, 3000));
		}

	void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
	}
	
	@FXML
    void onGenerarDietaClicked(MouseEvent event) {
		setNumComidas(Integer.toString(spinner.getValue()));
		setCalorias(Integer.toString(spinnerCalorias.getValue()));
    	
    	if(onGenerarDieta != null) onGenerarDieta.handle(event);
    }
    
    public void setonGenerarDieta(EventHandler<MouseEvent> onGenerarDieta) {
		this.onGenerarDieta = onGenerarDieta;
	}

	public String getNumComidas() {
		return numComidas;
	}

	public void setNumComidas(String numComidas) {
		this.numComidas = numComidas;
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

	public String getCalorias() {
		return calorias;
	}

	public void setCalorias(String calorias) {
		this.calorias = calorias;
	}
	 
}
