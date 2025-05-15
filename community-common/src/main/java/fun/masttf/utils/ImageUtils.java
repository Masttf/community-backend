package fun.masttf.utils;

import java.io.File;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static Boolean createThumbnail(File file, int thumbnailWidth, int thumbnailHeight, File tarFile) {
        try{
            BufferedImage src = ImageIO.read(file);
            // thumbnailWidth缩略图的宽度，height高度
            int sorceW = src.getWidth();
            int sorceH = src.getHeight();
            // 小于指定高宽不压缩
            if(sorceW <= thumbnailWidth) {
                return false;
            }
            //
            int height = sorceH; //目标文件高度
            if(sorceW > thumbnailWidth) {//如果宽度大于指定宽度
                height = sorceH * thumbnailWidth / sorceW;
            } else{ //如果宽度小于指定宽度
                thumbnailWidth = sorceW;
                height = sorceH;
            }  
            //生成宽度150的缩略图
            BufferedImage dst = new BufferedImage(thumbnailWidth, height, BufferedImage.TYPE_INT_RGB);
            Image scaleImage = src.getScaledInstance(thumbnailWidth, height, Image.SCALE_SMOOTH);
            Graphics2D g = dst.createGraphics();
            g.drawImage(scaleImage, 0, 0, thumbnailWidth, height, null);
            g.dispose();
            int resultH = dst.getHeight();
            //高度过大的，剪裁图片
            if(resultH > thumbnailHeight) {
                resultH = thumbnailHeight;
                dst = dst.getSubimage(0, 0, thumbnailWidth, resultH);
            }
            ImageIO.write(dst, "jpeg", tarFile);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
