import com.kddi.system.Location;

import java.util.Date;

/**
 * @author dimiter@schizogenesis.com
 * @since Sep 5, 2004  4:28:37 PM
 */
public class GpsLocation {
    private static final char CHAR_SECOND = '\u2033';
    private static final char CHAR_MINUTE = '\u2032';
    private static final char CHAR_DEGREE = '\u00b0';

    private final long timestamp;
    private final long longtitude;
    private final long latitude;

    private GpsLocation(long longtitude, long latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.timestamp = System.currentTimeMillis();
    }

    public String getLattitude()  { return formatCoordinate(latitude, false); }
    public String getLongtitude() { return formatCoordinate(longtitude, true);  }
    public Date   getDate()       { return new Date(timestamp); }

    public static GpsLocation currentPosition(){
        Location location = Location.getLocation();
        if (!location.getDatum().equals(Location.TOKYO)) {
            throw new IllegalArgumentException("Unrecognized datum: " + location.getDatum());
        }
        if (!location.getUnit().equals(Location.DMS)) {
            throw new IllegalArgumentException("Unrecognized unit: " + location.getUnit());
        }

        return new GpsLocation(parseDms(location.getLon()), parseDms(location.getLat()));

    }

    private static long parseDms(String dms) {
        long value = 0;
        for (int i=dms.length()-1; i>=0; i--) {
            char c = dms.charAt(i);
            if (Character.isDigit(c)) {
                value = value*10 + Character.digit(c, 10);
            }
        }
        return value;
    }

    private static String formatCoordinate(long value, boolean isLongtitude) {
        char qualifier = isLongtitude
                ? (value>0 ? 'W' : 'E')
                : (value>0 ? 'N' : 'S');

        StringBuffer strb = new StringBuffer();
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(CHAR_SECOND);
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(CHAR_MINUTE);
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(CHAR_DEGREE);
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(String.valueOf(value % 10));    value/=10;
        strb.append(qualifier);

        if (value!=0) throw new IllegalStateException("Coordinate too large!");

        return strb.reverse().toString();
    }
}
