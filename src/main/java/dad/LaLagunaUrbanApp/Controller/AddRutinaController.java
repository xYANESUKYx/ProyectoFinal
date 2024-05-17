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

public class AddRutinaController implements Initializable {

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onGrupo;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;
	private String grupo = "";
	
	//view 
	@FXML
    private ImageView imageViewAbdomen;

    @FXML
    private ImageView imageViewBrazo;

    @FXML
    private ImageView imageViewEspalda;

    @FXML
    private ImageView imageViewHombro;

    @FXML
    private ImageView imageViewPectoral;

    @FXML
    private ImageView imageViewPierna;
    
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
		
	public AddRutinaController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddRutinaView.fxml"));
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
	
	public void changeImages(String sexo) {
		if(sexo.equals("hombre")) {
			imageViewPierna.setImage(new Image("body/piernaH.png"));
			imageViewAbdomen.setImage(new Image("body/abdomenH.png"));
			imageViewBrazo.setImage(new Image("body/brazoH.png"));
			imageViewHombro.setImage(new Image("body/hombroH.png"));
			imageViewEspalda.setImage(new Image("body/espaldaH.png"));
			imageViewPectoral.setImage(new Image("body/pectoralH.png"));
		}else {
			imageViewPierna.setImage(new Image("body/piernaM.png"));
			imageViewAbdomen.setImage(new Image("body/abdomenM.png"));
			imageViewBrazo.setImage(new Image("body/brazoM.png"));
			imageViewHombro.setImage(new Image("body/hombroM.png"));
			imageViewEspalda.setImage(new Image("body/espaldaM.png"));
			imageViewPectoral.setImage(new Image("body/pectoralM.png"));
		}
	}
	
	@FXML
    void onAbdomenClick(MouseEvent event) {
		setGrupo("Abdomen");
		if(onGrupo != null) onGrupo.handle(event);
    }

    @FXML
    void onBrazoClick(MouseEvent event) {
		setGrupo("Brazos");
    	if(onGrupo != null) onGrupo.handle(event);
    }
    
    @FXML
    void onEspaldaClick(MouseEvent event) {
		setGrupo("Espalda");
    	if(onGrupo != null) onGrupo.handle(event);
    }

    @FXML
    void onHombroClick(MouseEvent event) {
		setGrupo("Hombro");
    	if(onGrupo != null) onGrupo.handle(event);
    }

    @FXML
    void onPectoralClick(MouseEvent event) {
		setGrupo("Pectoral");
    	if(onGrupo != null) onGrupo.handle(event);
    }

    @FXML
    void onPiernaClick(MouseEvent event) {
		setGrupo("Pierna");
    	if(onGrupo != null) onGrupo.handle(event);
    }
    
    public void setonGrupo(EventHandler<MouseEvent> onGrupo) {
		this.onGrupo = onGrupo;
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
}
