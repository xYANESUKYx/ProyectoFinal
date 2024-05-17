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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class FisicoController implements Initializable {

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onFisico1;
	private EventHandler<MouseEvent> onFisico2;
	private EventHandler<MouseEvent> onFisico3;
	private EventHandler<MouseEvent> onFisico4;
	private EventHandler<MouseEvent> onFisico5;
	private EventHandler<MouseEvent> onFisico6;
	private EventHandler<MouseEvent> onInicio;
    private int fisico;
    private String tipoVentana;

	//view 
	@FXML
    private Label cualLabel;
	
    @FXML
    private ImageView fisicoImage1;
    
    @FXML
    private ImageView fisicoImage2;
    
    @FXML
    private ImageView fisicoImage3;
    
    @FXML
    private ImageView fisicoImage4;
    
    @FXML
    private ImageView fisicoImage5;
    
    @FXML
    private ImageView fisicoImage6;
    
    @FXML
    private Label tituloLabel;
    
	@FXML
    private ScrollPane view;
	
	public ScrollPane getView() {
		return view;
	}
		
	public FisicoController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FisicosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}

    @FXML
    void onFisico1Clicked(MouseEvent event) {
    	if(onFisico1 != null) onFisico1.handle(event);
    	marcarImagen(1);
    	setFisico(1);
    }

    @FXML
    void onFisico2Clicked(MouseEvent event) {
    	if(onFisico2 != null) onFisico2.handle(event);
    	marcarImagen(2);
    	setFisico(2);
    }

    @FXML
    void onFisico3Clicked(MouseEvent event) {
    	if(onFisico3 != null) onFisico3.handle(event);
    	marcarImagen(3);
    	setFisico(3);
    }

    @FXML
    void onFisico4Clicked(MouseEvent event) {
    	if(onFisico4 != null) onFisico4.handle(event);
    	marcarImagen(4);
    	setFisico(4);
    }

    @FXML
    void onFisico5Clicked(MouseEvent event) {
    	if(onFisico5 != null) onFisico5.handle(event);
    	marcarImagen(5);
    	setFisico(5);
    }

    @FXML
    void onFisico6Clicked(MouseEvent event) {
    	if(onFisico6 != null) onFisico6.handle(event);
    	marcarImagen(6);
    	setFisico(6);
    }


	@FXML
    void onBackClicked(MouseEvent event) {
		if(onBack != null) onBack.handle(event);
    }
	
	public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}
	
	public void modificarVentana(String concepto, String sexo, String fisicoSelect) {
		marcarImagen(Integer.parseInt(fisicoSelect));
		
		if(concepto.equals("objetivo")) {
			cualLabel.setText("¿Cuál es tu objetivo?");
			tituloLabel.setText("OBJETIVO");
			setTipoVentana("OBJETIVO");
		}else {
			cualLabel.setText("¿Cuál es tu estado actual?");
			tituloLabel.setText("ACTUALIDAD");
			setTipoVentana("ACTUALIDAD");
		}
		
		if(sexo.equals("mujer")) {
	    	fisicoImage1.setImage(new Image(cambiarRuta(fisicoImage1.getImage().getUrl(), "M")));
	    	fisicoImage2.setImage(new Image(cambiarRuta(fisicoImage2.getImage().getUrl(), "M")));
	    	fisicoImage3.setImage(new Image(cambiarRuta(fisicoImage3.getImage().getUrl(), "M")));
	    	fisicoImage4.setImage(new Image(cambiarRuta(fisicoImage4.getImage().getUrl(), "M")));
	    	fisicoImage5.setImage(new Image(cambiarRuta(fisicoImage5.getImage().getUrl(), "M")));
	    	fisicoImage6.setImage(new Image(cambiarRuta(fisicoImage6.getImage().getUrl(), "M")));
		}else {
	    	fisicoImage1.setImage(new Image(cambiarRuta(fisicoImage1.getImage().getUrl(), "H")));
	    	fisicoImage2.setImage(new Image(cambiarRuta(fisicoImage2.getImage().getUrl(), "H")));
	    	fisicoImage3.setImage(new Image(cambiarRuta(fisicoImage3.getImage().getUrl(), "H")));
	    	fisicoImage4.setImage(new Image(cambiarRuta(fisicoImage4.getImage().getUrl(), "H")));
	    	fisicoImage5.setImage(new Image(cambiarRuta(fisicoImage5.getImage().getUrl(), "H")));
	    	fisicoImage6.setImage(new Image(cambiarRuta(fisicoImage6.getImage().getUrl(), "H")));
		}
	}
	
	public static String cambiarRuta(String ruta, String sexo) {
		if(sexo.equals("H")) {
		    char[] letras = ruta.toCharArray();
		    letras[letras.length - 7] = 'H';
		    return new String(letras);
			
		} else {
		    char[] letras = ruta.toCharArray();
		    letras[letras.length - 7] = 'M';
		    return new String(letras);
		}
	}
	
	public void marcarImagen(int imagen) {

	    fisicoImage1.setStyle("-fx-opacity: 100%;");
	    fisicoImage2.setStyle("-fx-opacity: 100%;");
	    fisicoImage3.setStyle("-fx-opacity: 100%;");
	    fisicoImage4.setStyle("-fx-opacity: 100%;");
	    fisicoImage5.setStyle("-fx-opacity: 100%;");
	    fisicoImage6.setStyle("-fx-opacity: 100%;");
	    
    	setFisico(imagen);
	    
		switch (imagen) {
        case 1:
        	fisicoImage1.setStyle("-fx-opacity: 50%;");
            break;
        
        case 2:
        	fisicoImage2.setStyle("-fx-opacity: 50%;");
            break;
        
        case 3:
        	fisicoImage3.setStyle("-fx-opacity: 50%;");
            break;
            
        case 4:
        	fisicoImage4.setStyle("-fx-opacity: 50%;");
            break;
                
       case 5:
    	    fisicoImage5.setStyle("-fx-opacity: 50%;");
            break;
                
       case 6:
    	     fisicoImage6.setStyle("-fx-opacity: 50%;");
             break;
		}
	}

	public int getFisico() {
		return fisico;
	}

	public void setFisico(int fisico) {
		this.fisico = fisico;
	}

	public String getTipoVentana() {
		return tipoVentana;
	}

	public void setTipoVentana(String tipoVentana) {
		this.tipoVentana = tipoVentana;
	}	

    @FXML
    void onInicioClicked(MouseEvent event) {
	 	if(onInicio != null) onInicio.handle(event);
	 }
	 
	 public void setonInicio(EventHandler<MouseEvent> onInicio) {
		this.onInicio = onInicio;
	}
}
