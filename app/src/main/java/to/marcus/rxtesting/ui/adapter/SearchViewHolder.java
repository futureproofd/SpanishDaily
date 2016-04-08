package to.marcus.rxtesting.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.R;

/**
 * Created by marcus on 12/3/2015
 */
public class SearchViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.search_history_image)ImageView imageView;
    @Bind(R.id.search_history_text)TextView textView;
    @Bind(R.id.search_history_logo)ImageView logoView;

    public SearchViewHolder(View v){
        super(v);
        ButterKnife.bind(this, v);
    }

    public TextView getText(){
        return textView;
    }

    public void setText(TextView text){
        this.textView = text;
    }

}
