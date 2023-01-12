package com.axepert.kheloindiaqrscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axepert.kheloindiaqrscanner.databinding.ItemImageBinding;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FOPAdapter extends RecyclerView.Adapter<FOPAdapter.FOPViewHolder>{
    List<ScanResponse.Data.Access> accessList;
    Context context;

    public FOPAdapter(List<ScanResponse.Data.Access> accessList, Context context) {
        this.accessList = accessList;
        this.context = context;
    }

    @NonNull
    @Override
    public FOPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FOPViewHolder(
                ItemImageBinding.inflate(LayoutInflater.from(context),
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FOPViewHolder holder, int position) {
        holder.setData(accessList.get(position));
    }

    @Override
    public int getItemCount() {
        return accessList.size();
    }

    class FOPViewHolder extends RecyclerView.ViewHolder {
        ItemImageBinding binding;
        PreferenceManager preferenceManager = new PreferenceManager(context);

        FOPViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ScanResponse.Data.Access access) {
            Picasso.get().load(access.getImage())
                    .into(binding.imgFOP);
            binding.tvFOP.setText(access.getText());
        }
    }

}
