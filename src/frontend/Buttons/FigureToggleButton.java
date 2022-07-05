package frontend.Buttons;

import javafx.scene.control.ToggleButton;

public abstract class FigureToggleButton extends ToggleButton implements FigureMaker {
    FigureToggleButton(String name){
        super(name);
    }
}
