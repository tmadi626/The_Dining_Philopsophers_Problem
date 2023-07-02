import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class Room implements AncestorListener, ActionListener {
    final Integer PHILOSOPHERS = 25;
    final Integer TABLES = 6;

    // controls the delay between each tick in ms
    private final int DELAY = 1000;
    int count = 1;
    long startTimer;
    long nowTimer;
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    ArrayList<Philopsopher> philopsophers = new ArrayList<Philopsopher>(PHILOSOPHERS);
    ArrayList<Table> tables = new ArrayList<Table>(TABLES);

    ArrayList<Thread> philopsophersThreads;
    String lastP;

    public Room() {
        System.out.println("Creating Room with " + PHILOSOPHERS + " Philopsophers and " + TABLES + " Tables");
        assignPhilosophersToTables();
    }

    /**
     * Assigns philosophers to tables with a maximum of 5 philosophers per table.
     * If there are more philosophers than can fit in a table, the remaining
     * philosophers
     * will be assigned to the next table.
     */
    public void assignPhilosophersToTables() {
        int philopsophersLeft = PHILOSOPHERS;

        for (int i = 1; i <= TABLES; i++) {
            Table table = new Table("Table " + i);
            this.tables.add(table);

            // add philopsophers
            for (char c = (char) ('A' + (PHILOSOPHERS - philopsophersLeft)); c < (char) ('A' + (i) * 5)
                    && philopsophersLeft > 0; c++) {
                Philopsopher p = new Philopsopher(Character.toString(c));
                philopsophers.add(p);
                table.addPhilopsopher(p);
                p.setSeated(true);
                philopsophersLeft--;
            }
        }
    }

    /**
     * This method is called by the timer every DELAY ms.
     * It prints the count and iterates over the tables.
     * If a table is deadlocked, it moves a random philosopher
     * from that table to the last table. If the last table is
     * also deadlocked, it stops the simulation. Finally, it
     * displays the table and increments the count.
     *
     * @param e the ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Count " + count);
        for (Table table : tables) {

            if (table.isDeadLocked()) {
                if (table != tables.get(tables.size() - 1)) {
                    System.out.println(table.getTableName() + " is deadlocked");
                    // move random philopsopher at this table to the 6th table
                    Philopsopher randPhilopsopher = table.getRandomPhilopsopher(); // get a random philopsopher

                    table.removePhilopsopher(randPhilopsopher);
                    if ((tables.get(tables.size() - 1)).addPhilopsopher(randPhilopsopher)) { // move to the last table
                                                                                             // ie
                                                                                             // 6th
                        // save the last philopsopher's information
                        lastP = randPhilopsopher.getName();
                    } else {
                        table.addPhilopsopher(randPhilopsopher); // move philopsopher from this table
                    }
                } else {
                    System.out.println(tables.get(tables.size() - 1).getTableName() + " is deadlocked");
                    this.stopSimulation();
                }
            }

            table.display(); // print table
        }
        count++;
    }

    /**
     * Starts the simulation.
     *
     * @param None No parameters required.
     * @return None No return value.
     */
    public void startSimulation() {
        // this timer will call the actionPerformed() method every DELAY ms
        startTimer = System.currentTimeMillis();

        this.timer = new Timer(DELAY, this);
        this.timer.start();
        // Make a thread for each Philopsopher and Run them
        this.philopsophersThreads = new ArrayList<Thread>();
        // Start the philopsophers
        for (Philopsopher p : philopsophers) {
            Thread philopsopherThread = new Thread(p);
            philopsophersThreads.add(philopsopherThread);
            philopsopherThread.start();
        }
        System.out.println("Room started");
    }

    /**
     * Stops the simulation by setting all philosophers to be not alive and stopping
     * the timer.
     *
     */
    public void stopSimulation() {
        // Stop the robots
        for (Philopsopher p : philopsophers) {
            p.setSeated(false);
            p.setAlive(false);
        }
        // kill the simulation
        this.timer.stop();
        this.timer.removeActionListener(this);
        nowTimer = System.currentTimeMillis();
        System.out.println("Simulation stopped, took " + (int) (nowTimer - startTimer) / 1000 + "s");
        System.out.println("Last Philopsopher @ table " + tables.get(tables.size() - 1).getTableName() + ": " + lastP);
    }

    @Override
    public void ancestorAdded(AncestorEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'ancestorAdded'");
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'ancestorRemoved'");
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'ancestorMoved'");
    }

}
