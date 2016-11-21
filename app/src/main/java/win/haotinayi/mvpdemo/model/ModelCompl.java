package win.haotinayi.mvpdemo.model;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des
 * @time 2016-11-21 17:03
 */

public class ModelCompl implements IModel {

    String name;
    String password;

    public ModelCompl(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPssword() {
        return password;
    }

    @Override
    public boolean isSuccess(String name, String password) {
        return name.equals(password);
    }
}
