package dad.LaLagunaUrbanApp.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import dad.LaLagunaUrbanApp.Bd.usuario;

public class QuienesSomosController implements Initializable {

	//model 
	private EventHandler<MouseEvent> onRutinas;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onInicio;
	private EventHandler<MouseEvent> onDietas;
	private EventHandler<MouseEvent> onSuplementos;

	//view
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView ubiImage1;

    @FXML
    private ImageView ubiImage2;

    @FXML
    private ImageView userImage;

    @FXML
    private ScrollPane view;
	
	public ScrollPane getView() {
		return view;
	}
		
	public QuienesSomosController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuienesSomosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        MapView mapView = new MapView();
        mapView.setMaxWidth(390);
        mapView.setMaxHeight(240);
        mapView.setCenter(28.472850, -16.300793);
        mapView.setZoom(17);
     
        mapView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {Desktop.getDesktop().browse(new URI("https://maps.app.goo.gl/sdiduC447cbjZDju5"));
                } catch (Exception ex) {}
            }
        });

        mapView.flyTo(0, new MapPoint(28.472850, -16.300793), 0.1);
        
        MapView mapView2 = new MapView();
        mapView2.setMaxWidth(390);
        mapView2.setMaxHeight(240);
        
        mapView2.setCenter(28.477785471720512, -16.310087099498446);
        mapView2.setZoom(17);
     
        mapView2.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {Desktop.getDesktop().browse(new URI("https://maps.app.goo.gl/2yNoe66uLwcCV3t6A"));
                } catch (Exception ex) {}
            }
        });
        
        mapView2.flyTo(0, new MapPoint(28.477785471720512, -16.310087099498446), 0.1);
     
        gridPane.setAlignment(Pos.CENTER); 

        Border border = new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
  
        mapView.setBorder(border); 
        mapView2.setBorder(border);
        
		gridPane.getChildren().addAll(mapView, mapView2);
		
		GridPane.setColumnIndex(mapView, 0);
		GridPane.setRowIndex(mapView, 1);
		
		GridPane.setColumnIndex(mapView2, 1);
		GridPane.setRowIndex(mapView2, 1);
	}
	
	void cargarDatos(usuario usuario) {
		nombreLabel.setText(usuario.getNombre());
        userImage.setImage(new Image(usuario.getimagenAvatar()));
	}

    @FXML
    void onMapa1Clicked(MouseEvent event) {
    	System.out.print("drgfjuihdzrgiuo");
    }

    @FXML
    void hyperlink1(ActionEvent event) {
        try {Desktop.getDesktop().browse(new URI("https://www.instagram.com/lalagunaurbanfitness/?hl=es"));
        } catch (Exception ex) {}
    }

    @FXML
    void hyperlink2(ActionEvent event) {
        try {Desktop.getDesktop().browse(new URI("https://www.instagram.com/lalagunaurbanfuncional/?hl=es"));
        } catch (Exception ex) {}
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
