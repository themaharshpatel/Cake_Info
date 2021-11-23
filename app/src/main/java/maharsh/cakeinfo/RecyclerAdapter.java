package maharsh.cakeinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maharsh.cakeinfo.databinding.CakeItemBinding;
import maharsh.cakeinfo.datamodel.CakeDataModel;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {


    List<CakeDataModel> cakes;
    private OnItemClickListener listener;

    public RecyclerAdapter(List<CakeDataModel> cakes) {
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        CakeDataModel cake = cakes.get(position);
        Picasso.get().load(cake.getImageURL()).placeholder(R.drawable.placeholder).into(holder.binding.cakeImage);
        holder.binding.cakeTitle.setText(cake.getTitle());
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClicked(CakeDataModel cakeDataModel);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        CakeItemBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CakeItemBinding.bind(itemView);
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null || getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.onClicked(cakes.get(getAdapterPosition()));
            });
        }
    }

}
