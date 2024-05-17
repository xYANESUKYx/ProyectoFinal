package dad.LaLagunaUrbanApp;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SplashScreen extends Preloader {

    private ProgressBar progressBar;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);

        BorderPane root = new BorderPane(progressBar);
        Scene scene = new Scene(root, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        progressBar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
}
