package com.alisir.login_automator_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.editText)).getText().toString().equals("123") && ((EditText)findViewById(R.id.editText2)).getText().toString().equals("123")){
                    Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,LoginSucActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
