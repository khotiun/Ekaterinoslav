package com.example.khotiun.ekaterinoslav.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.model.ArchitectLab;
import com.squareup.picasso.Picasso;

/**
 * Created by hotun on 30.08.2017.
 */

public class ArchitectFragment extends Fragment {
    private static final String ARG_ARCHITECT_ID = "architect_id";

    private Architect mArchitect;
    private TextView mNameTextView;
    private TextView mBiographyTextView;
    private TextView mDateTextView;
    private TextView mSourceTextView;
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
        mSourceTextView = (TextView) view.findViewById(R.id.architect_source);
        mSourceTextView.setText(mArchitect.getSource());
        mPhotoImageView = (ImageView) view.findViewById(R.id.architect_photo);
        setImage(mArchitect.getPhotos(), mPhotoImageView);
        return view;

    }

    private void setImage(String mUrl, ImageView view) {
        Picasso.with(getContext()).load(mUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }

}
