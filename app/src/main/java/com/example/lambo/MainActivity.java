package com.example.lambo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.lambo.DataClass.CatClass;
import com.example.lambo.DataClass.GoodsClass;
import com.example.lambo.UI.HorizontalListView;
import com.example.lambo.UI.MyListView;
import com.example.lambo.net.BaseNet;
import com.example.lambo.net.CatChildrenNet;
import com.example.lambo.net.CatsNet;
import com.example.lambo.net.GoodsListNet;
import com.example.lambo.net.LoginNet;
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
    ArrayList<CatClass> cats;
    ArrayList<CatClass> catChildren;
    ArrayList<GoodsClass> goodsList;
    GoodsListAdapter goodsListAdapter;

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
        goodsListAdapter = new GoodsListAdapter(this);
        lv_goods.setAdapter(goodsListAdapter);
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
            lv_cat.setAdapter(new SimpleAdapter(this, getCatsList(cats), R.layout.lvh_item, new String[]{"name"}, new int[]{R.id.tv}));
            if (cats.size() > 0) {
                catChildren = cats.get(0).getChildren();
                initGoods(catChildren);
                lv_cat_tree.setAdapter(new SimpleAdapter(this, getCatsList(catChildren),
                        R.layout.list_item, new String[]{"name"}, new int[]{R.id.tv}));
            }
        } else if (net.getClass().getName().contains("CatChildrenNet")) {
            catChildren = ((CatChildrenNet) net).cat.getChildren();
            initGoods(catChildren);
            lv_cat_tree.setAdapter(new SimpleAdapter(this, getCatsList(catChildren),
                    R.layout.list_item, new String[]{"name"}, new int[]{R.id.tv}));
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
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_cat:
                URL.throwNet(new CatChildrenNet(cats.get(position).getCatId(), MainActivity.this));
                break;
            case R.id.lv_cat_tree:
                URL.throwNet(new GoodsListNet(catChildren.get(position-1).getCatId(),this));
                break;
            case R.id.lv_goods:
                break;
        }
    }

    private void initGoods(ArrayList<CatClass> list){
        if (list != null && list.size() > 0){
            URL.throwNet(new GoodsListNet(list.get(0).getCatId(),this));
        }
    }

    //提取list cat 中的name展示列表
    private List<HashMap<String, String>> getCatsList(ArrayList<CatClass> cats) {
        List<HashMap<String, String>> list = new ArrayList<>();
        if (cats == null) return list;
        for (int i = 0; i < cats.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", cats.get(i).getCatName());
            list.add(hashMap);
        }
        return list;
    }

    public class GoodsListAdapter extends BaseAdapter{
        private ArrayList<GoodsClass> mList;
        private Context mContext;
        public GoodsListAdapter(Context mContext){
            this.mContext = mContext;
        }

        public void setList(ArrayList<GoodsClass> mList) {
            this.mList = mList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mList == null ? 0 : position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_item,null);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(mList.get(position).getGoodsName());
            viewHolder.tv_desc.setText(mList.get(position).getDescription());
            String img = mList.get(position).getImages();
            String[] imgs = img.split(",");
            ImageLoader.getInstance().displayImage(URL.QINIU + imgs[0], viewHolder.img);
            return convertView;
        }
        class ViewHolder{
            public TextView tv_name;
            public TextView tv_desc;
            public TextView tv_price;
            public ImageView img;
        }
    }
}
