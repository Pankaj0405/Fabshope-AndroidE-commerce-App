package ucs.tech.fabshope.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ucs.tech.fabshope.HomeActivity;
import ucs.tech.fabshope.MainActivity;
import ucs.tech.fabshope.R;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView tshirt,jacket,boywear;
    private ImageView tops,girlwear,glasses;
    private ImageView earbuds,watch,shoes;

    private Button LogoutBtn,CheckOrdersBtn,maintainProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn=findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn=findViewById(R.id.check_orders_btn);

        maintainProductsBtn=findViewById(R.id.maintain_btn);

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Adminss", "Adminss");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);

                startActivity(intent);

            }
        });

        tshirt = (ImageView) findViewById(R.id.t_shirts);
        jacket = (ImageView) findViewById(R.id.jacket);
        boywear = (ImageView) findViewById(R.id.boyswear);


        glasses = (ImageView) findViewById(R.id.glasses);
        girlwear = (ImageView) findViewById(R.id.girlswear);
        tops = (ImageView) findViewById(R.id.tops);
        shoes = (ImageView) findViewById(R.id.shoes);

        earbuds = (ImageView) findViewById(R.id.earbuds);
        watch = (ImageView) findViewById(R.id.watch);


        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "tshirt");
                startActivity(intent);
            }
        });


        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "jacket");
                startActivity(intent);
            }
        });


        boywear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "boywear");
                startActivity(intent);
            }
        });


        girlwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "girlwear");
                startActivity(intent);
            }
        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "glasses");
                startActivity(intent);
            }
        });


        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "shoes");
                startActivity(intent);
            }
        });



        tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "tops");
                startActivity(intent);
            }
        });


        earbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "earbuds");
                startActivity(intent);
            }
        });



        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "watch");
                startActivity(intent);
            }
        });



    }
}