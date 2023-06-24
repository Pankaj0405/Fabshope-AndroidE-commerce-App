package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
//import com.razorpay.Checkout;
//import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import ucs.tech.fabshope.Prevalent.Prevalent;

public class ConfirmFinalOrderActivity extends AppCompatActivity implements PaymentResultListener {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;
    private TextView orderTotal;
    private  String totalAmount="",count="";
    private int l=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        Checkout.preload(getApplicationContext());
        totalAmount=getIntent().getStringExtra("Total Price");
orderTotal=findViewById(R.id.ordertotal3);
        confirmOrderBtn=findViewById(R.id.confirm_final_order_btn);
        nameEditText=findViewById(R.id.shippment_name);
        phoneEditText=findViewById(R.id.shippment_phone_number);
        addressEditText=findViewById(R.id.shippment_address);
        cityEditText=findViewById(R.id.shippment_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        orderTotal.setText(totalAmount.toString());
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your city name", Toast.LENGTH_SHORT).show();
        }
        else 
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

        PaymentNow(totalAmount);
        


    }

    private void PaymentNow(String totalAmount) {
        final Activity activity=this;
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_UT7Lsv2gCVUGsO");
        checkout.setImage(R.mipmap.ic_launcher_round);

        double finalAmount=Float.parseFloat(totalAmount)*100;
        try {
            JSONObject options =new JSONObject();
            options.put("name","FabshopE");
            options.put("description","1234");
            options.put("image",Prevalent.currentOnlineUser.getImage());
            options.put("themeColor","#A020F0");
            options.put("currency","INR");
            options.put("amount",finalAmount+"");
            options.put("prefill.email",nameEditText.getText().toString());
            options.put("prefil.contact",phoneEditText.getText().toString());
            checkout.open(activity,options);



        } catch (JSONException e) {
            Log.e("TAG","Error in starting RajorpAY");
        }

    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        String p=saveCurrentDate+saveCurrentTime;
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(p);
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
ordersMap.put("orderid",p);
        ordersMap.put("userid",Prevalent.currentOnlineUser.getPhone());

        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "Order confirmed!");

        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    DatabaseReference questionNodes = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Products");
                                                    final DatabaseReference toUsersQuestions = FirebaseDatabase.getInstance().getReference().child("Orderproduct").child(p).child("Products");

                                                    questionNodes.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            for (DataSnapshot questionCode : dataSnapshot.getChildren()) {
                                                                String questionCodeKey = questionCode.getKey();
                                                                String date = questionCode.child("date").getValue(String.class);
                                                                String discount = questionCode.child("discount").getValue(String.class);
                                                                String image = questionCode.child("image").getValue(String.class);
                                                                String pid = questionCode.child("pid").getValue(String.class);
                                                                String pname = questionCode.child("pname").getValue(String.class);
                                                                String price = questionCode.child("price").getValue(String.class);
                                                                String quantity = questionCode.child("quantity").getValue(String.class);
                                                                String size = questionCode.child("size").getValue(String.class);
                                                                String state = questionCode.child("state").getValue(String.class);
                                                                String time = questionCode.child("time").getValue(String.class);

                                                                toUsersQuestions.child(questionCodeKey).child("date").setValue(date);
                                                                toUsersQuestions.child(questionCodeKey).child("discount").setValue(discount);
                                                                toUsersQuestions.child(questionCodeKey).child("image").setValue(image);
                                                                toUsersQuestions.child(questionCodeKey).child("pid").setValue(pid);
                                                                toUsersQuestions.child(questionCodeKey).child("pname").setValue(pname);
                                                                toUsersQuestions.child(questionCodeKey).child("price").setValue(price);
                                                                toUsersQuestions.child(questionCodeKey).child("quantity").setValue(quantity);
                                                                toUsersQuestions.child(questionCodeKey).child("size").setValue(size);
                                                                toUsersQuestions.child(questionCodeKey).child("state").setValue(state);
                                                                toUsersQuestions.child(questionCodeKey).child("time").setValue(time);

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                    Toast.makeText(ConfirmFinalOrderActivity.this, "your final order has placed sucessfully", Toast.LENGTH_SHORT).show();

                                                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                                                    DatabaseReference dayOneRef = rootRef.child("User View").child(Prevalent.currentOnlineUser.getPhone());

                                                    ValueEventListener valueEventListener = new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            if (dataSnapshot.getValue() == null) {

                                                                Toast.makeText(ConfirmFinalOrderActivity.this, "your value put sucessfully", Toast.LENGTH_SHORT).show();
                                                                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                                editor.putString("value", "1");
                                                                editor.apply();

                                                            }
                                                        }


                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    };
                                                    dayOneRef.addListenerForSingleValueEvent(valueEventListener);


                                                    Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }





    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }
}