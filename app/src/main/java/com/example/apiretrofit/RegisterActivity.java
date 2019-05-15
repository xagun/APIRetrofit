package com.example.apiretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiretrofit.apiclient.EmployeeAPI;
import com.example.apiretrofit.model.EmployeeCUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private final String BASE_URL="http://dummy.restapiexample.com/api/v1/";

    private EditText txtname,txtsalary,txtage;
    private Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname=findViewById(R.id.txtname);
        txtsalary=findViewById(R.id.txtsalary);
        txtage=findViewById(R.id.txtage);
        btnregister=findViewById(R.id.btnregister);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Register();
                }
            }
        });
    }

    private void Register() {
        String name=txtname.getText().toString();
        Float salary=Float.parseFloat(txtsalary.getText().toString());
        int age=Integer.parseInt(txtage.getText().toString());

        EmployeeCUD employee = new EmployeeCUD(name,salary,age);

        Retrofit retrofit = new Retrofit.Builder()
                          .baseUrl(BASE_URL)
                          .addConverterFactory(GsonConverterFactory.create())
                          .build();

        EmployeeAPI employeeAPI =retrofit.create(EmployeeAPI.class);

        Call<Void> voidCall = employeeAPI.registerEmployee(employee);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Error"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validate(){
        boolean valid=true;
        if(TextUtils.isEmpty(txtname.getText())){
            txtname.setError("Required");
            valid=false;
        }
        if(TextUtils.isEmpty(txtsalary.getText())){
            txtsalary.setError("Required");
            valid=false;
        }
        if(TextUtils.isEmpty(txtage.getText())){
            txtage.setError("Required");
            valid=false;
        }


        return valid;

    }
}
