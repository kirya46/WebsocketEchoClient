import java.util.TimerTask;

/**
 * Created by Kirill Stoianov on 28.11.17.
 * <p>
 * Task for sending periodically ping message to server.
 */

public abstract class PingTimerTask extends TimerTask {

    public void run() {
        this.invoke();
    }

    /**
     * Implement sending ping message in subclass.
     */
    abstract void invoke();
}

