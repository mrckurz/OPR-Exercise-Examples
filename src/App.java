public class App {
    public static void main(String[] args) {
        Stack s = new Stack(2);
        try {
            s.push(1);
            s.push(2);
        } catch(StackFullException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            System.out.println(s.pop());
            System.out.println(s.pop());
            System.out.println(s.pop());

        } catch (StackEmptyException e) {
            System.out.println(e.getMessage() + ": " + e.getStatus());
        }
    }
}
