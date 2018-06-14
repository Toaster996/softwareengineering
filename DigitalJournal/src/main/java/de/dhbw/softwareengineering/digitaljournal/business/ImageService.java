package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.persistence.UploadImageRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.JournalNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements AbstractService {

    private final UploadImageRepository repository;
    private final JournalService journalService;

    public ImageService(UploadImageRepository repository, JournalService journalService) {
        this.repository = repository;
        this.journalService = journalService;
    }

    public Image saveImage(byte[] file, String journal) {
        Image image = new Image();

        image.setId(UUIDGenerator.generateUniqueUUID(repository));
        try {
            Journal j = journalService.findById(journal);
            j.setImages(j.getImages()+1);
            journalService.update(j);
        } catch (JournalNotFoundException e) {
            e.printStackTrace();
        }
        image.setJournalid(journal);
        image.setImage(file);

        return repository.save(image);
    }

    public byte[] getImageByJournalId(String journal, int image) {
        List<Image> images = repository.findAllByJournalid(journal);

        if (images.size() <= image) {
            return new byte[1];
        }

        return images.get(image).getImage();
    }

    public void deleteAllByJournalId(String journalId) {
        List<Image> images = findAllByJournalId(journalId);

        try {
            Journal j = journalService.findById(journalId);
            for(int i = 0; i < images.size(); i++){
                j.setImages(j.getImages()-1);
            }
            journalService.update(j);
        } catch (JournalNotFoundException e) {
            e.printStackTrace();
        }

        repository.deleteAllByJournalid(journalId);
    }

    public void deleteImageById(String journal, int image) {
        List<Image> images = repository.findAllByJournalid(journal);

        if (images.size() <= image) {
            return;
        }

        try {
            Journal j = journalService.findById(journal);
            j.setImages(j.getImages()-1);
            journalService.update(j);
        } catch (JournalNotFoundException e) {
            e.printStackTrace();
        }

        repository.delete(images.get(image));
    }

    public List<Image> findAllByJournalId(String journal) {
        return repository.findAllByJournalid(journal);
    }
}
