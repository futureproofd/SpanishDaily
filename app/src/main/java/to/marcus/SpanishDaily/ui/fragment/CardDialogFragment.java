package to.marcus.SpanishDaily.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import to.marcus.SpanishDaily.R;

/**
 * Created by marcus on 10/5/2015
 */
public class CardDialogFragment extends DialogFragment{
    private CardDialogListener mListener;
    public CardDialogFragment(){}

    public interface CardDialogListener{
        void onDialogClickDismiss(CardDialogFragment dialogFragment, String itemId, String dataSet);
        void onDialogClickModifyProperty(CardDialogFragment dialogFragment, String itemId, String dataSet);
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final String itemId = args.getString("itemId");
        final String dataSet = args.getString("dataSetMode");

        switch (dataSet != null ? dataSet : "unfiltered") {
            case "unfiltered":
                return new AlertDialog.Builder(getActivity(), R.style.CardDialogTheme)
                        .setItems(R.array.dialog_main_options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                switch (option) {
                                    case 0:
                                        mListener.onDialogClickModifyProperty(CardDialogFragment.this, itemId, dataSet);
                                        break;
                                    case 1:
                                        mListener.onDialogClickDismiss(CardDialogFragment.this, itemId, dataSet);
                                        break;
                                }
                            }
                        })
                        .create();

            case "favorites":
                return new AlertDialog.Builder(getActivity(), R.style.CardDialogTheme)
                    .setItems(R.array.dialog_fav_options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int option) {
                            switch (option) {
                                case 0:
                                    mListener.onDialogClickDismiss(CardDialogFragment.this, itemId, dataSet);
                                    break;
                            }
                        }
                    })
                    .create();
             case "dismissed":
                return new AlertDialog.Builder(getActivity(), R.style.CardDialogTheme)
                    .setItems(R.array.dialog_dismissed_options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int option) {
                            switch (option) {
                                case 0:
                                    mListener.onDialogClickModifyProperty(CardDialogFragment.this, itemId, dataSet);
                                    break;
                                case 1:
                                    mListener.onDialogClickDismiss(CardDialogFragment.this, itemId, dataSet);
                                    break;
                            }
                        }
                    })
                    .create();
            default:
                return null;
        }

    }
}
