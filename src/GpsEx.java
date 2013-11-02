import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;


/**
 * @author dimiter@schizogenesis.com
 * @since Sep 5, 2004  3:37:02 AM
 */
public class GpsEx extends MIDlet implements CommandListener {
    private Command cmdScan = new Command("Scan", Command.ITEM, 0);
    StringItem sitmLong = new StringItem("Lon:", "");
    StringItem sitmLat = new StringItem("Lat:", "");
    StringItem sitmDate = new StringItem("Date:", "");

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("SYSTEM: Starting application.");
        Form locateForm = new Form("GPS Locate");
        locateForm.append(sitmLong);
        locateForm.append(sitmLat);
        locateForm.append(sitmDate);
        locateForm.addCommand(cmdScan);
        locateForm.setCommandListener(this);
        Display.getDisplay(this).setCurrent(locateForm);
    }

    protected void pauseApp() {
        System.out.println("SYSTEM: Pausing application.");
    }

    protected void destroyApp(boolean b) throws MIDletStateChangeException {
        System.out.println("SYSTEM: Destroying application.");
    }

    public void commandAction(Command cmd, Displayable displayable) {
        if (cmd==cmdScan) {
            GpsLocation loc = GpsLocation.currentPosition();
            sitmLong.setText(loc.getLongtitude());
            sitmLat.setText(loc.getLattitude());
            sitmDate.setText(loc.getDate().toString());
        }
    }
}
