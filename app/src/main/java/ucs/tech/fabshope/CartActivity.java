package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import ucs.tech.fabshope.Model.Cart;
import ucs.tech.fabshope.Prevalent.Prevalent;
import ucs.tech.fabshope.ViewHolder.CartViewHolder;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn,viewproducts;
    private TextView txtTotalAmount,txtMsg1,orderTotal,orderTotal1,cartempty;
private RelativeLayout r1,r2,r3;
    public  int overTotalPrice=0, oneTyprProductTPrice=0,p=0;
    private  String imagename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(false);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
orderTotal=findViewById(R.id.ordertotal1);
        orderTotal1=findViewById(R.id.ordertotal2);
        NextProcessBtn=findViewById(R.id.next_process_btn);
        r1=findViewById(R.id.rl11);
        r2=findViewById(R.id.r14);
        r3=findViewById(R.id.r12);
        cartempty=findViewById(R.id.cartempty);
        viewproducts=findViewById(R.id.viewproducts);
        txtTotalAmount=findViewById(R.id.page_title);
        txtMsg1=findViewById(R.id.msg1);
        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
viewproducts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(CartActivity.this,HomeActivity.class);

        startActivity(intent);
        finish();
    }
});

    }

    @Override
    protected void onStart() {
        super.onStart();
//        CheckOrderState();

overTotalPrice=0;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        DatabaseReference dayOneRef = rootRef.child("User View")
                .child(Prevalent.currentOnlineUser.getPhone())
                .child("Products");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
p= (int) dataSnapshot.getChildrenCount();

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dayOneRef.addListenerForSingleValueEvent(valueEventListener);

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart>options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Products"),Cart.class).
                        build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder>adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder=new CartViewHolder(view);
                        return  holder;

                    }

                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.txtProductQuantity.setText("Quantity "+model.getQuantity());
                        holder.txtProductPrice.setText("Price=Rs "+model.getPrice());
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductSize.setText("Size "+model.getSize());
                        oneTyprProductTPrice=((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                        overTotalPrice=overTotalPrice+ oneTyprProductTPrice;


                        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("value", String.valueOf(overTotalPrice));
                        editor.apply();
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[]=new CharSequence[]
                                        {
                                                "Edit",
                                                "Remove"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i==0)
                                        {
                                            Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);

                                        }
                                        if (i==1)
                                        {
                                            cartListRef.child("User View")
                                                    .child(Prevalent.currentOnlineUser.getPhone())
                                                    .child("Products")
                                                    .child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful())
                                                       {
                                                           Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                           Intent intent=new Intent(CartActivity.this,CartActivity.class);
                                                           startActivity(intent);
                                                           finish();
                                                           if (p==1)
                                                           {
                                                               SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                                                               SharedPreferences.Editor editor = sharedPref.edit();
                                                               editor.putString("value", "0");
                                                               editor.apply();
                                                           }

                                                       }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });


                            txtTotalAmount.setText("Rs "+String.valueOf(overTotalPrice));
                            orderTotal1.setText("Rs "+String.valueOf(overTotalPrice));
                            orderTotal.setText("Rs "+String.valueOf(overTotalPrice));


                    }


                };
        adapter.startListening();
recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("value","");
        if (String.valueOf(value).equals("0")) {

            r1.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
            cartempty.setVisibility(View.VISIBLE);
            viewproducts.setVisibility(View.VISIBLE);
        }


    }






    private void CheckOrderState()
    {
        DatabaseReference  ordersRef=FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if (dataSnapshot.exists())
           {
               String shippingState=dataSnapshot.child("state").getValue().toString();
               String username=dataSnapshot.child("name").getValue().toString();
               if (shippingState.equals("shipped"))
               {
                txtTotalAmount.setText("Dear"+username+"\n order is shipped sucessfully");
                recyclerView.setVisibility(View.GONE);

                txtMsg1.setVisibility(View.VISIBLE);
                txtMsg1.setText("Congratulation ,your final order has been shipped sucessfully.Soon you will receive your order at door step");
                NextProcessBtn.setVisibility(View.GONE);
                   Toast.makeText(CartActivity.this, "you can purchase more products,once you receive your first final order", Toast.LENGTH_SHORT).show();
               }
               else if (shippingState.equals("not shipped"))
               {
                   txtTotalAmount.setText("Dear"+username+"\n order is not shipped ");
                   recyclerView.setVisibility(View.GONE);

                   txtMsg1.setVisibility(View.VISIBLE);
                   NextProcessBtn.setVisibility(View.GONE);
                   Toast.makeText(CartActivity.this, "you can purchase more products,once you receive your first final order", Toast.LENGTH_SHORT).show();


               }
           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}