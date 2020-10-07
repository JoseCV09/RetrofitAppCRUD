package xyz.yoandroid.retrofitapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.yoandroid.retrofitapp.Model.Employee;
import xyz.yoandroid.retrofitapp.R;

public class ListViewAdapter extends BaseAdapter {

    private List<Employee> employees;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employees.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = layoutInflater.inflate(R.layout.item_employee, parent,
                false);
        Employee employee = employees.get(position);

        TextView txtName = itemView.findViewById(R.id.txtName);
        TextView txtSalary = itemView.findViewById(R.id.txtSalary);

        txtName.setText(employee.getName());
        txtSalary.setText("S/."+Integer.toString(employee.getSalary()));

        return itemView;
    }
}
