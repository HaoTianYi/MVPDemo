package win.haotinayi.mvpdemo.model;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des   获得用户的用户名和密码并且判断是否符合规则
 * @time 2016-11-21 17:03
 */

public interface IModel {
    String getName();
    String getPssword();
    boolean isSuccess(String name,String password);
}
