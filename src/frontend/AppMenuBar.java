package frontend;

import javafx.scene.control.*;

import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class AppMenuBar extends MenuBar {

    public AppMenuBar() {
        Menu file = new Menu("Archivo");
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir de la aplicación");
            alert.setContentText("¿Está seguro que desea salir de la aplicación?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    System.exit(0);
                }
            }
        });
        file.getItems().add(exitMenuItem);
        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Paint");
            Image image = new Image("frontend/icon.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.setContentText("TPE Final POO Julio 2022 - Grupo" + "\n" + "Bruno Enzo Baumgart - 62161"+ "\n" + "Paz Aramburu - 62556"+ "\n" + "Gregorio Maspero - 62486");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);
        getMenus().addAll(file, help);
    }

}
