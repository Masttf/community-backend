package fun.masttf.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.config.WebConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.FileUploadDto;
import fun.masttf.entity.enums.FileUploadEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.utils.StringTools;

@RestController
@RequestMapping("/file")
public class FileController extends ABaseController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileController.class);
    @Autowired
    private WebConfig webConfig;

    //上传临时图片
    //放在images/temp
    @RequestMapping("uploadImage")
    public ResponseVo<Object> uploadImage(MultipartFile file) {
        if (file == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        String fileName = file.getOriginalFilename();
        String suffix = StringTools.getFileSuffix(fileName);
        if(!ArrayUtils.contains(Constans.IMAGE_SUFFIX, suffix)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String fileRealName = StringTools.getRandomString(Constans.LENGTH_30) + "." + suffix;
        copyFile(file, FileUploadEnum.TEMP.getFolder(), fileRealName);
        FileUploadDto imageVo = new FileUploadDto();
        imageVo.setOriginalFileName(fileName);
        imageVo.setLocalPath(FileUploadEnum.TEMP.getFolder() + fileRealName);
        return getSuccessResponseVo(imageVo);
    }

    @RequestMapping("getImage/{imageFolder}/{imageName}")
    public void getImage(HttpServletResponse response,
                        @PathVariable("imageFolder") String imageFolder,
                        @PathVariable("imageName") String imageName) {
        readImage(response, FileUploadEnum.COMMENT_IMAGE.getFolder() + imageFolder + "/", imageName);
    }
    @RequestMapping("getAvatar/{userId}")
    public void getAvatar(HttpServletResponse response,
                        @PathVariable("userId") String userId) {
        String avatarFolderPath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + FileUploadEnum.AVATAR.getFolder();
        String avatarPath = avatarFolderPath + userId + Constans.AVATAR_SUFFIX;
        File avatarFolder = new File(avatarFolderPath);
        if (!avatarFolder.exists()) {
            throw new BusinessException("头像文件夹不存在");
            // avatarFolder.mkdirs();
        }
        File avatarFile = new File(avatarPath);
        String imageName = userId + Constans.AVATAR_SUFFIX;
        if (!avatarFile.exists()) {
            imageName = Constans.AVATAR_DEFAULT;
        }
        readImage(response, FileUploadEnum.AVATAR.getFolder(), imageName);
    }
    //放在images/{imageFolder}
    private void copyFile(MultipartFile file, String imageFolder, String fileRealName) {
        try {
            String folderPath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_IMAGES + imageFolder;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File uploadFile = new File(folderPath + fileRealName);
            file.transferTo(uploadFile);
            return ;
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    private void readImage(HttpServletResponse response, String imageFolder, String imageName) {
        ServletOutputStream sos = null;
        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            if(StringTools.isEmpty(imageName) || StringTools.isEmpty(imageFolder)) {
                return ;
            }
            String suffix = StringTools.getFileSuffix(imageName);
            String filePath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + imageFolder + imageName;
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            if(!imageFolder.equals(FileUploadEnum.AVATAR.getFolder())) {
                response.setContentType("Cache-Control: max-age=2592000");
            }
            response.setContentType("image/" + suffix);
            in = new FileInputStream(file);
            sos = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            sos.write(baos.toByteArray());
        } catch (Exception e) {
            logger.error("读取图片失败", e);
        } finally {
            try {
                if (sos != null) {
                    sos.close();
                }
                if (in != null) {
                    in.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                logger.error("关闭流失败", e);
            }
        }
    }
}

/* 废案
@RequestMapping("uploadImage")
    public ResponseVo<Object> uploadImage(MultipartFile file) {
        if (file == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        String fileName = file.getOriginalFilename();
        String suffix = StringTools.getFileSuffix(fileName);
        if(!ArrayUtils.contains(Constans.IMAGE_SUFFIX, suffix)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String path = copyFile(file);
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("fileName", path);
        return getSuccessResponseVo(fileMap);
    }

    @RequestMapping("getImage/{imageFolder}/{imageName}")
    public void getImage(HttpServletResponse response,
                        @PathVariable("imageFolder") String imageFolder,
                        @PathVariable("imageName") String imageName) {
        readImage(response, imageFolder, imageName);
    }
    @RequestMapping("getAvatar/{userId}")
    public void getAvatar(HttpServletResponse response,
                        @PathVariable("userId") String userId) {
        String avatarFoldName = Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_AVATAR_NAME;
        String avatarPath = webConfig.getProjectFolder() + avatarFoldName + userId + Constans.AVATAR_SUFFIX;
        File avatarFolder = new File(avatarFoldName);
        if (!avatarFolder.exists()) {
            throw new BusinessException("头像文件夹不存在");
            // avatarFolder.mkdirs();
        }
        File avatarFile = new File(avatarPath);
        String imageName = userId + Constans.AVATAR_SUFFIX;
        if (!avatarFile.exists()) {
            imageName = Constans.AVATAR_DEFAULT;
        }
        readImage(response, , imageName);
    }
    private String copyFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String suffix = StringTools.getFileSuffix(fileName);
            String fileRealName = StringTools.getRandomString(Constans.LENGTH_30) + "." + suffix;
            String folderPath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_TEMP;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File uploadFile = new File(folderPath + fileRealName);
            file.transferTo(uploadFile);
            return Constans.FILE_FOLDER_TEMP + fileRealName;
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    private void readImage(HttpServletResponse response, String imageFolder, String imageName) {
        ServletOutputStream sos = null;
        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            if(StringTools.isEmpty(imageFolder) || StringTools.isEmpty(imageName)) {
                return ;
            }
            String suffix = StringTools.getFileSuffix(imageName);
            String filePath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_IMAGE + imageFolder + "/" + imageName;
            if(Constans.FILE_FOLDER_TEMP_2.equals(imageFolder)) {
                filePath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + imageFolder + "/" + imageName;
            } else if(imageFolder.contains(Constans.FILE_FOLDER_AVATAR_NAME)) {
                filePath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + imageFolder + imageName;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            if(!Constans.FILE_FOLDER_AVATAR_NAME.equals(imageFolder)) {
                response.setContentType("Cache-Control: max-age=2592000");
            }
            response.setContentType("image/" + suffix);
            in = new FileInputStream(file);
            sos = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            sos.write(baos.toByteArray());
        } catch (Exception e) {
            logger.error("读取图片失败", e);
        } finally {
            try {
                if (sos != null) {
                    sos.close();
                }
                if (in != null) {
                    in.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                logger.error("关闭流失败", e);
            }
        }
    }
 */
