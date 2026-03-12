import java.util.*;
import java.util.stream.*;

public class ArrayListAdvanced {

    record Student(String name, int grade, double gpa) {}

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>(Arrays.asList(
            new Student("Alice",   10, 3.9),
            new Student("Bob",     11, 2.8),
            new Student("Carol",   10, 3.5),
            new Student("Dave",    12, 3.7),
            new Student("Eve",     11, 1.9),
            new Student("Frank",   12, 3.2)
        ));

        // Sort by GPA descending
        students.sort(Comparator.comparingDouble(Student::gpa).reversed());
        System.out.println("=== By GPA (desc) ===");
        students.forEach(s -> System.out.printf("  %-8s G%d GPA:%.1f%n", s.name(), s.grade(), s.gpa()));

        // Filter honor students (GPA >= 3.5)
        List<Student> honor = students.stream()
            .filter(s -> s.gpa() >= 3.5)
            .collect(Collectors.toList());
        System.out.println("\nHonor students (GPA>=3.5): " + honor.stream().map(Student::name).toList());

        // Group by grade
        System.out.println("\n=== By Grade ===");
        Map<Integer, List<Student>> byGrade = students.stream()
            .collect(Collectors.groupingBy(Student::grade));
        byGrade.forEach((g, list) ->
            System.out.println("  Grade " + g + ": " + list.stream().map(Student::name).toList()));

        // Average GPA per grade
        System.out.println("\n=== Average GPA per Grade ===");
        byGrade.forEach((g, list) -> {
            double avg = list.stream().mapToDouble(Student::gpa).average().orElse(0);
            System.out.printf("  Grade %d: %.2f%n", g, avg);
        });

        // Remove low performers
        students.removeIf(s -> s.gpa() < 2.0);
        System.out.println("\nAfter removing GPA<2.0: " + students.stream().map(Student::name).toList());
    }
}
