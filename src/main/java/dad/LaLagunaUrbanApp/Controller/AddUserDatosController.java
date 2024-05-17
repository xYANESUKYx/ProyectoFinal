package dad.LaLagunaUrbanApp.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AddUserDatosController implements Initializable {

	private Connection connection;
	public void setConnection(Connection connection) {this.connection = connection;}

	//model
	private EventHandler<MouseEvent> onBack;
	private EventHandler<MouseEvent> onAvatar;
	private EventHandler<MouseEvent> onActualidad;
	private EventHandler<MouseEvent> onObjetivo;
	private EventHandler<MouseEvent> onHombre;
	private EventHandler<MouseEvent> onMujer;
	private int id_usuario;
	private String nombre = "";
	private int edad = 0;
	private Double peso = 0.0;
	private int altura = 0;
	private String sexo = "";
	private String estado = "1";
	private String objetivo = "1";
	
	//view  
	@FXML
    private ImageView actualidadImage;

    @FXML
    private TextField alturaText;

    @FXML
    private TextField edadTextField;

    @FXML
    private Label grasaAlabel;

    @FXML
    private Label grasaOlabel;

    @FXML
    private ImageView hombreImage;

    @FXML
    private ImageView mujerImage;

    @FXML
    private TextField nombreTextField;

    @FXML
    private ImageView objetivoImage;

    @FXML
    private Label pesoLabel;

    @FXML
    private Label pesoLabel1;

    @FXML
    private Label pesoObjetivoLabel;

    @FXML
    private TextField pesoTextField;

    @FXML
    private ImageView userImage;

    @FXML
    private ScrollPane view;

	public ScrollPane getView() {
		return view;
	}
		
	public AddUserDatosController( ) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddUserDatosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 25 && newText.matches("[a-zA-Z\\s]*")) {
                return change;
            } else {
                return null; 
            }
        };
        nombreTextField.setTextFormatter(new TextFormatter<>(filter));
        
        UnaryOperator<TextFormatter.Change> filter2 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}(\\.\\d{0,1})?")) {
                return change;
            } else {
                return null; 
            }
        };
        pesoTextField.setTextFormatter(new TextFormatter<>(filter2));
        
        UnaryOperator<TextFormatter.Change> filter3 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}")) {
                return change;
            } else {
                return null; 
            }
        };
        alturaText.setTextFormatter(new TextFormatter<>(filter3));
    
        UnaryOperator<TextFormatter.Change> filter4 = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,2}")) {
                return change;
            } else {
                return null; 
            }
        };
        edadTextField.setTextFormatter(new TextFormatter<>(filter4));
        
		pesoTextField.textProperty().addListener((o, ov, nv) -> {
		    pesoLabel.setText(nv);
		});
	}
	
	@FXML
    void onBackClicked(MouseEvent event) {
		//GUARDAR DATOS
	/*
		
		if (nombreTextField != null && !nombreTextField.getText().isEmpty()) {
			this.nombre = nombreTextField.getText();
		}
		if (edadTextField != null && !edadTextField.getText().isEmpty()) {
		    this.edad = Integer.parseInt(edadTextField.getText());
		}
		if (pesoTextField != null && !pesoTextField.getText().isEmpty()) {
			this.peso = Double.parseDouble(pesoTextField.getText());
		}
		if (alturaText != null && !alturaText.getText().isEmpty()) {
			this.altura = Integer.parseInt(alturaText.getText());
		}
		
		try {actualizarUsuario();} catch (SQLException e) {e.printStackTrace();}
		*/
    	if(onBack != null) onBack.handle(event);
    }
	
	 public void actualizarUsuario() throws SQLException {
	        String sql = "UPDATE usuarios SET nombre = ?, edad = ?, peso = ?, altura = ?, sexo = ?, estado = ?, objetivo = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            // Establecer los parámetros de la sentencia SQL
	            statement.setString(1, nombre);
	            statement.setInt(2, edad);
	            statement.setDouble(3, peso);
	            statement.setDouble(4, altura);
	            statement.setString(5, sexo);
	            statement.setString(6, estado);
	            statement.setString(7, objetivo);
	            statement.setInt(8, id_usuario);
	            
	            // Ejecutar la sentencia SQL de actualización
	            int filasActualizadas = statement.executeUpdate();
	            
	            if (filasActualizadas > 0) {
	                System.out.println("Se han actualizado los datos del usuario con ID  correctamente.");
	            } else {
	                System.out.println("No se ha encontrado ningún usuario con el ID " );
	            }
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
	
	@FXML
	void onHClicked(MouseEvent event) {
    	if(onHombre != null) onHombre.handle(event);
    	
    	setSexo("hombre");

    	hombreImage.setStyle("-fx-opacity: 50%;");
    	mujerImage.setStyle("-fx-opacity: 100%;");
    	
    	String actualidad = actualidadImage.getImage().getUrl();
    	String objetivo = objetivoImage.getImage().getUrl();
    	
	    actualidadImage.setImage(new Image(cambiarRuta(actualidad, "H")));
	    objetivoImage.setImage(new Image(cambiarRuta(objetivo, "H")));
	    
	    actualidadImage.prefHeight(175);
	    actualidadImage.prefWidth(200);

	    modificarGrasa("H");
	}
	
	public void setonHombre(EventHandler<MouseEvent> onHombre) {
		this.onHombre = onHombre;
	}

	@FXML
	void onMClicked(MouseEvent event) {
    	if(onMujer != null) onMujer.handle(event);
    	
    	setSexo("mujer");

    	mujerImage.setStyle("-fx-opacity: 50%;");
    	hombreImage.setStyle("-fx-opacity: 100%;");
    	
    	String actualidad = actualidadImage.getImage().getUrl();
    	String objetivo = objetivoImage.getImage().getUrl();
    	
	    actualidadImage.setImage(new Image(cambiarRuta(actualidad, "M")));
	    objetivoImage.setImage(new Image(cambiarRuta(objetivo, "M")));
	    
	    actualidadImage.prefHeight(175);
	    actualidadImage.prefWidth(200);

	    modificarGrasa("M");
	}
	
	public void setonMujer(EventHandler<MouseEvent> onMujer) {
		this.onMujer = onMujer;
	}	
	
	@FXML
    void onActualidadClicked(MouseEvent event) {
    	if(onActualidad != null) onActualidad.handle(event);
    }
	
	public void setonActualidad(EventHandler<MouseEvent> onActualidad) {
		this.onActualidad = onActualidad;
	}
	
	@FXML
    void onObjetivoClicked(MouseEvent event) {
    	if(onObjetivo != null) onObjetivo.handle(event);
    }
    
    public void setonObjetivo(EventHandler<MouseEvent> onObjetivo) {
		this.onObjetivo = onObjetivo;
	}
    
    public void setonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}
    
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void getDatos(int id_usuario, String nombre, int edad, Double peso, int altura, String sexo, String estado, String objetivo) {
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.altura = altura;
		this.sexo = sexo;
		this.estado = estado;
		this.objetivo = objetivo;
	}

	public void cargarDatos() {
		nombreTextField.setText(nombre);
		edadTextField.setText(edad + "");
		alturaText.setText(altura + "");
		pesoTextField.setText(peso + "");
		pesoLabel.setText(peso + "");
		
		if (sexo.equals("mujer")) {
        	mujerImage.setStyle("-fx-opacity: 50%;");
        	
        	String actualidad = actualidadImage.getImage().getUrl();
        	String objetivo = objetivoImage.getImage().getUrl();
        	
    	    actualidadImage.setImage(new Image(cambiarRuta(actualidad, "M")));
    	    objetivoImage.setImage(new Image(cambiarRuta(objetivo, "M")));
    	    
    	    modificarGrasa("M");
    	    calcularPesoMujer(altura, objetivo);
		}else {
			hombreImage.setStyle("-fx-opacity: 50%;");
	    	
	    	String actualidad = actualidadImage.getImage().getUrl();
	    	String objetivo = objetivoImage.getImage().getUrl();
	    	
		    actualidadImage.setImage(new Image(cambiarRuta(actualidad, "H")));
		    objetivoImage.setImage(new Image(cambiarRuta(objetivo, "H")));
		    
		    modificarGrasa("H");
    	    calcularPesoHombre(altura, objetivo);
		}
		
    	String actualidad = actualidadImage.getImage().getUrl();
    	String objetivo = objetivoImage.getImage().getUrl();
    	
	    actualidadImage.setImage(new Image(cambiarFisico(actualidad, this.estado.charAt(0))));
	    objetivoImage.setImage(new Image(cambiarFisico(objetivo, this.objetivo.charAt(0))));
	}
	
	private void modificarGrasa(String sexo) {
		if(sexo.equals("H")) {
			switch(estado) {
		    	case "1":
		    		grasaAlabel.setText("12% - 15% Grasa");
		    		break;
		    	case "2":
		    		grasaAlabel.setText("15% - 19% Grasa");
		    		break;
		    	case "3":
		    		grasaAlabel.setText("20% - 40% Grasa");
		    		break;
		    	case "4":
		    		grasaAlabel.setText("10% - 12% Grasa");
		    		break;
		    	case "5":
		    		grasaAlabel.setText("8% - 10% Grasa");
		    		break;
		    	case "6":
		    		grasaAlabel.setText("5% - 8% Grasa");
		    		break;
			}
			switch(objetivo) {
	    	case "1":
	    		grasaOlabel.setText("12% - 15% Grasa");
	    		break;
	    	case "2":
	    		grasaOlabel.setText("15% - 19% Grasa");
	    		break;
	    	case "3":
	    		grasaOlabel.setText("20% - 40% Grasa");
	    		break;
	    	case "4":
	    		grasaOlabel.setText("10% - 12% Grasa");
	    		break;
	    	case "5":
	    		grasaOlabel.setText("8% - 10% Grasa");
	    		break;
	    	case "6":
	    		grasaOlabel.setText("5% - 8% Grasa");
	    		break;
		}
		}else {
			switch(estado) {
	    	case "1":
	    		grasaAlabel.setText("25% - 30% Grasa");
	    		break;
	    	case "2":
	    		grasaAlabel.setText("30% - 35% Grasa");
	    		break;
	    	case "3":
	    		grasaAlabel.setText("35% - 45% Grasa");
	    		break;
	    	case "4":
	    		grasaAlabel.setText("20% - 25% Grasa");
	    		break;
	    	case "5":
	    		grasaAlabel.setText("15% - 20% Grasa");
	    		break;
	    	case "6":
	    		grasaAlabel.setText("5% - 15% Grasa");
	    		break;
			}	
			switch(objetivo) {
		    	case "1":
		    		grasaOlabel.setText("25% - 30% Grasa");
		    		break;
		    	case "2":
		    		grasaOlabel.setText("30% - 35% Grasa");
		    		break;
		    	case "3":
		    		grasaOlabel.setText("35% - 45% Grasa");
		    		break;
		    	case "4":
		    		grasaOlabel.setText("20% - 25% Grasa");
		    		break;
		    	case "5":
		    		grasaOlabel.setText("15% - 20% Grasa");
		    		break;
		    	case "6":
		    		grasaOlabel.setText("5% - 15% Grasa");
		    		break;
			}
		}
	}
    
	public static String cambiarFisico(String ruta, char num) {
		char[] letras = ruta.toCharArray();
		letras[letras.length - 5] = num;
		return new String(letras);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}
    
	public void calcularPesoMujer(int altura, String objetivo) {
		int peso1 = 0, peso2 = 0;
		
		if (altura <= 147) {
		    peso1 = 49;
		    peso2 = 55;
		} else if (altura >= 148 && altura <= 150) {
		    peso1 = 50;
		    peso2 = 56;
		} else if (altura >= 151 && altura <= 152) {
		    peso1 = 51;
		    peso2 = 57;
		} else if (altura >= 153 && altura <= 155) {
		    peso1 = 52;
		    peso2 = 59;
		} else if (altura >= 156 && altura <= 157) {
		    peso1 = 54;
		    peso2 = 60;
		} else if (altura >= 158 && altura <= 160) {
		    peso1 = 55;
		    peso2 = 61;
		} else if (altura >= 161 && altura <= 163) {
		    peso1 = 56;
		    peso2 = 63;
		} else if (altura >= 164 && altura <= 165) {
		    peso1 = 58;
		    peso2 = 64;
		} else if (altura >= 166 && altura <= 168) {
		    peso1 = 59;
		    peso2 = 65;
		} else if (altura >= 169 && altura <= 170) {
		    peso1 = 60;
		    peso2 = 67;
		} else if (altura >= 171 && altura <= 173) {
		    peso1 = 62;
		    peso2 = 68;
		} else if (altura >= 174 && altura <= 175) {
		    peso1 = 63;
		    peso2 = 70;
		} else if (altura >= 176 && altura <= 178) {
		    peso1 = 60;
		    peso2 = 71;
		} else if (altura >= 179 && altura <= 180) {
		    peso1 = 66;
		    peso2 = 72;
		} else if (altura >= 181) {
		    peso1 = 67;
		    peso2 = 74;
		}	
		System.out.print("" + objetivo);
		if(objetivo.equals("1")) {
			//1  = - =
			pesoObjetivoLabel.setText(peso1 + "-" + peso2 + " kg");
			
		} else if(objetivo.equals("2")) {
			//2  +5 - +5
			pesoObjetivoLabel.setText(peso1 + 5 + "-" + peso2 + 5 + " kg");
			
		} else if(objetivo.equals("3")) {
			//3  +10 - <
			pesoObjetivoLabel.setText(peso1 + 10 + "-< kg");
			
		} else if(objetivo.equals("4")) {
			//4  +3 - +3
			pesoObjetivoLabel.setText(peso1 + 3 + "-" + peso2 + 3 + " kg");
			
		} else if(objetivo.equals("5")) {
			//5  +6 - +6
			pesoObjetivoLabel.setText(peso1 + 6 + "-" + peso2 + 6 + " kg");
			
		} else if(objetivo.equals("6")) {
			//6  +9 - <
			pesoObjetivoLabel.setText(peso1 + 9 + "-< kg");
		}
	}
	
	public void calcularPesoHombre(int altura, String objetivo) {
		int peso1 = 0, peso2 = 0;
		
		if (altura <= 157) {
			peso1 = 59;
			peso2 = 64;
	    } else if (altura >= 158 && altura <= 160) {
            peso1 = 60;
            peso2 = 65;
        } else if (altura >= 161 && altura <= 163) {
            peso1 = 61;
            peso2 = 65;
        } else if (altura >= 164 && altura <= 165) {
            peso1 = 62;
            peso2 = 67;
        } else if (altura >= 166 && altura <= 168) {
            peso1 = 63;
            peso2 = 68;
        } else if (altura >= 169 && altura <= 170) {
            peso1 = 64;
            peso2 = 70;
        } else if (altura >= 171 && altura <= 173) {
            peso1 = 65;
            peso2 = 71;
        } else if (altura >= 174 && altura <= 175) {
            peso1 = 67;
            peso2 = 72;
        } else if (altura >= 176 && altura <= 178) {
            peso1 = 68;
            peso2 = 74;
        } else if (altura >= 179 && altura <= 180) {
            peso1 = 70;
            peso2 = 75;
        } else if (altura >= 181 && altura <= 183) {
            peso1 = 71;
            peso2 = 77;
        } else if (altura >= 184 && altura <= 185) {
            peso1 = 72;
            peso2 = 79;
        } else if (altura >= 185 && altura <= 187) {
            peso1 = 74;
            peso2 = 80;
        } else if (altura >= 187 && altura <= 189) {
            peso1 = 75;
            peso2 = 82;
	    } else if (altura >= 190) {
            peso1 = 77;
            peso2 = 85;
	    } 
		
		if(objetivo.equals("1")) {
			//1  < - 10
			pesoObjetivoLabel.setText("<-" + (peso2 - 10) + " kg");
			
		} else if(objetivo.equals("2")) {
			//2  = - =
			pesoObjetivoLabel.setText(peso1 + "-" + peso2 + " kg");
			
		} else if(objetivo.equals("3")) {
			//3  +10 - <
			pesoObjetivoLabel.setText(peso1 + 10 + "-< kg");
			
		} else if(objetivo.equals("4")) {
			//4  +5 - +5
			pesoObjetivoLabel.setText((peso1 + 5) + "-" + (peso2 + 5) + " kg");
			
		} else if(objetivo.equals("5")) {
			//5  +8 - +8
			pesoObjetivoLabel.setText((peso1 + 8 )+ "-" + (peso2 + 8) + " kg");
			
		} else if(objetivo.equals("6")) {
			//6  +10 - <
			pesoObjetivoLabel.setText(peso1 + 10 + "-< kg");
			
		}
	}

    @FXML
    void onGuardarClicked(MouseEvent event) {

    }
	
    @FXML
    void onAvatarClicked(MouseEvent event) {
    	if(onAvatar != null) onAvatar.handle(event);
    }

    public void setonAvatar(EventHandler<MouseEvent> onAvatar) {
		this.onAvatar = onAvatar;
	}
}

	
/*
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

    @FXML
    void onBackClicked(MouseEvent event) {
    	if(onBack != null) onBack.handle(event);
    }

    public void setoonBack(EventHandler<MouseEvent> onBack) {
		this.onBack = onBack;
	}

    @FXML
    void onHClicked(MouseEvent event) {
    	if(onHombre != null) onHombre.handle(event);
    }
	
	public void setonHombre(EventHandler<MouseEvent> onHombre) {
		this.onHombre = onHombre;
	}

    @FXML
    void onMClicked(MouseEvent event) {

    }
    
    @FXML
    void onActualidadClicked(MouseEvent event) {
    	if(onActualidad != null) onActualidad.handle(event);
    }
	
	public void setonActualidad(EventHandler<MouseEvent> onActualidad) {
		this.onActualidad = onActualidad;
	}
	
	@FXML
    void onObjetivoClicked(MouseEvent event) {
    	if(onObjetivo != null) onObjetivo.handle(event);
    }
    
    public void setonObjetivo(EventHandler<MouseEvent> onObjetivo) {
		this.onObjetivo = onObjetivo;
	}

    @FXML
    void onGuardarClicked(MouseEvent event) {

    }

    @FXML
    void onAvatarClicked(MouseEvent event) {
    	if(onAvatar != null) onAvatar.handle(event);
    }

    public void setonAvatar(EventHandler<MouseEvent> onAvatar) {
		this.onAvatar = onAvatar;
	}
    

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}*/
