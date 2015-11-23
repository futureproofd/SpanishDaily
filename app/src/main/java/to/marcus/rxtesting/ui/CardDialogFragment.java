package to.marcus.rxtesting.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import to.marcus.rxtesting.R;

/**
 * Created by marcus on 10/5/2015
 */
public class CardDialogFragment extends DialogFragment{
    CardDialogListener mListener;
    public CardDialogFragment(){}

    public interface CardDialogListener{
        void onDialogClickDismiss(CardDialogFragment dialogFragment, String itemId);
        void onDialogClickFavorite(CardDialogFragment dialogFragment, String itemId);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (CardDialogListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement CardDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle args = getArguments();
        final String itemId = args.getString("itemId");
        return new AlertDialog.Builder(getActivity(), R.style.CardDialogTheme)
                .setItems(R.array.dialog_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int option) {
                        switch (option) {
                            case 0:
                                mListener.onDialogClickFavorite(CardDialogFragment.this, itemId);
                                break;
                            case 1:
                                mListener.onDialogClickDismiss(CardDialogFragment.this, itemId);
                                break;
                        }
                    }
                })
                .create();
    }
}
