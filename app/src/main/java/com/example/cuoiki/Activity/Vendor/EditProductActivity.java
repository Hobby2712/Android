package com.example.cuoiki.Activity.Vendor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cuoiki.Model.Categories;
import com.example.cuoiki.Model.KeyValueCategories;
import com.example.cuoiki.Model.Store;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.Category2Response;
import com.example.cuoiki.Response.OneProductResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.RealPathUtil;
import com.example.cuoiki.Utils.contants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {

    List<KeyValueCategories> categories = new ArrayList<>();
    EditText pName, pPrice, description, quantity;
    TextView btnChooseImg;
    ImageView imgUpload, btnBack;
    Button btnEdit;
    Uri mUri;
    AutoCompleteTextView categoriesList;
    ArrayAdapter<KeyValueCategories> adapterItems;
    private static final int REQUEST_CODE = 1;
    String categorySelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_editp);

        anhXa();
        getListCategories2();
        adapterItems = new ArrayAdapter<>(this, R.layout.list_item, categories);
        categoriesList.setAdapter(adapterItems);
        Log.e("edit product", "productid: " + getIntent().getSerializableExtra("id"));
        Log.e("edit product", "storeid: " + SharedPrefManager.getInstance(this).getStoreInfo());
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                KeyValueCategories selectedKeyValue = (KeyValueCategories) adapterView.getItemAtPosition(position);
                categorySelected = selectedKeyValue.getKey();
            }
        });

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUri != null && categorySelected != null){
                    uploadImage();
                } else if(categorySelected == null){
                    Toast.makeText(EditProductActivity.this, "Hãy chọn danh mục", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditProductActivity.this, "Hãy chọn hình ảnh sản phẩm", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProductActivity.this, ManageProductActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void uploadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }

        final String name = pName.getText().toString();
        final String price = pPrice.getText().toString();
        final String pQuantity = quantity.getText().toString();
        final String pDescription = description.getText().toString();

        if (TextUtils.isEmpty(name)){
            pName.setError("Vui lòng nhập tên sản phẩm");
            pName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(price)){
            pPrice.setError("Vui lòng nhập giá sản phẩm");
            pPrice.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pQuantity)){
            quantity.setError("Vui lòng nhập số lượng nhập kho");
            quantity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pDescription)){
            description.setError("Vui lòng nhập mô tả sản phẩm");
            description.requestFocus();
            return;
        }

        Store store = SharedPrefManager.getInstance(this).getStoreInfo();
        RequestBody storeId = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                Integer.toString(store.getId()));

        RequestBody cateId = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                categorySelected);
        RequestBody productName = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                name);
        RequestBody productPrice = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                price);
        RequestBody productQuantity = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                pQuantity);
        RequestBody productDescription = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                pDescription);

        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
        Log.e("ffff", IMAGE_PATH);
        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                file);
        MultipartBody.Part partBodyImage = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RetrofitClient.getInstance()
                .getRetrofit(contants.URL_PRODUCT2)
                .create(APIService.class)
                .addNewProduct(
                        productName,
                        productPrice,
                        productDescription,
                        productQuantity,
                        cateId,
                        storeId,
                        partBodyImage
                )
                .enqueue(new Callback<OneProductResponse>() {
            @Override
            public void onResponse(Call<OneProductResponse> call, Response<OneProductResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().isError()){
                        Toast.makeText(EditProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<OneProductResponse> call, Throwable t) {
                Toast.makeText(EditProductActivity.this, "Gọi API thất bại", Toast.LENGTH_SHORT).show();

                Log.d(EditProductActivity.class.getName(), t.toString());
            }
        });
    }

    private void getListCategories2(){
        RetrofitClient.getInstance()
                .getRetrofit(contants.URL_CATEGORY)
                .create(APIService.class)
                .getAllCategories2().enqueue(new Callback<Category2Response>() {
                    @Override
                    public void onResponse(Call<Category2Response> call, Response<Category2Response> response) {
                        if(response.isSuccessful()){
                            List<Categories> categoryList = response.body().getData().getCategories2();
                            for (Categories category : categoryList) {
                                categories.add(new KeyValueCategories(Integer.toString(category.getId()), category.getName()));
                            }
                        }else{
                            int statusCode= response.code();
                            Log.d("logg",Integer.toString(statusCode));
                        }
                    }

                    @Override
                    public void onFailure(Call<Category2Response> call, Throwable t) {
                        Log.d("logg",t.getMessage());
                    }
                });
    }

    private void anhXa(){
        pName = findViewById(R.id.etEditName);
        pPrice = findViewById(R.id.etEditPrice);
        quantity = findViewById(R.id.etEditQuantity);
        description = findViewById(R.id.etEditDescription);
        btnChooseImg = findViewById(R.id.choose_edit_image);
        imgUpload = findViewById(R.id.ivEditUploaded);
        categoriesList = findViewById(R.id.category_choose);
        btnBack = findViewById(R.id.iv_edit_back);
        btnEdit = findViewById(R.id.editProductBtn);
    }
    public static String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    };

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("Profile Tag", "OnActivityResult");
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            imgUpload.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public static String[] permissions() {
        String[] p;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) p = storage_permissions_33;
        else p = storage_permissions;
        return p;
    }
    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
}
