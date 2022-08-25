package com.intin.intin_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog  {
    private Context context;

    public CustomDialog(Context context){
        this.context = context;
    }
    public void callFunction(final TextView main_label){
        final Dialog dlg = new Dialog(context);                 // 커스텀 다이얼로그를 정의하기 위해
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);      // 액티비티의 타이틀바를 숨김
        dlg.setContentView(R.layout.custom_dialog);             // 커스텀 다이얼로그의 레이아웃
        dlg.show();                                             // 다이얼로그 노출

        final EditText message = (EditText) dlg.findViewById(R.id.dlg_measege);
        final Button okButton = (Button) dlg.findViewById(R.id.dlg_okbutton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.blg_cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_label.setText(message.getText().toString());                                                   //확인 버튼을 누르면 액티비티에 설정한 곳에
                Message.message(context, "\"" + message.getText().toString() + "\"을 입력하였습니다.");   // 커스텀 다이얼로그에서 입력한 메시지를 대입
                dlg.dismiss();          //커스텀 다이얼로그를 종료
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.message(context,"취소 했습니다");
                dlg.dismiss();
            }
        });
    }
}
