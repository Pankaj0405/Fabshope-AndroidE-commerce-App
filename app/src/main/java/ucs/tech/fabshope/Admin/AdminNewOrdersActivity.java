package ucs.tech.fabshope.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;

import ucs.tech.fabshope.ConfirmFinalOrderActivity;
import ucs.tech.fabshope.HomeActivity;
import ucs.tech.fabshope.Model.AdminOrders;
import ucs.tech.fabshope.Model.Products;
import ucs.tech.fabshope.Prevalent.Prevalent;
import ucs.tech.fabshope.R;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList=findViewById(R.id.orders_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders>options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef,AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter=new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+model.getName());
                holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                holder.userTotalPrice.setText("Total Amount: "+model.getTotalAmount());
                holder.userDatetime.setText("Order at: "+model.getDate()+" "+model.getTime());
                holder.userShippingAddress.setText("Shipping Address: "+model.getAddress());

                holder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String uID=getRef(holder.getBindingAdapterPosition()).getKey();
                        Intent intent=new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID=getRef(holder.getBindingAdapterPosition()).getKey();
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have you Delivered this order products?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                                    productRef.child(uID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                AdminOrders products = dataSnapshot.getValue(AdminOrders.class);
                                                final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                                                        .child("Orders1")
                                                        .child(uID);
                                                HashMap<String, Object> ordersMap = new HashMap<>();
                                                ordersMap.put("totalAmount", products.getTotalAmount());
                                                ordersMap.put("name", products.getName());
                                                ordersMap.put("phone", products.getPhone());
                                                ordersMap.put("address", products.getAddress());
                                                ordersMap.put("city", products.getCity());
                                                ordersMap.put("date", products.getDate());
                                                ordersMap.put("orderid", uID);
                                                ordersMap.put("userid", products.getUserid());

                                                ordersMap.put("time", products.getTime());
                                                ordersMap.put("state", "Order Delivered!");

                                                orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                });
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                            }
                           else
                                            {
                                                DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                                                productRef.child(uID).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            AdminOrders products = dataSnapshot.getValue(AdminOrders.class);
                                                            final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                                                                    .child("Orders2")
                                                                    .child(uID);
                                                            HashMap<String, Object> ordersMap = new HashMap<>();
                                                            ordersMap.put("totalAmount", products.getTotalAmount());
                                                            ordersMap.put("name", products.getName());
                                                            ordersMap.put("phone", products.getPhone());
                                                            ordersMap.put("address", products.getAddress());
                                                            ordersMap.put("city", products.getCity());
                                                            ordersMap.put("date", products.getDate());
                                                            ordersMap.put("orderid", uID);
                                                            ordersMap.put("userid", products.getUserid());

                                                            ordersMap.put("time", products.getTime());
                                                            ordersMap.put("state", "Order Delivered!");

                                                            orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }
                                        }
                                    });
                                    builder.show();


                                }


                });




            }



            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
            return  new AdminOrdersViewHolder(view);
            }
        };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }


    public  static  class  AdminOrdersViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView userName,userPhoneNumber,userTotalPrice,userDatetime,userShippingAddress;
        public Button ShowOrderBtn;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDatetime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);
            ShowOrderBtn=itemView.findViewById(R.id.show_all_products_btn);
        }
    }


}