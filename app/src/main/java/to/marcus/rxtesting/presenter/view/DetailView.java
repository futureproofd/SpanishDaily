package to.marcus.rxtesting.presenter.view;

/**
 * Created by marcus on 9/29/2015.
 */
public interface DetailView {
    void showLoading();
    void hideLoading();
    void showWordDetails();
    void onClickPlayback(byte[] soundByte);
    void showNotification(String notification);
}
