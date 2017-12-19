package com.zerualem.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zerualem.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.zerualem.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by zeru on 12/17/2017.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString=getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title=getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date=getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int solved=getInt(getColumnIndex(CrimeTable.Cols.DATE));

        Crime crime=new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(solved !=0);
        return crime;
    }
}
