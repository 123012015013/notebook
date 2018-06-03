package com.example.android.notebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.notebook.data.Note;
import com.example.android.notebook.database.NoteUtil;


/**
 *
 */

public class ColorDialogFragment extends DialogFragment {


    private RecyclerView mRecyclerView;
    private static Note mNote;
    private OnColorChangeListener mOnColorChangeListener;

    public void setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        mOnColorChangeListener = onColorChangeListener;
    }

    public static ColorDialogFragment newInstance(Note note){
        mNote = note;
        return new ColorDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_colorselect,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(lm);

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setAdapter(new GridViewAdapter());

        return view;

    }



    public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridHolder>{

        private int[] colors = new int[]{
                R.color.green,R.color.blue,R.color.red,R.color.yellow,R.color.pink,R.color.purple
        };

        @Override
        public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.color_item,parent,false);
            return new GridHolder(view);
        }

        @Override
        public void onBindViewHolder(GridHolder holder, int position) {
            holder.bind(colors[position],position);
        }

        @Override
        public int getItemCount() {
            return colors.length;
        }

        public  class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private ImageView mImageView;
            private int mId;
            private int mResColorId;
            public GridHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView;
                mImageView.setOnClickListener(this);
            }

            public void bind(int resColorId,int id){
                mImageView.setBackgroundColor(getResources().getColor(resColorId));
                this.mId = id;
                mResColorId = resColorId;
            }

            @Override
            public void onClick(View v) {

                mNote.setColor(mResColorId);
                NoteUtil.getsNoteUtil(getContext()).updateNote(mNote);
                if(mOnColorChangeListener != null)
                {
                    mOnColorChangeListener.onColorChange();
                }
                dismiss();
            }
        }
    }


}
