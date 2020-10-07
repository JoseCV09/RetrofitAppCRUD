package xyz.yoandroid.retrofitapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.yoandroid.retrofitapp.Adapter.ListViewAdapter;
import xyz.yoandroid.retrofitapp.Interface.JsonPlaceHolderApi;
import xyz.yoandroid.retrofitapp.Model.Employee;
import xyz.yoandroid.retrofitapp.R;
import xyz.yoandroid.retrofitapp.Ruta.APIUtils;

public class MainActivity extends AppCompatActivity {

    JsonPlaceHolderApi jsonPlaceHolderApi;

    private ListView lvEmployee;
    private List<Employee> listEmployee;
    private ListViewAdapter listViewAdapter;

    FloatingActionButton fab;

    private TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Empleados");

        jsonPlaceHolderApi = APIUtils.getInterfaceEmployee();
        lvEmployee = findViewById(R.id.mListView);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        lvEmployee.setOnItemClickListener(clickItem());
        getEmployees();

    }


    private void getEmployees(){
        Call<List<Employee>> call = jsonPlaceHolderApi.getEmployee();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    listEmployee = response.body();
                    lvEmployee.setAdapter(new ListViewAdapter(MainActivity.this, listEmployee));
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private AdapterView.OnItemClickListener clickItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailEmployee.class);
                intent.putExtra("id", String.valueOf(id));
                startActivity(intent);
            }
        };
    }

    private void getEmployeeId(){
        Call<Employee> call = jsonPlaceHolderApi.getEmployeeById(5);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if(response.isSuccessful()){
                    Employee employee = response.body();
                    textViewResult.setText("Empleado: " + employee.getName() +" \n" +
                            "Salary: "+ employee.getSalary());
                } else {
                    Log.e("error", "Hubo un error inesperado!");
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

    }


}