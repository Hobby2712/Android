package com.example.cuoiki.Retrofit;

import com.example.cuoiki.Model.Categories;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.Response.Category2Response;
import com.example.cuoiki.Response.CategoryResponse;
import com.example.cuoiki.Response.ChartResponse;
import com.example.cuoiki.Response.OneProductResponse;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Response.SignUpResponse;
import com.example.cuoiki.Response.StoreResponse;
import com.example.cuoiki.Response.ThongKeResponse;
import com.example.cuoiki.Response.VerifyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    
    public static final String BASE_URL2="http://192.168.43.18:8080/Web/api/v1/";
    //public static final String BASE_URL2="http://192.168.1.20:8080/Web/api/v1/";
    //public static final String BASE_URL2="http://192.168.6.165:8080/Web/api/v1/";


    //public static final String BASE_URL2="http://192.168.43.18:8080/Web/api/v1/";
    //public static final String BASE_URL2="http://192.168.1.20:8080/Web/api/v1/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM d HH:mm:ss").create();

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES);



    APIService apiSevice2 = new Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class) ;
    @GET("cate1")
    Call<CategoryResponse> getCategoryAll();

    @GET("cate2")
    Call<Category2Response> getAllCategories2();

    @GET("cate1")
    Call<Categories> getCategory();

    @GET("latest")
    Call<ProductResponse> getNewProducts();

    @GET("best-seller")
    Call<ProductResponse> getBestSeller();


    @GET("products/{id}")
    Call<OneProductResponse> getProductById(@Path("id") String id);

    @GET("products")
    Call<ProductResponse> searchProductByName(@Query("search") String search);

    @GET("products")
    Call<ProductResponse> getProductByStoreId(@Query("store") int storeId);

    @GET("orders")
    Call<OrderResponse> getOrders(@Query("user") int userId);

    @GET("orders")
    Call<OrderResponse> getShipperOrders(@Query("status") String status);

    @GET("orders")
    Call<OrderResponse> getOrdersStore(@Query("store") int storeId);

    @GET("orders")
    Call<OrderResponse> changeStatus(@Query("id") int id, @Query("status") int status);

    @GET("orders/count-status")
    Call<OrderResponse> getQuantityStatus(@Query("store") int storeId);

    @GET("products")
    Call<ProductResponse> searchStoreProductByName(@Query("store") int storeId,
                                                   @Query("search") String search);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("user") String username, @Field("pass") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<VerifyResponse> signup(@Field("email") String email, @Field("user") String username, @Field("pass") String password, @Field("repass") String repassword);
    @FormUrlEncoded
    @POST("verifySignUp")
    Call<SignUpResponse> verifysignup(@Field("email") String email, @Field("user") String username, @Field("pass") String password, @Field("otp") String otp, @Field("otpSend") String otpSend);

    @FormUrlEncoded
    @POST("deleteCart")
    Call<VerifyResponse> deleteCart(@Field("id") int uId);

    @FormUrlEncoded
    @POST("addCartItems")
    Call<VerifyResponse> insertCart(@Field("id") int uId, @Field("pid") int pId, @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<VerifyResponse> findAccount(@Field("email") String email);

    @FormUrlEncoded
    @POST("verifyForgotPass")
    Call<VerifyResponse> forgotPass(@Field("email") String email, @Field("newpass") String newpassword, @Field("otp") String otp, @Field("otpSend") String otpSend);

    @FormUrlEncoded
    @POST("changePassword")
    Call<VerifyResponse> changePass(@Field("id") int id, @Field("oldPass") String oldPass,@Field("newPass") String newPass,@Field("repeatNewPass") String repeatNewPass);

    @FormUrlEncoded
    @POST("verifyChangePass")
    Call<VerifyResponse> verifyChangePass(@Field("id") int id, @Field("pass") String newpassword, @Field("otp") String otp, @Field("otpSend") String otpSend);

    @FormUrlEncoded
    @POST("profile")
    Call<VerifyResponse> editProfile(@Field("id") int id, @Field("name") String name, @Field("address") String address, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("addOrder")
    Call<VerifyResponse> addOrder(@Field("id") int id, @Field("name") String name, @Field("phone") String phone, @Field("address") String address);


    @POST("updateimages.php")
    @Multipart
    Call<ProductResponse> upload1(@Part("username") RequestBody username, @Part MultipartBody.Part avatar);

//    @POST("updateimages.php")
//    @Multipart
//    Call<List<ImageUpload>> upload(@Part("id") RequestBody id, @Part MultipartBody.Part images);

    @GET("products")
    Call<ProductResponse> getProductByCategoryId(@Query("cid1") String cid1);

    @Multipart
    @POST("products")
    Call<OneProductResponse> addNewProduct(@Part("name") RequestBody pName,
                                        @Part("price") RequestBody pPrice,
                                        @Part("description") RequestBody description,
                                        @Part("quantity") RequestBody quantity,
                                        @Part("categoryId") RequestBody cateId,
                                        @Part("storeId") RequestBody storeId,
                                        @Part MultipartBody.Part image);

    @Multipart
    @PUT("products/{id}")
    Call<OneProductResponse> editStoreProduct(@Path("id") long id,
                                           @Part("name") RequestBody pName,
                                           @Part("price") RequestBody pPrice,
                                           @Part("description") RequestBody description,
                                           @Part("quantity") RequestBody quantity,
                                           @Part("categoryId") RequestBody cateId,
                                           @Part("storeId") RequestBody storeId,
                                           @Part MultipartBody.Part image);
    @Multipart
    @PUT("products/{id}")
    Call<OneProductResponse> editStoreProductWithoutImage(@Path("id") long id,
                                                          @Part("name") RequestBody pName,
                                                          @Part("price") RequestBody pPrice,
                                                          @Part("description") RequestBody description,
                                                          @Part("quantity") RequestBody quantity,
                                                          @Part("categoryId") RequestBody cateId,
                                                          @Part("storeId") RequestBody storeId);

    @DELETE("products/{id}")
    Call<OneProductResponse> deleteProductById(@Path("id") long id);

    @GET("stores")
    Call<StoreResponse> getStoreInfoByUserId(@Query("user") int userId);

    @GET("statistic")
    Call<ThongKeResponse> getStoreStatistic(@Query("store") int storeId);

    @GET("statistic/chart")
    Call<ChartResponse> getChart(@Query("store") int storeId);
}
