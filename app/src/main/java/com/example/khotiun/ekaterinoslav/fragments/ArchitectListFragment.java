package com.example.khotiun.ekaterinoslav.fragments;

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

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.activities.ArchitectPagerActivity;
import com.example.khotiun.ekaterinoslav.activities.PlacePagerActivity;
import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.model.ArchitectLab;
import com.example.khotiun.ekaterinoslav.model.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by hotun on 31.08.2017.
 */

public class ArchitectListFragment extends Fragment {
    public static String TAG = "ArchitectListFragment";
    private RecyclerView mArchitectRecyclerView;
    private ArchitectAdapter mAdapter;

    public static Fragment newInstance() {
        return new ArchitectListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//удержание фрагмента
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_architect_list, container, false);
        mArchitectRecyclerView = (RecyclerView) view.findViewById(R.id.architect_recycler_view);
        mArchitectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArchitectAdapter(ArchitectLab.getArchitectLab().getArchitects());
        mArchitectRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class ArchitectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhotoImageView;
        private TextView mNameTextView;
        private TextView mDateTextView;
        private Architect mArchitect;

        public void bindArchitect(Architect architect) {
            mArchitect = architect;
            Picasso.with(getActivity()).load(architect.getPhotos())
                    .placeholder(R.mipmap.empty_photo)
                    .into(mPhotoImageView);
            mNameTextView.setText(architect.getName());
            mDateTextView.setText(architect.getDate());

        }

        public ArchitectHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.item_architect_iv);
            mNameTextView = (TextView) itemView.findViewById(R.id.item_architect_architect_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.item_architect_architect_date);

        }

        @Override
        public void onClick(View v) {
            Intent intent = ArchitectPagerActivity.newIntent(getActivity(), mArchitect.getId());
            startActivity(intent);
        }
    }

    private class ArchitectAdapter extends RecyclerView.Adapter<ArchitectHolder> {

        private List<Architect> mArchitects;

        public ArchitectAdapter(List<Architect> architects) {
            mArchitects = architects;
        }

        @Override
        public ArchitectHolder onCreateViewHolder(ViewGroup parent, int viewType) {//вызывается виджетом RecyclerView, когда ему потребуется новое представление для отображения элемента.
            // В этом методе мы создаем объект View и упаковываем его в ViewHolder. RecyclerView пока не ожидает,что представление будет связано с какими-либо данными.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_architect, parent, false);
            return new ArchitectHolder(view);
        }

        @Override
        public void onBindViewHolder(ArchitectHolder holder, int position) {//этот метод связывает представление View объекта ViewHolder с объектом модели. При вызове он получает ViewHolder
            // и позицию в наборе данных. Позиция используется для нахождения правильных данных модели, после чего View обновляется в соответствии с этими данными.
            Architect architect = mArchitects.get(position);
            holder.bindArchitect(architect);
        }

        @Override
        public int getItemCount() {
            return mArchitects.size();
        }

        public void setCrimes(List<Architect> architects) {
            mArchitects = architects;
        }
    }
}
