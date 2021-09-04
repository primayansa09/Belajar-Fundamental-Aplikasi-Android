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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<DataUser> listUser = new ArrayList<>();

    public void setData(ArrayList<DataUser> items) {
        listUser.clear();
        listUser.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        DataUser user = listUser.get(position);
        Glide.with(holder.itemView.getContext())
                .load(user.getPhotoUser())
                .apply(new RequestOptions().override(150, 150))
                .into(holder.imgUser);

        holder.nameUser.setText(user.getNameUser());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUser user1 = new DataUser();
                user1.setPhotoUser(user.getPhotoUser());
                user1.setNameUser(user.getNameUser());

                Intent intent = new Intent(holder.itemView.getContext(), ActivityDetail.class);
                intent.putExtra(ActivityDetail.EXTRA_USER, (Parcelable) user1);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }



    public class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView nameUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.avatar_user);
            nameUser = itemView.findViewById(R.id.name_user);
        }
    }
}
