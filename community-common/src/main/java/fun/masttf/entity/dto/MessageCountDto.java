package fun.masttf.entity.dto;

/*
 * Message mapper使用
 */
public class MessageCountDto {

    private Integer messageType;
    private Integer count;
    public Integer getMessageType() {
        return messageType;
    }
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
        
}
