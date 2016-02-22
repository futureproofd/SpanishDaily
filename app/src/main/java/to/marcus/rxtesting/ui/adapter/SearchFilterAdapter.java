package to.marcus.rxtesting.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 12/10/2015
 */
public class SearchFilterAdapter extends ArrayAdapter<Word> implements Filterable {
    private static final String TAG = SearchFilterAdapter.class.getSimpleName();
    private ArrayList<Word> mWordArrayList, mSearchResults;
    Context context;
    int resource, textViewResourceId;
    private final SearchAdapterClickListener clickListener;

    public SearchFilterAdapter(Context context, int resource, int textViewResourceId
            ,ArrayList<Word> wordArrayList, SearchAdapterClickListener listener){
        super(context, resource, textViewResourceId, wordArrayList);
        this.context = context;
        this.mWordArrayList = wordArrayList;
        this.mSearchResults = new ArrayList<>();
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.clickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_row_text,parent,false);
            holder = new ViewHolder();
            holder.txtWord = (TextView)view.findViewById(R.id.search_row_text);
            holder.txtWordEN = (TextView)view.findViewById(R.id.search_row_english);
            holder.imgWord = (ImageView)view.findViewById(R.id.search_row_image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Word w = mSearchResults.get(position);

        if(w != null){
            if(holder.txtWord != null && holder.txtWordEN != null)
                holder.imgWord.getContext();
                Uri uri = Uri.parse(w.getImgUrl());
                Picasso.with(context)
                    .load(uri)
                    .into(holder.imgWord);
                holder.imgWord.setTag(w.getImgUrl());
                holder.txtWord.setText(w.getWord());
                holder.txtWordEN.setText(": " + w.getTranslation());
                holder.imgWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onSearchResultClick(v, (String) holder.imgWord.getTag());
                    }
                });
        }
        return view;
    }

    @Override
    public int getCount() {
        if(mSearchResults == null){
            return 0;
        }else{
            return mSearchResults.size();
        }
    }

    @Override
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Word> values = new ArrayList<>();
                try{
                    for (Word w : mWordArrayList)
                        if (w.getWord().startsWith(constraint.toString().toLowerCase())){
                            values.add(w);
                        }else if(w.getTranslation().startsWith(constraint.toString().toLowerCase())){
                            values.add(w);
                        }
                }catch (NullPointerException n){
                    Log.i(TAG, "no word found");
                }
                results.values = values;
                results.count = values.size();
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results){
                if(results.count == 0){
                    notifyDataSetInvalidated();
                }else{
                    mSearchResults = (ArrayList<Word>)results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    static class ViewHolder{
        TextView txtWord;
        TextView txtWordEN;
        ImageView imgWord;
    }
}
