package fun.masttf.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.masttf.config.AppConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.enums.DateTimePatternEnum;
import fun.masttf.entity.enums.FileUploadEnum;

@Component
public class ImageUtils {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ImageUtils.class);
    @Autowired
    private AppConfig appConfig;
    public Boolean createThumbnail(File file, int thumbnailWidth, int thumbnailHeight, File tarFile) {
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

    public String resetImageHtml(String html) {
        String month = DateUtils.format(new Date(), DateTimePatternEnum.YYYY_MM.getPattern());
        List<String> imaList = getImageList(html);
        for(String img : imaList) {
            resetImage(img, month);
        }
        return month;
    }
    
    private String resetImage(String imagePath, String month){
        if(StringTools.isEmpty(imagePath) || !imagePath.startsWith(Constans.READ_IMAGE_PATH + FileUploadEnum.TEMP.getFolder())) {
            return imagePath;
        }
        // /api/file/getImage/temp/dsafasfas.jpg -> 202303/dsafasfas.jpg
        imagePath = imagePath.replace(Constans.READ_IMAGE_PATH, "");
        String imageFileName = month + "/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);
        File targetFile = new File(appConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + FileUploadEnum.COMMENT_IMAGE.getFolder() + imageFileName);
        try {
            File tempFile = new File(appConfig.getProjectFolder() + imagePath);
            org.apache.commons.io.FileUtils.copyFile(tempFile, targetFile);
            tempFile.delete();
        } catch(IOException e) {
            logger.error("复制图片失败", e);
            return imagePath;
        }
        return imageFileName;
    }
    public List<String> getImageList(String html) {
        List<String> list = new ArrayList<>();
        String regEx = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        while (m.find()) {
            String img = m.group();
            Matcher m2 = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while(m2.find()) {
                String src = m2.group(1);
                list.add(src);
            }
        }
        return list;
    }
}
