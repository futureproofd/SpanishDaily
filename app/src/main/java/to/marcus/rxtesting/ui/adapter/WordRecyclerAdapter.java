package to.marcus.rxtesting.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
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
    private final RecyclerViewMenuClickListener menuClickListener;

    public WordRecyclerAdapter(ArrayList<Word> wordArrayList
            ,RecyclerViewItemClickListener listener
            ,RecyclerViewMenuClickListener menuListener){
        this.mWordArrayList = wordArrayList;
        this.clickListener = listener;
        this.menuClickListener = menuListener;
    }

    public void swapDataSet(ArrayList<Word> wordArrayList){
        mWordArrayList.clear();
        mWordArrayList.addAll(wordArrayList);
        notifyDataSetChanged();
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        final WordViewHolder mViewHolder = new WordViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
        //listen on presenter for click types
            @Override
            public void onClick(View view){
                clickListener.onObjectClick(view, mViewHolder.getAdapterPosition());
            }
        });
        mViewHolder.cardMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                menuClickListener.onObjectMenuClick(view, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {
        Word word = mWordArrayList.get(position);
        Uri uri = Uri.parse(word.getImgUrl());
        Context context = holder.imageView.getContext();
        Picasso.with(context)
                .load(uri)
                .into(holder.imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess(){
                        Bitmap bitmap = ((BitmapDrawable)holder.imageView.getDrawable()).getBitmap();
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        int mutedColor = palette.getMutedColor(holder.wordView.getContext().getResources().getColor(android.R.color.black));
                                        holder.wordView.setBackgroundColor(mutedColor);
                                        holder.cardMenu.setColorFilter(mutedColor, PorterDuff.Mode.MULTIPLY);
                                    }
                                });
                    }
                    @Override
                    public void onError() {
                        holder.wordView.setBackgroundColor(holder.wordView.getContext().getResources().getColor(android.R.color.black));
                    }
                });
        holder.wordView.setText(word.getWord());
        holder.dateView.setText(word.getDate());
    }

    @Override
    public int getItemCount() {
        return mWordArrayList.size();
    }

    /*
    * ViewHolder Inner Class
    */
    public static class WordViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imgWord) ImageView imageView;
        @Bind(R.id.txtWord) TextView wordView;
        @Bind(R.id.txtDate) TextView dateView;
        @Bind(R.id.card_overflow_menu) ImageView cardMenu;

        public WordViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
