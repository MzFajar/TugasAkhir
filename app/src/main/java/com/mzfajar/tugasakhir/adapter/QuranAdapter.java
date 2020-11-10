package com.mzfajar.tugasakhir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.mzfajar.tugasakhir.R;
import com.mzfajar.tugasakhir.model.QuranModel;

import java.util.List;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {
    private SortedList<QuranModel> quranList;

    public QuranAdapter(){
        quranList = new SortedList<QuranModel>(QuranModel.class, new SortedList.Callback<QuranModel>() {
            @Override
            public int compare(QuranModel o1, QuranModel o2) {
                return String.valueOf(o1.getDistance()).compareTo(String.valueOf(o2.getDistance()));
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(QuranModel oldItem, QuranModel newItem) {
                return oldItem.getDistance() == newItem.getDistance();
            }

            @Override
            public boolean areItemsTheSame(QuranModel item1, QuranModel item2) {
                return item1.getDistance() == item2.getDistance();
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition,toPosition);
            }
        });
    }

    public void addAll(List<QuranModel> quranModels){
        quranList.beginBatchedUpdates();
        for(int i = 0; i< quranModels.size(); i++){
            quranList.add(quranModels.get(i));
        }
        quranList.endBatchedUpdates();
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
            tvItemTarget.setText(quranModel.getTarget()); // + "  :  " + quranModel.getPanjTar());
            tvItemTeks.setText(quranModel.getText());
            tvItemAyat.setText(", Ayat : " + String.valueOf(quranModel.getAyat()) + ")");
            tvItemJarak.setText("(Distance : " + quranModel.getDistance() + ")");
            tvItemSurat.setText("(" + quranModel.getNamaSura());
        }
    }
}
