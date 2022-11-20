package git_log_graph;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainApp {
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
            .getLogger(MainApp.class);

    private static Object _blinker = new Object();

    public MainApp() {
        // ...
    }

    public static void main(String[] args) {
        String xml = "sim-telemetry.sf.xml";
        boolean wait = false;
        boolean headless = false;

        for (int i = 0; i < args.length; i++) {
            if ("-x".equals(args[i])) {
                xml = args[++i];
            } else if ("--wait".equals(args[i])) {
                wait = true;
            } else if ("--headless".equals(args[i])) {
                headless = true;
            }
        }

        LOGGER.info(">>> Using parameters:");
        LOGGER.info("xml=" + xml);
        LOGGER.info("wait=" + wait);
        LOGGER.info("headless=" + headless);

        try {
            doStuff(xml, wait, headless);
        } catch (Exception e) {
            LOGGER.error("Failed", e);
        }
    }

    private static void doStuff(String xml, boolean wait, boolean headless) throws Exception {

        try (FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(xml)) {
            LOGGER.debug(xml + " --- STARTING");
            context.start();
            LOGGER.debug(xml + " --- STARTED");

            if (wait) {
                if (headless) {
                    // While headless, wait until someone wakes us up or the process gets killed
                    synchronized (_blinker) {
                        _blinker.wait();
                    }
                } else {
                    // Wait for the user presses ENTER
                    System.out.println("Press ENTER to continue...");
                    System.in.read();
                }
            } else {
                Thread.sleep(1000);
            }

            LOGGER.debug(xml + " --- STOPPING");
            context.stop();

            Thread.sleep(1000);

            LOGGER.debug(xml + " --- CLOSING");
            context.close();
            LOGGER.debug(xml + " --- CLOSED");

            // We are done
            System.exit(0);
        }

    }
}
