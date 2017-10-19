package com.example.android.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by husky on 17-10-17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    Context context;
    private List<Person> mPersonLists ;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View personView;
        TextView personName;
        TextView personNumber;

        public ViewHolder(View view){
            super(view);
            personView = view;
            personName = (TextView)view.findViewById(R.id.name);
            personNumber = (TextView)view.findViewById(R.id.number);
        }
    }

    public PersonAdapter(Context context,List<Person> personList){
        this.context = context;
        mPersonLists = personList;
    }
    



    //实现点击事件
    //具体实现了一个RecyclerView的item的跳转页面
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.personView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Person person = mPersonLists.get(position);

                Intent intent = new Intent(v.getContext(),CallActivity.class);
                intent.putExtra("name",person.getName());
                intent.putExtra("id",person.getId());

                intent.putExtra("number",person.getNumber());
                v.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {
        Person person = mPersonLists.get(position);
        holder.personName.setText(person.getName());
        holder.personNumber.setText(person.getNumber()+"");
    }

    @Override
    public int getItemCount() {
        return mPersonLists.size();
    }
}
