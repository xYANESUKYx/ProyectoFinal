package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddDietaController2 implements Initializable {

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onUsuario;
	private EventHandler<MouseEvent> onGenerarDieta;
	private String maxCValue = "0";
	private String maxGValue = "0";
	private String maxPValue = "0";
	private String maxKValue = "0";
	private String minCValue = "0";
	private String minGValue = "0";
	private String minKValue = "0";
	private String minPValue = "0";
	private String numComidas = "0";
	
	//view 
	@FXML
    private TextField maxC;

    @FXML
    private TextField maxG;

    @FXML
    private TextField maxP;

    @FXML
    private TextField maxK;

    @FXML
    private TextField minC;

    @FXML
    private TextField minG;

    @FXML
    private TextField minK;

    @FXML
    private TextField minP;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddDietaController2( ) {
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
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 6));
	}
	
	public void calcularYMostrarRangos(String sexo, double altura, double pesoActual, double porcentajeActual, double objetivo, int edad) {
	    System.out.println("sexo: " + sexo);
	    System.out.println("altura: " + altura);
	    System.out.println("peso actual: " + pesoActual);
	    System.out.println("porcentaje graso actual: " + porcentajeActual);
	    System.out.println("objetivo de porcentaje graso: " + objetivo);
	    System.out.println("edad: " + edad);

	    //PROTEINAS
	    double minProteinas = pesoActual * 1.8;
	    double maxProteinas = pesoActual * 2.2;
	    minP.setText(String.format("%.0f", minProteinas));
	    maxP.setText(String.format("%.0f", maxProteinas));
	    
	    //CALORIAS
	    double masaMagra = pesoActual * (1 - (porcentajeActual / 100));
	    double caloriasMinimas = masaMagra * 21; 
	    double caloriasMaximas = masaMagra * 33;
	    minK.setText(String.format("%.0f", caloriasMinimas));
	    maxK.setText(String.format("%.0f", caloriasMaximas));
	    
	    //CARBOHIDRATOS
	    double minCarbohidratos = sexo.equals("hombre") ? 4 : 3;
	    double maxCarbohidratos = sexo.equals("hombre") ? 7 : 5;
	    minC.setText(String.format("%.0f", minCarbohidratos * pesoActual));
	    maxC.setText(String.format("%.0f", maxCarbohidratos * pesoActual));
	    
	    //GRASAS
	    double minGrasas = masaMagra * (objetivo / 100);
	    double maxGrasas = masaMagra * (1 - (objetivo / 100));
	    minG.setText(String.format("%.0f", minGrasas));
	    maxG.setText(String.format("%.0f", maxGrasas));
	}
	
	
	@FXML
    void onGenerarDietaClicked(MouseEvent event) {
    	setMaxPValue(maxP.getText()); setMinPValue(minP.getText());
    	setMaxCValue(maxC.getText()); setMinCValue(minC.getText());
    	setMaxGValue(maxG.getText()); setMinGValue(minG.getText());
    	setMaxKValue(maxK.getText()); setMinKValue(minK.getText());
    	setNumComidas(Integer.toString(spinner.getValue()));
    	
    	if(onGenerarDieta != null) onGenerarDieta.handle(event);
    }
    
    public void setonGenerarDieta(EventHandler<MouseEvent> onGenerarDieta) {
		this.onGenerarDieta = onGenerarDieta;
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

	public String getMaxCValue() {
		return maxCValue;
	}

	public void setMaxCValue(String maxCValue) {
		this.maxCValue = maxCValue;
	}

	public String getMaxGValue() {
		return maxGValue;
	}

	public void setMaxGValue(String maxGValue) {
		this.maxGValue = maxGValue;
	}

	public String getMaxPValue() {
		return maxPValue;
	}

	public void setMaxPValue(String maxPValue) {
		this.maxPValue = maxPValue;
	}

	public String getMaxKValue() {
		return maxKValue;
	}

	public void setMaxKValue(String maxKValue) {
		this.maxKValue = maxKValue;
	}

	public String getMinCValue() {
		return minCValue;
	}

	public void setMinCValue(String minCValue) {
		this.minCValue = minCValue;
	}

	public String getMinGValue() {
		return minGValue;
	}

	public void setMinGValue(String minGValue) {
		this.minGValue = minGValue;
	}

	public String getMinKValue() {
		return minKValue;
	}

	public void setMinKValue(String minKValue) {
		this.minKValue = minKValue;
	}

	public String getMinPValue() {
		return minPValue;
	}

	public void setMinPValue(String minPValue) {
		this.minPValue = minPValue;
	}

	public String getNumComidas() {
		return numComidas;
	}

	public void setNumComidas(String numComidas) {
		this.numComidas = numComidas;
	}
}
