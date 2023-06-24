package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ucs.tech.fabshope.Model.Products;
import ucs.tech.fabshope.Prevalent.Prevalent;

public class ProductDetailsActivity extends AppCompatActivity {
    private FloatingActionButton addTocartBtn;
    private ImageView backbutton,addtowish;
    private ViewFlipper viewFlipper;
    private List<Products> slideLists;
    private LinearLayout r9;
    private ElegantNumberButton numberButton;
    private ReadMoreTextView productDescription, productDetails;
    private TextView productPrice, productName, productRating, demo;
 private int l=0;

    private String productID = "", state = "Normal",Size="",imagee="",color="",count="",image1="";
//    public  static  String  Size="",imagee="";

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        slideLists = new ArrayList<>();
        productID = getIntent().getStringExtra("pid");
        demo = findViewById(R.id.demo);
        addTocartBtn = findViewById(R.id.add_product_to_cart_btn);
        numberButton = findViewById(R.id.number_btn);
        viewFlipper = findViewById(R.id.product_image_details);
        backbutton = findViewById(R.id.backbutton);
addtowish=findViewById(R.id.addtowish);
        productName = findViewById(R.id.product_name_details);
        productDescription = findViewById(R.id.product_description_details);
        productDetails = findViewById(R.id.product_item_details);
        productPrice = findViewById(R.id.product_price_details);
        productRating = findViewById(R.id.product_rating1);
        r9 = findViewById(R.id.r9);
        getProductDetails(productID);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        addTocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (state.equals("Order Placed") || state.equals("Order Shipped")) {
//                    Toast.makeText(ProductDetailsActivity.this, "you can purchase more products,once your order is shipped or confirmed", Toast.LENGTH_LONG).show();
//                } else
                    if (Size.equals("")){
                  Toast.makeText(ProductDetailsActivity.this,"Select Size",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    addingToCartList();
                }
            }
        });
addtowish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Wishlist");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(productID)) {
                    addtowish.setColorFilter(Color.parseColor("#FF0000"));
                    color="1";
                }
                else
                {
                    addtowish.setColorFilter(Color.parseColor("#FFFFFF"));
                    color="0";
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (color.equals("0"))
        {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
           String saveCurrentTime = currentTime.format(calForDate.getTime());

           

        final DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Wishlist");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("rating", productRating.getText().toString());
        cartMap.put("image", imagee);


        wishlistref.child(productID).
                updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addtowish.setColorFilter(Color.parseColor("#FF0000"));
                    Toast.makeText(ProductDetailsActivity.this, "Added to Wish List", Toast.LENGTH_SHORT).show();
                    color="1";
                }
            }
        });
    }
        else
        {
            final DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Wishlist");
            wishlistref.child(productID)
                    .removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ProductDetailsActivity.this, "Removed from wishiist", Toast.LENGTH_SHORT).show();
                                addtowish.setColorFilter(Color.parseColor("#FFFFFF"));
                                color="0";
                            }
                        }
                    });
        }
}

});
    }


    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Wishlist");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(productID)) {
                    addtowish.setColorFilter(Color.parseColor("#FF0000"));
                    color="1";
                                    }
                    else
                {
                    addtowish.setColorFilter(Color.parseColor("#FFFFFF"));
                    color="0";
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addingToCartList() {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
String p=Size.toString();


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("state", "not confirmed");
        cartMap.put("discount", "");
        cartMap.put("image", image1);
        cartMap.put("size", p);


        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID).
                updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products").child(productID).
                            updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductDetailsActivity.this, "Added to Cart List", Toast.LENGTH_SHORT).show();

                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                                DatabaseReference dayOneRef = rootRef.child("User View")
                                        .child(Prevalent.currentOnlineUser.getPhone())
                                        .child("Products");
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        l= (int) dataSnapshot.getChildrenCount();

                                        count=String.valueOf(l);
                                        if (count.equals("1"))
                                        {

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



                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });


    }

    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    productRating.setText(products.getRating());
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    productDetails.setText(products.getDetails());
                    ArrayList<String> textArray = new ArrayList<String>();
                    String rev = "";
                    imagee=products.getImage();
                    char ch;
                    String det = products.getSize().toString();
                    for (int i = 0; i < det.length(); i++) {
                        ch = det.charAt(i);

                        if (ch == ',') {
                            textArray.add(rev);
                            rev = "";
                            continue;


                        }
                        if (ch == '.') {
                            textArray.add(rev);
                            break;
                        }
                        //extracts each character
                        rev = rev + ch; //adds each character in front of the existing string
                    }


                    ArrayList<TextView> mTextViewList = new ArrayList<>();
                    for (i = 0; i < textArray.size(); i++) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        layoutParams.setMargins(10, 40, 10, 40);
                        TextView textView = new TextView(ProductDetailsActivity.this);
                        textView.setText(textArray.get(i));
                        textView.setId(i);
                        textView.setClickable(true);
                        textView.setBackgroundResource(R.drawable.input_design);
                        textView.setTextSize((float) 13);
                        textView.setTextColor(Color.parseColor("#FF000000"));
                        textView.setPadding(20, 20, 20, 20);

                        mTextViewList.add(textView);
                        r9.addView(textView, layoutParams);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Size=textView.getText().toString();
                                textView.setBackgroundResource(R.drawable.input_design3);
                                textView.setPadding(20, 20, 20, 20);
                                int p = textView.getId();
                                for (int j = 0; j < textArray.size(); j++) {
                                    if (j == p) {
                                        continue;
                                    }


                                    mTextViewList.get(j).setBackgroundResource(R.drawable.input_design);
                                    mTextViewList.get(j).setPadding(20, 20, 20, 20);
                                }
                            }

                        });


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         String[] values = new String[4] ;
        DatabaseReference reff=productRef.child(productID);
        reff.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    for (int i=0;i<4;i++) {
                        String Answer = dataSnapshot.child(String.valueOf(i)).getValue().toString();
                        image1=dataSnapshot.child(String.valueOf(0)).getValue().toString();
                        ImageView imageView = new ImageView(ProductDetailsActivity.this);
                        Picasso.get().load(Answer).into(imageView);

                        viewFlipper.addView(imageView);

                        viewFlipper.setFlipInterval(2500);
                        viewFlipper.setAutoStart(true);

                        viewFlipper.startFlipping();
                        viewFlipper.setInAnimation(ProductDetailsActivity.this, android.R.anim.slide_in_left);
                        viewFlipper.setOutAnimation(ProductDetailsActivity.this, android.R.anim.slide_out_right);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demo.setText(values[0]);




    }

//        productRef.child(productID).child("images")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            slideLists.clear();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                Products model = snapshot.getValue(Products.class);
//
//                                slideLists.add(model);
//                            }
////                            Toast.makeText(SlideShowActivity.this, "All data fetched", Toast.LENGTH_SHORT).show();
//                            usingFirebaseImages(slideLists);
//                        } else {
//                            Toast.makeText(ProductDetailsActivity.this, "No images in firebase", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(ProductDetailsActivity.this, "NO images found \n" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//    private void usingFirebaseImages(List<Products> slideLists) {
//        for (int i = 0; i < slideLists.size(); i++) {
//            String downloadImageUrl = slideLists.get(i).getImageUrl();
//            flipImages(downloadImageUrl);
//        }
//    }






    private void CheckOrderState() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped")) {
                        state = "Order Shipped";
                    } else if (shippingState.equals("not shipped")) {
                        state = "Order Placed";

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
