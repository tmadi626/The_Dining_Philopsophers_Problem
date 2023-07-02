public class Fork {
    private boolean isAvailable;
    private int position;

    public Fork(int position) {
        this.isAvailable = true;
        this.position = position;
    }

    /**
     * Returns the position of the fork from the table.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Check if the fork is available.
     *
     * @return true if the fork is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Picks up this fork if unavailable, and setting it to unavailable.
     *
     * @return a boolean value indicating if the fork was picked up successfully or
     *         not
     */
    public boolean pickUp() {
        // if able to pick up the fork, return true
        // else return false
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    /**
     * Puts the fork down and setting it to available.
     */
    public void putDown() {
        isAvailable = true;
    }
}
