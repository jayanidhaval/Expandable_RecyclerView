package com.dhaval.expandablerecyclerview.models;

import com.dhaval.expandablerecyclerview.expandable.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhaval Jayani on 26/02/2020.
 */

public class DataModel
{
    @SerializedName("data")
    @Expose
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public class Data implements ParentListItem
    {
        @SerializedName("section_id")
        @Expose
        private String sectionId;

        @SerializedName("section_name")
        @Expose
        private String sectionName;

        @SerializedName("description_list")
        @Expose
        private List<DescriptionList> descriptionLists = new ArrayList<>();

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public List<DescriptionList> getDescriptionLists() {
            return descriptionLists;
        }

        public void setDescriptionLists(List<DescriptionList> descriptionLists) {
            this.descriptionLists = descriptionLists;
        }

        @Override
        public List<?> getChildItemList() {
            return descriptionLists;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }
    }

    public class DescriptionList
    {
        @SerializedName("description_id")
        @Expose
        private String descriptionId;

        @SerializedName("description")
        @Expose
        private String description;

        public String getDescriptionId() {
            return descriptionId;
        }

        public void setDescriptionId(String descriptionId) {
            this.descriptionId = descriptionId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
