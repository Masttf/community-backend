package fun.masttf.entity.query;

import fun.masttf.entity.enums.PageSize;
public class SimplePage {
    private Integer pageNo;
    private Integer countTotal;
    private Integer pageSize;
    private Integer pageTotal;
    private Integer start;
    private Integer end;

    public SimplePage() {

    }

    public SimplePage(Integer pageNo, Integer countTotal, Integer pageSize) {
        if (null == pageNo) {
            pageNo = 0;
        }
        this.pageNo = pageNo;
        this.countTotal = countTotal;
        this.pageSize = pageSize;
        action();
    }

    public SimplePage(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public void action() {
        if (this.pageSize <= 0) {
            this.pageSize = PageSize.SIZE20.getSize();
        }
        if (this.countTotal > 0) {
            this.pageTotal = (int) Math.ceil((double) this.countTotal / this.pageSize);
        } else {
            this.pageTotal = 1;
        }

        if (this.pageNo <= 1) {
            this.pageNo = 1;
        }
        if (this.pageNo > this.pageTotal) {
            this.pageNo = this.pageTotal;
        }
        this.start = (this.pageNo - 1) * this.pageSize;
        this.end = this.pageNo * this.pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

}
