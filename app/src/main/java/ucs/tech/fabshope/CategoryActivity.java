package ucs.tech.fabshope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ucs.tech.fabshope.Admin.AdminAddNewProductActivity;
import ucs.tech.fabshope.Admin.AdminCategoryActivity;
import ucs.tech.fabshope.Model.Products;
import ucs.tech.fabshope.ViewHolder.ProductViewHolder;

public class CategoryActivity extends AppCompatActivity {
    private ImageView tshirt,jacket,boywear;
    private ImageView tops,girlwear,glasses;
    private ImageView earbuds,watch,shoes;
    private RecyclerView categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categories=findViewById(R.id.categories);

        categories.setVisibility(View.GONE);


        tshirt = (ImageView) findViewById(R.id.t_shirts);
        jacket = (ImageView) findViewById(R.id.jacket);
        boywear = (ImageView) findViewById(R.id.boyswear);


        glasses = (ImageView) findViewById(R.id.glasses);
        girlwear = (ImageView) findViewById(R.id.girlswear);
        tops = (ImageView) findViewById(R.id.tops);
        shoes = (ImageView) findViewById(R.id.shoes);

        earbuds = (ImageView) findViewById(R.id.earbuds);
        watch = (ImageView) findViewById(R.id.watch);
        LinearLayout linearlayout1=(LinearLayout)findViewById(R.id.linear1_category_layout);
        LinearLayout linearlayout2=(LinearLayout)findViewById(R.id.linear2_category_layout);
        LinearLayout linearlayout3=(LinearLayout)findViewById(R.id.linear3_category_layout);


        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("tshirt"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }

        });


        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("jacket"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });


        boywear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("boywear"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });


        girlwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("girlwear"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("glasses"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });


        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("shoes"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });



        tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("tops"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });


        earbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("earbuds"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });



        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                categories.setVisibility(View.VISIBLE);
                linearlayout1.setVisibility(View.GONE);
                linearlayout2.setVisibility(View.GONE);
                linearlayout3.setVisibility(View.GONE);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
                FirebaseRecyclerOptions<Products> options=
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(reference.orderByChild("category").equalTo("watch"),Products.class)
                                .build();

                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter
                        =new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());

                        holder.txtProductPrice.setText("Rs " + model.getPrice() );
                        holder.txtProductRating.setText(model.getRating());

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(CategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
                categories.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
                categories.setAdapter(adapter);
                adapter.startListening();
            }


        });



    }
}