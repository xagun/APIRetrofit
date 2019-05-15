package com.example.apiretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apiretrofit.apiclient.EmployeeAPI;
import com.example.apiretrofit.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvData;
    private final static String BASEURL="http://dummy.restapiexample.com/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData=findViewById(R.id.tvData);
        loadEmployee();
    }
    private void loadEmployee(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall =employeeAPI.getEmployee();
        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()){
                    tvData.setText("Code :  "+ response.code());
                }
                List<Employee> employeeList =response.body();
                for(Employee employee:employeeList){
                    String content ="";
                    content +="ID: "+employee.getId() + "\n";
                    content +="Employee Name: "+employee.getEmployee_name() + "\n";
                    content +="Employee Age: "+employee.getEmployee_age() + "\n";
                    content +="Employee Salary: "+employee.getEmployee_salary() + "\n";
                    content +="........................."+ "\n";
                    tvData.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                  tvData.setText("Error "+ t.getMessage());
            }
        });
    }
}
