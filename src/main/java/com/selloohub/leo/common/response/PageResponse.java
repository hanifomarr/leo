package com.selloohub.leo.common.response;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> {

    private final List<T> items;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private PageResponse(List<T> items, int page, int size, long totalElements, int totalPages) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<T> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
