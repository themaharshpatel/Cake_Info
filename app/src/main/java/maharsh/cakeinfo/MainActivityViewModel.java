package maharsh.cakeinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import maharsh.cakeinfo.datamodel.CakeDataModel;
import maharsh.cakeinfo.datamodel.ResponseModel;

public class MainActivityViewModel extends ViewModel {

    private final CakeRepository repository;
    private LiveData<ResponseModel<ArrayList<CakeDataModel>>> cakeLiveData;

    public MainActivityViewModel() {
        repository = new CakeRepository();
    }

    public LiveData<ResponseModel<ArrayList<CakeDataModel>>> getCakes(boolean forceRefresh){
        if(cakeLiveData == null || forceRefresh){
            cakeLiveData = repository.getCakes();
        }
        return cakeLiveData;
    }

}
