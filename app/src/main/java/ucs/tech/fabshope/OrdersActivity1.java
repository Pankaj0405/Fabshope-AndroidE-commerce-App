package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ucs.tech.fabshope.Model.Cart;
import ucs.tech.fabshope.Prevalent.Prevalent;
import ucs.tech.fabshope.ViewHolder.CartViewHolder;

public class OrdersActivity1 extends AppCompatActivity {
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders1);
        userID=getIntent().getStringExtra("uid");

        productsList=findViewById(R.id.products_list1);
        productsList.setHasFixedSize(false);
        layoutManager=new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

        cartListRef= FirebaseDatabase.getInstance().getReference()
                .child("Orderproduct").child(userID).child("Products");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(OrdersActivity1.this,userID,Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<Cart> options=
                new  FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef,Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.txtProductQuantity.setText("Quantity"+model.getQuantity());
                holder.txtProductPrice.setText("Price Rs="+model.getPrice());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductSize.setText("Size "+model.getSize());


            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return  holder;

            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();


    }
}