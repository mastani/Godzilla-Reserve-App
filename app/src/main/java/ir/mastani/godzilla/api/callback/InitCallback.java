package ir.mastani.godzilla.api.callback;

public interface InitCallback {
    void onCaptcha(String url);
    void OnError();
}