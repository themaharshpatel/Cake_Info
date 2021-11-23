package maharsh.cakeinfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import maharsh.cakeinfo.datamodel.CakeDataModel;
import maharsh.cakeinfo.datamodel.ResponseModel;
import maharsh.cakeinfo.retrofit.RetrofitClient;
import maharsh.cakeinfo.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CakeRepository {

    private final RetrofitInterface retrofitInterface;

    public CakeRepository() {
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
    }

    public LiveData<ResponseModel<ArrayList<CakeDataModel>>> getCakes(){

        MutableLiveData<ResponseModel<ArrayList<CakeDataModel>>> mutableCakeLiveData = new MutableLiveData<>();

        retrofitInterface.getCake().enqueue(new Callback<ArrayList<CakeDataModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CakeDataModel>> call, @NonNull Response<ArrayList<CakeDataModel>> response) {

                ArrayList<CakeDataModel> cakeArrayList = response.body();
                if(cakeArrayList !=null && response.isSuccessful() && cakeArrayList.size()!=0)
                {
                    Map<String,Integer> dataMap = new HashMap<>();
                    for (int i = 0; i < cakeArrayList.size(); i++) {
                        dataMap.putIfAbsent(cakeArrayList.get(i).getTitle(),i);
                    }
                    TreeMap<String, Integer> sortedMap = new TreeMap<>(dataMap);

                    ArrayList<CakeDataModel> refinedCakeArrayList = new ArrayList<>();

                    for (Map.Entry<String,Integer> dataPair: sortedMap.entrySet()) {
                        refinedCakeArrayList.add(cakeArrayList.get(dataPair.getValue()));
                    }

                    cakeArrayList = refinedCakeArrayList;
                }

                mutableCakeLiveData.postValue(new ResponseModel<>(cakeArrayList, response.code(), response.errorBody()));

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CakeDataModel>> call, @NonNull Throwable t) {
                mutableCakeLiveData.postValue(null);
            }
        });

        return mutableCakeLiveData;
    }

}
