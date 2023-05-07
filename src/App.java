import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
  private Button countGradeButton;
  private Button exitButton;

  public void start(Stage stage) {
    countGradeButton = new Button("Hitung Nilai");
    countGradeButton.setOnAction(e -> {
      CountingGradeForm countingGradeForm = new CountingGradeForm();
      countingGradeForm.start(new Stage());
    });

    exitButton = new Button("Keluar");
    exitButton.setOnAction(e -> Platform.exit());

    VBox dashboardLayout = new VBox(10);
    dashboardLayout.setAlignment(Pos.CENTER);
    dashboardLayout.getChildren().addAll(countGradeButton, exitButton);

    Scene scene = new Scene(dashboardLayout, 400, 300);
    stage.setScene(scene);
    stage.setTitle("Dashboard");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}