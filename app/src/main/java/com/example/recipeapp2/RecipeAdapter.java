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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter {

    Context context;
    boolean isDeleting;
    private ArrayList<Recipe> recipeData;

    RecipeAdapter(ArrayList<Recipe> recipes, Context context){
        Log.d("2","2");
        this.context = context;
        Log.d("3","3");

        recipeData = recipes;
        Log.d("4",recipes.get(0).getRecipeName());

    }

    public void setDeleting(boolean deleting) {
        isDeleting = deleting;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("0","0");
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.complex_list_item, parent, false);        Log.d("0","0");
        Log.d("1","1");

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecipeViewHolder recipeVH = (RecipeViewHolder) holder;
        recipeVH.getNameTextView().
                setText(recipeData.get(position).getRecipeName());
        if(recipeData.get(position).getPhoto()!=null){

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
        return 0;
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
