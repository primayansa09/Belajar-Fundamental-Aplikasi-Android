package com.example.submissiongithub2;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
public class FragmentListAdapter extends RecyclerView.Adapter<FragmentListAdapter.FragmentViewHolder> {

    private ArrayList<DataUser> listFragment = new ArrayList<>();

    public void setFragment(ArrayList<DataUser> items){
        listFragment.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_tablayout, viewGroup, false);
        return new FragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position) {

        DataUser dataFragment = listFragment.get(position);
        Glide.with(holder.itemView.getContext())
                .load(dataFragment.getPhotoUser())
                .apply(new RequestOptions().override(100,100))
                .into(holder.imgPhoto);

        holder.nameUser.setText(dataFragment.getNameUser());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUser user = new DataUser();
                user.setPhotoUser(dataFragment.getPhotoUser());
                user.setNameUser(dataFragment.getNameUser());
                Intent intent = new Intent(holder.itemView.getContext(), ActivityDetail.class);
                intent.putExtra(ActivityDetail.EXTRA_USER, (Parcelable) user);
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listFragment.size();
    }

    public class FragmentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView nameUser;

        public FragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_Tablayout);
            nameUser = itemView.findViewById(R.id.name_Tablayout);
        }
    }
}
