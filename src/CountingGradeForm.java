import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CountingGradeForm extends Application {

  private StudentDao studentDao = new StudentDao();
  private Student student = new Student();

  private TextField nameField;
  private TextField nimField;
  private TextField taskField;
  private TextField quizField;
  private TextField midTermField;
  private TextField finalField;

  private Label showName = new Label(":");
  private Label showNim = new Label(":");
  private Label showFinalScore = new Label(":");
  private Label showGrade = new Label(":");
  private Label showNote = new Label(":");

  public void start(Stage stage) {
    // create form labels
    Label nameLabel = new Label("Nama");
    Label nimLabel = new Label("NIM");
    Label taskLabel = new Label("Nilai Tugas");
    Label quizLabel = new Label("Nilai Kuis");
    Label midTermLabel = new Label("Nilai UTS");
    Label finalLabel = new Label("Nilai UAS");

    // create form fields
    nameField = new TextField();
    nimField = new TextField();
    taskField = new TextField();
    quizField = new TextField();
    midTermField = new TextField();
    finalField = new TextField();

    // create preview fields
    Label showNameLabel = new Label("Nama");
    Label showNimLabel = new Label("NIM");
    Label showFinalScoreLabel = new Label("Rerata");
    Label showGradeLabel = new Label("Grade");
    Label showNoteLabel = new Label("Keterangan");

    // create submit button
    Button saveButton = new Button("Hitung");
    saveButton.setOnAction(e -> {
      String name = nameField.getText();
      String nim = nimField.getText();
      String task = taskField.getText();
      String quiz = quizField.getText();
      String midTerm = midTermField.getText();
      String finalExam = finalField.getText();
      String errorMessage = "";

      // Validate Name field
      if (name.isEmpty()) {
        errorMessage += "Nama harus diisi\n";
      } else if (name.length() > 20) {
        errorMessage += "Nama maksimal terdiri dari 20 karakter\n";
      }

      // Validate NIM field
      if (nim.isEmpty()) {
        errorMessage += "NIM harus diisi\n";
      } else if (!nim.matches("[0-9]+")) {
        errorMessage += "NIM harus berisi angka saja\n";
      } else if (nim.length() > 10) {
        errorMessage += "NIM maksimal terdiri dari 10 digit angka\n";
      } else if (studentDao.getStudentByNim(nim) != null) {
        errorMessage += "NIM telah terdaftar dalam sistem\n";
      }

      // Validate task field
      if (!task.matches("[0-9]+")) {
        errorMessage += "Nilai Tugas harus berisi angka saja\n";
      } else {
        int taskValue = Integer.parseInt(task);
        if (taskValue < 0 || taskValue > 100) {
          errorMessage += "Nilai Tugas harus di antara 0 dan 100\n";
        }
      }

      // Validate Quiz field
      if (!quiz.matches("[0-9]+")) {
        errorMessage += "Nilai Kuis harus berisi angka saja\n";
      } else {
        int quizValue = Integer.parseInt(quiz);
        if (quizValue < 0 || quizValue > 100) {
          errorMessage += "Nilai Kuis harus di antara 0 dan 100\n";
        }
      }

      // Validate Mid-Term field
      if (!midTerm.matches("[0-9]+")) {
        errorMessage += "Nilai UTS harus berisi angka saja\n";
      } else {
        int midTermValue = Integer.parseInt(midTerm);
        if (midTermValue < 0 || midTermValue > 100) {
          errorMessage += "Nilai UTS harus di antara 0 dan 100\n";
        }
      }

      // Validate Final Exam field
      if (!finalExam.matches("[0-9]+")) {
        errorMessage += "Nilai UAS harus berisi angka saja\n";
      } else {
        int finalExamValue = Integer.parseInt(finalExam);
        if (finalExamValue < 0 || finalExamValue > 100) {
          errorMessage += "Nilai UAS harus di antara 0 dan 100\n";
        }
      }

      if (!errorMessage.isEmpty()) {
        // Show error message if there are validation errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Terdapat kesalahan dalam mengisi formulir");
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return;
      } else {
        student.setName(name);
        student.setNim(nim);
        student.setTask(Integer.parseInt(task));
        student.setQuiz(Integer.parseInt(quiz));
        student.setMidTerm(Integer.parseInt(midTerm));
        student.setFinalExam(Integer.parseInt(finalExam));

        student.resetGrading();

        showName.setText(": " + student.getName());
        showNim.setText(": " + student.getNim());
        showFinalScore.setText(": " + student.getFinalScore());
        showGrade.setText(": " + student.getGrade());
        showNote.setText(": " + student.getNote());
      }
    });

    Button resetButton = new Button("Reset");
    resetButton.setOnAction(e -> resetFields());

    Button submitButton = new Button("Simpan");
    submitButton.setOnAction(e -> {
      if (student.isNimEmpty()) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Gagal menyimpan!");
        errorAlert.setContentText("Data mahasiswa kosong.");
        errorAlert.showAndWait();
        return;
      } else {
        studentDao.createStudent(student);
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Berhasil");
        successAlert.setHeaderText("Berhasil menyimpan");
        successAlert.setContentText(student.getName()
            + " telah disimpan dalam sistem.\n Klik tombol Daftar Mahasiswa untuk melihat seluruh data.");
        successAlert.showAndWait();
        resetFields();
        return;
      }
    });

    // create form layout
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(20);

    // add labels and fields to form
    gridPane.add(nameLabel, 0, 0);
    gridPane.add(nameField, 1, 0);
    gridPane.add(showNameLabel, 2, 0);
    gridPane.add(showName, 3, 0);
    gridPane.add(nimLabel, 0, 1);
    gridPane.add(nimField, 1, 1);
    gridPane.add(showNimLabel, 2, 1);
    gridPane.add(showNim, 3, 1);
    gridPane.add(taskLabel, 0, 2);
    gridPane.add(taskField, 1, 2);
    gridPane.add(showFinalScoreLabel, 2, 2);
    gridPane.add(showFinalScore, 3, 2);
    gridPane.add(quizLabel, 0, 3);
    gridPane.add(quizField, 1, 3);
    gridPane.add(showGradeLabel, 2, 3);
    gridPane.add(showGrade, 3, 3);
    gridPane.add(midTermLabel, 0, 4);
    gridPane.add(midTermField, 1, 4);
    gridPane.add(showNoteLabel, 2, 4);
    gridPane.add(showNote, 3, 4);
    gridPane.add(finalLabel, 0, 5);
    gridPane.add(finalField, 1, 5);

    HBox actions = new HBox();
    saveButton.setPrefWidth(80);
    resetButton.setPrefWidth(80);
    submitButton.setPrefWidth(80);
    actions.getChildren().addAll(saveButton, resetButton, submitButton);
    actions.setSpacing(10);
    actions.setAlignment(Pos.CENTER);

    // create scene
    VBox vbox = new VBox(gridPane, actions);
    vbox.setSpacing(20);
    Scene scene = new Scene(vbox, 500, 250);
    stage.setScene(scene);
    stage.setTitle("Hitung Nilai");
    stage.show();
  }

  private void resetFields() {
    nameField.setText("");
    nimField.setText("");
    taskField.setText("");
    quizField.setText("");
    midTermField.setText("");
    finalField.setText("");
    showName.setText(": ");
    showNim.setText(": ");
    showFinalScore.setText(": ");
    showGrade.setText(": ");
    showNote.setText(": ");
    student = new Student();
  }

}