package com.example.lambo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lambo.dataclass.ZAXJGClass;
import com.example.lambo.R;
import com.example.lambo.net.BaseNet;
import com.example.lambo.net.ZAXJGnet;
import com.example.lambo.other.URL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.CellType;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.CellValue;

public class AXJGActivity extends AppCompatActivity implements BaseNet.NetCallBack {
    final static String TAG = "lambo";
    EditText et;
    Button btn;
    ZAXJGClass zaxjg;
//    http://120.25.152.212:3030/data?phonenum=

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_anxinjigong);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("生成中");
                btn.setClickable(false);
                URL.throwNet(new ZAXJGnet(et.getText().toString(), AXJGActivity.this));
            }
        });
    }

    @Override
    public void netErrorResponse(BaseNet net) {

    }

    @Override
    public void netResponse(BaseNet net) {
        ZAXJGnet zaxjGnet = (ZAXJGnet) net;
        zaxjg = zaxjGnet.zaxjg;
        if (zaxjg == null){
            btn.setText("查询");
            btn.setClickable(true);
            Toast.makeText(this,"查询失败",Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < zaxjg.getAccounting_list().size(); i++) {
            for (int j = 0; j < zaxjg.getAccounting_foreman().size(); j++) {
                if (zaxjg.getAccounting_list().get(i).getForeman_id() == zaxjg.getAccounting_foreman().get(j).getForeman_id()) {
                    zaxjg.getAccounting_list().get(i).setForeman_name(zaxjg.getAccounting_foreman().get(j).getUsername());
                }
            }
        }
        for (int i = 0; i < zaxjg.getAccountingz_list().size(); i++) {
            for (int j = 0; j < zaxjg.getAccountingz_foreman().size(); j++) {
                if (zaxjg.getAccountingz_list().get(i).getForeman_id() == zaxjg.getAccountingz_foreman().get(j).getForeman_id()) {
                    zaxjg.getAccountingz_list().get(i).setForeman_name(zaxjg.getAccountingz_foreman().get(j).getUsername());
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/axjg.xls");
                Log.d(TAG, "run: " + file.getPath());
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                try {
                    createExcel(new FileOutputStream(file), zaxjg);
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }

    public void createExcel(OutputStream os, ZAXJGClass data) throws WriteException, IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        createSheet(data.getAccounting_list(), workbook, 0);
        createSheet(data.getAccountingz_list(), workbook, 1);
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
        Toast.makeText(AXJGActivity.this, "完成", Toast.LENGTH_LONG).show();
        Log.d(TAG, "createExcel: " + "完成");
        btn.setText("查询");
        btn.setClickable(true);
    }

    public WritableSheet createSheet(ArrayList<ZAXJGClass.ALIST> list, WritableWorkbook workbook, int num) throws WriteException, IOException {
        String role = "工人";
        if (num == 0) {
            role = "工头";
        }
        WritableSheet sheet = workbook.createSheet(role + "账单列表", num);
        sheet.addCell(new Label(0, 0, "序号"));
        sheet.addCell(new Label(1, 0, role));
        sheet.addCell(new Label(2, 0, "地址"));
        sheet.addCell(new Label(3, 0, "日期"));
        sheet.addCell(new Label(4, 0, "类型"));
        sheet.addCell(new Label(5, 0, "金额"));
        sheet.addCell(new Label(6, 0, "时间"));
        sheet.addCell(new Label(7, 0, "合计天数"));
        sheet.setColumnView(3, 100);
        Log.d(TAG, "createSheet: " + list.toString());
        for (int i = 0; i < list.size(); i++) {
            DateFormat df = new DateFormat("yyyy-MM-dd");
            WritableCellFormat wcfDF = new WritableCellFormat(df);
            String hours = list.get(i).getWork_hours();
            if (hours == "" || hours == null) hours = "0";
            sheet.addCell(new Label(0, i+1, String.valueOf(i + 1)));
            sheet.addCell(new Label(1, i+1, list.get(i).getForeman_name()));
            sheet.addCell(new Label(2, i+1, list.get(i).getAddr()));
            sheet.addCell(new DateTime(3, i+1, formatterDate(list.get(i).getStarttime()), wcfDF));
            sheet.addCell(new Label(4, i+1, translateWorkType(list.get(i).getWork_type())));
            sheet.addCell(new Number(5, i + 1, Float.valueOf(list.get(i).getWage())));
            sheet.addCell(new Number(6, i + 1, Float.valueOf(hours)));
            sheet.addCell(new Number(7, i + 1, list.get(i).getEqual_day()));
        }
        return sheet;
    }
    public Date formatterDate(String str){
        String pat1 = "yyyy-MM-dd" ;
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1) ;
        Date date = null;
        try {
            date = sdf1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null){date = new Date();}
        return date;
    }
    public String translateWorkType(int type){
        if (type==1){
            return "点工";
        }else if(type == 2){
            return "包工";
        }else{
            return "借支";
        }
    }
}