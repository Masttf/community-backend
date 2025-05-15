package fun.masttf.utils;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.config.AppConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.FileUploadDto;
import fun.masttf.entity.enums.DateTimePatternEnum;
import fun.masttf.entity.enums.FileUploadEnum;
import fun.masttf.exception.BusinessException;

@Component
public class FileUtils {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtils.class);
    @Autowired
    private AppConfig appConfig;

    public FileUploadDto uploadFile2Local(MultipartFile file, FileUploadEnum typeEnum) {
        try {
            FileUploadDto uploadDto = new FileUploadDto();
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = StringTools.getFileSuffix(originalFileName);
            if(!ArrayUtils.contains(typeEnum.getSuffix(), fileSuffix)){
                throw new BusinessException("文件格式不正确");
            }
            String month = DateUtils.format(new Date(), DateTimePatternEnum.YYYY_MM.getPattern());
            String baseFolder = appConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE;
            String rd = StringTools.getRandomString(Constans.LENGTH_30);
            String fileName = rd + "." + fileSuffix;
            File targetFolder = new File(baseFolder + typeEnum.getFolder() + month + "/");
            
            String localPath = month + "/" + fileName;
            if(typeEnum.equals(FileUploadEnum.AVATAR)){
                targetFolder = new File(baseFolder + typeEnum.getFolder());
                localPath = typeEnum.getFolder() + fileName;
            }
            if(!targetFolder.exists()){
                targetFolder.mkdirs();
            }
            File targetFile = new File(targetFolder, fileName);
            file.transferTo(targetFile);
            uploadDto.setOriginalFileName(originalFileName);
            uploadDto.setLocalPath(localPath);

            // 生成缩略图
            if(typeEnum.equals(FileUploadEnum.COMMENT_IMAGE)){
                String thumbFileName = fileName.replace(".", "_.");
                File thumbFile = new File(targetFolder, thumbFileName);
                Boolean isSuccess = ImageUtils.createThumbnail(targetFile, Constans.LENGTH_200, Constans.LENGTH_200, thumbFile);
                if(!isSuccess){
                    org.apache.commons.io.FileUtils.copyFile(targetFile, thumbFile);
                }
            } else if(typeEnum.equals(FileUploadEnum.AVATAR) || typeEnum.equals(FileUploadEnum.ARTICLE_COVER)){
                ImageUtils.createThumbnail(targetFile, Constans.LENGTH_200, Constans.LENGTH_200, targetFile);
            }
            return uploadDto;
        }catch(BusinessException e){
            logger.error("上传文件失败", e);
            throw e;
        } catch (Exception e){
            logger.error("上传文件失败", e);
            throw new BusinessException("上传文件失败");
        }
    }
}
