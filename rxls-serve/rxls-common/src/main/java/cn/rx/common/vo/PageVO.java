package cn.rx.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 分页查询
 */
@Data
public class PageVO<T> {

    /**
     * 总量
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> dataList;

    public PageVO() {
        this.total = 0L;
        this.dataList = new ArrayList<>();
    }

    public void setTotal(Long total) {
        if(Objects.isNull(total)){
            this.total = 0L;
            return;
        }
        this.total = total;
    }

    public void setDataList(List<T> lists) {
        if(Objects.isNull(lists)){
            this.dataList = new ArrayList<>();
            return;
        }
        this.dataList = lists;
    }

}

