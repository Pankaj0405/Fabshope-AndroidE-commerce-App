package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ucs.tech.fabshope.Admin.AdminNewOrdersActivity;
import ucs.tech.fabshope.Admin.AdminUserProductsActivity;
import ucs.tech.fabshope.Model.AdminOrders;
import ucs.tech.fabshope.Model.Cart;
import ucs.tech.fabshope.Prevalent.Prevalent;
import ucs.tech.fabshope.ViewHolder.CartViewHolder;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView orderList,orderlist1,orderList3;
    private DatabaseReference ordersRef,OrdersRef1,OrdersRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        OrdersRef1= FirebaseDatabase.getInstance().getReference().child("Orders1");
        OrdersRef2= FirebaseDatabase.getInstance().getReference().child("Orders2");
        orderList=findViewById(R.id.orders_listt);
        orderlist1=findViewById(R.id.orders_list2);
        orderList3=findViewById(R.id.orders_list3);
        orderList.setLayoutManager(new LinearLayoutManager(this));
        orderlist1.setLayoutManager(new LinearLayoutManager(this));
        orderList3.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders>options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef.orderByChild("userid").equalTo(Prevalent.currentOnlineUser.getPhone()),AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder> adapter=new FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+model.getName());
                holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                holder.userTotalPrice.setText("Total Amount: "+model.getTotalAmount());
                holder.userDatetime.setText("Order at: "+model.getDate()+" "+model.getTime());
                holder.userShippingAddress.setText("Shipping Address: "+model.getAddress());
                holder.complaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "fabshopeapp@gmail.com"));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                            startActivity(intent);
                        } catch(Exception e) {
                            Toast.makeText(OrdersActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }}
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID=getRef(holder.getBindingAdapterPosition()).getKey();
                        Intent intent=new Intent(OrdersActivity.this, OrdersActivity1.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);

                    }
                });




            }

            @NonNull
            @Override
            public OrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout2,parent,false);
                return  new OrdersActivity.AdminOrdersViewHolder(view);
            }
        };
        orderList.setAdapter(adapter);
        adapter.startListening();
        FirebaseRecyclerOptions<AdminOrders>options1=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(OrdersRef1.orderByChild("userid").equalTo(Prevalent.currentOnlineUser.getPhone()),AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder> adapter1=new FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+model.getName());
                holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                holder.userTotalPrice.setText("Total Amount: "+model.getTotalAmount());
                holder.userDatetime.setText("Order at: "+model.getDate()+" "+model.getTime());
                holder.userShippingAddress.setText("Shipping Address: "+model.getAddress());

                holder.complaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "your_emailid@gmail.com"));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                            startActivity(intent);
                        } catch(Exception e) {
                            Toast.makeText(OrdersActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }             }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID=getRef(holder.getBindingAdapterPosition()).getKey();
                        Intent intent=new Intent(OrdersActivity.this, OrdersActivity1.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);

                    }
                });




            }

            @NonNull
            @Override
            public OrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout2,parent,false);
                return  new OrdersActivity.AdminOrdersViewHolder(view);
            }
        };
        orderlist1.setAdapter(adapter1);
        adapter1.startListening();
        FirebaseRecyclerOptions<AdminOrders>options2=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(OrdersRef2.orderByChild("userid").equalTo(Prevalent.currentOnlineUser.getPhone()),AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder> adapter2=new FirebaseRecyclerAdapter<AdminOrders, OrdersActivity.AdminOrdersViewHolder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+model.getName());
                holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                holder.userTotalPrice.setText("Total Amount: "+model.getTotalAmount());
                holder.userDatetime.setText("Order at: "+model.getDate()+" "+model.getTime());
                holder.userShippingAddress.setText("Shipping Address: "+model.getAddress());
holder.complaint.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "your_emailid@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(OrdersActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }}
});

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID=getRef(holder.getBindingAdapterPosition()).getKey();
                        Intent intent=new Intent(OrdersActivity.this, OrdersActivity1.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);

                    }
                });




            }

            @NonNull
            @Override
            public OrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout2,parent,false);
                return  new OrdersActivity.AdminOrdersViewHolder(view);
            }
        };
        orderList3.setAdapter(adapter2);
        adapter2.startListening();
    }


    public  static  class  AdminOrdersViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView userName,userPhoneNumber,userTotalPrice,userDatetime,userShippingAddress;
public Button complaint;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDatetime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);
            complaint=itemView.findViewById(R.id.complaint_button);

        }
    }
}