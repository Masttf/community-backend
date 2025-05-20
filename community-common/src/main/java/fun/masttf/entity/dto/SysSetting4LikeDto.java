package fun.masttf.entity.dto;

import fun.masttf.annotation.VerifyParam;

/*
 * 点赞设置
 */
public class SysSetting4LikeDto {
    /*
     * 点赞阈值
     */
    @VerifyParam(required = true)
    private Integer likeDayCountThresold;

    public Integer getLikeDayCountThresold() {
        return likeDayCountThresold;
    }

    public void setLikeDayCountThresold(Integer likeDayCountThresold) {
        this.likeDayCountThresold = likeDayCountThresold;
    }

}
