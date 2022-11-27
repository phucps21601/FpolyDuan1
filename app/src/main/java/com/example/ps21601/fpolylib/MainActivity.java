package com.example.ps21601.fpolylib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ps21601.fpolylib.adapter.AdapterClickEvent;
import com.example.ps21601.fpolylib.adapter.TacgiaAdapter;
import com.example.ps21601.fpolylib.model.TacgiaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterClickEvent {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout mDrawerLayout;
    private ImageView imgView;
    private TextView tvName, tvEMAIL;
    private NavigationView mNavigationView;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUI();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


//        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_phieumuon, R.id.nav_thanhvien,R.id.nav_sign_out)
                .setOpenableLayout(mDrawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);
        showUserInfor();
      //  onNavigationItemSelected();

        mNavigationView.getMenu().findItem(R.id.nav_sign_out).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_sign_out){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent =  new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    private void initUI() {
        mNavigationView = findViewById(R.id.nav_view);
        imgView = mNavigationView.getHeaderView(0).findViewById(R.id.imgNAV);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tvNAV_Name);
        tvEMAIL = mNavigationView.getHeaderView(0).findViewById(R.id.tvNAV_EMAIL);
    }



    private void showUserInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setVisibility(View.VISIBLE);
        }

        tvName.setText(name);
        tvEMAIL.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.bg_button).into(imgView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onEditTacgiaClick(TacgiaModel tacgiaModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tac_gia_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onDeleteTacgiaClick(TacgiaModel tacgiaModel) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Xóa")
                .setMessage("Xóa sẽ mất vĩnh viễn!!")
                .setNegativeButton("Hủy",null)
                .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("tacgia")
                                .document(tacgiaModel.getMa_tacgia())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        readData();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                }).show();
    }
    public void readData(){
        db.collection("tacgia")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<TacgiaModel> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String tenTacgia = map.get("ten_tacgia").toString();
                                String maTacgia = map.get("ma_tacgia").toString();
                                TacgiaModel tacgia = new TacgiaModel(maTacgia,tenTacgia);
                                list.add(tacgia);
                            }
                            TacgiaAdapter tacgiaAdapter = new TacgiaAdapter(MainActivity.this,list);
                            recyclerView.setAdapter(tacgiaAdapter);
                        }

                    }
                });
    }


}