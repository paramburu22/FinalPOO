package frontend;

//import backend.Action.ActionType;
import backend.Action.ActionType;
import backend.CanvasState;
import backend.model.*;
import frontend.Buttons.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Slider;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;


public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	//Magic Numbers
	private static final double INITIAL_BORDER = 1;
	private static final Color LINE_COLOR = Color.BLACK;
	private static final Color FILL_COLOR = Color.YELLOW;


	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	FigureToggleButton rectangleButton = new RectangleButton("Rectángulo");
	FigureToggleButton circleButton = new CircleButton("Círculo");
	FigureToggleButton squareButton = new SquareButton("Cuadrado");
	FigureToggleButton ellipseButton = new EllipseButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	Slider slider = new Slider(1, 50, INITIAL_BORDER);
	ColorPicker lineColorPicker = new ColorPicker(LINE_COLOR);
	ColorPicker fillColorPicker = new ColorPicker(FILL_COLOR);
	ToggleButton increaseButton = new ToggleButton("Agrandar");
	ToggleButton decreaseButton = new ToggleButton("Achicar");
	ToggleButton undoButton = new ToggleButton("Deshacer");
	ToggleButton redo = new ToggleButton("Rehacer");
	Label undoLabel = new Label("0");
	Label redoLabel = new Label("0");
	FigureToggleButton[] figureButtonsArr = { rectangleButton, circleButton, squareButton, ellipseButton};
	ToggleButton[] listOfButtons = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton,increaseButton, decreaseButton, undoButton,redo};
	ColorPicker[] listOfColorPickers = {lineColorPicker, fillColorPicker};
	Control[] leftControls = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, new Label("Borde"), slider, lineColorPicker, new Label("Relleno"), fillColorPicker,increaseButton, decreaseButton};
	Control[] topControls = {undoLabel, undoButton,redo, redoLabel};

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleGroup tools = new ToggleGroup();

		setButtonsProps(listOfButtons,tools);
		setColorPickersProps(listOfColorPickers);
		setSliderProps(slider);


		VBox buttonsBox = setButtonsBox(leftControls);
		HBox topButtonsBox = setTopButtonsBox(topControls);


		//relaciono el slider del borde con el setlinewidth de la figura seleccionada.
		// Esto hace que se pueda ver el nuevo borde mientras lo arrastras y no tener que esperar a soltar. 
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (selectedFigure !=null ) {
					selectedFigure.setLineWidth((double) newValue);
					redrawCanvas();
				}
		});

		// creacion del punto de inicio donde a continuacion creara la figura
		canvas.setOnMousePressed(event -> {
			//no es lo mismo que un click. Es cuando empieza a mantener apretado.
			startPoint = new Point(event.getX(), event.getY());
		});

		//creacion de figura
		canvas.setOnMouseReleased(event -> {
			//cuando lo suelto
			Point endPoint = new Point(event.getX(), event.getY());

			//tengo que soltar abajo a la derecha
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			//cuando suelto el mouse se crea la figura.
			for(FigureToggleButton figureButton : figureButtonsArr){
				if(figureButton.isSelected()) {
					Figure newFigure = figureButton.make(startPoint, endPoint, lineColorPicker.getValue(), fillColorPicker.getValue(), slider.getValue(), gc);
					canvasState.addFigure(newFigure);
					//canvasState.toUndo(ActionType.DRAW, newFigure, newFigure);
				}
			}

			startPoint = null;
			redrawCanvas();
		});

		//cuando muevo el mouse si tener clickeado nada
		canvas.setOnMouseMoved(event -> {
			if (selectedFigure == null)
				figureStatus(new Point(event.getX(), event.getY()), new StringBuilder());
		});

		//Seleccion de figura
		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				selectedFigure = figureStatus(new Point(event.getX(), event.getY()), new StringBuilder("Se selecciono: "));
				if(selectedFigure == null)
					statusPane.updateStatus("Ninguna figura encontrada");
				redrawCanvas();
			}
		});

		//mover la figura
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.move(diffX,diffY);
				redrawCanvas();
			}
		});

		//cambiar el color del borde
		lineColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				Figure oldFigure = selectedFigure.clone();
				selectedFigure.setLineColor(lineColorPicker.getValue());
				//canvasState.toUndo(ActionType.LINECOLOR, oldFigure, selectedFigure);
			}
			redrawCanvas();
		});

		//cambiar el color del relleno
		fillColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				Figure oldFigure = selectedFigure.clone();
				selectedFigure.setBackGroundColor(fillColorPicker.getValue());
				//canvasState.toUndo(ActionType.FILLCOLOR, oldFigure, selectedFigure);
			}
			redrawCanvas();
		});

		//borrar la figura
		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				//canvasState.toUndo(ActionType.LINECOLOR, selectedFigure, selectedFigure);
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		// incrementa 10% las dimensiones de la figura
		increaseButton.setOnAction(event->{
			if(selectedFigure != null) {
				Figure oldFigure = selectedFigure.clone();
				selectedFigure.increase();
				//canvasState.toUndo(ActionType.INCREASE, oldFigure, selectedFigure);
				redrawCanvas();
			}
		});
		// decrementa 10% las dimensiones de la figura
		decreaseButton.setOnAction(event-> {
			if(selectedFigure != null) {
				Figure oldFigure = selectedFigure.clone();
				selectedFigure.decrease();
				//canvasState.toUndo(ActionType.DECREASE, oldFigure, selectedFigure);
				redrawCanvas();
			}
		});

		// realiza el undo dependiendo la accion correspondiente
		undoButton.setOnAction(event ->{
			canvasState.undoAction();
			undoLabel.setText(String.format("%s",canvasState.getUnDoSize() != 0 ?canvasState.getLastAction():"0"));
			redrawCanvas();
		});

		setTop(topButtonsBox);
		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {

		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure:canvasState.figures()) {
			System.out.println(String.format("%s",figure));
		}

		for(Figure figure : canvasState.figures()) {
			gc.setStroke(figure == selectedFigure ? Color.RED : figure.getLineColor());
			gc.setLineWidth(figure.getLineWidth());
			gc.setFill(figure.getBackGroundColor());
			figure.draw();
		}
	}

	private VBox setButtonsBox(Control[] controls){
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(controls);
		buttonsBox.setPadding(new Insets(5)); //espacio entre los bordes y el boton
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		return buttonsBox;
	}
	private HBox setTopButtonsBox(Control[] controls){
		HBox topButtonsBox = new HBox(10);
		topButtonsBox.getChildren().addAll(topControls);
		topButtonsBox.setPadding(new Insets(5)); //espacio entre los bordes y el boton
		topButtonsBox.setStyle("-fx-background-color: #999");
		topButtonsBox.setPrefHeight(25);
		topButtonsBox.setAlignment(Pos.CENTER);
		return topButtonsBox;
	}


	private void setButtonsProps(ToggleButton[] toolsArr, ToggleGroup tools){
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
	}
	private void setColorPickersProps(ColorPicker[] colorPickers) {
		for (ColorPicker colorPicker : colorPickers) {
			colorPicker.setMinWidth(90);
			colorPicker.setCursor(Cursor.HAND);
		}
	}
	private void setSliderProps(Slider slider) {
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(25);
		slider.setMinWidth(90);
		slider.setCursor(Cursor.HAND);
	}
	Figure figureStatus(Point eventPoint, StringBuilder label) {
		for(Figure figure : canvasState.figures()) {
			if(figure.containsOn(eventPoint)) {
				label.append(figure);
				statusPane.updateStatus(label.toString());
				return figure;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
		return null;
	}

}
