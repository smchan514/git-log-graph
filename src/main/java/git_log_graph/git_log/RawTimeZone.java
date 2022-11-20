package git_log_graph.git_log;

import java.util.Date;
import java.util.TimeZone;

public class RawTimeZone extends TimeZone {

    private static final long serialVersionUID = 1L;
    private int _rawOffsetMillis;

    public RawTimeZone(int rawOffsetMillis) {
        _rawOffsetMillis = rawOffsetMillis;
    }

    @Override
    public boolean useDaylightTime() {
        return false;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return false;
    }

    @Override
    public void setRawOffset(int offsetMillis) {
        _rawOffsetMillis = offsetMillis;
    }

    @Override
    public int getRawOffset() {
        return _rawOffsetMillis;
    }

    @Override
    public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
        throw new RuntimeException("Unsupported operation");
    }

}
