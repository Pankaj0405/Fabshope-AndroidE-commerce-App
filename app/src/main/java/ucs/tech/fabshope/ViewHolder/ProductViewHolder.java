package ucs.tech.fabshope.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ucs.tech.fabshope.Interface.ItemClickListner;
import ucs.tech.fabshope.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductDescription, txtProductPrice,txtProductRating,txtProductDetails;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductRating=itemView.findViewById(R.id.product_rating);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);

        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
