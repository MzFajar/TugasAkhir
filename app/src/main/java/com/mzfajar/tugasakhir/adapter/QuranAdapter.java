package com.mzfajar.tugasakhir.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mzfajar.tugasakhir.R;
import com.mzfajar.tugasakhir.model.QuranModel;

import java.util.Collections;
import java.util.List;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {
    private List<QuranModel> quranList;

    public void setData(List<QuranModel> quranList){
        this.quranList = quranList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quran_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(quranList.get(position));
    }

    @Override
    public int getItemCount() {
        return quranList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvItemSurat;
        private final TextView tvItemAyat;
        private final TextView tvItemTeks;
        private final TextView tvItemJarak;
        private final TextView tvItemTarget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemAyat = itemView.findViewById(R.id.tvItemAyat);
            tvItemTeks = itemView.findViewById(R.id.tvItemTeks);
            tvItemSurat = itemView.findViewById(R.id.tvItemSurat);
            tvItemJarak = itemView.findViewById(R.id.tvItemJarak);
            tvItemTarget = itemView.findViewById(R.id.tvItemTarget);
        }

        public void onBind(QuranModel quranModel){
            tvItemTarget.setText(quranModel.getTarget()+ "  :  " + quranModel.getPanjTar());
            tvItemTeks.setText(quranModel.getText());
            tvItemAyat.setText(", Ayat : " + String.valueOf(quranModel.getAyat()) + ")");
            tvItemJarak.setText("(Distance : " + quranModel.getDistance() + ")");
            tvItemSurat.setText("(" + quranModel.getNamaSura());
        }
    }
}
