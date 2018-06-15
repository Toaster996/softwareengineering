package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import de.dhbw.softwareengineering.digitaljournal.persistence.UploadImageRepository;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.JournalNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {

    private ImageService service;
    private UploadImageRepository repository;
    private JournalService journalService;

    @Before
    public void setUp() {
        repository      = mock(UploadImageRepository.class);
        journalService  = mock(JournalService.class);
        service    = new ImageService(repository, journalService);
    }

    @Test
    public void saveImage() throws JournalNotFoundException {
        Image img = TestingData.createImage();
        when(repository.save(any(Image.class))).thenReturn(img);
        when(journalService.findById(any(String.class))).thenReturn(TestingData.createJournal());
        assertEquals(img, service.saveImage(new byte[2], "1234"));
    }

    @Test
    public void getImageByJournalIdSuccess() {
        Image img = TestingData.createImage();
        List<Image> images = new ArrayList<>();
                    images.add(img);

        when(repository.findAllByJournalid(any(String.class))).thenReturn(images);
        assertEquals(img.getImage(), service.getImageByJournalId("1234",0));
    }

    @Test
    public void getImageByJournalIdFail() {
        List<Image> images = new ArrayList<>();

        when(repository.findAllByJournalid(any(String.class))).thenReturn(images);
        assertEquals(0, service.getImageByJournalId("1234",0)[0]);
    }

    @Test
    public void deleteAll() {
        service.deleteAllByJournalId("1234");

        verify(repository, times(1)).deleteAllByJournalid("1234");
    }
}