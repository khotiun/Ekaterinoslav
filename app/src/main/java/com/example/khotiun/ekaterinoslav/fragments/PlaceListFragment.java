package com.example.khotiun.ekaterinoslav.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.activities.PlacePagerActivity;
import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hotun on 24.07.2017.
 */

public class PlaceListFragment extends Fragment {
    private static String TAG = "PlaceListFragment";

    private RecyclerView mPlaceRecyclerView;
    private PlaceAdapter mAdapter;

    public static Fragment newInstance() {
        return new PlaceListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//удержание фрагментаZz
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        mPlaceRecyclerView = (RecyclerView) view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PlaceAdapter(PlaceLab.getPlaceLab().getPlaces());
        mPlaceRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoImageView;
        private TextView mTitleTextView;
        private TextView mAddressTextView;
        private Place mPlace;

        public void bindPlace(Place place) {
            mPlace = place;
            Picasso.with(getActivity()).load(place.getOldPhotos())
                    .placeholder(R.mipmap.empty_photo)
                    .into(mPhotoImageView);
            mTitleTextView.setText(place.getTitle());
            mAddressTextView.setText(place.getAddress());

        }

        public PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.item_place_iv);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_place_place_title);
            mAddressTextView = (TextView) itemView.findViewById(R.id.item_place_place_address);

        }

        @Override
        public void onClick(View v) {

            Intent intent = PlacePagerActivity.newIntent(getActivity(), mPlace.getId());
            startActivity(intent);
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder> {

        private List<Place> mPlaces;

        public PlaceAdapter(List<Place> places) {
            mPlaces = places;
        }

        @Override
        public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {//вызывается виджетом RecyclerView, когда ему потребуется новое представление для отображения элемента.
            // В этом методе мы создаем объект View и упаковываем его в ViewHolder. RecyclerView пока не ожидает,что представление будет связано с какими-либо данными.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_place, parent, false);
            return new PlaceHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaceHolder holder, int position) {//этот метод связывает представление View объекта ViewHolder с объектом модели. При вызове он получает ViewHolder
            // и позицию в наборе данных. Позиция используется для нахождения правильных данных модели, после чего View обновляется в соответствии с этими данными.
            Place place = mPlaces.get(position);
            holder.bindPlace(place);
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }

        public void setCrimes(List<Place> places) {
            mPlaces = places;
        }
    }
}
