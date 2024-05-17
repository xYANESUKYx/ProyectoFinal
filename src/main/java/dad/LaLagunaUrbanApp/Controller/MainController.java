package dad.LaLagunaUrbanApp.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dad.LaLagunaUrbanApp.Bd.Conection;
import dad.LaLagunaUrbanApp.Bd.usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController implements Initializable {

	private usuario usuario = new usuario();
	private LoginController loginController;
	private PerfilOtherController perfilOtherController;
	private AddUserController addUserController;
	private AddUser2Controller addUser2Controller;
	private AjustesController ajustesController;
	private InicioController inicioController;
	private FisicoController fisicoController;
	private SuplementosController suplementosController;
	private SuplementosHoraController suplementosHoraController;
	private AddSuplementosController addSuplementosController;
	private AddSuplementoController addSuplementoController;
	private QuienesSomosController quienesSomosController;
	private DietasController dietasController;
	private AddDietaController addDietaController;
	private CreateDietaController createDietaController;
	private RutinasController rutinasController;
	private AddRutinaController addRutinaController;
	private CreateRutinaController createRutinaController;
	private VerRutinaController verRutinaController;
	private VerRutinaOtherController verRutinaOtherController;
	private AvataresController avataresController;
	private PerfilController perfilController;
	private ChangePassController changePassController;
	private VerDietaController verDietaController;
	private VerDietaOtherController verDietaOtherController;

    Conection conection = new Conection();
    Connection connection = conection.obtenerConexion();
	
	@FXML
	private BorderPane view;
	
	public BorderPane getView() {
		return view;
	}
		
	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instancias();
		
        //LOGIN > INICIO
		loginController.setonAcceder (e -> {
			usuario.setId_usuario(loginController.getId());
			cargarDatos();
			cargarDatosInstancias();
			view.setCenter(inicioController.getView());
		});
		
        //LOGIN > ADD-USER
		loginController.setonAddUser (e -> {
			view.setCenter(addUserController.getView());
		});
		
        //ADD-USER > BACK
		addUserController.setonBack (e -> {
			view.setCenter(loginController.getView());
		});
		
        //ADD-USER >ADD-USER2
		addUserController.setonAcceder (e -> {
			usuario.setNombre_user(addUserController.getNombre_user());
			usuario.setPassword(addUserController.getPassword());
			view.setCenter(addUser2Controller.getView());
		});
		
        //ADD-USER2 > INICIO
		addUser2Controller.setonAcceder(e -> {
			usuario.setUsuario(0, addUser2Controller.getNombre(), addUser2Controller.getEdad(), addUser2Controller.getPeso(), addUser2Controller.getAltura(), addUser2Controller.getSexo(), "2", "6", addUser2Controller.getAvatar(), addUserController.getPassword(), addUserController.getNombre_user());
			subirDatos();
			cargarDatosInstancias();
			view.setCenter(inicioController.getView());
		});

        //ADD-USER2 > BACK
		addUser2Controller.setonBack (e -> {
			view.setCenter(addUserController.getView());
		});
		
		//INICIO > RUTINAS
		inicioController.setonRutina (e -> {
			view.setCenter(rutinasController.getView());
		});
		
		//INICIO > VER-RUTINA-OTHER
		inicioController.setonVerRutina (e -> {
			verRutinaOtherController.rellenarTabla(inicioController.getIdRutina(), String.valueOf(usuario.getId_usuario()), inicioController.getGrupoRutina(), inicioController.getNombreRutina(), "I", usuario.getNombre(), usuario.getimagenAvatar());
			view.setCenter(verRutinaOtherController.getView());
		});
		
		//INICIO > VER-DIETA-OTHER
		inicioController.setonVerDieta (e -> {
			verDietaOtherController.rellenarTabla(inicioController.getIdDieta(), inicioController.getNombreDieta());
			view.setCenter(verDietaOtherController.getView());
		});
		
		//INICIO > DOWLOAD-RUTINA
		inicioController.setonDescargar (e -> {
			cargarRutinas();
		});
		
		//INICIO > DOWLOAD-DIETA
		inicioController.setonDescargarDieta (e -> {
			cargarDietas();
		});
		
		//RUTINAS > DELETE
		rutinasController.setonDelete (e -> {
			cargarRutinas();
		});
		
		//RUTINAS > INICIO
		rutinasController.setonBack (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//RUTINAS > PERFIL
		rutinasController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});
		
		//RUTINAS > VER RUTINA
		rutinasController.setonVerRutina (e -> {
			verRutinaController.rellenarTabla(rutinasController.getIdRutina(), String.valueOf(usuario.getId_usuario()), rutinasController.getGrupoRutina(), rutinasController.getNombreRutina(), "R", usuario.getNombre(),usuario.getimagenAvatar());
			view.setCenter(verRutinaController.getView());
		});
		
		//VER RUTINA > PERFIL
		verRutinaController.setonPerfil (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//VER RUTINA > INICIO
		verRutinaController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//VER RUTINA > RUTINAS
		verRutinaController.setonBack (e -> {
			view.setCenter(rutinasController.getView());
		});
		
		//VER RUTINA > DIETAS
		verRutinaController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//VER RUTINA > SUPLEMENTOS
		verRutinaController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//VER RUTINA-OTHER > INICIO
		verRutinaOtherController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//VER RUTINA-OTHER > RUTINAS
		verRutinaOtherController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		});
		
		//VER RUTINA-OTHER > DIETAS
		verRutinaOtherController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//VER RUTINA-OTHER > SUPLEMENTOS
		verRutinaOtherController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//VER RUTINA-OTHER > PERFIL
		verRutinaOtherController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//VER RUTINA-OTHER > PERFIL-USER
		verRutinaOtherController.setonPerfilOther (e -> {
			perfilOtherController.cargarDatos(verRutinaOtherController.getId_user(), verRutinaOtherController.getImagen(), true, verRutinaOtherController.getNombre_user());
			view.setCenter(perfilOtherController.getView());
		});
		
		//VER DIETA-OTHER > PERFIL-USER
		verDietaOtherController.setonPerfilOther (e -> {
			perfilOtherController.cargarDatos(verDietaOtherController.getId_user(), verDietaOtherController.getImagen(), true, verDietaOtherController.getNombre_user());
			view.setCenter(perfilOtherController.getView());
		});
		
		//RUTINAS > ADD RUTINA
		rutinasController.setonRutina (e -> {
			view.setCenter(addRutinaController.getView());
		});
		
		//RUTINAS > DIETAS
		rutinasController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//RUTINAS > SUPLEMENTOS
		rutinasController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//ADD RUTINA > PERFIL
		addRutinaController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		}); 
		
		//ADD RUTINA > INICIO
		addRutinaController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		}); 
		
		//ADD RUTINA > DIETAS
		addRutinaController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//ADD RUTINA > SUPLEMENTOS
		addRutinaController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		}); 
		
		//CREATE RUTINA > RUTINAS
		createRutinaController.setonBack (e -> {
			view.setCenter(rutinasController.getView());
		});
		
		//CREATE RUTINA > INICIO
		createRutinaController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//CREATE RUTINA > DIETAS
		createRutinaController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//CREATE RUTINA > SUPLEMENTOS
		createRutinaController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//CREATE RUTINA > PERFIL
		createRutinaController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//CREATE RUTINA > GUARDAR (RUTINAS)
		createRutinaController.setonGuardar (e -> {
			cargarRutinas();
			view.setCenter(rutinasController.getView());
		}); 
		
		//ADD RUTINA > CREATE RUTINA 
		addRutinaController.setonGrupo (e -> {
			createRutinaController.rellenarTabla(addRutinaController.getGrupo(), String.valueOf(usuario.getId_usuario()));
			view.setCenter(createRutinaController.getView());
		}); 
		
		//ADD RUTINA > BACK
		addRutinaController.setonBack (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//INICIO > DIETAS
		inicioController.setonDieta (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//INICIO > SUPLEMENTOS
		inicioController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//SUPLEMENTOS > ADD-SUPLEMENTOS
		suplementosController.setonAdd(e -> {
			addSuplementosController.refrescarDatos();
			view.setCenter(addSuplementosController.getView());
		});
		
		//ADD-SUPLEMENTOS > SUPLEMENTOS
		addSuplementosController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//ADD-SUPLEMENTOS > INICIO
		addSuplementosController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//ADD-SUPLEMENTOS > RUTINAS
		addSuplementosController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//ADD-SUPLEMENTOS > DIETAS
		addSuplementosController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//ADD-SUPLEMENTOS > PERFIL
		addSuplementosController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//SUPLEMENTOS > PERFIL
		suplementosController.setonUsuario(e -> {
			//perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), false, usuario.getNombre());
			view.setCenter(perfilController.getView());
		});
		
		//ADD SUPLEMENTOS > PERFIL
		addSuplementosController.setonUsuario(e -> {
			perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), 
					false, usuario.getNombre());
			view.setCenter(perfilController.getView());
		});
		
		//ADD SUPLEMENTOS > PERFIL
		addSuplementosController.setonUsuario(e -> {
			perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), 
					false, usuario.getNombre());
			view.setCenter(perfilController.getView());
		});
		
		//ADD SUPLEMENTOS > SUPLEMENTO
		addSuplementosController.setonSuplemento(e -> {
			addSuplementoController.seleccionarSuplemento(usuario.getPeso());
			addSuplementoController.setIdUser(Integer.toString(usuario.getId_usuario()));
			addSuplementoController.setSuplemento(addSuplementosController.getSuplemento());
			addSuplementoController.seleccionarSuplemento(usuario.getPeso());
			view.setCenter(addSuplementoController.getView());
		});
		
		//SUPLEMENTO > ADD (ACTUALIZAR-DATOS)
		addSuplementoController.setonSuplementos(e -> {
			suplementosHoraController.cargarDatos(usuario);
		});
		
		//ADD-SUPLEMENTO > SUPLEMENTOS
		addSuplementoController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//ADD-SUPLEMENTO > INICIO
		addSuplementoController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//ADD-SUPLEMENTO > RUTINAS
		addSuplementoController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//ADD-SUPLEMENTO > DIETAS
		addSuplementoController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//ADD-SUPLEMENTO > PERFIL
		addSuplementoController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//SUPLEMENTO > PERFIL
		addSuplementoController.setonUsuario(e -> {
			perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), 
					false, usuario.getNombre());
			view.setCenter(perfilController.getView());
		});
		
		//INICIO > QUIENES SOMOS
		inicioController.setonLogo (e -> {
			view.setCenter(quienesSomosController.getView());
		});
		
		//INICIO > PERFIL
		inicioController.setonUsuario (e -> {
			perfilController.cargarDatos(usuario);
			view.setCenter(perfilController.getView());
		});

		//PERFIL-OTHER > DOWLOAD-RUTINA
		perfilOtherController.setonDescargar (e -> {
			cargarRutinas();
		});
		
		//PERFIL-OTHER > DOWLOAD-DIETA
		perfilOtherController.setonDescargarDieta (e -> {
			cargarDietas();
		});
		
		//PERFIL-OTHER > PERFIL
		perfilOtherController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//PERFIL-OTHER > SUPLEMENTOS
		perfilOtherController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//PERFIL-OTHER > INICIO
		perfilOtherController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//PERFIL-OTHER > RUTINAS
		perfilOtherController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//PERFIL-OTHER > DIETAS
		perfilOtherController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//PERFIL-OTHER > VER RUTINA-OTHER
		perfilOtherController.setonVerRutina (e -> {
			verRutinaOtherController.rellenarTabla(perfilOtherController.getIdRutina(), String.valueOf(usuario.getId_usuario()), perfilOtherController.getGrupoRutina(), perfilOtherController.getNombreRutina(), "I", usuario.getNombre(), usuario.getimagenAvatar());
			view.setCenter(verRutinaOtherController.getView());
		});
		
		//PERFIL-OTHER > VER DIETA-OTHER
		perfilOtherController.setonVerDieta (e -> {
			verDietaOtherController.rellenarTabla(perfilOtherController.getIdDieta(), perfilOtherController.getNombreDieta());
			view.setCenter(verDietaOtherController.getView());
		});
		
		//PERFIL > AJUSTES
		perfilController.setonAjustes(e -> {
			ajustesController.getDatos(usuario);
			view.setCenter(ajustesController.getView());
		});

		//PERFIL > SUPLEMENTOS
		perfilController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//PERFIL > INICIO
		perfilController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//PERFIL > RUTINAS
		perfilController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//PERFIL > DIETAS
		perfilController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//PERFIL > AVATARES
		perfilController.setonAvatares (e -> {
			avataresController.setIdUser(Integer.toString(usuario.getId_usuario()));
			avataresController.setAvatar(usuario.getAvatar());
			avataresController.seleccionarAvatar(usuario.getimagenAvatar(), usuario.getNombre()	);
			view.setCenter(avataresController.getView());
		});
		
		//PERFIL > VER RUTINA
		perfilController.setonVerRutina (e -> {
			verRutinaController.rellenarTabla(perfilController.getIdRutina(), String.valueOf(usuario.getId_usuario()), perfilController.getGrupoRutina(), perfilController.getNombreRutina(), "R", usuario.getNombre(), usuario.getimagenAvatar());
			view.setCenter(verRutinaController.getView());
		});
		
		//PERFIL > VER DIETA
		perfilController.setonVerRutina (e -> {
			verDietaController.rellenarTabla(perfilController.getIdDieta(), perfilController.getNombreDieta());
			view.setCenter(verDietaController.getView());
		});
		
		//PERFIL > DELETE-RUTINA
		perfilController.setonDelete (e -> {
			cargarRutinas();
		});
		
		//PERFIL > DELETE-DIETA
		perfilController.setonDeleteDieta (e -> {
			cargarDietas();
		});
		
		//PERFIL > CHANGE P RUTINAS
		perfilController.setonChangePrivado (e -> {
			cargarRutinas();
			cargarDietas();
		});
		
		//AVATARES > CHANGE AVATAR
		avataresController.setonAvatar (e -> {
			usuario.setAvatar(avataresController.getAvatar());
			avataresController.seleccionarAvatar(usuario.getimagenAvatar(), usuario.getNombre());
			cargarDatosInstancias();
		});
		
		//AVATARES > BACK-(CHANGE AVATAR)
		avataresController.setonBack (e -> {
			usuario.setAvatar(avataresController.getAvatar());
			perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), 
					false, usuario.getNombre());
			view.setCenter(perfilController.getView());
		});
		
		//AVATARES > PERFIL
		avataresController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//AVATARES > SUPLEMENTOS
		avataresController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//AVATARES > INICIO
		avataresController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//AVATARES > RUTINAS
		avataresController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//AVATARES > DIETAS
		avataresController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//CHANGE-PASS > CHANGE-USER
		changePassController.setonChangeUser (e -> {
			usuario.setNombre_user(changePassController.getName_user());
		});
		
		//CHANGE-PASS > SALIR
		changePassController.setonSalir(e -> {
			view.setCenter(loginController.getView());
		});
		
		//CHANGE-PASS > BORRAR-CUENTA
		changePassController.setonBorrarCuenta(e -> {
			view.setCenter(loginController.getView());
		});

		//CHANGE-PASS > SUPLEMENTOS
		changePassController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//CHANGE-PASS > INICIO
		changePassController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//CHANGE-PASS > RUTINAS
		changePassController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//CHANGE-PASS > DIETAS
		changePassController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//CHANGE-PASS > PERFIL
		changePassController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		}); 
		
		//AJUSTES > PERFIL
		ajustesController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		});
		
		//AJUSTES > CHANGE-PASS
		ajustesController.setonChangeUser (e -> {
			view.setCenter(changePassController.getView());
		});
		
		//AJUSTES > FISICO-OBJETIVO
		ajustesController.setonObjetivo (e -> {
			fisicoController.modificarVentana("objetivo", ajustesController.getSexo(), usuario.getObjetivo());
			view.setCenter(fisicoController.getView());
		});
		
		//AJUSTES > FISICO-ACTUALIDAD
		ajustesController.setonActualidad(e -> {
			fisicoController.setFisico(2);
			fisicoController.modificarVentana("actualidad", ajustesController.getSexo(), usuario.getEstado());
			view.setCenter(fisicoController.getView());
		});
		
		//FISICO > AJUSTES
		fisicoController.setonBack (e -> {
			if (fisicoController.getTipoVentana().equals("OBJETIVO")) {
				usuario.setObjetivo(Integer.toString(fisicoController.getFisico()));
				ajustesController.setObjetivo(usuario.getObjetivo());
				
			}else {
				usuario.setEstado(Integer.toString(fisicoController.getFisico()));
				ajustesController.setEstado(usuario.getEstado());
			}
			ajustesController.cargarDatos();
			view.setCenter(ajustesController.getView());
		});
		
		//FISICO > INICIO
		fisicoController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//AJUSTES > GUARDAR CAMBIOS
		ajustesController.setonGuardar (e -> {
			usuario.setNombre(ajustesController.getNombre());		
			usuario.setEdad(ajustesController.getEdad());	
			usuario.setPeso(ajustesController.getPeso());	
			usuario.setAltura(ajustesController.getAltura());	
			usuario.setSexo(ajustesController.getSexo());	
			usuario.setEstado(ajustesController.getEstado());	
			usuario.setObjetivo(ajustesController.getObjetivo());	
			
			cargarRutinas();
		});

		//AJUSTES > SUPLEMENTOS
		ajustesController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//AJUSTES > INICIO
		ajustesController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//AJUSTES > RUTINAS
		ajustesController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//AJUSTES > DIETAS
		ajustesController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//QUIENES-SOMOS > INICIO
		quienesSomosController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});

		//QUIENES-SOMOS > SUPLEMENTOS
		quienesSomosController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//QUIENES-SOMOS > RUTINAS
		quienesSomosController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//QUIENES-SOMOS > DIETAS
		quienesSomosController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//QUIENES-SOMOS > PERFIL
		quienesSomosController.setonUsuario (e -> {
			view.setCenter(perfilController.getView());
		}); 
		
		//SUPLEMENTOS > INICIO
		suplementosController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//SUPLEMENTOS > RUTINAS
		suplementosController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//SUPLEMENTOS > DIETAS
		suplementosController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 

		//SUPLEMENTOS > SUPLEMENTOS-MAÑANA
		suplementosHoraController.setConnection(connection);
		suplementosController.setonMorning (e -> {
			suplementosHoraController.setIdUser(Integer.toString(usuario.getId_usuario()));
			suplementosHoraController.rellenarTabla("morning", usuario.getPeso());
			view.setCenter(suplementosHoraController.getView());
		});

		//SUPLEMENTOS > SUPLEMENTOS-TARDE
		suplementosController.setonTarde (e -> {
			suplementosHoraController.setIdUser(Integer.toString(usuario.getId_usuario()));
			suplementosHoraController.rellenarTabla("tarde", usuario.getPeso());
			view.setCenter(suplementosHoraController.getView());
		});

		//SUPLEMENTOS > SUPLEMENTOS-NOCHE
		suplementosController.setonNoche (e -> {
			suplementosHoraController.setIdUser(Integer.toString(usuario.getId_usuario()));
			suplementosHoraController.rellenarTabla("noche", usuario.getPeso());
			view.setCenter(suplementosHoraController.getView());
		});

		//SUPLEMENTOS-HORA > SUPLEMENTOS
		suplementosHoraController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//SUPLEMENTOS-HORA > INICIO
		suplementosHoraController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//SUPLEMENTOS-HORA > RUTINAS
		suplementosHoraController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//SUPLEMENTOS-HORA > DIETAS
		suplementosHoraController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		}); 

		//SUPLEMENTOS-HORA > PERFIL
		suplementosHoraController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//DIETAS > ADD DIETA
		dietasController.setonDieta (e -> {
			if(dietasController.getDieta().equals("")) {
				view.setCenter(addDietaController.getView());
			}
		});
		
		//DIETAS > INICIO
		dietasController.setonInicio(e -> {
			view.setCenter(inicioController.getView());
		});
		
		//DIETAS > PERFIL
		dietasController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//DIETAS > SUPLEMENTOS
		dietasController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//DIETAS > RUTINAS
		dietasController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//ADD-DIETA > INICIO
		addDietaController.setonInicio(e -> {
			view.setCenter(inicioController.getView());
		});
		
		//ADD-DIETA > PERFIL
		addDietaController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//ADD-DIETA > SUPLEMENTOS
		addDietaController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//ADD-DIETA > RUTINAS
		addDietaController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//ADD-DIETA > DIETAS
		addDietaController.setonDietas(e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//ADD-DIETA > CREATE DIETA
		addDietaController.setonGenerarDieta (e -> {
			createDietaController.caloriasComidas(Integer.parseInt(addDietaController.getCalorias()), Integer.parseInt(addDietaController.getNumComidas()));
			view.setCenter(createDietaController.getView());
		});
		
		//CREATE-DIETA > INICIO
		createDietaController.setonInicio(e -> {
			view.setCenter(inicioController.getView());
		});
		
		//CREATE-DIETA > PERFIL
		createDietaController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//CREATE-DIETA > SUPLEMENTOS
		createDietaController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//CREATE-DIETA > RUTINAS
		createDietaController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//CREATE-DIETA > DIETAS
		createDietaController.setonDietas(e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//CREATE-DIETA > GUARDAR
		createDietaController.setonGuardar(e -> {
			cargarDietas();
			view.setCenter(dietasController.getView());
		}); 
		
		//DIETAS > VER DIETA
		dietasController.setonVerDieta (e -> {
			System.out.print("id dieta: " + dietasController.getIdDieta());
			verDietaController.rellenarTabla(dietasController.getIdDieta(), dietasController.getNombreDieta());
			view.setCenter(verDietaController.getView());
		});
		
		//DIETAS > DELETE-DIETA
		dietasController.setonDelete (e -> {
			cargarDietas();
		});
		
		//VER-DIETA > INICIO
		verDietaController.setonInicio(e -> {
			view.setCenter(inicioController.getView());
		});
		
		//VER-DIETA > PERFIL
		verDietaController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});

		//VER-DIETA > SUPLEMENTOS
		verDietaController.setonSuplementos(e -> {
			view.setCenter(suplementosController.getView());
		});
		
		//VER-DIETA > RUTINAS
		verDietaController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		}); 
		
		//VER-DIETA > DIETAS
		verDietaController.setonDietas(e -> {
			view.setCenter(dietasController.getView());
		}); 
		
		//VER-DIETA-OTHER > INICIO
		verDietaOtherController.setonInicio (e -> {
			view.setCenter(inicioController.getView());
		});
		
		//VER-DIETA-OTHER > PERFIL
		verDietaOtherController.setonUsuario(e -> {
			view.setCenter(perfilController.getView());
		});
		
		//VER-DIETA-OTHER > RUTINAS
		verDietaOtherController.setonRutinas (e -> {
			view.setCenter(rutinasController.getView());
		});
		
		//VER-DIETA-OTHER > DIETAS
		verDietaOtherController.setonDietas (e -> {
			view.setCenter(dietasController.getView());
		});
		
		//VER-DIETA-OTHER > SUPLEMENTOS
		verDietaOtherController.setonSuplementos (e -> {
			view.setCenter(suplementosController.getView());
		});
		

		view.setCenter(loginController.getView());
	}
	
	void cargarDatosInstancias() {
		dietasController.cargarDatos(usuario);
		verRutinaOtherController.cargarDatos(usuario);
		changePassController.cargarDatos(usuario);
		createDietaController.cargarDatos(usuario);
		quienesSomosController.cargarDatos(usuario);
		addDietaController.cargarDatos(usuario);
		suplementosController.cargarDatos(usuario);
		perfilController.cargarDatos(usuario);
		perfilOtherController.cargarDatos(usuario);
		suplementosHoraController.cargarDatos(usuario);
		inicioController.cargarDatos(usuario);
		rutinasController.cargarDatos(usuario);
		addSuplementosController.cargarDatos(usuario);
		addSuplementoController.cargarDatos(usuario);
		createRutinaController.cargarDatos(usuario);
		verDietaOtherController.cargarDatos(usuario);
		verDietaController.cargarDatos(usuario);
		perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), false, usuario.getNombre());
		addRutinaController.cargarDatos(usuario);
		addRutinaController.changeImages(usuario.getSexo());
	}
	
	void cargarRutinas() {
		inicioController.cargarDatos(usuario);
		rutinasController.cargarDatos(usuario);
		perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), false, usuario.getNombre());
	}
	
	void cargarDietas() {
		inicioController.cargarDatos(usuario);
		dietasController.cargarDatos(usuario);
		perfilController.cargarDatos(Integer.toString(usuario.getId_usuario()), usuario.getimagenAvatar(), false, usuario.getNombre());
	}
	
	void instancias() {
        //LOGIN
		loginController = new LoginController();
		loginController.setConnection(connection);
		
		//INICIO
		inicioController = new InicioController();
		inicioController.setConnection(connection);
		
		//PERFIL-OTHER
		perfilOtherController = new PerfilOtherController();
		perfilOtherController.setConnection(connection);
		
		//VER-RUTINA-OTHER
		verRutinaOtherController = new VerRutinaOtherController();
		verRutinaOtherController.setConnection(connection);
		
		//ADD-USER
		addUserController = new AddUserController();
		addUserController.setConnection(connection);
		
		//ADD-USER2
		addUser2Controller = new AddUser2Controller();
		
		//RUTINAS
		rutinasController = new RutinasController();
		rutinasController.setConnection(connection);
		
		//VER-RUTINA
		verRutinaController = new VerRutinaController();
		verRutinaController.setConnection(connection);
		
		//VER-DIETA
		verDietaController = new VerDietaController();
		verDietaController.setConnection(connection);
		
		//VER-DIETA-OTHER
		verDietaOtherController = new VerDietaOtherController();
		verDietaOtherController.setConnection(connection);
		
		//CREATE-RUTINA
		createRutinaController = new CreateRutinaController();
		createRutinaController.setConnection(connection);
		
		//ADD RUTINA
		addRutinaController = new AddRutinaController();
		
		//DIETAS
		dietasController = new DietasController();
		dietasController.setConnection(connection);
		
		//CREATE DIETA
		createDietaController = new CreateDietaController();
		createDietaController.setConnection(connection);
		
		//ADD-DIETA
		addDietaController = new AddDietaController();
		
		//SUPLEMENTOS
		addSuplementosController = new AddSuplementosController();
		addSuplementosController.setConnection(connection);
		
		//SUPLEMENTOS-HORA
		suplementosHoraController = new SuplementosHoraController();
		
		//SUPLEMENTOS
		suplementosController = new SuplementosController();
		
		//SUPLEMENTO
		addSuplementoController = new AddSuplementoController();
		addSuplementoController.setConnection(connection);
		
		//QUIENES-SOMOS
		quienesSomosController = new QuienesSomosController();
		
		//PERFIL
		perfilController = new PerfilController();
		perfilController.setConnection(connection);
		
		//AVATARES
		avataresController = new AvataresController();
		avataresController.setConnection(connection);
		
		//AJUSTES
		ajustesController = new AjustesController();
		ajustesController.setConnection(connection);
		
		//FISICO-OBJETIVO
		fisicoController = new FisicoController();
		
		//CHANGE-PASS
		changePassController = new ChangePassController();
		changePassController.setConnection(connection);
	}
	
	void subirDatos() {
	    String sql = "INSERT INTO usuarios (nombre, edad, peso, altura, sexo, estado, objetivo, avatar, password, nombre_user) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        statement.setString(1, usuario.getNombre());
	        statement.setInt(2, usuario.getEdad());
	        statement.setDouble(3, usuario.getPeso());
	        statement.setInt(4, usuario.getAltura());
	        statement.setString(5, usuario.getSexo());
	        statement.setString(6, "2");
	        statement.setString(7, "6");
	        statement.setString(8, usuario.getAvatar());
	        statement.setString(9, usuario.getPassword());
	        statement.setString(10, usuario.getNombre_user());
	        
	        int filasAfectadas = statement.executeUpdate();
	        if (filasAfectadas == 1) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int idGenerado = generatedKeys.getInt(1);
	                usuario.setId_usuario(idGenerado);
	            }
	        } 
	    } catch (SQLException e) {e.printStackTrace();}
	}

	void cargarDatos() {
		String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, String.valueOf(usuario.getId_usuario()));
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                //usuario.setId_usuario(resultSet.getInt("id_usuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setEdad(resultSet.getInt("edad"));
                usuario.setPeso(resultSet.getDouble("peso"));
                usuario.setAltura(resultSet.getInt("altura"));
                usuario.setSexo(resultSet.getString("sexo"));
                usuario.setEstado(resultSet.getString("estado"));
                usuario.setObjetivo(resultSet.getString("objetivo"));
                usuario.setAvatar(resultSet.getString("avatar"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setNombre_user(resultSet.getString("nombre_user"));
            }
        } catch (SQLException e) {e.printStackTrace();}
	}
	
}
