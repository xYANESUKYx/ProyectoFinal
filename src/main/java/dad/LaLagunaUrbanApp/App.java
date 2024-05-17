package dad.LaLagunaUrbanApp;

import dad.LaLagunaUrbanApp.Controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{
	
	private MainController controlador = new MainController();
		
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("La Laguna Urban App");
		primaryStage.setScene(new Scene(controlador.getView()));
		primaryStage.getIcons().add(new Image("images/logo.png"));
		primaryStage.show();
	}
}
