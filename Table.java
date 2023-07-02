import java.util.ArrayList;
import java.util.Random;

public class Table {
    final Integer CAPACITY = 5;
    String tableName;
    String status;
    ArrayList<Fork> forks = new ArrayList<Fork>(CAPACITY);
    ArrayList<Philopsopher> seats = new ArrayList<Philopsopher>(CAPACITY);

    public Table(String tableName) {
        this.tableName = tableName;
        status = "";
        // genrate forks
        for (int i = 0; i < CAPACITY; i++) {
            Fork fork = new Fork(i);
            forks.add(fork);
        }
    }

    /**
     * Returns the name of the table.
     *
     * @return the name of the table
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Determines if the table is deadlocked.
     *
     * @return true if the table is deadlocked, false otherwise
     */
    public boolean isDeadLocked() {
        if (!seats.isEmpty()) {
            for (Philopsopher p : seats) {
                if (!p.getIsWating()) {
                    status = "";
                    return false; // if just one philopsopher is not waiting
                }
            }
            status = "deadlocked";
            return true;
        }
        status = "";
        return false; // if no philopsophers on the table therefore none are waiting
    }

    /**
     * Adds a philosopher to the table.
     *
     * @param philopsopher the philosopher to add
     * @return true if the philosopher was added successfully, false otherwise
     */
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

    /**
     * Removes a philosopher from the list of philosophers and makes them stand up.
     *
     * @param philopsopher the philosopher to be removed
     */
    public void removePhilopsopher(Philopsopher philopsopher) {
        philopsopher.standUp();
        seats.remove(philopsopher);
    }

    /**
     * Get a random philosopher from the table seats.
     *
     * @return the randomly selected philosopher
     */
    public Philopsopher getRandomPhilopsopher() {
        Random random = new Random();
        int num = random.nextInt(seats.size());
        return seats.get(num);
    }

    /**
     * Displays the information about the table, including the table name, number of
     * seats, and status.
     * Also displays the availability of forks and the status of each seat.
     * 
     * @Fork
     *       status: (F) available, (X) unavailable
     * @Philosopher
     *              status: (@) thinkning, (!) have left fork, (*) eating,
     *              (philosopher's letter) idle
     */
    public void display() {
        System.out.println(this.tableName + " (" + seats.size() + "): " + status);
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
                    System.out.print(seats.get(i).getStatus() + "");
                } else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println("]");
        System.out.println("");

    }

}
