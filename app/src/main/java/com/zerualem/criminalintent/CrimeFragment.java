package com.zerualem.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zeru on 12/5/2017.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mCrimeDate;
    private CheckBox mSolvedCheckBox;
    public static final String ARG_CRIME_ID="crime_id";
    private static final String DIALOG_DATE ="DialogDate";
    private static final int REQUEST_CODE=0;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);

        CrimeFragment crimeFragment=new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime=new Crime();

        //UUID crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeID=(UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container,false);
        mTitleField =v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                UUID itemPos = mCrime.getId();
            }
        });

        mCrimeDate = v.findViewById(R.id.crime_date);
        updateDateButton();
        //mCrimeDate.setEnabled(false);
        mCrimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CODE);         // to result from datepickerfragment
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_CODE){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDateButton();
        }

    }

    private void updateDateButton() {
        mCrimeDate.setText(mCrime.getDate().toString());
    }
}
