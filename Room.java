import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class Room implements AncestorListener, ActionListener {
    final Integer PHILOSOPHERS = 5;
    final Integer TABLES = 2;
    // controls the delay between each tick in ms
    private final int DELAY = 1000;
    int count = 1;
    long startTimer;
    long nowTimer;
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    boolean isRunning;
    ArrayList<Philopsopher> philopsophers = new ArrayList<Philopsopher>(PHILOSOPHERS);
    ArrayList<Table> tables = new ArrayList<Table>(TABLES);

    ArrayList<Thread> philopsophersThreads;
    Philopsopher lastP;

    public Room() {
        System.out.println("Creating Room with " + PHILOSOPHERS + " Philopsophers and " + TABLES + " Tables");
        int philopsophersLeft = PHILOSOPHERS;

        for (int i = 0; i < TABLES; i++) {
            Table table = new Table("Table " + i);
            this.tables.add(table);

            for (char c = (char) ('a' + (PHILOSOPHERS - philopsophersLeft)); c < (char) ('a' + PHILOSOPHERS)
                    && philopsophersLeft > 0; c++) {
                Philopsopher p = new Philopsopher(Character.toString(c));
                // if (c == 'a') {
                // p.logger = true;
                // }
                philopsophers.add(p);
                table.addPhilopsopher(p);

                philopsophersLeft--;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        System.out.println("Count " + count);
        for (Table table : tables) {
            table.display(); // print table

            if (table.isDeadLocked()) {
                System.out.println("Table " + table.getTableName() + " is deadlocked");
                // move random philopsopher at this table to the 6th table
                Philopsopher randPhilopsopher = table.getRandomPhilopsopher(); // get a random philopsopher

                if ((tables.get(tables.size() - 1)).addPhilopsopher(randPhilopsopher)) { // move to the last table ie
                                                                                         // 6th
                    table.removePhilopsopher(randPhilopsopher); // move philopsopher from this table
                    // save the last philopsopher's information
                    lastP = randPhilopsopher;
                }
            }

            if (tables.get(tables.size() - 1).isDeadLocked()) {
                System.out.println("Table " + tables.get(tables.size() - 1).getTableName() + " is deadlocked");
                this.stopSimulation();
            }
        }
        count++;
    }

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
        isRunning = true;
    }

    public void stopSimulation() {
        // Stop the robots
        for (Philopsopher p : philopsophers) {
            p.setAlive(false);
        }
        // kill the simulation
        this.timer.stop();
        this.timer.removeActionListener(this);
        nowTimer = System.currentTimeMillis();
        System.out.println("Room stopped: " + (int) (nowTimer - startTimer) / 1000 + "s");
        System.out.println("Last Philopsopher @ table " + tables.get(tables.size() - 1).getTableName() + ": " + lastP);
        isRunning = false;
        return;
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
