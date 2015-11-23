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
import android.widget.Filter;
import android.widget.Filterable;
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
public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder>
                implements Filterable{
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

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        final WordViewHolder mViewHolder = new WordViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
        //listen on presenter for click types
            @Override
            public void onClick(View view){
                clickListener.onObjectClick(view, (String)mViewHolder.imageView.getTag());
            }
        });
        mViewHolder.cardMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                menuClickListener.onObjectMenuClick(view, (String)mViewHolder.imageView.getTag());
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
                public void onSuccess() {
                    Bitmap bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
                    Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette != null) {
                                    final Palette.Swatch swatch = getSwatch(palette);
                                    holder.wordView.setBackgroundColor(swatch.getRgb());
                                    holder.wordView.setTextColor(swatch.getBodyTextColor());
                                    holder.dateView.setTextColor(swatch.getBodyTextColor());
                                    holder.cardMenu.setColorFilter(palette.getMutedColor(0x000000), PorterDuff.Mode.MULTIPLY);
                                }
                            }
                        });
                }
                @Override
                public void onError() {
                    holder.wordView.setBackgroundColor(0x000000);
                }
            });
        holder.wordView.setText(word.getWord());
        holder.dateView.setText(word.getDate());
        //Set TAG as a unique id, instead of position. This allows updates to the original object,
        //not dependent on DataSet
        holder.imageView.setTag(word.getImgUrl());
    }

    @Override
    public int getItemCount() {
        return mWordArrayList.size();
    }

    public void resetDataSet(ArrayList<Word> words){
        mWordArrayList.clear();
        mWordArrayList.addAll(words);
        notifyDataSetChanged();
    }

    private Palette.Swatch getSwatch(Palette palette){
        final Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
        final Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
        final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
        final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

        Palette.Swatch wordElementColor = (darkMutedSwatch != null)
                ? darkMutedSwatch : darkVibrantSwatch;
        if (wordElementColor == null) {
            wordElementColor = (vibrantSwatch != null) ? vibrantSwatch : lightVibrantSwatch;
        }
        return wordElementColor;
    }

    //Filter implementation: Switch between dataSet types
    @Override
    public Filter getFilter() {
        Filter filter = new Filter(){

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mWordArrayList = (ArrayList<Word>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults(); // holds the results
                ArrayList<Word> filteredArray = new ArrayList<>();

                if(constraint == null || constraint.length() == 0){
                    results.count = mWordArrayList.size();
                    results.values = mWordArrayList;
                }else{
                    String option = constraint.toString();
                    switch(option){
                        case "favorites":
                            for(int i = 0; i < mWordArrayList.size(); i++){
                                Word w = mWordArrayList.get(i);
                                if(w.getFavorite() == 1){
                                    filteredArray.add(w);
                                }
                            }
                            results.count = filteredArray.size();
                            results.values = filteredArray;
                            break;
                        case "dismissed":
                            for(int i = 0; i < mWordArrayList.size(); i++){
                                Word w = mWordArrayList.get(i);
                                if(w.getVisibility() == 0){
                                    filteredArray.add(w);
                                }
                            }
                            results.count = filteredArray.size();
                            results.values = filteredArray;
                            break;
                        case "unfiltered":
                            for(int i = 0; i < mWordArrayList.size(); i++) {
                                Word w = mWordArrayList.get(i);
                                filteredArray.add(w);
                            }
                            results.count = filteredArray.size();
                            results.values = filteredArray;
                            break;
                    }
                }
                return results;
            }
        };
        return filter;
    }

    //ViewHolder Inner Class
    public static class WordViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imgWord) ImageView imageView;
        @Bind(R.id.txtWord) TextView wordView;
        @Bind(R.id.txtDate) TextView dateView;
        @Bind(R.id.card_overflow_menu) ImageView cardMenu;

        public WordViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
