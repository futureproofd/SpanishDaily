package to.marcus.SpanishDaily.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import to.marcus.SpanishDaily.R;

/**
 * Created by marcus on 4/13/2016
 */
public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_about, null);
        final TextView mTextSource = (TextView)view.findViewById(R.id.tv_source_code);
        final TextView mTextContact = (TextView)view.findViewById(R.id.tv_send_feedback);
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        mTextSource.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/futureproofd/SpanishDaily"));
                getActivity().startActivity(intent);
            }
        });
        mTextContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","nexus06@gmail.com",null));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Spanish Daily Feedback");
                getActivity().startActivity(Intent.createChooser(intent, "Send Feedback:"));
            }
        });

        return builder.create();
    }
}
