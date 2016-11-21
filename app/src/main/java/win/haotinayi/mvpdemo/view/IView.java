package win.haotinayi.mvpdemo.view;

import java.util.List;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des
 * @time 2016-11-21 17:00
 */

public interface IView {
    void onClesrText();

    void onSuccessAndRefuslView(boolean result);

    void onShowDIalog(boolean result);
}
