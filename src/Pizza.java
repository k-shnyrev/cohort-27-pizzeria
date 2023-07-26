import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Pizza {

  // Map<НазваниеПиццы, Map<Размер, Стоимость>>
  private static final Map<String, Map<String, Double>> prices = new HashMap<>();
  // TODO прочитаем из файла

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
}
