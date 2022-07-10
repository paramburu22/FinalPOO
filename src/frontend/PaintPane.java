package frontend;

//import backend.Action.ActionType;
import backend.Action.ActionType;
import backend.Action.PaintAction;
import backend.CanvasState;
import backend.Exception.NothingSelectedException;
import backend.Exception.NothingToDoException;
import backend.model.*;
import backend.model.Point;
import frontend.Buttons.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;


public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	//Magic Numbers
	private static final double INITIAL_BORDER = 1;
	private static final Color LINE_COLOR = Color.BLACK;
	private static final Color FILL_COLOR = Color.YELLOW;
	private static final double CANVAS_HEIGHT = 600;
	private static final double CANVAS_WIDTH = 800;
	private static final double BUTTON_WIDTH = 90;
	private static final double PADDING = 5;
	private static final double SLIDER_MINVALUE = 1;
	private static final double SLIDER_MAXVALUE = 50;
	private static final double BOX_VALUE = 10;
	private static final double BUTTON_PREFWIDTH = 100;
	private static final double BUTTON_PREFHEIGHT = 25;
	private static final double MOVEMENT_MULTIPLIER = 100;
	private static final double SLIDER_TICKUNIT = 25;


	// Canvas y relacionados
	Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();



	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	FigureToggleButton rectangleButton = new RectangleButton("Rectángulo");
	FigureToggleButton circleButton = new CircleButton("Círculo");
	FigureToggleButton squareButton = new SquareButton("Cuadrado");
	FigureToggleButton ellipseButton = new EllipseButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	Slider slider = new Slider(SLIDER_MINVALUE, SLIDER_MAXVALUE, INITIAL_BORDER);
	ColorPicker lineColorPicker = new ColorPicker(LINE_COLOR);
	ColorPicker fillColorPicker = new ColorPicker(FILL_COLOR);
	ToggleButton increaseButton = new ToggleButton("Agrandar");
	ToggleButton decreaseButton = new ToggleButton("Achicar");
	ToggleButton undoButton = new ToggleButton("Deshacer");
	ToggleButton redoButton = new ToggleButton("Rehacer");
	Label undoLabel = new Label("0");
	Label redoLabel = new Label("0");
	FigureToggleButton[] figureButtonsArr = { rectangleButton, circleButton, squareButton, ellipseButton};
	ToggleButton[] listOfButtons = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton,increaseButton, decreaseButton, undoButton,redoButton};
	ColorPicker[] listOfColorPickers = {lineColorPicker, fillColorPicker};
	Label[] listOfLabels = {undoLabel,redoLabel};
	Control[] leftControls = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, new Label("Borde"), slider, lineColorPicker, new Label("Relleno"), fillColorPicker,increaseButton, decreaseButton};
	Control[] topControls = {undoLabel, undoButton,redoButton, redoLabel};

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
		setLabelsProps(listOfLabels);
		
		VBox buttonsBox = setButtonsBox(leftControls);
		HBox topButtonsBox = setTopButtonsBox(topControls);

		//Relaciono el slider del borde con el setlinewidth de la figura seleccionada.
		//Esto hace que se pueda ver el nuevo borde mientras lo arrastras y no tener que esperar a soltar. 
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (selectedFigure !=null ) {
					selectedFigure.setLineWidth((double) newValue);
					redrawCanvas();
				}
		});

		//Reacion del punto de inicio donde a continuacion creara la figura
		canvas.setOnMousePressed(event -> {
			//No es lo mismo que un click. Es cuando empieza a mantener apretado.
			startPoint = new Point(event.getX(), event.getY());
		});

		//Creacion de figura
		canvas.setOnMouseReleased(event -> {
			//Cuando lo suelto
			Point endPoint = new Point(event.getX(), event.getY());

			//Tengo que soltar abajo a la derecha. De no ser asi, no creo la figura y no hago nada.
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return;
			}

			//Cuando suelto el mouse se crea la figura.
			for(FigureToggleButton figureButton : figureButtonsArr){
				if(figureButton.isSelected()) {
					Figure newFigure = figureButton.make(startPoint, endPoint, lineColorPicker.getValue(), fillColorPicker.getValue(), slider.getValue(), gc);
					canvasState.addFigure(newFigure);
					try {
						canvasState.toUndo(ActionType.DRAW, newFigure);
					} catch (NothingSelectedException ex) {
						showAlarm(ex.getMessage());
					}
					updateLabels();
				}
			}
			startPoint = null;
			redrawCanvas();
		});

		//Cuando muevo el mouse sin tener clickeado no hago nada, simplemente actualizo el status.
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

		//Mover la figura
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				if(selectedFigure != null) {
					Point eventPoint = new Point(event.getX(), event.getY());
					double diffX = (eventPoint.getX() - startPoint.getX()) / MOVEMENT_MULTIPLIER;
					double diffY = (eventPoint.getY() - startPoint.getY()) / MOVEMENT_MULTIPLIER;
					selectedFigure.move(diffX, diffY);
					redrawCanvas();
				}
			}
		});

		//Cambiar el color del borde
		lineColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				try {
					canvasState.toUndo(ActionType.LINECOLOR, selectedFigure);
				} catch (NothingSelectedException ex) {
					showAlarm(ex.getMessage());
					return;
				}
				selectedFigure.setLineColor(lineColorPicker.getValue());
				updateLabels();
				redrawCanvas();
			}
		});

		//Cambiar el color del relleno
		fillColorPicker.setOnAction(event->{
			if(selectedFigure != null) {
				try {
					canvasState.toUndo(ActionType.FILLCOLOR, selectedFigure);
				} catch (NothingSelectedException ex) {
					showAlarm(ex.getMessage());
					return;
				}
				selectedFigure.setBackGroundColor(fillColorPicker.getValue());
				updateLabels();
				redrawCanvas();
			}

		});

		//Borrar la figura
		deleteButton.setOnAction(event -> {
			try {
				canvasState.toUndo(ActionType.DELETE, selectedFigure);
			} catch (NothingSelectedException ex) {
				showAlarm(ex.getMessage());
				return;
			}
			canvasState.deleteFigure(selectedFigure);
			selectedFigure = null;
			updateLabels();
			redrawCanvas();
		});

		//Incrementa 10% las dimensiones de la figura
		increaseButton.setOnAction(event->{
			try{
				canvasState.toUndo(ActionType.INCREASE, selectedFigure);
			}
			catch (NothingSelectedException ex){
				showAlarm(ex.getMessage());
				return;
			}
			selectedFigure.decrease();
			updateLabels();
			redrawCanvas();
		});
		
		//Decrementa 10% las dimensiones de la figura
		decreaseButton.setOnAction(event-> {
			try{
					canvasState.toUndo(ActionType.DECREASE, selectedFigure);
			}
			catch (NothingSelectedException ex){
				showAlarm(ex.getMessage());
				return;
			}
			selectedFigure.decrease();
			updateLabels();
			redrawCanvas();
			});

		//Realiza el undo dependiendo la accion correspondiente
		undoButton.setOnAction(event ->{

			try {
				canvasState.undoAction();
			} catch (NothingToDoException ex) {
				showAlarm(ex.getMessage());
			}
			updateLabels();
			redrawCanvas();
		});

		//Realiza el redo dependiendo de la accion correspondiente
		redoButton.setOnAction(event ->{
			try {
				canvasState.redoAction();
			} catch(NothingToDoException ex) {
				showAlarm(ex.getMessage());
			}
			updateLabels();
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
		for(PaintAction action : canvasState.getUnDo()){
			System.out.println(String.format("%s",action));
		}

		for(Figure figure : canvasState.figures()) {
			gc.setStroke(figure == selectedFigure ? Color.RED : figure.getLineColor());
			gc.setLineWidth(figure.getLineWidth());
			gc.setFill(figure.getBackGroundColor());
			figure.draw();
		}
	}

	private VBox setButtonsBox(Control[] controls){
		VBox buttonsBox = new VBox(BOX_VALUE);
		buttonsBox.getChildren().addAll(controls);
		buttonsBox.setPadding(new Insets(PADDING)); //espacio entre los bordes y el boton
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(BUTTON_PREFWIDTH);
		return buttonsBox;
	}
	private HBox setTopButtonsBox(Control[] controls){
		HBox topButtonsBox = new HBox(BOX_VALUE);
		topButtonsBox.getChildren().addAll(topControls);
		topButtonsBox.setPadding(new Insets(PADDING)); //espacio entre los bordes y el boton
		topButtonsBox.setStyle("-fx-background-color: #999");
		topButtonsBox.setPrefHeight(BUTTON_PREFHEIGHT);
		topButtonsBox.setAlignment(Pos.CENTER);
		return topButtonsBox;
	}
	private void setButtonsProps(ToggleButton[] toolsArr, ToggleGroup tools){
		for (ToggleButton tool : toolsArr) {
			tool.setPrefWidth(BUTTON_WIDTH);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
	}
	private void setColorPickersProps(ColorPicker[] colorPickers) {
		for (ColorPicker colorPicker : colorPickers) {
			colorPicker.setPrefWidth(BUTTON_WIDTH);
			colorPicker.setCursor(Cursor.HAND);
		}
	}
	private void setLabelsProps(Label[] labels) {
		for (Label label : labels) {
			label.setPrefWidth((CANVAS_WIDTH / 2) - BUTTON_WIDTH - (PADDING*3));
		}
		labels[0].setAlignment(Pos.TOP_RIGHT);
	}
	private void setSliderProps(Slider slider) {
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(SLIDER_TICKUNIT);
		slider.setPrefWidth(BUTTON_WIDTH);
		slider.setCursor(Cursor.HAND);
	}
	private Figure figureStatus(Point eventPoint, StringBuilder label) {
		List<Figure> aux = canvasState.figures();
		Collections.reverse(aux); //buscamos dar vuelta la lista de manera que la figura que devuelva sea la ultima agregada
		//en la pantalla se selecciona la figura que esta "mas adelante"
		for(Figure figure : aux){
			if(figure.containsOn(eventPoint)) {
				label.append(figure);
				statusPane.updateStatus(label.toString());
				return figure;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
		return null;
	}

	//Crea carteles cuando se lanza una excepcion
	private void showAlarm(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	//Actualiza las labels
	private void updateLabels(){
		undoLabel.setText(String.format("%s [%d]", canvasState.getUnDoSize() != 0 ? canvasState.getUndoLastAction() : "", canvasState.getUnDoSize()));
		redoLabel.setText(String.format("[%d] %s", canvasState.getReDoSize() ,canvasState.getReDoSize() != 0 ?canvasState.getRedoLastAction():""));
	}

}
