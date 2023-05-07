import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class StudentTableView extends Application {
  private StudentDao studentDao = new StudentDao();

  public void start(Stage stage) {

    List<Student> students = studentDao.getAllStudents();

    ObservableList<Student> studentObservableList = FXCollections.observableArrayList(students);

    TableColumn<Student, String> nimColumn = new TableColumn<>("NIM");
    nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

    TableColumn<Student, String> nameColumn = new TableColumn<>("Nama");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Student, String> finalScoreColumn = new TableColumn<>("Rerata");
    finalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("finalScore"));

    TableColumn<Student, String> gradeColumn = new TableColumn<>("Grade");
    gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

    TableColumn<Student, Void> deleteColumn = new TableColumn<>("");
    deleteColumn.setCellFactory(column -> new TableCell<Student, Void>() {
      private final Button deleteButton = new Button("Delete");

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setGraphic(null);
        } else {
          Student student = getTableView().getItems().get(getIndex());

          deleteButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Data yang telah dihapus tidak dapat dikembalikan lagi.",
                ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Apakah anda yakin ingin menghapus data ini?");

            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
              studentDao.deleteStudent(student.getNim());

              List<Student> updatedStudents = studentDao.getAllStudents();
              studentObservableList.setAll(updatedStudents);
            }
          });

          setGraphic(deleteButton);
        }
      }
    });

    TableColumn<Student, Integer> indexColumn = new TableColumn<>("No");
    indexColumn.setSortable(false);
    indexColumn.setPrefWidth(50);

    indexColumn.setCellFactory(column -> {
      return new TableCell<Student, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
          super.updateItem(item, empty);

          if (empty) {
            setText(null);
          } else {
            int index = getIndex() + 1;
            setText(String.valueOf(index));
          }
        }
      };
    });

    TableView<Student> studentTableView = new TableView<>();
    studentTableView.setItems(studentObservableList);
    studentTableView.getColumns().add(indexColumn);
    studentTableView.getColumns().add(nimColumn);
    studentTableView.getColumns().add(nameColumn);
    studentTableView.getColumns().add(finalScoreColumn);
    studentTableView.getColumns().add(gradeColumn);
    studentTableView.getColumns().add(deleteColumn);

    VBox vBox = new VBox();
    vBox.getChildren().add(studentTableView);

    Scene scene = new Scene(vBox);
    stage.setScene(scene);
    stage.setTitle("Daftar Mahasiswa");
    stage.show();

  }
}