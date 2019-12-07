[TOC]

### 1. 配置

在module中引入依赖以及配置module名

```groovy
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

dependencies {
    implementation 'com.alibaba:arouter-api:1.5.0'
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
}
```

### 2. 使用

#### 2.1 初始化

在Application中初始化ARouter

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}
```

#### 2.2 基本使用

在Activity中添加路由路径

```java
@Route(path = Constants.ACTIVITY_URL_PATH_MAIN)
public class MainActivity extends AppCompatActivity {
 		... 
}

@Route(path = Constants.ACTIVITY_URL_PATH_A)
public class A_Activity extends BaseActivity {
  	...
}
```

根据路由路径跳转到指定的Activity

```java
ARouter.getInstance()
  .build(Constants.ACTIVITY_URL_PATH_A)
  .navigation();
```

#### 2.3 参数传递

```java
with(Bundle bundle)
withObject(String key,Object value)
withString(String key,String value)
withBoolean(String key,boolean value)
withShort(String key, short value)
withInt(String key, int value)
withLong(String key, long value)
withDouble(String key, double value)
withByte(@Nullable String key, byte value)
withParcelable(@Nullable String key, @Nullable Parcelable value)
withParcelableArray(@Nullable String key, @Nullable Parcelable[] value)
withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) 
withSerializable(@Nullable String key, @Nullable Serializable value)
```

使用示例

```java
 ARouter.getInstance()
                .build(Constants.ACTIVITY_URL_PATH_A)
                .withLong("id", 123456L)
                .withString("title", "标题")
                .withObject("dog", new Dog("tom", 12))
                .withParcelableArrayList("catLists", lists)
                .withParcelable("cat", new Cat("cat3", 33))
                .withObject("map", maps)
                .navigation();

//在配置了路由路径Constants.ACTIVITY_URL_PATH_A的类中接收参数

@Route(path = Constants.ACTIVITY_URL_PATH_A)
public class A_Activity extends BaseActivity {

    private static final String TAG = "tag";

    @Autowired
    long id;

    @Autowired(name = "title")
    String title;

    @Autowired
    Dog dog;


    /**
     * withParcelableArrayList 这里有点坑，接收类型需为具体类型
     */
    @Autowired(name = "catLists")
    ArrayList<Cat> mCatLists;

    @Autowired(name = "cat")
    Cat mCat;

    @Autowired(name = "map")
    Map<String, List<Cat>> mMap;

		@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
				//注入，给声明注解的属性赋值
        ARouter.getInstance().inject(this);
    }
}
```

使用withObject传递普通对象需要实现SerializationService接口，实现对象的序列化

进入withObject看其具体的实现，可以看到这里使用了SerializationService将对象序列化为json数据

```java
public Postcard withObject(@Nullable String key, @Nullable Object value) {
  serializationService = ARouter.getInstance().navigation(SerializationService.class);
  mBundle.putString(key, serializationService.object2Json(value));
  return this;
}
```

实现SerializationService接口，自定义序列化逻辑

```java
@Route(path = "/app/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return JSON.toJSONString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return JSON.parseObject(input, clazz);
    }
}
```

#### 2.4 通过Uri进行跳转

在开发中常会遇到这种需求，例如推送。前后端会通过协商定义一些schema跳转协议，当推送达到时，移动端可以根据接收到的schema跳转到指定的界面。在这种情况下，我们可以使用一个Activity来接收这些schema，通过在Activity中来处理具体的界面跳转。

```java
@Route(path = Constants.ACTIVITY_URL_PATH_SCHEMA)
public class SchemaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            ARouter.getInstance().build(uri)
                    .navigation(this, new NavigationCallback() {
                        @Override
                        public void onFound(Postcard postcard) {
                            //找到该路径
                            LogUtils.d("");
                            finish();
                        }

                        @Override
                        public void onLost(Postcard postcard) {
                            //没找到路径
                            LogUtils.d("");
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            //跳转到达
                            LogUtils.d("");
                            finish();
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                            //跳转被拦截
                            LogUtils.d("");
                        }
                    });
        }
    }

}
```

#### 2.5 拦截器

ARouter支持拦截器功能，可以在跳转之前根据条件进行拦截，例如有一些模块只有登录才能进入界面，此时就可以使用拦截器功能。

其中priority是拦截器的优先级，值越小优先级越高，会优先拦截。

```java
@Interceptor(priority = 1)
public class UserInterceptor implements IInterceptor {
    private Handler mHandler;

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = UserGlobalManager.getInstance().isLogin();
                if (isLogin) {
                    //已经登录，继续执行
                    callback.onContinue(postcard);
                } else {
                    //未登录，进行拦截
                    callback.onInterrupt(null);
                }
            }
        });


    }


    @Override
    public void init(Context context) {
        mHandler = new Handler(context.getMainLooper());
    }

}
```



