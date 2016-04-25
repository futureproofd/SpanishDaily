package to.marcus.SpanishDaily.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.SpanishDaily.R;

/**
 * Created by marcus on 4/11/2016
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.empty_text_view)     TextView emptyText;

    public EmptyViewHolder(View v){
        super(v);
        ButterKnife.bind(this, v);
    }
}

