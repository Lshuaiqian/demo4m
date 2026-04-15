package org.example.DTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageVO <T>{
    private List<T> records;
    private Long total;
    private Long pages;
    private Long current;
    private Long size;
    //ipagevo转化为pagevo
    public static <T> PageVO<T> convert(IPage<T> iPage) {
        PageVO<T> vo = new PageVO<>();
        vo.setRecords(iPage.getRecords());
        vo.setTotal(iPage.getTotal());
        vo.setCurrent(iPage.getCurrent());
        vo.setSize(iPage.getSize());
        vo.setPages(iPage.getPages());
        return vo;
    }
}
