package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.Buttons.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



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
	Color lineColor = LINE_COLOR;
	Color fillColor = FILL_COLOR;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	FigureToggleButton rectangleButton = new RectangleButton("Rectángulo");
	FigureToggleButton circleButton = new CircleButton("Círculo");
	FigureToggleButton squareButton = new SquareButton("Cuadrado");
	FigureToggleButton ellipseButton = new EllipseButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	FigureToggleButton[] figureButtonsArr = { rectangleButton, circleButton, squareButton, ellipseButton};
	Slider slider = new Slider(1, 50, INITIAL_BORDER);
	ColorPicker lineColorPicker = new ColorPicker(LINE_COLOR);
	ColorPicker fillColorPicker = new ColorPicker(FILL_COLOR);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};

		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10); //espacio entre boton y boton
		buttonsBox.getChildren().addAll(toolsArr);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1.0f);
		slider.setBlockIncrement(1.0f);
		buttonsBox.getChildren().add(new Label("Borde"));
		buttonsBox.getChildren().add(slider);
		buttonsBox.getChildren().add(lineColorPicker);
		buttonsBox.getChildren().add(new Label("Relleno"));
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5)); //espacio entre los bordes y el boton
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		//gc.setLineWidth(slider.getValue()); // grosor del borde

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
			if(newFigure != null)
				canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		//cuando muevo elmouse si tener clickeado nada
		canvas.setOnMouseMoved(event -> {

			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure instanceof Rectangle) {
					Rectangle rectangle = (Rectangle) selectedFigure;
					rectangle.getTopLeft().x += diffX;
					rectangle.getBottomRight().x += diffX;
					rectangle.getTopLeft().y += diffY;
					rectangle.getBottomRight().y += diffY;
				} else if(selectedFigure instanceof Circle) {
					Circle circle = (Circle) selectedFigure;
					circle.getCenterPoint().x += diffX;
					circle.getCenterPoint().y += diffY;
				} else if(selectedFigure instanceof Square) {
					Square square = (Square) selectedFigure;
					square.getTopLeft().x += diffX;
					square.getBottomRight().x += diffX;
					square.getTopLeft().y += diffY;
					square.getBottomRight().y += diffY;
				} else if(selectedFigure instanceof Ellipse) {
					Ellipse ellipse = (Ellipse) selectedFigure;
					ellipse.getCenterPoint().x += diffX;
					ellipse.getCenterPoint().y += diffY;
				}
				redrawCanvas();
			}
		});

		lineColorPicker.setOnMouseClicked(event->{
			if(selectedFigure != null) {
				selectedFigure.setLineColor(lineColorPicker.getValue());
			}
			redrawCanvas();
		});
		fillColorPicker.setOnMouseClicked(event->{
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

	boolean figureBelongs(Figure figure, Point eventPoint) {
		boolean found = false;
		if(figure instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) figure;
			found = eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
					eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
		} else if(figure instanceof Circle) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
		} else if(figure instanceof Square) {
			Square square = (Square) figure;
			found = eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getBottomRight().getX() &&
					eventPoint.getY() > square.getTopLeft().getY() && eventPoint.getY() < square.getBottomRight().getY();
		} else if(figure instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) figure;
			// Nota: Fórmula aproximada. No es necesario corregirla.
			found = ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2) / Math.pow(ellipse.getsMayorAxis(), 2)) +
					(Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2) / Math.pow(ellipse.getsMinorAxis(), 2))) <= 0.30;
		}
		return found;
	}

}
