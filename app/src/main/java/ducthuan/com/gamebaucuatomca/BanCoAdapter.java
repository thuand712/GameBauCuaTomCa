package ducthuan.com.gamebaucuatomca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BanCoAdapter extends RecyclerView.Adapter<BanCoAdapter.ViewHolder>{

    Context context;
    ArrayList<Integer>dsHinhConVat;
    Integer[]giatien = {0,100,200,300,400,500};
    ArrayAdapter<Integer>adapter;

    public BanCoAdapter(Context context, ArrayList<Integer> dsHinhConVat) {
        this.context = context;
        this.dsHinhConVat = dsHinhConVat;
        adapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item,giatien);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_banco,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imgConVat.setImageResource(dsHinhConVat.get(position));
        holder.spChonDiem.setAdapter(adapter);
        holder.spChonDiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positionSP, long id) {

                MainActivity.giatienDatCuoc[position] = giatien[positionSP];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dsHinhConVat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgConVat;
        Spinner spChonDiem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgConVat = itemView.findViewById(R.id.imgConVat);
            spChonDiem = itemView.findViewById(R.id.spChonDiem);
        }
    }
}
