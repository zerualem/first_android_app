package com.zerualem.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by zeru on 12/6/2017.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    //private int itemPosition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        mRecyclerView=view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        CrimeLab crimeLab=CrimeLab.get(getActivity());      //get the data object
        List<Crime> crimes=crimeLab.getCrimes();            //Get the data
        if (mAdapter==null) {                               // if true, it's first time running
            mAdapter = new CrimeAdapter(crimes);            //add the data to the adapter
            mRecyclerView.setAdapter(mAdapter);             // set the adapter
        } else {
            mAdapter.notifyDataSetChanged();                //to make sure the data is updated
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        Crime mCrime;


        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent, false));

            mTitleTextView =itemView.findViewById(R.id.crime_title);
            mDateTextView =itemView.findViewById(R.id.crime_date);
            mSolvedImageView= (ImageView) itemView.findViewById(R.id.crime_solved);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            SimpleDateFormat df2= new SimpleDateFormat("EEE, MM d, YYYY", Locale.US);
            String dateString = df2.format(mCrime.getDate());
            //java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG);
            //String stringDate = df.format(mCrime.getDate());

            mDateTextView.setText(dateString);
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE: View.GONE);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            //itemPosition=position;
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
