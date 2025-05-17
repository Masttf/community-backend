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
    @Autowired
    private ImageUtils imageUtils;

    public FileUploadDto uploadFile2Local(MultipartFile file, FileUploadEnum typeEnum, String userId) {
        try {
            FileUploadDto uploadDto = new FileUploadDto();
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = StringTools.getFileSuffix(originalFileName);
            if(!ArrayUtils.contains(typeEnum.getSuffix(), fileSuffix)){
                throw new BusinessException("文件格式不正确");
            }

            String baseFolder = appConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE;
            String fileName = null;
            String localPath = null;
            File targetFolder = null;
            if(typeEnum.equals(FileUploadEnum.AVATAR)){
                if(StringTools.isEmpty(userId)){
                    logger.error("用户ID不能为空");
                    throw new BusinessException("用户ID不能为空");
                }
                fileName = userId + "." + Constans.AVATAR_SUFFIX;
                targetFolder = new File(baseFolder + typeEnum.getFolder());
                localPath = typeEnum.getFolder() + fileName;
            }else{
                String month = DateUtils.format(new Date(), DateTimePatternEnum.YYYY_MM.getPattern());
                fileName = StringTools.getRandomString(Constans.LENGTH_30) + "." + fileSuffix;
                targetFolder = new File(baseFolder + typeEnum.getFolder() + month + "/");
                localPath = typeEnum.getFolder() + month + "/" + fileName;
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
                Boolean isSuccess = imageUtils.createThumbnail(targetFile, Constans.LENGTH_200, Constans.LENGTH_200, thumbFile);
                if(!isSuccess){
                    org.apache.commons.io.FileUtils.copyFile(targetFile, thumbFile);
                }
            } else if(typeEnum.equals(FileUploadEnum.AVATAR) || typeEnum.equals(FileUploadEnum.ARTICLE_COVER)){
                imageUtils.createThumbnail(targetFile, Constans.LENGTH_200, Constans.LENGTH_200, targetFile);
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

    public void deleteFile(String filePath) {
        if(StringTools.isEmpty(filePath)){
            return;
        }
        String baseFolder = appConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE;
        File file = new File(baseFolder + filePath);
        if(file.exists()){
            file.delete();
        }
    }
}
