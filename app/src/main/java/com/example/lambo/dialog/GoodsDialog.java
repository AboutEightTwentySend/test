package com.example.lambo.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lambo.R;
import com.example.lambo.adapter.MyAdapter;
import com.example.lambo.dataclass.Attr;
import com.example.lambo.dataclass.Goods;
import com.example.lambo.other.URL;
import com.example.lambo.ui.FlowLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/21.
 */
public class GoodsDialog extends Dialog{
    public final static String TAG = "lambo";
    private Goods goods;
    private Context context;
    private AttrsAdapter attrsAdapter;
    private TextView tv_goods_name,tv_desc;
    private ImageView imageView;
    public GoodsDialog(Context context) {
        super(context,R.style.FullHeightDialog);
        this.context = context;
        initView(context);
    }

    public GoodsDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView(context);
    }

    protected GoodsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initView(context);
    }
    public void setGoods(Goods goods){
        this.goods = goods;
        tv_goods_name.setText(goods.getGoodsName());
        tv_desc.setText(goods.getDescription());
        if (goods.getImages() != null){
            ImageLoader.getInstance().displayImage(URL.QINIU+goods.getImages().split(",")[0],imageView);
        }
        attrsAdapter.setList(getAttrs(null, goods.getAttrs().get(0)));
    }
    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.choose_goods,null);
        tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        imageView = (ImageView) view.findViewById(R.id.img);
        ListView listView = (ListView) view.findViewById(R.id.lv_attr);
        attrsAdapter = new AttrsAdapter();
        listView.setAdapter(attrsAdapter);
        setContentView(view);
    }
    public ArrayList<Attr> getAttrs(ArrayList<Attr> attrs,Attr attr){
        if (attrs == null) attrs = new ArrayList<>();
        attrs.add(attr);
        if (attr.getChildren() != null) getAttrs(attrs,attr.getChildren().get(0));
        return attrs;
    }
    public class AttrsAdapter extends MyAdapter<Attr>{
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.attr_item,null);
            TextView textView = (TextView) convertView.findViewById(R.id.attName);
            textView.setText(mList.get(position).getAttrName());
            FlowLayout flowLayout = (FlowLayout) convertView.findViewById(R.id.fl);
            flowLayout.setHorizontalSpacing(10);
            flowLayout.setVerticalSpacing(10);
            ArrayList<Attr.GoodsAttr> goodsAttrs = mList.get(position).getGoodsAttrs();
            for (int i = 0; i < goodsAttrs.size(); i++) {
                TextView tv_value = new TextView(context);
                tv_value.setText(goodsAttrs.get(i).getAttrValue());
                tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.normal));
                tv_value.setMinWidth(50);
                tv_value.setBackground(context.getResources().getDrawable(R.drawable.red_click));
                tv_value.setTextColor(context.getResources().getColor(R.color.white));
                tv_value.setPadding(10, 5, 10, 5);
                flowLayout.addView(tv_value);
                tv_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: ");
                    }
                });
            }
            return convertView;
        }
    }
}
