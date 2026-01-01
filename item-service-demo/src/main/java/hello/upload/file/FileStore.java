package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;
    public List<UploadFile> storeFiles (List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> storeFileResult = new ArrayList<UploadFile>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) storeFileResult.add(storeFile(multipartFile));
        }
        return storeFileResult;
    }

    private UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = creatStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename,storeFileName);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String creatStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+ext;

    }

    //확장자 제거한 파일명
    private String extractExt(String originalFilename) {
        int pos = originalFilename.indexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
