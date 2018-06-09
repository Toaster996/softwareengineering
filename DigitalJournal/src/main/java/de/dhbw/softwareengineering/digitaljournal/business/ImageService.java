package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import de.dhbw.softwareengineering.digitaljournal.persistence.UploadImageRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements AbstractService {

    private final UploadImageRepository repository;

    public ImageService(UploadImageRepository repository) {
        this.repository = repository;
    }

    public Image saveImage(byte[] file, String journalId) {
        Image image = new Image();

        image.setImage(file);
        image.setJournalid(journalId);
        image.setId(UUIDGenerator.generateUniqueUUID(repository));

        return repository.save(image);
    }

    public byte[] getImageByJournalId(String journalId, int image) {
        List<Image> images = repository.findAllByJournalid(journalId);

        if (images.size() <= image) {
            return new byte[0];
        }

        return images.get(image).getImage();
    }

    public void deleteAllByJournalId(String journalId) {
        repository.deleteAllByJournalid(journalId);
    }
}
