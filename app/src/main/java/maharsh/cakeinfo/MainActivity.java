package maharsh.cakeinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import maharsh.cakeinfo.databinding.ActivityMainBinding;
import maharsh.cakeinfo.datamodel.CakeDataModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainActivityViewModel viewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerAdapter cakeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        swipeRefreshLayout=binding.getRoot();

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getData(true);
        });

        getData(false);

        binding.cakeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getData(boolean b) {
        viewModel.getCakes(b).observe(this, response -> {
            swipeRefreshLayout.setRefreshing(false);
            if(response!=null){
                ArrayList<CakeDataModel> cakes = response.getResponseClass();
                if(response.getResponseCode() >= 200 && response.getResponseCode() < 300){
                    if(cakes.size()>0){
                        cakeAdapter = new RecyclerAdapter(cakes);
                        cakeAdapter.setOnItemClickListener(cakeDataModel -> {
                            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
                            dialogBuilder.setTitle(cakeDataModel.getTitle());
                            dialogBuilder.setMessage(cakeDataModel.getDesc());
                            dialogBuilder.setPositiveButton("Okay", (dialog, which) -> {

                            });
                            dialogBuilder.show();
                        });
                        binding.cakeRecyclerView.setAdapter(cakeAdapter);
                    }
                }
                else {
                    switch (response.getResponseCode()){
                        case 404:
                            makeToast("Not Found");
                            break;
                        case 500:
                            makeToast("Internal Server Error");
                    }
                }
            }
            else
                makeToast("Something went wrong, please check your network");
        });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}