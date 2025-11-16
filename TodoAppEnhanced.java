import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Task implements Serializable {
    String name;
    boolean completed;

    Task(String name) {
        this.name = name;
        this.completed = false;
    }

    void markCompleted() {
        this.completed = true;
    }

    @Override
    public String toString() {
        // ANSI colors: Green for completed, Yellow for pending
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        return (completed ? green + "[✔] " : yellow + "[ ] ") + name + reset;
    }
}

public class TodoAppEnhanced {

    private static final String FILE_NAME = "tasks.dat";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        // Load tasks from file
        File file = new File(FILE_NAME);
        if (file.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
        }

        int choice;
        System.out.println("=== Welcome to Java To-Do List App ===");

        do {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Summary");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String name = sc.nextLine();
                    tasks.add(new Task(name));
                    System.out.println("Task added!");
                    break;

                case 2:
                    System.out.println("\nYour Tasks:");
                    if (tasks.isEmpty()) System.out.println("No tasks yet.");
                    else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter task number to mark completed: ");
                    int num = sc.nextInt();
                    if (num > 0 && num <= tasks.size()) {
                        tasks.get(num - 1).markCompleted();
                        System.out.println("Task marked completed!");
                    } else {
                        System.out.println("Invalid number!");
                    }
                    break;

                case 4:
                    System.out.print("Enter task number to delete: ");
                    int del = sc.nextInt();
                    if (del > 0 && del <= tasks.size()) {
                        tasks.remove(del - 1);
                        System.out.println("Task deleted!");
                    } else {
                        System.out.println("Invalid number!");
                    }
                    break;

                case 5:
                    int total = tasks.size();
                    long done = tasks.stream().filter(t -> t.completed).count();
                    long remaining = total - done;
                    System.out.println("\n=== Summary ===");
                    System.out.println("Total tasks: " + total);
                    System.out.println("Completed: " + done);
                    System.out.println("Remaining: " + remaining);
                    break;

                case 6:
                    // Save tasks to file
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                    oos.writeObject(tasks);
                    oos.close();
                    System.out.println("Tasks saved. Exiting... Bye!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
