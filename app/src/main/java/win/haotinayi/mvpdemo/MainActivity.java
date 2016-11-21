package win.haotinayi.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import win.haotinayi.mvpdemo.presenter.IPresenter;
import win.haotinayi.mvpdemo.presenter.PresenterCompl;
import win.haotinayi.mvpdemo.view.IView;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des   作为view的实现
 * @time 2016-11-21 17:00
 */
public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener{

    IPresenter mPresenter;

    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    private Button   btnClear;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view
        editUser = (EditText) this.findViewById(R.id.et_login_username);
        editPass = (EditText) this.findViewById(R.id.et_login_password);
        btnLogin = (Button) this.findViewById(R.id.btn_login_login);
        btnClear = (Button) this.findViewById(R.id.btn_login_clear);
        progressBar = (ProgressBar) this.findViewById(R.id.progress_login);

        //set listener
        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        //init
        mPresenter = new PresenterCompl(this);
        mPresenter.setProgressBarVisiblity(false);

    }

    @Override
    public void onClesrText() {
        editUser.setText("");
        editPass.setText("");
    }

    @Override
    public void onSuccessAndRefuslView(boolean result) {
        Toast.makeText(MainActivity.this,result?"成功":"失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowDIalog(boolean result) {
        progressBar.setVisibility(result?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_clear:
                mPresenter.setClearText();
                break;
            case R.id.btn_login_login:
                mPresenter.setProgressBarVisiblity(true);
                btnLogin.setEnabled(false);
                btnClear.setEnabled(false);
                mPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
                break;
        }
    }
}
