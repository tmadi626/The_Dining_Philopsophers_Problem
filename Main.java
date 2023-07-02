/**
 * main
 */
public class Main {

    public static void main(String[] args) {
        try {
            Room room = new Room();
            room.startSimulation();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}