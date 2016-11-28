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
import com.example.lambo.dataclass.Product;
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
    private int goodsAttrId = 0;
    private ArrayList<Integer> showGoodsAttrIds;
    public GoodsDialog(Context context) {
        super(context, R.style.FullHeightDialog);
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
        showGoodsAttrIds = getGoodsAttrIds(goods.getAttrs(),goodsAttrId,new ArrayList<Integer>());
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.attr_item,null);
            TextView textView = (TextView) convertView.findViewById(R.id.attName);
            textView.setText(mList.get(position).getAttrName());
            FlowLayout flowLayout = (FlowLayout) convertView.findViewById(R.id.fl);
            flowLayout.setHorizontalSpacing(10);
            flowLayout.setVerticalSpacing(10);
            final ArrayList<Attr.GoodsAttr> goodsAttrs = mList.get(position).getGoodsAttrs();
            for (int i = 0; i < goodsAttrs.size(); i++) {
                TextView tv_value = new TextView(context);
                tv_value.setText(goodsAttrs.get(i).getAttrValue());
                tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.normal));
                tv_value.setMinWidth(50);
                tv_value.setBackgroundColor(context.getResources().getColor(R.color.line));
                tv_value.setTextColor(context.getResources().getColor(R.color.white));
                tv_value.setPadding(10, 5, 10, 5);
                flowLayout.addView(tv_value);
                for (int j = 0; j < showGoodsAttrIds.size(); j++) {
                    if(showGoodsAttrIds.get(j) == goodsAttrs.get(i).getGoodsAttrId()){
                        tv_value.setBackground(context.getResources().getDrawable(R.drawable.btn_white));
                        tv_value.setTextColor(context.getResources().getColor(R.color.black));
//                        break;
                    }
                }
                final int finalI = i;
                tv_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setGoodsAttrId(goodsAttrs.get(finalI).getGoodsAttrId());
                    }
                });
            }
            return convertView;
        }
    }

    public ArrayList<Integer> getGoodsAttrIds(ArrayList<Attr> attrs,int goodsAttrId,ArrayList<Integer> goodsAttrIds){
//        Log.d(TAG, goodsAttrId+"getGoodsAttrIds: "+attrs.toString());
        Log.d(TAG, "getGoodsAttrIds: "+goodsAttrId);
        for (int i = 0; i < attrs.size(); i++) {
            Attr attr = attrs.get(i);
            for (int j = 0; j < attr.getGoodsAttrs().size(); j++) {
                int nowGoodsAttrId = attr.getGoodsAttrs().get(j).getGoodsAttrId();
                int parentAttrId = attr.getGoodsAttrs().get(j).getParentId();
                if (nowGoodsAttrId == goodsAttrId || goodsAttrId==0){//goodsAttrId=0 初始展示所有
                    for (int n = 0; n < attr.getGoodsAttrs().size(); n++) {
                        addInt(goodsAttrIds, attr.getGoodsAttrs().get(n).getGoodsAttrId());
                    }
                    ArrayList<Attr> childrenAttrs = attr.getChildren();
                    addInt(goodsAttrIds, nowGoodsAttrId);
                    if (parentAttrId != 0) addInt(goodsAttrIds, parentAttrId);
                    if (childrenAttrs!= null){
                        for (int k = 0; k < childrenAttrs.size(); k++) {
                            for (int l = 0; l < childrenAttrs.get(k).getGoodsAttrs().size(); l++) {
                                if (childrenAttrs.get(k).getGoodsAttrs().get(l).getParentId() == goodsAttrId|| goodsAttrId==0)
                                getGoodsAttrIds(childrenAttrs,childrenAttrs.get(k).getGoodsAttrs().get(l).getGoodsAttrId(),goodsAttrIds);
                            }
                        }
                    }
                }else if(attr.getChildren() != null){
                    ArrayList<Integer> arrayList = getGoodsAttrIds(attr.getChildren(), goodsAttrId, goodsAttrIds);
                    if (arrayList != null && arrayList.size() > goodsAttrIds.size()) {
                        goodsAttrIds = arrayList;
                    }
                }
            }
        }
        return goodsAttrIds;
    }

    public void setGoodsAttrId(int goodsAttrId) {
        if (this.goodsAttrId == goodsAttrId){
            goodsAttrId = 0;
        }
        this.goodsAttrId = goodsAttrId;
        showGoodsAttrIds = getGoodsAttrIds(goods.getAttrs(),goodsAttrId,new ArrayList<Integer>());
        Log.d(TAG, goodsAttrId + "setGoodsAttrId: " + showGoodsAttrIds.toString());
        attrsAdapter.notifyDataSetChanged();
    }
    public void addInt(ArrayList<Integer> arrayList,int id){
        boolean has = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) == id)
                has=!has;
        }
        if(!has) arrayList.add(id);
    }
}
