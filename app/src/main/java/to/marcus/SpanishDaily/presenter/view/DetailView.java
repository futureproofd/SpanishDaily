package to.marcus.SpanishDaily.presenter.view;

/**
 * Created by marcus on 9/29/2015
 */
public interface DetailView {
    void showWordDetails();
    void onClickPlayback(byte[] soundByte);
    void showNotification(String notification);
}
