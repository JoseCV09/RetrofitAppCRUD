package xyz.yoandroid.retrofitapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.yoandroid.retrofitapp.Interface.JsonPlaceHolderApi;
import xyz.yoandroid.retrofitapp.Model.Employee;
import xyz.yoandroid.retrofitapp.R;

public class CreateActivity extends AppCompatActivity {

    EditText etName, etSalary;
    ProgressDialog progressDialog;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create Empleados");

        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://node-api-mysql.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                Integer salary = Integer.parseInt(etSalary.getText().toString().trim());
                createEmployee(name, salary);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    private void createEmployee(final String name, final int salary) {
        progressDialog.show();
        Employee employee = new Employee(name,salary);

        Call<Employee> call = jsonPlaceHolderApi.insertEmployee(employee);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                progressDialog.dismiss();
                if(!response.isSuccessful()){
                    Toast.makeText(CreateActivity.this, "Failed",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent=new Intent(CreateActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateActivity.this, "Dato Insertado!",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CreateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}