package com.example.khotiun.ekaterinoslav.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.activities.LinkPageActivity;
import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.model.ArchitectLab;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;
import com.squareup.picasso.Picasso;

/**
 * Created by hotun on 30.08.2017.
 */

public class ArchitectFragment extends Fragment {
    private static final String TAG = "ArchitectFragment";
    private static final String ARG_ARCHITECT_ID = "architect_id";

    private Architect mArchitect;
    private TextView mNameTextView;
    private TextView mBiographyTextView;
    private TextView mDateTextView;
    private TextView mSourceTextView;
    private TextView mProjectTextView;
    private ImageView mPhotoImageView;

    public static ArchitectFragment newInstance(int architectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_ARCHITECT_ID, architectId);

        ArchitectFragment fragment = new ArchitectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int placeId = getArguments().getInt(ARG_ARCHITECT_ID);
        mArchitect = ArchitectLab.getArchitectLab().getArchitect(placeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_architect, container, false);
        mNameTextView = (TextView) view.findViewById(R.id.architect_name);
        mNameTextView.setText(mArchitect.getName());
        mBiographyTextView = (TextView) view.findViewById(R.id.architect_biography);
        mBiographyTextView.setText(mArchitect.getBiography());
        mDateTextView = (TextView) view.findViewById(R.id.architect_date);
        mDateTextView.setText(mArchitect.getDate());
        mSourceTextView = (TextView) view.findViewById(R.id.architect_source_line);
        mSourceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = LinkPageActivity.newIntent(getActivity(), mArchitect.getSource());
                startActivity(i);
            }
        });
        mProjectTextView = (TextView) view.findViewById(R.id.architect_project);
        mProjectTextView.setText(PlaceLab.getPlaceLab().getPlace(mArchitect.getPlaces().get(0)).getTitle());
        mPhotoImageView = (ImageView) view.findViewById(R.id.architect_photo);
        setImage(mArchitect.getPhotos(), mPhotoImageView);
        return view;

    }

    private void setImage(String mUrl, ImageView view) {
        Picasso.with(getContext()).load(mUrl)
                .placeholder(R.mipmap.empty_photo)
                .into(view);
    }

}
