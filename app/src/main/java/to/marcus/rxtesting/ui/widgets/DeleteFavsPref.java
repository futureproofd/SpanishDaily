package to.marcus.rxtesting.ui.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * Created by marcus on 10/29/2015
 */
public class DeleteFavsPref extends DialogPreference {

    public DeleteFavsPref(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onClick(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Delete all favorites");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Deleted favorites!", Toast.LENGTH_SHORT).show();
                putPrefValue("key_delete_favs",true);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void putPrefValue(String key, boolean value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        //removes existing key to trigger change listener
        editor.remove(key);
        editor.commit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
