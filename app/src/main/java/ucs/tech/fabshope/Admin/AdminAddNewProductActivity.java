package ucs.tech.fabshope.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import ucs.tech.fabshope.R;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime,rating,size,details;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice,InputProductRating,InputProductDetails,InputProductSize;
    private static final int GalleryPick = 1;
    private ArrayList<Uri> Imagelist=new ArrayList<Uri>();
    private Uri ImageUri;
    private  int upload_count=0;
    private  static final  int PICK_IMAGE=1;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductRating=findViewById(R.id.product_rating3);
        InputProductSize=findViewById(R.id.product_size);
        InputProductDetails=findViewById(R.id.product_item_detials1);

        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE    )
        {
            if (resultCode==RESULT_OK)
            {
                if   (data.getClipData()!=null)
                {
int countClipData=data.getClipData().getItemCount();
int currentImageSelect=0;
while (currentImageSelect<countClipData)
{
ImageUri=data.getClipData().getItemAt(currentImageSelect).getUri();
Imagelist.add(ImageUri);
currentImageSelect=currentImageSelect+1;
}
String p="You have Selected Image ="+Imagelist.size()+"/images";
Toast.makeText(this,p,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"Please Select multiple image",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private void ValidateProductData()
    {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        rating=InputProductRating.getText().toString();
        size=InputProductSize.getText().toString();
        details=InputProductDetails.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(rating))
        {
            Toast.makeText(this, "Please write product rating...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(size))
        {
            Toast.makeText(this, "Please write product Size...", Toast.LENGTH_SHORT).show();
        }
            else if (TextUtils.isEmpty(details))
    {
        Toast.makeText(this, "Please write product Details...", Toast.LENGTH_SHORT).show();
    }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        ArrayList urlStrings = new ArrayList<>();
        for (upload_count = 0; upload_count < Imagelist.size(); upload_count++) {
            Uri IndividualImage = Imagelist.get(upload_count);
            StorageReference filePath = ProductImagesRef.child(IndividualImage.getLastPathSegment() + productRandomKey + ".jpg");
            filePath.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                       @Override
                                                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                           filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                               @Override
                                                                               public void onSuccess(Uri uri) {
                                                                                   urlStrings.add(String.valueOf(uri));


                                                                                   if (urlStrings.size() == Imagelist.size()) {
                                                                                       downloadImageUrl=urlStrings.get(0).toString();
SaveProductInfoToDatabase(urlStrings);

                                                                                   }

                                                                               }

                                                                           });
                                                                       }

                                                                   }
            );
        }



            }


//            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    filePath.getDownloadUrl().addOnSuccessListener(
//                            new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    urlStrings.add(String.valueOf(uri));
//
//
//
//                                    if (urlStrings.size() == Imagelist.size()){
//sav
//                                    }
//                                }
//                            }
//                    );
//                }
//            }


//
//        final UploadTask uploadTask = filePath.putFile(IndividualImage);
//
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e)
//            {
//                String message = e.toString();
//                Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                loadingBar.dismiss();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//            {
//                Toast.makeText(AdminAddNewProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
//
//                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
//                    {
//                        if (!task.isSuccessful())
//                        {
//                            throw task.getException();
//                        }
//
//                        downloadImageUrl = filePath.getDownloadUrl().toString();
//                        return filePath.getDownloadUrl();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            downloadImageUrl = task.getResult().toString();
//                            urlStrings.add(String.valueOf(downloadImageUrl));
//
//

//                                    if (urlStrings.size() == Imagelist.size()){
//
//                                        Toast.makeText(AdminAddNewProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
//
//                                        SaveProductInfoToDatabase(urlStrings);
//                                    }
//
//                        }
//                    }
//                });
//            }
//        });



//    private void StoreLink(String url,int i) {
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        hashMap.put("ImgLink" + i, url);
//DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("Products").child(productRandomKey);
//
//        database.push().setValue(hashMap)
//                .addOnCompleteListener(
//                        new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//Toast.makeText(AdminAddNewProductActivity.this,"image uploaded",Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        }
//                ).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(AdminAddNewProductActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//




    private void SaveProductInfoToDatabase(ArrayList<String> urlStrings)
{
    HashMap<String, Object> productMap = new HashMap<>();
    productMap.put("pid", productRandomKey);
    productMap.put("date", saveCurrentDate);
    productMap.put("time", saveCurrentTime);
    productMap.put("description", Description);
    productMap.put("image", downloadImageUrl);
    productMap.put("category", CategoryName);
    productMap.put("size", size);
    productMap.put("details", details);
    productMap.put("price", Price);
    productMap.put("pname", Pname);
    productMap.put("rating", rating);

    ProductsRef.child(productRandomKey).updateChildren(productMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        ProductsRef.child(productRandomKey).child("images").setValue(urlStrings);
                        Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);

                        loadingBar.dismiss();
                        Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
}
}