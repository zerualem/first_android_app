package com.zerualem.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by zeru on 12/6/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
