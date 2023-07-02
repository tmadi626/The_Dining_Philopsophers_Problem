public class Fork {
    private boolean isAvailable;
    private int position;

    public Fork(int position) {
        this.isAvailable = true;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean pickUp() {
        // if able to pick up the fork, return true
        // else return false
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    public void putDown() {
        isAvailable = true;
    }
}
