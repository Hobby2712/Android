package com.example.cuoiki.Retrofit;

import com.example.cuoiki.Model.Categories;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.Response.CategoryResponse;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Response.ProductsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    public static final String BASE_URL2="http://192.168.43.18:8080/Web/api/v1/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM d HH:mm:ss").create();


    APIService apiSevice2 = new Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class) ;
    @GET("cate1")
    Call<CategoryResponse> getCategoryAll();

    @GET("cate1")
    Call<Categories> getCategory();

    @GET("latest")
    Call<ProductResponse> getNewProducts();

    @GET("best-seller")
    Call<ProductResponse> getBestSeller();

    @GET("products/{id}")
    Call<ProductResponse> getProductById(@Path("id") String id);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("user") String username, @Field("pass") String password);

    @POST("updateimages.php")
    @Multipart
    Call<ProductResponse> upload1(@Part("username") RequestBody username, @Part MultipartBody.Part avatar);

//    @POST("updateimages.php")
//    @Multipart
//    Call<List<ImageUpload>> upload(@Part("id") RequestBody id, @Part MultipartBody.Part images);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsResponse> getProductByCategoryId(@Field("cid1") String cid1);

    @FormUrlEncoded
    @POST("newmealdetail.php")
    Call<ProductResponse> getProduct(@Field("id") String id);
}
