package com.mzfajar.tugasakhir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mzfajar.tugasakhir.R;
import com.mzfajar.tugasakhir.model.QuranModel;

import java.util.List;

public class QuranAdapter extends SortedListRecyclerViewAdapter<QuranModel, QuranAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quran_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(getData().get(position));
    }

    @Override
    protected Class<QuranModel> getItemClass() {
        return QuranModel.class;
    }

    @Override
    protected int compare(QuranModel item1, QuranModel item2) {
        return (int) (item1.getDistance() - item2.getDistance());
    }

    public void addOrUpdate(List<QuranModel> quranList) {
        for (QuranModel quran : quranList) {
            int index = findPosition(quran);
            if (index == -1) {
                getData().add(quran);
            } else {
                getData().updateItemAt(index, quran);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

        public void onBind(QuranModel quranModel) {
            tvItemTarget.setText(quranModel.getTarget()); // + "  :  " + quranModel.getPanjTar());
            tvItemTeks.setText(quranModel.getText());
            tvItemAyat.setText(", Ayat : " + String.valueOf(quranModel.getAyat()) + ")");
            tvItemJarak.setText("(Distance : " + quranModel.getDistance() + ")");
            tvItemSurat.setText("(" + quranModel.getNamaSura());
        }
    }
}
