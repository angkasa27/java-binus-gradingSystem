public class Student {
  private String name;
  private String nim;
  private int task;
  private int quiz;
  private int midTerm;
  private int finalExam;
  private double finalScore;
  private String grade;
  private boolean isPassed;

  public Student() {
    // Empty Constructor
  }

  public Student(String nim, String name, int task, int quiz, int midTerm, int finalExam) {
    this.name = name;
    this.nim = nim;
    this.task = task;
    this.quiz = quiz;
    this.midTerm = midTerm;
    this.finalExam = finalExam;
    this.finalScore = calculateFinalScore(task, quiz, midTerm, finalExam);
    this.grade = calculateGrade(finalScore);
    this.isPassed = checkIsPassed(finalExam);
  }

  public String getNim() {
    return nim;
  }

  public void setNim(String nim) {
    this.nim = nim;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTask() {
    return task;
  }

  public void setTask(int task) {
    this.task = task;
  }

  public int getQuiz() {
    return quiz;
  }

  public void setQuiz(int quiz) {
    this.quiz = quiz;
  }

  public int getMidTerm() {
    return midTerm;
  }

  public void setMidTerm(int midTerm) {
    this.midTerm = midTerm;
  }

  public int getFinalExam() {
    return finalExam;
  }

  public void setFinalExam(int finalExam) {
    this.finalExam = finalExam;
  }

  public double getFinalScore() {
    return finalScore;
  }

  public String getGrade() {
    return grade;
  }

  public String getNote() {
    if (isPassed)
      return "Dinyatakan Lulus";
    else
      return "Dinyatakan Tidak Lulus";
  }

  public void resetGrading() {
    this.finalScore = calculateFinalScore(task, quiz, midTerm, finalExam);
    this.grade = calculateGrade(finalScore);
    this.isPassed = checkIsPassed(finalExam);
  }

  double calculateFinalScore(int task, int quiz, int midTerm, int finalExam) {
    double average = ((double) task + (double) quiz + (double) midTerm + (double) finalExam) / 4;

    return average;
  }

  String calculateGrade(double finalScore) {
    if (finalScore >= 86 && finalScore <= 100) {
      return "A";
    } else if (finalScore >= 81) {
      return "A-";
    } else if (finalScore >= 76) {
      return "B+";
    } else if (finalScore >= 71) {
      return "B";
    } else if (finalScore >= 66) {
      return "B-";
    } else if (finalScore >= 61) {
      return "C+";
    } else if (finalScore >= 56) {
      return "C";
    } else if (finalScore >= 51) {
      return "C-";
    } else if (finalScore >= 46) {
      return "D+";
    } else {
      return "D";
    }
  }

  boolean checkIsPassed(double finalScore) {
    if (finalScore >= 76) {
      return true;
    } else
      return false;
  }

  public boolean isNimEmpty() {
    return nim == null || nim.isEmpty();
  }
}
