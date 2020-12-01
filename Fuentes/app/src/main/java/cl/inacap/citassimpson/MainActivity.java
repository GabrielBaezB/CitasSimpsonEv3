package cl.inacap.citassimpson;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.citassimpson.adapters.CitasListAdapter;
import cl.inacap.citassimpson.dto.Cita;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private List<Cita> citas = new ArrayList<>();
    private CitasListAdapter adaptador;
    private ListView listViewCitas;
    private Spinner spinner;
    private Button botonConsulta;
    private Integer[] citaSolicitadas = {1,2,3,4,5,6,7,8,9,10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume(){
        super.onResume();

        this.listViewCitas = findViewById(R.id.list_citas_view);
        this.adaptador = new CitasListAdapter(this, R.layout.list_citas, this.citas);
        this.listViewCitas.setAdapter(this.adaptador);
        this.spinner =findViewById(R.id.spinner);
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,citaSolicitadas);
       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        this.botonConsulta = findViewById(R.id.btnConsulta);
        this.botonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(MainActivity.this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, "https://thesimpsonsquoteapi.glitch.me/quotes?count="
                        + spinner.getSelectedItem().toString()
                        , null
                        , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                                citas.clear();
                                Cita[] citaArray = new Gson()
                                        .fromJson(response.toString(), Cita[].class);
                                citas.addAll(Arrays.asList(citaArray));

                        } catch (Exception e) {
                            citas = null;

                        } finally {
                            adaptador.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        citas = null;
                        adaptador.notifyDataSetChanged();
                    }
                });
                queue.add(jsonArrayRequest);
            }
        });
    }


}