package com.example.todolist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private ArrayList<ToDo> todoList = new ArrayList<>();

    private ToDoDB db;

    public ToDoAdapter(ToDoDB db) {
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.task_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDo item = todoList.get(position);

        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //체크상태면 1 아니면 0
                if(isChecked) {
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }
    /**
     * 체크상태 boolean으로 변경
     * @param n 상태값
     * @return
     */
    private boolean toBoolean(int n) {
        return n != 0;
    }
    /**
     * 리스트에 데이터 담기
     * @param todoList 할일 리스트
     */
    public void setTasks(ArrayList<ToDo> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }
    /**
     * 할일 삭제
     * @param position 할일 위치
     */
    public void removeItem(int position) {
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    /**
     * 할일 갯수
     * @return 갯수
     */
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCheckBox = itemView.findViewById(R.id.m_check_box);
        }
    }
}
