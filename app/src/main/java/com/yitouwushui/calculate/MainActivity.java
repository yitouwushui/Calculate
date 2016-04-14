package com.yitouwushui.calculate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    /**
     * 减号的索引
     */
    private boolean isMinus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        textView = (TextView) findViewById(R.id.textView);
    }

    /**
     * 获得运算符和数字
     *
     * @param view
     */
    public void getParam(View view) {
        Button button = (Button) view;
        String text = textView.getText().toString();
        String input = button.getText().toString();

        Log.e("--------", text.toString());
        if (input.equals("-")) {
            // 如果文本不为空
            if (text.equals("")) {
                textView.append("(");
            } else {
                switch (text.substring(text.length() - 1)) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                    case "√":
                        textView.append("(");
                        break;

                }
            }
        }

        if (isNum(text)) {
            text = "(" + text + ")";
            textView.setText(text);
        }

        textView.append(input);
    }

    /**
     * 计算，清除等
     *
     * @param view
     */
    public void showResult(View view) {
        Button button = (Button) view;
        StringBuilder text = new StringBuilder(textView.getText().toString());
        switch (button.getId()) {
            case R.id.button_15:
                if (!text.equals("")) {
                    Calculate cal = new Calculate();
                    // 检查里面是否有负号- 并转换
                    operatorTrans(text);
                    try {
                        BigDecimal result = cal.computeStack(text.toString());

                        textView.setText(String.valueOf(result));

                    } catch (Exception e) {
                        Toast.makeText(this, "计算式有误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.button_16:
                textView.setText("");
                break;
            case R.id.button_17:
                if (text.length() > 0) {
                    textView.setText(text.substring(0, text.length() - 1));
                } else {
                    textView.setText("");
                }
                break;
        }
    }

    /**
     * 将负号进行转换
     *
     * @param str
     */
    public StringBuilder operatorTrans(StringBuilder str) {

        int index = str.indexOf("(-");
        while (index >= 0) {
            str.insert(index + 1, "0");
//            Log.e("mainActivity", str.toString());
            index = str.indexOf("(-", index + 2);
//            Log.e("---------", String.valueOf(index));
        }
        return str;
    }

    /**
     * 判断一个字符串是否为负数类型
     *
     * @param str
     * @return
     */
    private boolean isNum(String str) {
        String numRegex = "^[-]\\d+(\\.\\d+)?$";
        return Pattern.matches(numRegex, str);
    }

    /**
     * 处理科学显示方式
     *
     * @param result
     * @return
     */
    private boolean isExponent(String result) {


        return true;
    }

}
