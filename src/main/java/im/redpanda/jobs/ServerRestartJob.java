package im.redpanda.jobs;

import im.redpanda.core.Server;
import im.redpanda.core.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ServerRestartJob extends Job {

    private static final long STARTED_AT = System.currentTimeMillis();
    public static final long RESTART_TIME = 1000L * 60L * 60L * 24L;

    public ServerRestartJob() {
        super(1000L * 60L * 60L, true);
    }

    @Override
    public void init() {
    }

    @Override
    public void work() {

        if (System.currentTimeMillis() - STARTED_AT < RESTART_TIME) {
            return;
        }

        if (!Settings.isLoadUpdates()) {
            return;
        }

        System.out.println("########################\n#    Server restart due to Job... #\n########################");

        File file = new File(".restart");
        if (!file.exists()) {
            try {
                new FileOutputStream(file).close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("coould not create .restart file, the restart will fail... exiting...");
            }
        }

        Server.shutdown();
        System.exit(0);


    }
}
