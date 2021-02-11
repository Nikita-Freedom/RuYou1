package com.example.ruyou1.API;

import com.example.ruyou1.Model.GET.ItemList;
import com.example.ruyou1.Model.POST.Contact;
import com.example.ruyou1.Model.POST.ItemPostList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @GET("get.php")
    Call<ItemList> getItems(@Query("action") String action);

    @Multipart
    @POST("send.php")
    Call<ResponseBody> sendItems(@Query("action") String action,
                                 @Part ("id")Integer id,
                                 @Part MultipartBody.Part image,
                                 @Part ("contact")List<Contact> contact);
}
