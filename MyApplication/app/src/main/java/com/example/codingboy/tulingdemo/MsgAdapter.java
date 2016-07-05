package com.example.codingboy.tulingdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by codingBoy on 16/6/29.
 */
public class MsgAdapter extends BaseAdapter {

    List<Data> mlist;
    Context context;

    public MsgAdapter(List<Data> mlist,Context context)
    {
        this.mlist=mlist;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView==null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_view, null);
            viewHolder=new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);

            viewHolder.tv_left = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.tv_right = (TextView) view.findViewById(R.id.right_msg);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        if (mlist.get(position).getType() == mlist.get(position).TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.tv_left.setText(mlist.get(position).getContent());
        } else if (mlist.get(position).getType() == mlist.get(position).TYPE_SEND) {
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.tv_right.setText(mlist.get(position).getContent());
        }

        return view;
    }
    class ViewHolder
    {
        LinearLayout leftLayout,rightLayout;
        TextView tv_left,tv_right;
    }
}
