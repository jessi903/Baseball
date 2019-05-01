package com.example.baseball;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.baseball.custom.AnsAdapter;
import com.example.baseball.custom.AnsListBean;
import com.example.baseball.main.Main;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {
    //The answer
    private int ranNum;
    //Counter
    private int count = 0;
    //Shows the list
    private AnsAdapter ansAdapter;
    //Saved values
    private ArrayList<AnsListBean> ansArrayList = new ArrayList<AnsListBean>();
    public static boolean isCorrect = false;
    public static Context context;


    EditText ansEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context=this;
        //Generate integer
        ranNum =generateRanNum(context);
        // values inserted
        ansEditText = (EditText) findViewById(R.id.ansEditTextId);
        // List of saved values
        ListView ansList = (ListView) findViewById(R.id.ansListId);

        ansAdapter = new AnsAdapter(this,R.layout.anslist, ansArrayList);
        ansList.setAdapter(ansAdapter);
    }

    private int generateRanNum(Context context) {
        Main m = new Main(context);
        // Generate four-digit number
        int ranNum = m.genRanNum();
        return ranNum;
    }


    public void mOnClick(View v) {
        switch(v.getId()){
            case R.id.ansBtnId:
                count++;
                ansArrayList = doCompare();
                // Reset the value
                ansEditText.setText("");
                ansAdapter.notifyDataSetChanged();
                // Restart?
                if(isCorrect) {
                    alertDialog();
                    isCorrect = !isCorrect;
                }
                break;
        }
    }

    private void alertDialog() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Restart
                ranNum = generateRanNum(context);
                ansArrayList.clear();
                ansAdapter.notifyDataSetChanged();
            }
        };

        Builder builder = new Builder(this);
        builder.setTitle("Congratulations!(You've tried : " + count + " times)\nWant to restart?");
        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("No", null);
        count = 0;
        builder.show();
    }


    private ArrayList<AnsListBean> doCompare() {
        Main m = new Main(getApplicationContext());
        String ansNum = (String) ansEditText.getText().toString();
        // Check if the val is number
        int ans = m.isValid(ansNum);

        if( ans != -1 ) {
            String result = m.process(ans, ranNum);
            System.out.println(result);
            AnsListBean ansListBean = new AnsListBean();
            ansListBean.setAnsNum(ansNum);
            if(!result.contains("4 strikes")) {
                ansListBean.setAnsResult(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else {
                ansListBean.setAnsResult("Answer");
                isCorrect = !isCorrect;
            }
            ansArrayList.add(ansListBean);
        }
        return ansArrayList;
    }
}