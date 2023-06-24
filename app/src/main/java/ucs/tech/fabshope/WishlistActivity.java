package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ucs.tech.fabshope.Admin.AdminMaintainProductsActivity;
import ucs.tech.fabshope.Model.Products;
import ucs.tech.fabshope.Prevalent.Prevalent;
import ucs.tech.fabshope.ViewHolder.ProductViewHolder;

public class WishlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        recyclerView1= findViewById(R.id.recycler_menu3);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView1.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Wishlist");
        FirebaseRecyclerOptions<Products> options1 =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(reference, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter1 =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {



                                    Intent intent = new Intent(WishlistActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView1.setAdapter(adapter1);
        adapter1.startListening();
    }
}