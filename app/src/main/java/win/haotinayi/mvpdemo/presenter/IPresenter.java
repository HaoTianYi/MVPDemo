package win.haotinayi.mvpdemo.presenter;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des   三个方法，控制视图的不同显示
 * @time 2016-11-21 17:08
 */

public interface IPresenter {
    /**
     * 清空输入框的文字
     */
    void setClearText();

    /**
     * 登录操作
     * @param name
     * @param passwd
     */
    void doLogin(String name, String passwd);

    /**
     * 设置progressBar是否显示
     * @param result
     */
    void setProgressBarVisiblity(boolean result);
}
