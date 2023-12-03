import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static Random random = new Random();
    static List<String> departaments = new ArrayList<>(List.of("Архив", "Бухгалтерия", "Отдел Продаж", "Отдел Кадров"));

    public static void main(String[] args) {

        List<Integer> intList = generateList(1000);
        int max = intList.stream().max(Comparator.comparingInt(x -> x)).get();
        System.out.println("Максимальное число:");
        System.out.println(max);

        int summ = intList.stream().filter(x->x>500000).map(x->x*5).map(x->x-150).mapToInt(Integer::intValue).sum();
        System.out.println("Сумма:");
        System.out.println(summ);

        long count = intList.stream().filter(x->x*x<100000).count();
        System.out.println("Кол-во:");
        System.out.println(count);

        List<Employee> employees = generateEmployees(20);
        List<String> depts = employees.stream().map(Employee::getDepartment).distinct().toList();
        System.out.println("Департаменты:");
        System.out.println(depts);

        employees.forEach(employee -> {
            if (employee.getSalary() < 10000) {
                employee.setSalary(employee.getSalary() + (((double) 20/100)*employee.salary));
            }
        });

        Map<String, List<Employee>> departamentGroups = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println("Департаменты с сотрудниками:");
        System.out.println(departamentGroups);

        Map<String, Double> departamentsSalaryGroups = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment))
                .entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
                        e -> e.getValue().stream().mapToDouble(Employee::getSalary).average().getAsDouble()));
        System.out.println("Департаменты со средней зп:");
        System.out.println(departamentsSalaryGroups);

    }

    private static List<Integer> generateList(int size) {
        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i<size; i++) {
            temp.add(random.nextInt(1000000));
        }
        return temp;
    }

    private static List<Employee> generateEmployees(int size) {
        List<Employee> temp = new ArrayList<>();

        for (int i = 0; i<size; i++) {
            int age = random.nextInt(30, 40);
            String name = String.format("Иванов-%s", random.nextInt(100));
            double salary = random.nextDouble(5000, 15000);
            String departament = departaments.get(random.nextInt(departaments.size()-1));
            temp.add(new Employee(name, age, salary, departament));
        }
        return temp;
    }
}