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

//ViewHolder Inner Class
public class CardViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.imgWord) ImageView imageView;
    @Bind(R.id.txtWord) TextView wordView;
    @Bind(R.id.txtDate) TextView dateView;
    @Bind(R.id.card_overflow_menu) ImageView cardMenu;

    public CardViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

}