import java.util.Scanner;

public enum MenuCommand {
  UNEXPECTED(""), // служебное значение, которое не должен видеть пользователь
  START("Начать заказ"),
  EXIT("Выйти");

  private final String message;

  MenuCommand(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public static MenuCommand readCommand(Scanner scanner) {
    printMenu();
    System.out.print("Введите команду: ");
    if (!scanner.hasNext()) {
      // Мы должны выбрасывать исключение, когда что-то пошло настолько не так,
      // что мы не можем это исправить.
      throw new RuntimeException("Ожидается ввод команды");
    }
    String input = scanner.next();
    scanner.nextLine(); // дочитываем строку после команды до конца
    switch (input.toLowerCase()) {
      case "1": // Integer.toString(START.ordinal())
      case "start":
      case "начать": // первое слово START.getMessage()
        return START;
      case "2": // Integer.toString(EXIT.ordinal())
      case "exit":
      case "выйти": // EXIT.getMessage()
      case "выход":
        return EXIT;
      default:
        return UNEXPECTED;
    }
  }

  public static void printMenu() {
    for (MenuCommand command : values()) {
      if (!command.message.isEmpty()) { // message пустое для всех служебных значений
        System.out.println(command.ordinal() + ". " + command.message);
      }
    }
  }
}
