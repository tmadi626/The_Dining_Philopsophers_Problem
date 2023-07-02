import java.util.ArrayList;
import java.util.Random;

public class Table {
    final Integer CAPACITY = 5;
    String tableName;
    ArrayList<Fork> forks = new ArrayList<Fork>(CAPACITY);
    ArrayList<Philopsopher> seats = new ArrayList<Philopsopher>(CAPACITY);

    public Table(String tableName) {
        this.tableName = tableName;
        // genrate forks
        for (int i = 0; i < CAPACITY; i++) {
            Fork fork = new Fork(i);
            forks.add(fork);
        }
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean isDeadLocked() {
        if (!seats.isEmpty()) {
            for (Philopsopher p : seats) {
                if (!p.getIsWating()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Might move this method to Room class
    public boolean addPhilopsopher(Philopsopher philopsopher) {
        if (seats.size() < CAPACITY) {// if there's space on the table
            seats.add(philopsopher);
            // Philosopher data
            int seatPosition = seats.indexOf(philopsopher);
            philopsopher.sitDown(forks.get(seatPosition), forks.get((seatPosition + 1) % 5), seatPosition);

            return true;
        }
        return false;
    }

    // Might move this method to Room class
    public void removePhilopsopher(Philopsopher philopsopher) {
        seats.remove(philopsopher);
        philopsopher.standUp();
    }

    public Philopsopher getRandomPhilopsopher() {
        Random random = new Random();
        int num = random.nextInt(seats.size());
        return seats.get(num);
    }

    public void display() {
        System.out.println(this.tableName + ":");
        System.out.print("Forks [");
        for (int i = 0; i < CAPACITY; i++) {
            if (i < CAPACITY - 1) {
                if (forks.get(i) != null) {
                    System.out.print(forks.get(i).isAvailable() ? "F ," : "X ,");
                } else {
                    System.out.print("  ,");
                }

            } else {
                if (forks.get(i) != null) {
                    System.out.print(forks.get(i).isAvailable() ? "F " : "X ");
                } else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println("]");

        System.out.print("Seats [");
        for (int i = 0; i < CAPACITY; i++) {
            if (i < CAPACITY - 1) {
                if (i < seats.size()) {
                    System.out.print(seats.get(i).getStatus() + ",");
                } else {
                    System.out.print("  ,");
                }

            } else {
                if (i < seats.size()) {
                    System.out.print(seats.get(i).getStatus() + ",");
                } else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println("]");
        System.out.println("");

    }

}
