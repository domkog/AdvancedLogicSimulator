package at.fishkog.als.ui.common.dialogs;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogConsole {
	private LanguageManager l = AdvancedLogicSimulator.lang;

	private TextArea textArea;

	private Alert alert;

	public DialogConsole() {
		int numRow = -1;

		this.alert = new Alert(AlertType.NONE);
		alert.setTitle(l.getString("Console") + " - Advanced Logic Simulator");
		// alert.setHeaderText(l.getString("Console") + ": ");
		alert.initModality(Modality.NONE);
		alert.setResizable(true);

		ImageView iconCons = new ImageView(AdvancedLogicSimulator.class.getClassLoader()
				.getResource("resources/icons/iconConsole.gif").toExternalForm());
		iconCons.setFitHeight(150);
		iconCons.setFitWidth(150);
		iconCons.setPreserveRatio(true);
		alert.setGraphic(iconCons);
		
		Window window = alert.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> {
			window.hide();

		});
		
		Stage stage = (Stage) window;
		stage.getIcons().add(new Image(AdvancedLogicSimulator.class.getClassLoader()
				.getResource("resources/icons/iconMain.png").toExternalForm()));

		textArea = new TextArea("");
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setFocusTraversable(false);

		// textArea.setMinSize(700, 500);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		TextField fldInput = new TextField();

		Button btnClear = new Button(l.getString("Clear"));
		btnClear.setPrefWidth(125);
		Button btnSave = new Button(l.getString("Save"));
		btnSave.setPrefWidth(125);
		Button btnClip = new Button(l.getString("Clip"));
		btnClip.setPrefWidth(125);

		btnClear.setOnAction((e) -> {
			this.clear();
			
		});

		btnSave.setOnAction((e) -> {

			
		});

		btnClip.setOnAction((e) -> {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
			content.putString(this.textArea.getText());
			
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Information");
			dialog.setHeaderText(null);
			dialog.setContentText(l.getString("ClipSuc"));

			dialog.showAndWait();

			clipboard.setContent(content);
			
		});

		HBox boxBtn = new HBox();
		GridPane btnPane = new GridPane();
		btnPane.setVgap(25);

		btnPane.add(btnSave, 0, 0);
		btnPane.add(btnClip, 0, 1);
		btnPane.add(btnClear, 0, 2);

		boxBtn.getChildren().add(btnPane);

		GridPane consContent = new GridPane();
		consContent.setMaxWidth(Double.MAX_VALUE);
		consContent.add(textArea, 0, ++numRow);
		consContent.add(fldInput, 0, ++numRow);

		AnchorPane.setBottomAnchor(consContent, 20.0);
		AnchorPane.setRightAnchor(consContent, 20.0);
		AnchorPane.setTopAnchor(consContent, 20.0);
		AnchorPane.setLeftAnchor(consContent, 0.0);

		AnchorPane.setLeftAnchor(boxBtn, -143.5);
		AnchorPane.setTopAnchor(boxBtn, 160.0);

		AnchorPane mainPane = new AnchorPane();
		mainPane.getChildren().addAll(boxBtn, consContent);
		mainPane.setMinSize(400, 400);

		alert.getDialogPane().setContent(mainPane);

	}

	public void show() {
		alert.show();

	}

	public void print(String text) {
		this.textArea.appendText(text);

	}

	public void print(char c) {
		this.textArea.appendText(String.valueOf(c));

	}

	public void print(int i) {
		this.textArea.appendText(String.valueOf(i));

	}

	public void clear() {
		this.textArea.clear();
		
	}
	
	
	public void hide() {
		Window window = alert.getDialogPane().getScene().getWindow();
		window.hide();

	}
}
