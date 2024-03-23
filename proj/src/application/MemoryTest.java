package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class MemoryTest extends Application {

	ArrayList<MartyryFx> martaryArray = new ArrayList<>();
	Label[] nameLabels;

	@Override
	public void start(Stage adamStage) {
		try {
			VBox vb = new VBox();
			Button b = new Button("Create Martyr List Window");
			Button b2 = new Button("Memory Test Window");
			vb.getChildren().addAll(b, b2);
			vb.setSpacing(20);
			vb.setAlignment(Pos.CENTER);
			Scene s1 = new Scene(vb, 300, 300);
			b.setOnAction(e -> {
				screen1();
			});
			b2.setOnAction(e -> {
				screen2();
			});
			adamStage.setScene(s1);
			adamStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void screen1() {
		Stage stage = new Stage();
		VBox v = new VBox();
		FlowPane H = new FlowPane(20, 10);
		Label l = new Label("Add Martyr (the name then (.) date of martyrdom)");
		TextField tx = new TextField();

		Button b = new Button("Add to File");
		b.setOnAction(e -> {
			String inputText = tx.getText();

			if (!inputText.isEmpty() && inputText.contains(".")) {
				String[] arr = inputText.split("\\.");

				if (arr.length >= 2) {
					MartyryFx martyr = new MartyryFx(arr[0], arr[1]);
					martaryArray.add(martyr);

					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("martyrs.dat", true))) {
						oos.writeObject(martyr);
						System.out.println(" Name: " + martyr.getmName() + ", Date of martyrdom: "
								+ martyr.getDateOfMartyrdom() + " added to the file.");
					} catch (IOException ex) {
						System.out.println("Error writing to the file: " + ex.getMessage());
					}
				} else {
					System.out.println("Invalid input enter a valid name and date.");
				}
			} else {
				System.out.println("Invalid input enter a valid name and date.");
			}
		});

		H.getChildren().addAll(l, tx, b);
		H.setAlignment(Pos.CENTER);
		v.getChildren().add(H);
		v.setAlignment(Pos.CENTER);
		Scene s = new Scene(v, 600, 150);
		stage.setScene(s);
		stage.show();
	}

	public void screen2() {
		Stage stage = new Stage();
		VBox v = new VBox(20);
		Label l1t = new Label("Test your memory");
		Label l2t = new Label("Hey, my friend! Test your memory to see if you remember who was martyred before");
		Label l3t = new Label(
				"Pick two Martyr names from the following list, enter them in the boxes in the correct order (date of death) and then press the Submit button");

		FlowPane namePane = new FlowPane(10, 10);
		nameLabels = new Label[martaryArray.size()];

		for (int i = 0; i < martaryArray.size(); i++) {
			String name = martaryArray.get(i).getmName();
			nameLabels[i] = new Label(name);
			namePane.getChildren().add(nameLabels[i]);
		}

		FlowPane fP = new FlowPane(10, 10);

		ComboBox<String> cBox = new ComboBox<>();
		cBox.getItems().addAll("RED", "GREEN", "YELLOW");

		cBox.setOnAction(e -> {
			if (cBox.getValue() != null) {
				if ("RED".equals(cBox.getValue())) {
					v.setStyle("-fx-background-color: red");
				} else if ("GREEN".equals(cBox.getValue())) {
					v.setStyle("-fx-background-color: green");
				} else if ("YELLOW".equals(cBox.getValue())) {
					v.setStyle("-fx-background-color: yellow");
				}
			}
		});

		TextField t1 = new TextField();
		TextField t2 = new TextField();
		HBox tf = new HBox();
		tf.setAlignment(Pos.CENTER);
		tf.setSpacing(30);
		tf.getChildren().addAll(t1, t2);
		Button b1 = new Button("Submit");
		Button b2 = new Button("Clear");
		Label r = new Label("Response");

		b2.setOnAction(e -> {
			t1.clear();
			t2.clear();
			r.setText("");
		});

		b1.setOnAction(e -> {
			String n1 = t1.getText();
			String n2 = t2.getText();

			if (n1.isEmpty() || n2.isEmpty()) {
				r.setText("Enter names in both boxes. Then press Submit.");
			} else if (!inList(n1) || !inList(n2)) {
				r.setText("One or both entries not in the name list – check spelling");
			} else if (n1.equals(n2)) {
				r.setText("You entered the same names. Try again.");
			} else if (!check(n1, n2)) {
				r.setText("Wrong. Try again.");
			} else {
				r.setText("Correct!");
			}
		});

		v.getChildren().addAll(l1t, l2t, l3t, namePane, fP, tf, b1, b2, r, cBox);
		v.setAlignment(Pos.CENTER);
		Scene scene = new Scene(v, 800, 400);
		stage.setScene(scene);
		stage.show();
	}

	public boolean inList(String name) {
		for (int i = 0; i < martaryArray.size(); i++) {
			MartyryFx martyr = martaryArray.get(i);
			if (martyr.getmName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean check(String name1, String name2) {
		int i1 = 0;
		int i2 = 0;

		for (int i = 0; i < martaryArray.size(); i++) {
			MartyryFx martyr = martaryArray.get(i);

			if (martyr.getmName().equals(name1)) {
				String[] d1 = martyr.getDateOfMartyrdom().split("-");
				if (d1.length >= 3) {
					i1 = Integer.parseInt(d1[2]);
				} else {
					System.out.println("Invalid date format for martyr: " + martyr.getmName());
					return false;
				}
			}

			if (martyr.getmName().equals(name2)) {
				String[] d2 = martyr.getDateOfMartyrdom().split("-");
				if (d2.length >= 3) {
					i2 = Integer.parseInt(d2[2]);
				} else {
					System.out.println("Invalid date format for martyr: " + martyr.getmName());
					return false;
				}
			}
		}
		return i1 - i2 >= 0;
	}

}
