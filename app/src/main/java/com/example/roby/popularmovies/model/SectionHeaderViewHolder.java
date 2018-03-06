package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/6/2018.
 */

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView headerSection;
    public SectionHeaderViewHolder(View itemView) {
        super(itemView);
        headerSection = itemView.findViewById(R.id.section_header_id);
    }

    public TextView getHeaderSection() {
        return headerSection;
    }
}
