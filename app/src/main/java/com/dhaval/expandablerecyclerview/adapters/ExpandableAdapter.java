package com.dhaval.expandablerecyclerview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.dhaval.expandablerecyclerview.R;
import com.dhaval.expandablerecyclerview.expandable.ChildViewHolder;
import com.dhaval.expandablerecyclerview.expandable.ExpandableRecyclerAdapter;
import com.dhaval.expandablerecyclerview.expandable.ParentListItem;
import com.dhaval.expandablerecyclerview.expandable.ParentViewHolder;
import com.dhaval.expandablerecyclerview.models.DataModel;

import java.util.List;

/**
 * Created by Dhaval Jayani on 26/02/2020.
 */

public class ExpandableAdapter extends ExpandableRecyclerAdapter<ExpandableAdapter
        .SectionViewHolder, ExpandableAdapter.DescriptionViewHolder> {
    private LayoutInflater mInflator;
    private Context mContext;

    public ExpandableAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public SectionViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View sectionCategoryView = mInflator.inflate(R.layout.template_section, parentViewGroup, false);
        return new SectionViewHolder(sectionCategoryView);
    }

    @Override
    public DescriptionViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.template_description_list, childViewGroup, false);
        return new DescriptionViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(SectionViewHolder sectionViewHolder, int position,
                                       ParentListItem parentListItem) {
        DataModel.Data sectionData = (DataModel.Data) parentListItem;
        sectionViewHolder.bind(mContext, sectionData);
    }

    @Override
    public void onBindChildViewHolder(DescriptionViewHolder descriptionViewHolder, int position,
                                      Object childListItem) {
        DataModel.DescriptionList descriptionData = (DataModel.DescriptionList) childListItem;
        descriptionViewHolder.bind(mContext, descriptionData);
    }

    class SectionViewHolder extends ParentViewHolder {
        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 0f;

        private AppCompatImageView imageViewExpandCollapse;
        private AppCompatTextView section_name;
        private ConstraintLayout constraintSection;

        public SectionViewHolder(View itemView) {
            super(itemView);
            section_name = itemView.findViewById(R.id.section_name);
            imageViewExpandCollapse = itemView.findViewById(R.id.imageViewExpandCollapse);
            constraintSection = itemView.findViewById(R.id.constraintSection);
            imageViewExpandCollapse.setImageResource(R.drawable.ic_expand);
            imageViewExpandCollapse.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }

        public void bind(Context context, DataModel.Data data) {
            section_name.setText(data.getSectionName());
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);

            if (expanded) {
                imageViewExpandCollapse.setRotation(ROTATED_POSITION);
            } else {
                imageViewExpandCollapse.setRotation(INITIAL_POSITION);
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);

            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                constraintSection.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                section_name.setTextColor(mContext.getResources().getColor(R.color.darkGrey));
                imageViewExpandCollapse.setImageResource(R.drawable.ic_expand);
                imageViewExpandCollapse.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary),
                        android.graphics.PorterDuff.Mode.SRC_IN);

                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                imageViewExpandCollapse.setImageResource(R.drawable.ic_collapse);
                imageViewExpandCollapse.setColorFilter(ContextCompat.getColor(mContext, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                constraintSection.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                section_name.setTextColor(mContext.getResources().getColor(R.color.white));
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            imageViewExpandCollapse.startAnimation(rotateAnimation);

        }

    }

    class DescriptionViewHolder extends ChildViewHolder {
        private AppCompatTextView textViewDescription;
        private ConstraintLayout constraintDescription;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            constraintDescription = itemView.findViewById(R.id.constraintDescription);
        }

        public void bind(final Context context, final DataModel.DescriptionList descriptionData) {
            textViewDescription.setText(descriptionData.getDescription());

            constraintDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, descriptionData.getDescription(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
