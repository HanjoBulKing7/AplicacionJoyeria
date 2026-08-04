package com.jewelry.managementsystem.factory;

import org.springframework.data.domain.*;

import java.util.List;

public class TestDataFactory {

    public static Pageable pageable(){
        return PageRequest.of(
                0,
                10,
                Sort.by("id"));
    }

    public static <T> Page<T> page(List<T> list){
        return new PageImpl<T>(
                list,
                pageable(),
                list.size());
    }

    public static <T> Page<T> emptyPage() {
        return Page.empty();
    }
}
