package com.gmail.blackbull8810.noiseapp;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ThirdFragment extends Fragment {

    private View view;

    /*리스트뷰*/
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    SQLiteDatabase database;
//    ArrayList<String> listvalue,listday,listvaluekorea = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();
    String time;
    AlertDialog dialog;
    TextView tv;
    int avg = 0;
    int max;
    int a;
    int sum = 0;
    Cursor cursor;

    private Button btn_all_delete;
    private TextView tv_empty_data;

    //UI에 데이터가 변경된 경우 다시 그려주게될 Runnable interface. 데이터를 갱신하고, 이를 화면에
    //반영해야할 경우에는 반드시 Activity의 runOnUiThread(Runnable r) 메쏘드를 이용해서 호출해야
    //실시간으로 화면에 반영됩니다.
    private Runnable updateUI = new Runnable() {
        public void run() {
            ThirdFragment.this.mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_third, container, false);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = nowtime.format(date);

        //a = (int)(Math.random()*300);

//        listvalue = new ArrayList<String>();
//        listday = new ArrayList<String>();
//        listvaluekorea = new ArrayList<String>();

        btn_all_delete = (Button) view.findViewById(R.id.btn_all_delete);
        tv_empty_data = (TextView) view.findViewById(R.id.tv_empty_data);

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.setMargins(30, 20, 0, 20); //넥서스기준
        //lp.setMargins(180, 20, 0, 20);

//        LinearLayout layout2 = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(880, 20, 0, 20); //넥서스 기준
        //lp2.setMargins(1200, 20, 0, 20); //노트기준

        DBHelper test = new DBHelper(this.getContext());
        database = test.getReadableDatabase();
//
//        String sql = "insert into noisevalue values(null,'"+a+"','"+time+"','위험 수치');";
//        database.execSQL(sql);

        mAdapter = new ListViewAdapter(this.getContext());
        mListView = (ListView) view.findViewById(R.id.listview1);

        sum = 0;
        avg = 0;

//        for(int i = 0; i < listvalue.size(); i++) {
//            mAdapter.addItem(listvalue.get(i), listday.get(i), listvaluekorea.get(i), getResources().getDrawable(R.drawable.delete));
//
//            if(max > Integer.parseInt(listvalue.get(i))){
//
//            }else{
//                max = Integer.parseInt(listvalue.get(i));
//            }
//            sum = sum + Integer.parseInt(listvalue.get(i));
//            avg = sum / listvalue.size();
//
//        }

        selectData();
        mListView.setAdapter(mAdapter);

        setView();


//        tv = new TextView(getActivity());
//        tv.setText("");
//        tv.setText("기록수 : "+ listvalue.size() +"  최대값 : "+ max +"(dB)  평균 : "+ avg +"(dB)");
//        tv.setTextColor(Color.BLACK);
//        tv.setTextSize(15);
//
//        layout.addView(tv, lp);
//        layout2.addView(tv2, lp2);

        mListView.addHeaderView(layout);
//        mListView.addFooterView(layout2);




        mAdapter.notifyDataSetChanged();


        btn_all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());

                builder2.setTitle("전체 삭제 안내").setCancelable(true);

                builder2.setMessage("소음기록을 전체 삭제하시겠습니까?")
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String sql2 = "delete from noisevalue ;";
                                database.execSQL(sql2);

                                mListData.clear();
                                setView();
                                mAdapter.notifyDataSetChanged();

//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        selectData();
//
//                                        mAdapter.clearAllItems();
//
//                                        sum = 0;
//                                        avg = 0;
//                                        max = 0;
//
//                                        for (int i = 0; i < listvalue.size(); i++) {
//                                            mAdapter.addItem(listvalue.get(i), listday.get(i), listvaluekorea.get(i), getResources().getDrawable(R.drawable.delete));
//
//                                            if(max > Integer.parseInt(listvalue.get(i))){
//
//                                            }else{
//                                                max = Integer.parseInt(listvalue.get(i));
//                                            }
//
//                                            sum = sum + Integer.parseInt(listvalue.get(i));
//                                            avg = sum / listvalue.size();
//
//                                        }
//                                        setView();
//                                        mAdapter.notifyDataSetChanged();
//
//                                    }
//                                });
//                                tv.setText("");
//                                tv.setText("기록수 : "+ listvalue.size() +"  최대값 : "+ max +"(dB)  평균 : "+ avg +"(dB)");

                                Toast.makeText(getActivity(), "소음기록이 전체 삭제되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog = builder2.create();
                dialog.show();

            }
        });



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.d("#position->",position+"");
                final ListData mdata = mListData.get(position-1);
                final String value = mdata.ListTime;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("삭제 안내").setCancelable(true);

                builder.setMessage("선택하신 소음기록을 삭제하시겠습니까?")
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String sql2 = "delete from noisevalue where num ='" + mdata.id + "';";
                                database.execSQL(sql2);

                                mListData.remove(position-1);
                                setView();
                                mAdapter.notifyDataSetChanged();
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        selectData();
//
//                                        mAdapter.clearAllItems();
//
//                                        sum = 0;
//                                        avg = 0;
//                                        max = 0;
//
//                                        for (int i = 0; i < listvalue.size(); i++) {
//                                            mAdapter.addItem(listvalue.get(i), listday.get(i), listvaluekorea.get(i), getResources().getDrawable(R.drawable.delete));
//
//                                            if(max > Integer.parseInt(listvalue.get(i))){
//
//                                            }else{
//                                                max = Integer.parseInt(listvalue.get(i));
//                                            }
//
//                                            sum = sum + Integer.parseInt(listvalue.get(i));
//                                            avg = sum / listvalue.size();
//
//                                        }
//                                        setView();
//                                        mAdapter.notifyDataSetChanged();
//
//                                    }
//                                });
//                                tv.setText("");
//                                tv.setText("기록수 : "+ listvalue.size() +"  최대값 : "+ max +"(dB)  평균 : "+ avg +"(dB)");

                                Toast.makeText(getActivity(), "선택하신 소음기록이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog = builder.create();
                dialog.show();

            }
        });

        return view;
    }

    public void setView(){
        if(mAdapter.getCount() == 0){
            tv_empty_data.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }else{
            tv_empty_data.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
    }


    public void selectData(){
        mListData.clear();
        String sql = "select * from noisevalue order by day desc";
        Cursor result = database.rawQuery(sql,null);
        result.moveToFirst();
        while (!result.isAfterLast()){
//            listvalue.add(result.getString(1));
//            listday.add(result.getString(2));
//            listvaluekorea.add(result.getString(3));

            ListData data = new ListData();
            data.setId(result.getInt(0));
            data.setListTime(result.getString(2));
            data.setListValue(result.getString(1));
            mListData.add(data);

            result.moveToNext();
        }
        result.close();
    }


    private class ViewHolder{
        public TextView listvalue;
        public TextView listday;
        public TextView listvaluekorea;
        public ImageView listimg;
    }

    class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
//        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext){
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {return mListData.size();}

        @Override
        public Object getItem(int position) {return mListData.get(position);}

        @Override
        public long getItemId(int position){return position;}

        public void addItem(String listvalue, String listday, String listvaluekorea, Drawable listimg){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.ListValue = listvalue;
            addInfo.ListTime = listday;
            addInfo.ListValueKorea = listvaluekorea;
            addInfo.ListImg = listimg;

            mListData.add(addInfo);
            mAdapter.notifyDataSetChanged();
        }

        public void clearAllItems(){mListData.clear();}

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.noiselist, null);

                holder.listvalue = (TextView) convertView.findViewById(R.id.txtvalue);
                holder.listday = (TextView) convertView.findViewById(R.id.txtdays);
                holder.listimg = (ImageView) convertView.findViewById(R.id.listimg);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);


            holder.listvalue.setText(mData.ListValue);
            holder.listday.setText(mData.ListTime.split(" ")[0] + "\n" +mData.ListTime.split(" ")[1]);
//            holder.listvaluekorea.setText(mData.ListValueKorea);
//            holder.listimg.setImageDrawable(mData.ListImg);

            holder.listimg.getLayoutParams().width = 80;

            return convertView;
        }

    }

}