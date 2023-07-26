import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Pizza {

  private static final String SEP = ",";
  // Map<НазваниеПиццы, Map<Размер, Стоимость>>
  private static final Map<String, Map<String, Double>> prices = readFromCsv("res/pizzas.csv");

  private final String name;
  private final String size;
  private final double price;

  public Pizza(String name, String size) {
    if (!prices.containsKey(name)) {
      throw new IllegalArgumentException("Некорректное название пиццы: " + name);
    }
    this.name = name;
    Map<String, Double> sizeToPrice = prices.get(name); // стоимость в зависимости от размера
    if (!sizeToPrice.containsKey(size)) {
      throw new IllegalArgumentException("Некорректный размер: " + size);
    }
    this.size = size;
    price = sizeToPrice.get(size);
  }

  public static Pizza readData(Scanner scanner) {
    // TODO избавиться от дублирования
    System.out.println("Выберите пиццу:");
    for (String name : prices.keySet()) {
      System.out.println("- " + name);
    }
    System.out.print("Введите название: ");
    String name = scanner.nextLine();
    while (!prices.containsKey(name)) {
      System.out.println("Некорректное название пиццы: " + name);
      System.out.print("Введите название: ");
      name = scanner.nextLine();
    }

    Set<String> sizes = prices.get(name).keySet();
    System.out.println("Выберите размер:");
    for (String size : sizes) {
      System.out.println("- " + size);
    }
    System.out.print("Введите размер: ");
    String size = scanner.nextLine();
    while (!sizes.contains(size)) {
      System.out.println("Некорректный размер: " + size);
      System.out.print("Введите размер: ");
      size = scanner.nextLine();
    }

    return new Pizza(name, size);
  }

  private static Map<String, Map<String, Double>> readFromCsv(String filename) {
    // CSV - comma separated values - значения, разделённые запятыми -
    // самый простой формат таблиц.
    Map<String, Map<String, Double>> result = new HashMap<>();
    File pizzasFile = new File(filename);
    try {
      Scanner scanner = new Scanner(pizzasFile);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] cells = line.split(SEP);
        try {
          String pizza = cells[0];
          String size = cells[1];
          double price = Double.parseDouble(cells[2]);
          if (!result.containsKey(pizza)) { // пицца встретилась первый раз
            result.put(pizza, new HashMap<>()); // кладём ей пока пустой словарь "размер-цена"
          }
          // теперь словарь "размер-цена" для пиццы точно есть в нашем словаре
          result.get(pizza).put(size, price);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          System.out.println("Некорректная строка файла: " + line);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("Не найден файл: " + e);
    }
    return result;
  }
}
