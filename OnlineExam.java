import java.util.*;
public class OnlineExam {
    private static final int EXAM_DURATION = 30;
    private static int count = 0; // Class-level variable to store the score
    public static void main(String[] args) {
        UserDatabase userDB = new UserDatabase();
        userDB.addUser(new User("12321", "12345", "Amit"));
        userDB.addUser(new User("45654", "56789", "Arjun"));
        Scanner sc = new Scanner(System.in);
        boolean isLoggedIn = false;
        User currentUser = null;
        while (!isLoggedIn) {
            System.out.println("Welcome to Online Examination System");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            currentUser = userDB.confirmUser(username, password);
            if (currentUser != null) {
                isLoggedIn = true;
                System.out.println("Login successful! Welcome, " + currentUser.getName());
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline character
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    currentUser.updateProfile(newName);
                    System.out.println("Profile updated successfully!");
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    currentUser.updatePassword(newPassword);
                    System.out.println("Password updated successfully!");
                    break;
                case 3:
                    startExam(sc);
                    break;
                case 4:
                    isRunning = false;
                    logout();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }
    private static void startExam(Scanner sc) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                System.out.println("\nTime's up! Auto-submitting answers...");
                submitAnswers();
            }
        }, EXAM_DURATION * 1000);
        System.out.println("\nMCQs:");
        String[] questions = {
            "1. What is the difference between '==', '.equals()', and '.compareTo()' methods in Java?",
            "2. What is the difference between 'ArrayList' and 'LinkedList' in Java?",
            "3. Explain the concept of method overloading and method overriding in Java.",
            "4. What are access modifiers in Java? List and explain them.",
            "5. What is the difference between 'abstract classes' and 'interfaces' in Java?"
        };
        String[] options = {
            "a) '==' is used for reference comparison, '.equals()' compares the content of objects, and '.compareTo()' is used for comparing strings. \n" +
            "b) '==' is used for value comparison, '.equals()' compares the content of objects, and '.compareTo()' is used for comparing numbers. \n" +
            "c) '==' is used for value comparison, '.equals()' compares the content of strings, and '.compareTo()' is used for comparing strings. \n" +
            "d) All three methods are used for value comparison.",
    
            "a) 'ArrayList' is faster for adding and removing elements, 'LinkedList' is faster for random access. \n" +
            "b) 'ArrayList' is faster for random access, 'LinkedList' is faster for adding and removing elements. \n" +
            "c) Both 'ArrayList' and 'LinkedList' have similar performance characteristics. \n" +
            "d) None of the above.",
    
            "a) Method overloading is having multiple methods with the same name but different parameters, while method overriding is providing a new implementation for an inherited method. \n" +
            "b) Method overloading is providing a new implementation for an inherited method, while method overriding is having multiple methods with the same name but different parameters. \n" +
            "c) Method overloading is having multiple methods with the same name and parameters, while method overriding is providing a new implementation for an inherited method. \n" +
            "d) None of the above.",
    
            "a) Access modifiers control the visibility and accessibility of classes, methods, and other members. The access modifiers in Java are 'public', 'protected', 'default', and 'private'. \n" +
            "b) Access modifiers control the synchronization of threads. \n" +
            "c) Access modifiers control the inheritance hierarchy of classes. \n" +
            "d) None of the above.",
    
            "a) Abstract classes can have constructors, while interfaces cannot. \n" +
            "b) Abstract classes can have instance variables, while interfaces cannot. \n" +
            "c) Classes can implement multiple interfaces, but can only extend one abstract class. \n" +
            "d) All of the above."
        };
        String[] answers = {"a", "b", "a", "a", "d"};
        count = 0; // Reset score before starting the exam   
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.println(options[i]);
            System.out.print("Your answer (a/b/c/d): ");
            String answer = sc.nextLine();   
            if (answer.equalsIgnoreCase(answers[i])) {
                System.out.println("Correct answer!");
                count++;
            } else {
                System.out.println("Incorrect answer.");
            }
        }    
        t.cancel();
        submitAnswers();
    }    
    private static void submitAnswers() {
        System.out.println("You scored " + count + " out of 5.");
        logout();
    }
    private static void logout() {
        System.out.println("\nLogging out...");
        System.exit(0);
    }
}
class User {
    private String username;
    private String password;
    private String name;
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public void updateProfile(String newName) {
        this.name = newName;
    }
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
class UserDatabase {
    private User[] users;
    private int count;
    public UserDatabase() {
        users = new User[10];
        count = 0;
    }
    public void addUser(User user) {
        users[count++] = user;
    }
    public User confirmUser(String username, String password) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
