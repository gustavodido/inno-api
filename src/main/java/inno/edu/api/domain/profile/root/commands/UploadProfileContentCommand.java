package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadProfileContentCommand {
    private final StorageService storageService;

    public UploadProfileContentCommand(StorageService storageService) {
        this.storageService = storageService;
    }

    public void run(UUID id, MultipartFile file) {
        storageService.save(id, file);
    }
}
