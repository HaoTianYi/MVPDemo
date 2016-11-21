# MVP的基本使用
整理自[mvp](http://kaedea.com/2015/10/11/android-mvp-pattern/)

## 简介



MVP 模式（Model-View-Presenter）可以说是 MVC 模式（Model-View-Controller）在 Android 开发上的一种变种、进化模式。

在 MVC 模式中，Activity 应该是属于 View 这一层。而实质上，它既承担了 View，同时也包含一些 Controller 的东西在里面。这对于开发与维护来说不太友好，耦合度大高了。把 Activity 的 View 和 Controller 抽离出来就变成了 View 和 Presenter，这就是 MVP 模式。

## MVC模式

MVC 模式的结构分为三部分，实体层的 Model，视图层的 View，以及控制层的 Controller。

![sp161121_161924](http://oaxelf1sk.bkt.clouddn.com/sp161121_161924.png)

- 其中 View 层其实就是程序的 UI 界面，用于向用户展示数据以及接收用户的输入
- 而 Model 层就是 JavaBean 实体类，用于保存实例数据
- Controller 控制器用于更新 UI 界面和数据实例

## MVP 模式

按照 MVC 的分层，Activity 和 Fragment（后面只说 Activity）应该属于 View 层，用于展示 UI 界面，以及接收用户的输入，此外还要承担一些生命周期的工作。Activity 是在 Android 开发中充当非常重要的角色，特别是 TA 的生命周期的功能，所以开发的时候我们经常把一些业务逻辑直接写在 Activity 里面，这非常直观方便，代价就是 Activity 会越来越臃肿，所以看起来有必要在 Activity 中，把 View 和 Controller 抽离开来，而这就是 MVP 模式的工作了。

![sp161121_162259](http://oaxelf1sk.bkt.clouddn.com/sp161121_162259.png)

核心思想：

**MVP 把 Activity 中的 UI 逻辑抽象成 View 接口，把业务逻辑抽象成 Presenter 接口，Model 类还是原来的 Model**。

这就是 MVP 模式，现在这样的话，Activity 的工作的简单了，只用来响应生命周期，其他工作都丢到 Presenter 中去完成。

## MVP 模式的使用

![sp161121_164834](http://oaxelf1sk.bkt.clouddn.com/sp161121_164834.png)

上面一张简单的 MVP 模式的 UML 图，从图中可以看出，使用 MVP，至少需要经历以下步骤：

1. 创建 IPresenter 接口，把所有业务逻辑的接口都放在这里，并创建它的实现 PresenterCompl（在这里可以方便地查看业务功能，由于接口可以有多种实现所以也方便写单元测试）
2. 创建 IView 接口，把所有视图逻辑的接口都放在这里，其实现类是当前的 Activity/Fragment
3. 由 UML 图可以看出，Activity 里包含了一个 IPresenter，而 PresenterCompl 里又包含了一个 IView 并且依赖了 Model。Activity 里只保留对 IPresenter 的调用，其它工作全部留到 PresenterCompl 中实现
4. Model 并不是必须有的，但是一定会有 View 和 Presenter


MVP 的主要特点就是把 Activity 里的许多逻辑都抽离到 View 和 Presenter 接口中去，并由具体的实现类来完成。这种写法多了许多 IView 和 IPresenter 的接口

## 具体代码

一个简单的登录界面：

![sp161121_173136](http://oaxelf1sk.bkt.clouddn.com/sp161121_173136.png)

```
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
```

IPresenter的写法：

```
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

```

IModel的写法：

```
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
```

PresenterCompl的写法：

```
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
```

具体代码：[GitHub](https://github.com/HaoTianYi/MVPDemo)
