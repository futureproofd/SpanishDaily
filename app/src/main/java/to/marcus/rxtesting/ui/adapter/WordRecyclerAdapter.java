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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/14/2015
 * Custom RecyclerAdapter and ViewHolder to filter across multiple DataSets
 * Custom Layout is determined via getItemViewType
 */
public class WordRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
                implements Filterable{
    private static final String TAG = WordRecyclerAdapter.class.getSimpleName();
    private ArrayList<Word> mWordArrayList;
    private final RecyclerViewItemClickListener clickListener;
    private final RecyclerViewMenuClickListener menuClickListener;
    private String mDataSetMode;
    private final int CARDVIEW = 0;
    private final int SEARCHVIEW = 1;

    public WordRecyclerAdapter(ArrayList<Word> wordArrayList
            ,RecyclerViewItemClickListener listener
            ,RecyclerViewMenuClickListener menuListener){
        this.mWordArrayList = wordArrayList;
        this.clickListener = listener;
        this.menuClickListener = menuListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View searchView = inflater.inflate(R.layout.search_history_layout, parent, false);
        final SearchHistoryViewHolder searchHistoryViewHolder = new SearchHistoryViewHolder(searchView);
        View cardView = inflater.inflate(R.layout.card_view_layout, parent, false);
        final CardViewHolder cardViewHolder = new CardViewHolder(cardView);

        int result = 0;
        switch (viewType){
            case SEARCHVIEW:
                result = 1;
                searchView.setOnClickListener(new View.OnClickListener() {
                    //listen on activity for clicks
                    @Override
                    public void onClick(View view) {
                        clickListener.onObjectClick(view, (String) searchHistoryViewHolder.imageView.getTag());
                    }
                });
                break;
            case CARDVIEW:
                cardView.setOnClickListener(new View.OnClickListener() {
                    //listen on activity for clicks
                    @Override
                    public void onClick(View view) {
                        clickListener.onObjectClick(view, (String) cardViewHolder.imageView.getTag());
                    }
                });
                cardViewHolder.cardMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        menuClickListener.onObjectMenuClick(view, (String) cardViewHolder.imageView.getTag());
                    }
                });
                break;
        }
        if(result == 1){
            return searchHistoryViewHolder;
        }else{
            return cardViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
       switch (this.getItemViewType(position)){
           case SEARCHVIEW:
                SearchHistoryViewHolder searchHistoryViewHolder = (SearchHistoryViewHolder) holder;
                configureSearchViewHolder(searchHistoryViewHolder, position);
                break;
           case CARDVIEW:
                CardViewHolder cardViewHolder = (CardViewHolder) holder;
                configureCardViewHolder(cardViewHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(mWordArrayList.get(position).getSearched() == 1 && mDataSetMode == "search"){
            return SEARCHVIEW;
        }else{
            return CARDVIEW;
        }
    }

    private void configureSearchViewHolder(SearchHistoryViewHolder holder, int position){
        Word word = mWordArrayList.get(position);
        Uri uri = Uri.parse(word.getImgUrl());
        Context context = holder.imageView.getContext();
        Picasso.with(context)
                .load(uri)
                .into(holder.imageView);
        //Set TAG as a unique id, instead of position. This allows updates to the original object,
        //independent of DataSet
        holder.imageView.setTag(word.getImgUrl());
        holder.textView.setText(word.getWord());
    }

    private void configureCardViewHolder(final CardViewHolder holder, int position){
        Word word = mWordArrayList.get(position);
        Uri uri = Uri.parse(word.getImgUrl());
        Context context = holder.imageView.getContext();
        Picasso.with(context)
                .load(uri)
                //todo use a spinner or something here
                .placeholder(R.drawable.ic_history_grey600_18dp)
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
        //independent of DataSet
        holder.imageView.setTag(word.getImgUrl());
    }

    @Override
    public int getItemCount() {
        return mWordArrayList.size();
    }

    public void removeItem(String itemId){
        for(Word word : mWordArrayList) {
            if (word.getImgUrl().equals(itemId)) {
                mWordArrayList.remove(word);
                notifyItemRemoved(mWordArrayList.indexOf(word));
            }
        }
    }

    public void resetDataSet(ArrayList<Word> words){
        mWordArrayList.clear();
        mWordArrayList.addAll(words);
        notifyDataSetChanged();
    }

    public void setDataSetMode(String mode){
        this.mDataSetMode = mode;
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
                            break;
                        case "dismissed":
                            for(int i = 0; i < mWordArrayList.size(); i++){
                                Word w = mWordArrayList.get(i);
                                if(w.getVisibility() == 0){
                                    filteredArray.add(w);
                                }
                            }
                            break;
                        case "unfiltered":
                            for(int i = 0; i < mWordArrayList.size(); i++) {
                                Word w = mWordArrayList.get(i);
                                if(w.getVisibility() == 1){
                                    filteredArray.add(w);
                                }
                            }
                            break;
                        case "search":
                            for(int i = 0; i < mWordArrayList.size(); i++){
                                Word w = mWordArrayList.get(i);
                                if(w.getSearched() == 1){
                                    filteredArray.add(w);
                                }
                            }
                            break;
                    }
                    results.count = filteredArray.size();
                    results.values = filteredArray;
                }
                return results;
            }
        };
        return filter;
    }
}
