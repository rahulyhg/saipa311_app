package key_team.com.saipa311.Sale_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import key_team.com.saipa311.R;

/**
 * Created by ammorteza on 12/1/17.
 */
public class NewCars extends Fragment {
    private RecyclerView recyclerView;
    private NewCarsAdapter newCarAdapter;
    private ArrayList<newCar> newCarList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ss_new_car_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.new_car_recycler_view);
        newCarList = new ArrayList<>();
        newCarAdapter = new NewCarsAdapter(newCarList);
        recyclerView.setAdapter(newCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepareAlbums();
        return view;
    }
    private void prepareAlbums() {
        newCar a = new newCar("رنو ساندرو", "این خودرو داری قابلیت های بسیاری همچون ..." , R.drawable.new_car1);
        newCarAdapter.addItem(a);

        a = new newCar("کیا سراتو", "این خودرو داری قابلیت های بسیاری همچون ..." , R.drawable.new_car2);
        newCarAdapter.addItem(a);

        a = new newCar("بلریانس", "این خودرو داری قابلیت های بسیاری همچون ..." , R.drawable.new_car3);
        newCarAdapter.addItem(a);
    }
    public class newCar {
        String title;
        String description;
        int thumbnail;
        public newCar(String name, String desc, int image) {
            this.title = name;
            this.description = desc;
            this.thumbnail=image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getImage() {
            return thumbnail;
        }
    }

    public class NewCarsAdapter extends RecyclerView.Adapter<NewCarsAdapter.NewCarViewHolder> {

        private ArrayList<newCar> dataSet;
        public class NewCarViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView description;
            public ImageView thumbnail;

            public NewCarViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                description = (TextView) view.findViewById(R.id.description);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), NewCarInfoActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
        public NewCarsAdapter(ArrayList<newCar> data) {
            this.dataSet = data;
        }

        @Override
        public NewCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_car_cardview, parent, false);
            // view.setOnClickListener(MainActivity.myOnClickListener);
            NewCarViewHolder myViewHolder = new NewCarViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final NewCarViewHolder holder, final int listPosition) {
            holder.title.setText(dataSet.get(listPosition).getTitle());
            holder.description.setText(dataSet.get(listPosition).getDescription());
            holder.thumbnail.setImageResource(dataSet.get(listPosition).getImage());
        }

        public void addItem(newCar dataObj) {
            this.dataSet.add(dataObj);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
