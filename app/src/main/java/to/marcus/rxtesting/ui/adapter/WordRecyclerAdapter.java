package to.marcus.rxtesting.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/14/2015
 */

public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder>{
    private ArrayList<Word> mWordArrayList;
    private final RecyclerViewItemClickListener clickListener;

    public WordRecyclerAdapter(ArrayList<Word> wordArrayList, RecyclerViewItemClickListener listener){
        this.mWordArrayList = wordArrayList;
        this.clickListener = listener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        final WordViewHolder mViewHolder = new WordViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
           //listen on presenter
            @Override
            public void onClick(View view){
                clickListener.onObjectClick(view, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word word = mWordArrayList.get(position);
        Uri uri = Uri.parse(word.getImgUrl());
        Context context = holder.imageView.getContext();
        Picasso.with(context).load(uri)
                .into(holder.imageView);
        holder.wordView.setText(word.getWord());
        holder.translationView.setText(word.getTranslation());
        holder.exampleENView.setText(word.getExampleEN());
        holder.exampleESPView.setText(word.getExampleESP());
    }

    @Override
    public int getItemCount() {
        return mWordArrayList.size();
    }

    /*
    * ViewHolder Inner Class
    */
    public static class WordViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imgWord)ImageView imageView;
        @Bind(R.id.txtWord)TextView wordView;
        @Bind(R.id.txtTranslation)TextView translationView;
        @Bind(R.id.txtExampleEn)TextView exampleENView;
        @Bind(R.id.txtExampleESP) TextView exampleESPView;

        public WordViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
