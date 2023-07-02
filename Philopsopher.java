import java.util.Random;

public class Philopsopher implements Runnable {
    private String name; // name of the philopsopher
    private int position; // position of the philopsopher
    private Fork leftFork; // left fork object
    private Fork rightFork; // right fork object
    private Boolean seated; // if philosopher is seated
    private Boolean isAlive; // if thread is alive
    private String status; // (@) Thinking, (*) Eating, (!) Has left Fork & is Waiting
    private boolean isWating; // waitning for the forks

    boolean logger;

    public Philopsopher(String name) {
        this.isAlive = true;
        this.name = name;
        this.isWating = false;
        this.logger = false;
    }

    /**
     * Runs the function until the "isAlive" condition is false.
     *
     * @param none
     * @return none
     */
    public void run() {
        while (isAlive) {

            this.status = this.name; // Idle
            while (this.seated) {
                this.status = this.name + "@";

                think();
                takeFork(leftFork);

                this.status = "!" + this.name;

                this._wait(4, false);

                takeFork(rightFork);

                this.status = this.name + "*";
                this.eat();

                // Drop forks
                dropFork(leftFork);
                dropFork(rightFork);
            }
            if (logger) {
                System.out.println(this.name + ": is not SEATED");
            }
        }
    }

    /**
     * Gets the name of the philopsopher.
     *
     * @return the name of the philopsopher
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the status of the philopsopher.
     *
     * @return the status of the philopsopher
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the 'isWating' variable.
     *
     * @param isWating the new value for 'isWating'
     */
    public void setWating(boolean isWating) {
        this.isWating = isWating;
    }

    /**
     * Retrieves the value of the isWating variable.
     *
     * @return the value of the isWating variable
     */
    public boolean getIsWating() {
        return isWating;
    }

    /**
     * Sets the value of the isAlive variable.
     *
     * @param isAlive the new value for the isAlive variable
     */
    public void setAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Waits for a specified number of seconds.
     *
     * @param num  the number of seconds to wait
     * @param rand if true, waits for a random number of seconds between 0 and num
     */
    private void _wait(int num, boolean rand) {
        try {
            if (rand) {
                Random random = new Random();
                num = random.nextInt(num + 1);
                if (logger) {
                    System.out.println(name + " Waiting for " + num + "s");
                }
                Thread.sleep(num * 1000);
            } else {
                Thread.sleep(num * 1000);
            }

        } catch (Exception e) {
            if (logger) {
                System.out.println(name + " Could not wait " + num + "s");
            }
            System.out.println(e.getMessage());
        }
    }

    // Allows the Philopsopher to eat
    public void eat() {
        this._wait(5, true);
    }

    // Allows the Philopsopher to think
    public void think() {
        this._wait(10, true);
    }

    /**
     * Sets the left fork for the philopsopher.
     *
     * @param leftFork the fork to set as the left fork
     */
    public void setLeftFork(Fork leftFork) {
        this.leftFork = leftFork;
    }

    /**
     * Sets the right fork for the philopsopher.
     *
     * @param leftFork the fork to set as the right fork
     */
    public void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }

    /**
     * Takes a fork.
     *
     * @param fork the fork to take
     * @return void
     */
    public void takeFork(Fork fork) {
        // Wait for the forks
        this.isWating = true;
        if (logger) {
            System.out.println(this.name + " waiting for fork: " + fork.getPosition());
        }
        while (!fork.pickUp()) { // if the fork isn't picked up/ availabe then wait
            this._wait(1, false);
        }
        if (logger) {
            System.out.println(this.name + " took fork: " + fork.getPosition());
        }
        // the fork is picked up
        this.isWating = false;
    }

    /**
     * Drops the given fork.
     *
     * @param fork The fork to be dropped.
     */
    private void dropFork(Fork fork) {
        fork.putDown();
    }

    /**
     * Sets the position/seat of the philopsopher.
     *
     * @param position the position to be set
     */
    public void setPosition(Integer position) {
        if (position == null) {
            setSeated(false);
            return;
        }
        this.position = position;
        setSeated(true);
    }

    /**
     * Returns the seat position of the philopsopher.
     *
     * @return the position of the philopsopher
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets the left and right forks for the philosopher and their seating position.
     *
     * @param LFork        the left fork
     * @param RFork        the right fork
     * @param seatPosition the seating position of the philosopher
     */
    public void sitDown(Fork LFork, Fork RFork, Integer seatPosition) {
        this.setWating(false);
        this.setLeftFork(LFork);
        this.setRightFork(RFork);
        this.setPosition(seatPosition);
    }

    /**
     * Drops the fork for the philosopher, and removes them from the seat.
     * And make sure they are not waiting.
     */
    public void standUp() {
        this.setWating(false);
        this.setPosition(null);

        dropFork(leftFork);
        dropFork(rightFork);

        this.setLeftFork(null);
        this.setRightFork(null);
    }

    /**
     * Sets the seated status of the philosopher.
     *
     * @param seated the new seated status (true if seated, false if unseated)
     */
    public void setSeated(Boolean seated) {
        this.seated = seated;

        if (logger) {
            if (seated) {
                System.out.println(this.name + " is seated " + getPosition());
            } else {
                System.out.println(this.name + " is unseated " + getPosition());
            }
        }
    }

}
