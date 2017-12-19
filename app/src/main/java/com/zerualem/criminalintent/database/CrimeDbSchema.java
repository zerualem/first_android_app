package com.zerualem.criminalintent.database;

/**
 * Created by zeru on 12/16/2017.
 */

public class CrimeDbSchema {

    //Create the schema
    public static final class CrimeTable {
        public static final String NAME="crimes";

        public static final class Cols {
            public static final String UUID="uuid";
            public static final String TITLE="title";
            public static final String DATE="date";
            public static final String SOLVED="solved";
        }
    }
}

