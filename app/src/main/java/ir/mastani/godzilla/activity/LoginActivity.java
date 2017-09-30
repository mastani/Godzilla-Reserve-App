package ir.mastani.godzilla.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mastani.godzilla.R;
import ir.mastani.godzilla.api.Requests;
import ir.mastani.godzilla.api.callback.InitCallback;
import ir.mastani.godzilla.api.volley.AppController;

public class LoginActivity extends AppCompatActivity {

    ImageLoader mImageLoader;

    SubLogin subLogin;
    @BindView(R.id.sub_login) View loginView;
    public static class SubLogin {
        @BindView(R.id.txtUsername) EditText txtUsername;
        @BindView(R.id.txtPassword) EditText txtPassword;
        @BindView(R.id.captchaImageView) NetworkImageView captchaImageView;
        @BindView(R.id.txtCaptcha) EditText txtCaptcha;
        @BindView(R.id.btnSignIn) ActionProcessButton btnSignIn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        VolleyLog.DEBUG = true;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImageLoader = AppController.getInstance().getImageLoader();

        subLogin = new SubLogin();
        ButterKnife.bind(subLogin, loginView);

        Requests.initSession(getBaseContext(), new InitCallback() {
            @Override
            public void onCaptcha(String url) {
                subLogin.captchaImageView.setImageUrl(url, mImageLoader);
            }

            @Override
            public void OnError() {

            }
        });

        subLogin.btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        subLogin.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean errorFlag = false;

                if (subLogin.txtUsername.getText().length() == 0) {
                    subLogin.txtUsername.setError("شماره دانشجویی خود را وارد کنید");
                    errorFlag = true;
                }

                if (subLogin.txtPassword.getText().length() == 0) {
                    subLogin.txtPassword.setError("کلمه عبور خود را وارد کنید");
                    errorFlag = true;
                }

                if (errorFlag)
                    return;

                subLogin.btnSignIn.setProgress(50);
            }
        });
    }
}
