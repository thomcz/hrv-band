package hrv.band.aurora.storage;

import android.provider.BaseColumns;

/**
 * Created by Julian on 23.06.2016.
 */
public class RRIntervalContract {

    RRIntervalContract() {}

    public static abstract class RRIntercalEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "RRIinterval";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_ENTRY_VALUE = "value";
    }
}
