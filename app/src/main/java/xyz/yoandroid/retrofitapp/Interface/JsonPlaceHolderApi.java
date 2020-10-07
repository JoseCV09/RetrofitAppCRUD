package xyz.yoandroid.retrofitapp.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import xyz.yoandroid.retrofitapp.Model.Employee;

public interface JsonPlaceHolderApi {

    @GET("employee")
    Call<List<Employee>> getEmployee();

    @GET("employee/{id}")
    Call<Employee> getEmployeeById(@Path("id") int id);

    @POST("employee/insert")
    Call<Employee> insertEmployee(@Body Employee employee);

    @PUT("employee/update/{id}")
    Call<Employee> updateEmployee(@Path("id") int id, @Body Employee employee);

    @DELETE("employee/delete/{id}")
    Call<Employee> deleteEmployee(@Path("id") int id);

}
