package com.wetalk.summersnow.summersnow.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
   private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            this.totalPage = totalCount / size;
        } else this.totalPage = totalCount / size + 1;

        this.page = page;
        this.pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0)
                this.pages.add(0, page - i);
            if (page + i <=  this.totalPage) {
                this.pages.add(page + i);
            }
        }

        if (page == 1) {
            this.showPrevious = false;
        } else this.showPrevious = true;
        if (page ==  this.totalPage) {
            this.showNext = false;
        } else this.showNext = true;

        if (pages.contains(1)) {
            this.showFirstPage = false;
        } else this.showFirstPage = true;

        if (pages.contains( this.totalPage)) {
            this.showEndPage = false;
        } else this.showEndPage = true;
    }
}
