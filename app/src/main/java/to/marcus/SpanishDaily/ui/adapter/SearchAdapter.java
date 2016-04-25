package to.marcus.SpanishDaily.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.SpanishDaily.R;
import to.marcus.SpanishDaily.model.Word;

/**
 * Created by marcus on 12/4/2015
 *
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable{
    private final ArrayList<Word> mWordArrayList;
    private ArrayList<Word> mSearchResults;
    private final RecyclerViewItemClickListener clickListener;
    private final int layout;
    private static final String TAG = SearchAdapter.class.getSimpleName();

    public SearchAdapter(ArrayList<Word> wordArrayList, int layout
            ,RecyclerViewItemClickListener listener){
        this.mWordArrayList = wordArrayList;
        this.mSearchResults = new ArrayList<>();
        this.layout = layout;
        this.clickListener = listener;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(layout, parent, false);
        final SearchViewHolder mSearchViewHolder = new SearchViewHolder(convertView);
        mSearchViewHolder.searchText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickListener.onObjectClick(v, (String)mSearchViewHolder.searchText.getTag());
                Log.i(TAG, "clicked" + mSearchViewHolder.searchText.getTag());
            }
        });
        return mSearchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        holder.searchText.setText(mSearchResults.get(position).getWord());
        holder.searchTextEN.setText(" " + "(" + mSearchResults.get(position).getTranslation() + ")");
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Word> values = new ArrayList<>();
                for (Word w : mWordArrayList)
                    if (w.getWord().startsWith(constraint.toString().toLowerCase())){
                        values.add(w);
                    }else if(w.getTranslation().startsWith(constraint.toString().toLowerCase())){
                        values.add(w);
                    }
                results.values = values;
                results.count = values.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results){
                mSearchResults = (ArrayList<Word>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.search_row_text) TextView searchText;
        @Bind(R.id.search_row_english) TextView searchTextEN;

        public SearchViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
