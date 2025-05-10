package fun.masttf.entity.dto;

/*
 * 点赞设置
 */
public class SysSetting4LikeDto {
    /*
     * 点赞阈值
     */
    private Integer likeDayCountThresold;

    public Integer getLikeDayCountThresold() {
        return likeDayCountThresold;
    }

    public void setLikeDayCountThresold(Integer likeDayCountThresold) {
        this.likeDayCountThresold = likeDayCountThresold;
    }

}
