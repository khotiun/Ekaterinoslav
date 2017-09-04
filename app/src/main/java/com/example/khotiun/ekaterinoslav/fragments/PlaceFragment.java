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
import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;
import com.squareup.picasso.Picasso;

/**
 * Created by hotun on 30.08.2017.
 */

public class PlaceFragment extends Fragment {
    private static final String ARG_PLACE_ID = "place_id";

    private Place mPlace;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mAddressTextView;
    private TextView mArchitectNameTextView;
    private ImageView mOldPhotoImageView;
    private ImageView mNewPhotoOneImageView;
    private ImageView mNewPhotoTwoImageView;
    private ImageView mNewPhotoThreeImageView;
    private ImageView mNewPhotoFourImageView;

    public static PlaceFragment newInstance(int placeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PLACE_ID, placeId);

        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int placeId = getArguments().getInt(ARG_PLACE_ID);
        mPlace = PlaceLab.getPlaceLab().getPlace(placeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        mTitleTextView = (TextView) view.findViewById(R.id.place_title);
        mTitleTextView.setText(mPlace.getTitle());
        mDescriptionTextView = (TextView) view.findViewById(R.id.place_description);
        mDescriptionTextView.setText(mPlace.getDescription());
        mAddressTextView = (TextView) view.findViewById(R.id.place_address);
        mAddressTextView.setText(mPlace.getAddress());
        mArchitectNameTextView = (TextView) view.findViewById(R.id.place_architect_name);
        mArchitectNameTextView.setText(mPlace.getArchitect().getName());
        mOldPhotoImageView = (ImageView) view.findViewById(R.id.place_photo);
        setImage(mPlace.getOldPhotos(), mOldPhotoImageView);
        mNewPhotoOneImageView = (ImageView) view.findViewById(R.id.iv_detail_place_new_place_one);
        setImage(mPlace.getNewPhotos().get(0), mNewPhotoOneImageView);
        mNewPhotoTwoImageView = (ImageView) view.findViewById(R.id.iv_detail_place_new_place_two);
        setImage(mPlace.getNewPhotos().get(1), mNewPhotoTwoImageView);
        mNewPhotoThreeImageView = (ImageView) view.findViewById(R.id.iv_detail_place_new_place_three);
        setImage(mPlace.getNewPhotos().get(2), mNewPhotoThreeImageView);
        mNewPhotoFourImageView = (ImageView) view.findViewById(R.id.iv_detail_place_new_place_four);
        setImage(mPlace.getNewPhotos().get(3), mNewPhotoFourImageView);
        return view;

    }

    private void setImage(String mUrl, ImageView view) {
        Picasso.with(getContext()).load(mUrl)
                .placeholder(R.mipmap.empty_photo)
                .into(view);
    }

}
