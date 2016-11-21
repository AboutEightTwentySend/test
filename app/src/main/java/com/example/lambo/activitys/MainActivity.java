package com.example.lambo.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lambo.dataclasss.Cat;
import com.example.lambo.R;
import com.example.lambo.dataclasss.Goods;
import com.example.lambo.dialog.GoodsDialog;
import com.example.lambo.nets.GoodsDetailNet;
import com.example.lambo.uis.HorizontalListView;
import com.example.lambo.uis.MyListView;
import com.example.lambo.adapter.MyAdapter;
import com.example.lambo.nets.BaseNet;
import com.example.lambo.nets.CatChildrenNet;
import com.example.lambo.nets.CatsNet;
import com.example.lambo.nets.GoodsListNet;
import com.example.lambo.nets.LoginNet;
import com.example.lambo.other.URL;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseNet.NetCallBack, AdapterView.OnItemClickListener {
    public final static String TAG = "lambo";
    HorizontalListView lv_cat;
    MyListView lv_cat_tree;
    MyListView lv_goods;
    ArrayList<Cat> cats;
    ArrayList<Cat> catChildren;
    ArrayList<Goods> goodsList;
    GoodsListAdapter goodsListAdapter = new GoodsListAdapter();
    CatsListAdapter CLHAdapter = new CatsListAdapter(true);
    CatsListAdapter CLAdapter = new CatsListAdapter(false);
    GoodsDialog goodsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_cat = (HorizontalListView) findViewById(R.id.lv_cat);
        lv_cat_tree = (MyListView) findViewById(R.id.lv_cat_tree);
        lv_goods = (MyListView) findViewById(R.id.lv_goods);
        lv_cat.setOnItemClickListener(this);
        lv_cat_tree.setOnItemClickListener(this);
        lv_goods.setOnItemClickListener(this);
        lv_goods.setAdapter(goodsListAdapter);
        lv_cat.setAdapter(CLHAdapter);
        lv_cat_tree.setAdapter(CLAdapter);
        URL.throwNet(new CatsNet(this));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phonenum", "18306677680");
        hashMap.put("password", "123456");
        URL.throwNet(new LoginNet(hashMap));
    }

    @Override
    public void netErrorResponse(BaseNet net) {
        Log.d(TAG, "netErrorResponse: " + net.error.toString());
    }

    @Override
    public void netResponse(BaseNet net) {
        if (net.getClass().getName().contains("CatsNet")) {
            CatsNet catsNet = (CatsNet) net;
            cats = catsNet.cats;
            CLHAdapter.setList(cats);
            if (cats.size() > 0) {
                catChildren = cats.get(0).getChildren();
                initGoods(catChildren);
                CLAdapter.setList(catChildren);
            }
        } else if (net.getClass().getName().contains("CatChildrenNet")) {
            catChildren = ((CatChildrenNet) net).cat.getChildren();
            initGoods(catChildren);
            CLAdapter.setList(catChildren);
        } else if (net.getClass().getName().contains("GoodsListNet")) {
            GoodsListNet goodsListNet = (GoodsListNet) net;
            goodsList = goodsListNet.goodsListData.getRows();
            List<HashMap<String, String>> list = new ArrayList<>();
            for (int i = 0; i < goodsList.size(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("goodsName", goodsList.get(i).getGoodsName());
                list.add(hashMap);
            }
            goodsListAdapter.setList(goodsList);
        } else if (net.getClass().getName().contains("GoodsDetailNet")) {
            if(goodsDialog == null) goodsDialog = new GoodsDialog(this);
            goodsDialog.setGoods(((GoodsDetailNet) net).goodsDetail);
            if (!goodsDialog.isShowing()) goodsDialog.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_cat:
                URL.throwNet(new CatChildrenNet(cats.get(position).getCatId(), MainActivity.this));
                break;
            case R.id.lv_cat_tree:
                URL.throwNet(new GoodsListNet(catChildren.get(position - 1).getCatId(), this));
                break;
            case R.id.lv_goods:
                break;
        }
    }

    private void initGoods(ArrayList<Cat> list) {
        if (list != null && list.size() > 0) {
            URL.throwNet(new GoodsListNet(list.get(0).getCatId(), this));
        }
    }

    //提取list cat 中的name展示列表
    private List<HashMap<String, String>> getCatsList(ArrayList<Cat> cats) {
        List<HashMap<String, String>> list = new ArrayList<>();
        if (cats == null) return list;
        for (int i = 0; i < cats.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", cats.get(i).getCatName());
            list.add(hashMap);
        }
        return list;
    }

    public class GoodsListAdapter extends MyAdapter<Goods> {
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.goods_item, null);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(mList.get(position).getGoodsName());
            viewHolder.tv_desc.setText(mList.get(position).getDescription());
            String img = mList.get(position).getImages();
            String[] imgs = img.split(",");
            ImageLoader.getInstance().displayImage(URL.QINIU + imgs[0], viewHolder.img);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    URL.throwNet(new GoodsDetailNet(mList.get(position).getGoodsId(), MainActivity.this));
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView tv_name;
            public TextView tv_desc;
            public TextView tv_price;
            public ImageView img;
        }
    }

    public class CatsListAdapter extends MyAdapter<Cat> {
        boolean horizontal;

        public CatsListAdapter(boolean horizontal) {
            this.horizontal = horizontal;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.cats_item, null);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(mList.get(position).getCatName());
            viewHolder.tv_name.getPaint().setFakeBoldText(horizontal);
            if (horizontal) {
                viewHolder.tv_name.setTextColor(Color.parseColor("#ffffff"));
            }
            return convertView;
        }

        class ViewHolder {
            public TextView tv_name;
        }
    }
}
