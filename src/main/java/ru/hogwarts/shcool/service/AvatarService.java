package ru.hogwarts.shcool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.shcool.model.Avatar;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.AvatarRepository;
import ru.hogwarts.shcool.repository.StudentRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    Logger LOGGER = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        LOGGER.info("Was invoked method for upload avatar for student.");
        Student student = studentRepository.findById(studentId).orElseThrow();
        Path filePath = Path.of(avatarDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ){
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId) {
        LOGGER.info("Was invoked method for find avatar for student.");
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        LOGGER.info("Was invoked method for get all avatars.");
        if (pageNumber <= 0 || pageNumber > avatarRepository.count() / pageSize) {
            LOGGER.error("There is not page with page number = " + pageNumber);
            throw new RuntimeException();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
