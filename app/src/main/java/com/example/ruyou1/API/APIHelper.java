package com.example.ruyou1.API;

import android.util.Log;

import com.example.ruyou1.Model.GET.ItemList;
import com.example.ruyou1.Model.POST.ItemPostList;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {

    public interface APIHelperCallback<T> {
        void onResponse(T response);

        void onError();
    }

    private static APIHelper instance;
    private APIService apiService;

    private APIHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConfig.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }


    public static APIHelper getInstance() {
        if (instance == null)
            instance = new APIHelper();
        return instance;
    }


    public void getItems(String action, final APIHelperCallback<ItemList> callback){
        Call<ItemList> call = apiService.getItems(action);
        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {

                    callback.onResponse(response.body());

                    Log.e("Все отлично", String.valueOf(response.raw()));
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                callback.onError();
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }




    public void sendItems(String action, int id, ItemPostList contacts, final APIHelperCallback<ResponseBody> callback){

        File file = new File(contacts.getImage());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ResponseBody> call = apiService.sendItems("send_data", contacts.id, body , contacts.getContact());
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResponse(response.body());
                Log.e("что-то пошло не так222", String.valueOf(response.raw()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError();
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

}
