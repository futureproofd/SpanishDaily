package to.marcus.rxtesting.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import to.marcus.rxtesting.R;

/**
 * Created by marcus on 10/16/2015.
 */
public class AnimationUtility {

    //TODO: animation stuff that doesn't work yet
    public void animateTransition(View view, Intent intent, Context context){
        ImageView wordImage = (ImageView)view.findViewById(R.id.imgWord);
        LinearLayout wordString = (LinearLayout)view.findViewById(R.id.wordNameHolder);

     //   String transitionImgName = this.getString(R.string.transition_img);
     //   String transitionWordName = this.getString(R.string.transition_word);
     //   Pair<View, String> p1 = Pair.create((View) wordImage, transitionImgName);
     //   Pair<View, String> p2 = Pair.create((View) wordString, transitionWordName);

        /* Do this stuff in activity
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(HomeActivity.this,p1,p2);
        ActivityCompat.startActivity(this, intent, options.toBundle());
        */
    }
}
