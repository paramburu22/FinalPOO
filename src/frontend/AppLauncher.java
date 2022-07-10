package frontend;

import backend.CanvasState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class AppLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		CanvasState canvasState = new CanvasState(); // BackEnd
		MainFrame frame = new MainFrame(canvasState);
		Scene scene = new Scene(frame);
		primaryStage.setTitle("Final POO - Grupo 9");
		Image image = new Image("frontend/icon2.png");
		primaryStage.getIcons().add(image);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

}
