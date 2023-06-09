package com.wind.common.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kfg
 * @date 2023/6/7 21:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {

    private List<T> list;

    private long total;

    public static PageData get(List list, long total) {
        return new PageData(list, total);
    }

}
