package com.example.lambo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lambo.R;
import com.example.lambo.adapter.MyAdapter;
import com.example.lambo.dataclass.Attr;
import com.example.lambo.dataclass.Goods;
import com.example.lambo.net.BaseNet;
import com.example.lambo.other.URL;
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
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.attr_item,null);
            TextView textView = (TextView) convertView.findViewById(R.id.attName);
            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.attrLl);
            textView.setText(mList.get(position).getAttrName());
            ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return convertView;
        }
    }
}
