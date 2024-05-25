package com.example.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter {

    Context context;
    boolean isDeleting;
    private ArrayList<Recipe> recipeData;
    private View.OnClickListener onItemClickListener;


    RecipeAdapter(ArrayList<Recipe> recipes, Context context){

        this.context = context;
        recipeData = recipes;
        onItemClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                        v.getTag();
                int position = holder.getAdapterPosition();
                int recipeId = recipeData.get(position).getRecipeId();
                Intent intent = new Intent(context,
                        SingleRecipeActivity.class);
                intent.putExtra("recipeId", recipeId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        };

    }



    public void setDeleting(boolean deleting) {
        isDeleting = deleting;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.complex_list_item, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecipeViewHolder recipeVH = (RecipeViewHolder) holder;
        recipeVH.getNameTextView().
                setText(recipeData.get(position).getRecipeName());
        if(recipeData.get(position).getPhoto()!=null){
            recipeVH.getRecipeImage().setImageBitmap(recipeData.get(position).getPhoto());
        }
        if (isDeleting) {
            recipeVH.getDeleteImageButton().setVisibility(View.VISIBLE);
            recipeVH.getDeleteImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(holder.getAdapterPosition());
                }
            });
        } else {
            recipeVH.getDeleteImageButton().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeData != null ? recipeData.size() : 0;
    }


    private void deleteItem(int position) {
//        RecipeDataSource ds = new RecipeDataSource(context);
//        try {
//            ds.open();
//            int contactId = contactData.get(position).getContactID();
//            boolean didDelete = ds.deleteContact(contactId);
//            ds.close();
//            if (didDelete) {
//                contactData.remove(position);
//                notifyDataSetChanged();
//            } else {
//                Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
//        }
    }
    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView recipeImage;
        ImageButton deleteImageButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewRecipeName);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            deleteImageButton=itemView.findViewById(R.id.imageButtonDelete);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public ImageView getRecipeImage() {
            return recipeImage;
        }
        public ImageButton getDeleteImageButton() {
            return deleteImageButton;
        }

    }
}
