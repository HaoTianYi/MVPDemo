package win.haotinayi.mvpdemo.presenter;

import android.os.Handler;
import android.os.Looper;

import win.haotinayi.mvpdemo.model.IModel;
import win.haotinayi.mvpdemo.model.ModelCompl;
import win.haotinayi.mvpdemo.view.IView;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des
 * @time 2016-11-21 17:12
 */

public class PresenterCompl implements IPresenter {

    IView mIView;
    IModel mIModel;
    Handler mHandler;

    public PresenterCompl(IView iView) {
        mIView = iView;

        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void setClearText() {
        mIView.onClesrText();
    }

    @Override
    public void doLogin(final String name, final String passwd) {
        mIModel = new ModelCompl(name, passwd);
        final boolean isSuccess = mIModel.isSuccess(name, passwd);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIView.onSuccessAndRefuslView(isSuccess);
                mIView.onShowDIalog(!isSuccess);
            }
        }, 3000);
    }


    @Override
    public void setProgressBarVisiblity(boolean result) {
        mIView.onShowDIalog(result);
    }
}
