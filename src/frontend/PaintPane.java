package frontend;

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
	FigureToggleButton[] figureButtonsArr = { rectangleButton, circleButton, squareButton, ellipseButton};
	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton,increaseButton, decreaseButton};
		ToggleButton[] topToolsArr = { undoButton,redo};
		Control[] controlsArr = {new Label("Borde"), slider, lineColorPicker, new Label("Relleno"), fillColorPicker};
		ToggleGroup tools = new ToggleGroup();

		for (ToggleButton tool : topToolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		setButtonsProps(toolsArr,tools);
		setSliderProps(slider);
		VBox buttonsBox = setButtonsBox(toolsArr,controlsArr);
		HBox topButtonsBox = new HBox(10);
		topButtonsBox.getChildren().add(new Label("Cantidad de operaciones: "));
		topButtonsBox.getChildren().addAll(topToolsArr);
		topButtonsBox.getChildren().add(new Label("Cantidad de operaciones: "));
		topButtonsBox.setPadding(new Insets(5)); //espacio entre los bordes y el boton
		topButtonsBox.setStyle("-fx-background-color: #999");
		topButtonsBox.setPrefHeight(25);
		topButtonsBox.setAlignment(Pos.CENTER);

		//relaciono el slider del borde con el setlinewidth de la figura seleccionada.
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (selectedFigure !=null ) {
					selectedFigure.setLineWidth((double) newValue);
					redrawCanvas();
				}
		});

		canvas.setOnMousePressed(event -> {
			//no es lo mismo que un click. Es cuando empieza a mantener apretado.
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			//cuando lo suelto
			Point endPoint = new Point(event.getX(), event.getY());

			//tengo que soltar abajo a la derecha
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			//cuando suelto el mouse se crea la figura.
			Figure newFigure = null;
			for(FigureToggleButton figureButton : figureButtonsArr){
				if(figureButton.isSelected())
					newFigure = figureButton.make(startPoint, endPoint,lineColorPicker.getValue(), fillColorPicker.getValue(), slider.getValue(), gc);
			}

			//una vez creada la figura anteriormente, pasamos a agregarla al back.
			if(newFigure != null) {
				canvasState.addFigure(newFigure);
				canvasState.toUndo(ActionType.DRAW, newFigure);
			}
			startPoint = null;
			redrawCanvas();
		});

		//cuando muevo el mouse si tener clickeado nada
		canvas.setOnMouseMoved(event -> {
			if (selectedFigure == null)
				figureStatus(new Point(event.getX(), event.getY()), new StringBuilder());
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				selectedFigure = figureStatus(new Point(event.getX(), event.getY()), new StringBuilder("Se selecciono: "));
				if(selectedFigure == null)
					statusPane.updateStatus("Ninguna figura encontrada");
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				selectedFigure.move(diffX,diffY);
				redrawCanvas();
			}
		});

		lineColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				selectedFigure.setLineColor(lineColorPicker.getValue());
			}
			redrawCanvas();
		});
		fillColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				selectedFigure.setBackGroundColor(fillColorPicker.getValue());
			}
			redrawCanvas();
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		increaseButton.setOnAction(event->{
			selectedFigure.increase();
			redrawCanvas();
		});

		decreaseButton.setOnAction(event->{
			selectedFigure.decrease();
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

	private VBox setButtonsBox(ToggleButton[] toolsArr, Control[] controlsArr){
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(controlsArr);
		buttonsBox.setPadding(new Insets(5)); //espacio entre los bordes y el boton
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		return buttonsBox;
	}

	private void setButtonsProps(ToggleButton[] toolsArr, ToggleGroup tools){
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
	}

	private void setSliderProps(Slider slider) {
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(25);
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
