package com.axepert.kheloindiaqrscanner.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axepert.kheloindiaqrscanner.databinding.ItemImageBinding;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;

import java.util.List;

public class FOPAdapter extends RecyclerView.Adapter<FOPAdapter.FOPViewHolder> {
    List<ScanResponse.Data.Access> accessList;

    public FOPAdapter(List<ScanResponse.Data.Access> accessList) {
        this.accessList = accessList;
    }

    @NonNull
    @Override
    public FOPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FOPViewHolder(
                ItemImageBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
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

    static class FOPViewHolder extends RecyclerView.ViewHolder {
        ItemImageBinding binding;

        FOPViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ScanResponse.Data.Access access) {
            binding.tvFOP.setText(access.getText());
        }
    }

}
