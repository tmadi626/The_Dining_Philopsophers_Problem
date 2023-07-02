import java.util.Random;

public class Philopsopher implements Runnable {
    private String name;
    private int position;
    private Fork leftFork;
    private Fork rightFork;
    private Boolean seated;
    private Boolean isAlive;
    private String status;
    private boolean isWating; // waitning for the forks

    boolean logger;

    public Philopsopher(String name) {
        this.isAlive = true;
        this.name = name;
        this.isWating = false;
        this.logger = false;
    }

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
        }
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return status;
    }

    public void setWating(boolean isWating) {
        this.isWating = isWating;
    }

    public boolean getIsWating() {
        return isWating;
    }

    public void setAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

    // Wait
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

    // Eat
    public void eat() {
        this._wait(5, true);
    }

    // Think
    public void think() {
        this._wait(10, true);
    }

    public void setLeftFork(Fork leftFork) {
        this.leftFork = leftFork;
    }

    public void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }

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

    private void dropFork(Fork fork) {
        fork.putDown();
    }

    public void setPosition(Integer position) {
        if (position == null) {
            setSeated(false);
            return;
        }
        this.position = position;
        setSeated(true);
    }

    public Integer getPosition() {
        return position;
    }

    public void sitDown(Fork LFork, Fork RFork, Integer seatPosition) {
        this.setSeated(true);
        this.setPosition(seatPosition);
        this.setLeftFork(LFork);
        this.setRightFork(RFork);
    }

    public void standUp() {
        this.setWating(false);
        this.setSeated(false);
        this.setPosition(null);
        this.setLeftFork(null);
        this.setRightFork(null);
    }

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
