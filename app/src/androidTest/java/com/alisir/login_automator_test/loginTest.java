package com.alisir.login_automator_test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ALiSir on 17/6/1.
 */

@RunWith(AndroidJUnit4.class)
public class loginTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    static final String TAG = "登录自动化测试！";

    String basicPackage = "com.alisir.login_automator_test";
    static final int LAUNCH_TIMEOUT = 5000;
    UiDevice mDevice;

    @Before
    public void setUp() {
        //实例化
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        //跳转到主屏
        mDevice.pressHome();

        final String laucherPagckage = getLauncherPackageName();
        assertThat(laucherPagckage, notNullValue());
        System.out.print("包名：" + laucherPagckage);
        Log.i(TAG, "setUp: 包名：" + laucherPagckage);
        mDevice.wait(Until.hasObject(By.pkg(laucherPagckage).depth(0)), LAUNCH_TIMEOUT);

        //运行app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(basicPackage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        //显示App
        mDevice.wait(Until.hasObject(By.pkg(basicPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void login_suc() throws UiObjectNotFoundException, InterruptedException {
        String username = "123";
        String pwd = "123";

        //设置用户名
        UiObject userText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        userText.click();//点击一下
        userText.setText(username);

        //设置密码
        UiObject pwdText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        pwdText.click();
        pwdText.setText(pwd);

        //点击登录按钮
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();

        //通过对比主页面的text内容确定是否登录成功,通过ID找到该TextView控件
        UiObject2 mainText = mDevice.wait(Until.findObject(By.res(basicPackage, "textView")), 500);
        String welcomeStr = mainText.getText();

        assertEquals(welcomeStr, "我是主页面！");
    }

    @Test
    public void login_faild() throws UiObjectNotFoundException, InterruptedException {
        String username = "111";
        String pwd = "111";

        //设置用户名
        UiObject userText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        userText.click();//点击一下
        userText.setText(username);

        //设置密码
        UiObject pwdText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        pwdText.click();
        pwdText.setText(pwd);

        //点击登录按钮
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();


        //通过对比主页面的text内容确定是否登录成功,通过ID找到该TextView控件
        UiObject2 mainText = mDevice.wait(Until.findObject(By.res(basicPackage, "textView")), 500);
        assertNotEquals(mainText.getText(), is(equalTo("我是主页面！")));

    }

    //获取包名
    private String getLauncherPackageName() {
        //创建运行的Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        //通过包管理器获取运行的包名
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

}
