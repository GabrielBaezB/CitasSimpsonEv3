package cl.inacap.citassimpson.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.citassimpson.R;
import cl.inacap.citassimpson.dto.Cita;

public class CitasListAdapter extends ArrayAdapter<Cita> {

    private List<Cita> citas;
    private Activity contexto;


    public CitasListAdapter(@NonNull Activity context, int resource, @NonNull List<Cita> objects) {
        super(context, resource, objects);
        this.citas = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  this.contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_citas, null, true);
        TextView nombreTxt = rowView.findViewById(R.id.name_personaje_lv);
        TextView fraseTxt = rowView.findViewById(R.id.frase_personaje_lv);
        ImageView imgPer = rowView.findViewById(R.id.img_personaje_lv);
        nombreTxt.setText(this.citas.get(position).getCharacter());
        fraseTxt.setText(this.citas.get(position).getQuote());
        Picasso.get().load(this.citas.get(position).getImage())
                .resize(200,200)
                .into(imgPer);
        return rowView;




    }
}
