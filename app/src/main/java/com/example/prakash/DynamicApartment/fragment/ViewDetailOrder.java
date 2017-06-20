package com.example.prakash.DynamicApartment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.DynamicApartment.Pojo.Order_unit_data;
import com.example.prakash.DynamicApartment.Public_url;
import com.example.prakash.DynamicApartment.R;
import com.example.prakash.DynamicApartment.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 4/12/2017.
 */

public class ViewDetailOrder extends Fragment{
    View mainView;
    String userid,orderNo;
    String url = Public_url.viewOrder;

    Order_unit_data data;
    ArrayList<Order_unit_data> list;

    OrderUnit adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String unitName,quantity;

    TextView mOrdertotal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.view_detail_layout,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Order detail");



        recyclerView=(RecyclerView)mainView.findViewById(R.id.order_units);
        linearLayoutManager=new LinearLayoutManager(getContext());

        mOrdertotal=(TextView)mainView.findViewById(R.id.ordertotal);






        getData();



        return mainView;
    }

    void getData() {

        JsonArrayRequest request = new JsonArrayRequest(url+userid, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                list=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);



                        String orderno=obj.getString("order_number");
                        String ordertotal=obj.getString("total");





                        if (orderno.equals(orderNo))

                        {

                            mOrdertotal.setText("Total :"+ordertotal);




                            JSONArray array = obj.getJSONArray("line_items");




                            for (int ii = 0; ii < array.length(); ii++) {
                                JSONObject obj1 = array.getJSONObject(ii);

                                unitName = obj1.getString("line_item_label");
                                 quantity = obj1.getString("quantity");


                                data = new Order_unit_data(unitName, quantity);
                                list.add(data);


                                adapter = new OrderUnit(getContext(), list);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(linearLayoutManager);


                            }




                        }



                        Toast.makeText(getContext(), ""+unitName+"  "+quantity, Toast.LENGTH_SHORT).show();





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Network problem", Toast.LENGTH_SHORT).show();




            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Detail order");
    }
}


class OrderUnit extends RecyclerView.Adapter<OrderUnitHolder> {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Order_unit_data> list = new ArrayList<>();


    public OrderUnit(Context context, ArrayList<Order_unit_data> list) {


        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);


    }

    @Override
    public OrderUnitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.view_detail_order_singel_desigen,parent,false);
        OrderUnitHolder holder=new OrderUnitHolder(view,context,list);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderUnitHolder holder, int position) {
        Order_unit_data data=list.get(position);

        holder.unitname.setText(data.getUnitname());
        holder.quantity.setText(data.getUnitQuantity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class OrderUnitHolder extends RecyclerView.ViewHolder {

    TextView unitname;
    TextView quantity;

    ArrayList<Order_unit_data> list;
    Context context;


    public OrderUnitHolder(View itemView,Context context,ArrayList<Order_unit_data> list) {
        super(itemView);
        this.list=list;
        this.context=context;


        unitname = (TextView) itemView.findViewById(R.id.order_unit);
        quantity = (TextView) itemView.findViewById(R.id.order_quantity);


    }
}