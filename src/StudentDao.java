import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
  public List<Student> getAllStudents() {
    List<Student> students = new ArrayList<>();
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "SELECT * FROM student";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String nim = resultSet.getString("nim");
        String name = resultSet.getString("name");
        int task = resultSet.getInt("task");
        int quiz = resultSet.getInt("quiz");
        int midTerm = resultSet.getInt("midTerm");
        int finalExam = resultSet.getInt("finalExam");
        Student student = new Student(nim, name, task, quiz, midTerm, finalExam);
        students.add(student);
      }
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return students;
  }

  public void createStudent(Student student) {
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "INSERT INTO student (nim, name, task, quiz, midTerm, finalExam) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, student.getNim());
      preparedStatement.setString(2, student.getName());
      preparedStatement.setInt(3, student.getTask());
      preparedStatement.setInt(4, student.getQuiz());
      preparedStatement.setInt(5, student.getMidTerm());
      preparedStatement.setInt(6, student.getFinalExam());
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Student getStudentByNim(String nim) {
    Student student = null;
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "SELECT * FROM student WHERE nim = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, nim);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String name = resultSet.getString("name");
        int task = resultSet.getInt("task");
        int quiz = resultSet.getInt("quiz");
        int midTerm = resultSet.getInt("midTerm");
        int finalExam = resultSet.getInt("finalExam");
        student = new Student(nim, name, task, quiz, midTerm, finalExam);
      }
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return student;
  }

  public void updateStudent(Student student) {
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "UPDATE student SET name = ?, task = ?, quiz = ?, midTerm = ?, finalExam = ? WHERE nim = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, student.getName());
      preparedStatement.setInt(2, student.getTask());
      preparedStatement.setInt(3, student.getQuiz());
      preparedStatement.setInt(4, student.getMidTerm());
      preparedStatement.setInt(5, student.getFinalExam());
      preparedStatement.setString(6, student.getNim());
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteStudent(String nim) {
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "DELETE FROM student WHERE nim = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, nim);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
