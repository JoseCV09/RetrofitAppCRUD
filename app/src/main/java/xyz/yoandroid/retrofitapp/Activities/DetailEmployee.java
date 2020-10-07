package xyz.yoandroid.retrofitapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.yoandroid.retrofitapp.Interface.JsonPlaceHolderApi;
import xyz.yoandroid.retrofitapp.Model.Employee;
import xyz.yoandroid.retrofitapp.R;
import xyz.yoandroid.retrofitapp.Ruta.APIUtils;

public class DetailEmployee extends AppCompatActivity {

    ProgressDialog progressDialog;
    String id;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Employee employee;
    EditText etNombre, etSalary;
    Button btn_update, btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employee);

        setTitle("Actualizar Empleados");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");

        id = this.getIntent().getStringExtra("id");

        jsonPlaceHolderApi = APIUtils.getInterfaceEmployee();
        employee = new Employee();

        etNombre = findViewById(R.id.editName);
        etSalary = findViewById(R.id.editSalary);


        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValuesEmployees();
            }
        });

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValuesEmpaloyees();
            }
        });

        getValuesEditText();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    private void getValuesEditText() {
        Call<Employee> call = jsonPlaceHolderApi.getEmployeeById(Integer.parseInt(id));
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    etNombre.setText(response.body().getName());
                    etSalary.setText(Integer.toString(response.body().getSalary()));
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.e("Empleado", t.getMessage());

            }
        });
    }


    private void updateValuesEmployees() {
        progressDialog.show();
        employee.setName(etNombre.getText().toString());
        employee.setSalary(Integer.parseInt(etSalary.getText().toString()));

        Call<Employee> call = jsonPlaceHolderApi.updateEmployee(Integer.parseInt(id),employee);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.e("Empleado", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(DetailEmployee.this, "Empleado Actualizado Correctamente!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (DetailEmployee.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteValuesEmpaloyees(){
        progressDialog.show();
        Call<Employee> call = jsonPlaceHolderApi.deleteEmployee(Integer.parseInt(id));
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                progressDialog.dismiss();
                Toast.makeText(DetailEmployee.this, "Empleado Eliminado con exito.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (DetailEmployee.this, MainActivity.class);startActivity(intent);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.e("Empleado", t.getMessage());
            }
        });
    }

}