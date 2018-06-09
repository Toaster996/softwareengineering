package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.business.FriendService;
import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.ImageService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.business.SharedJournalService;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JournalControllerTest {
    private static final String TO_LONG_NAME = "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nulla maxime vel aliquam aspernatur similique autem, laboriosam adipisci laborum assumenda ducimus eius maiores atque culpa provident laudantium facilis voluptate magni cum cumque explicabo! Repellendus laboriosam saepe beatae ullam, architecto laborum accusantium dolores id enim placeat delectus porro tempora cumque dolorum totam ipsa expedita alias molestias! Est numquam inventore, voluptates cumque accusantium natus doloremque dolorem deserunt, dolorum assumenda maiores enim! Nisi dolor mollitia quas voluptas quibusdam consequuntur distinctio tempore? Rem blanditiis nobis quasi voluptas assumenda ratione, veritatis voluptatem similique magni illum, sunt al";
    private static final String TO_LONG_CONTENT = "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ipsum cupiditate provident eum, et, nobis ea voluptatum molestiae aperiam ratione vel consequuntur, ab rem perspiciatis. Molestias cum nisi illo provident eveniet quaerat praesentium iste autem? Laudantium consequatur mollitia provident enim corrupti sit sunt odio, delectus rem porro dolorum quidem dolor ipsa, quod distinctio unde odit quis eveniet nobis dignissimos maiores maxime omnis inventore perferendis. Facere dicta totam rem eaque dolores, culpa dolorum atque tenetur perspiciatis nisi excepturi. Esse cum corporis voluptatem officia repellendus temporibus consectetur rerum doloribus deserunt voluptas, aliquam vitae sit unde similique beatae doloremque ad iste nobis ex? Quas, commodi alias? Possimus, tempora necessitatibus maxime esse quaerat eligendi a nesciunt blanditiis magnam asperiores perspiciatis quia aliquid nihil quas minima at officia ullam voluptate ab id hic repudiandae saepe harum. Quod nesciunt commodi temporibus aspernatur veritatis amet quos illum? Quaerat tempora voluptatem neque mollitia, voluptates nihil iure aspernatur rem repudiandae labore nam obcaecati harum corporis? Suscipit magni eum sunt accusamus tenetur tempora nam, qui neque temporibus eveniet. Incidunt asperiores placeat repellendus iure, aut fugiat fuga optio sequi eveniet labore adipisci, cumque reprehenderit magnam quod nemo minima harum aliquam est maiores atque consequuntur excepturi aliquid reiciendis? Magni minus voluptate nesciunt ut adipisci quod! Fuga minus nostrum omnis voluptas enim aperiam vero sunt aspernatur, earum magnam iure dolorem quod totam impedit quia ipsam. Voluptate, laborum rerum quo odit vitae maxime eos cum tenetur tempora iure adipisci ratione porro inventore! Sapiente ab, recusandae, voluptatem et hic fugiat magni, necessitatibus quaerat itaque placeat atque iusto doloribus? Neque quaerat obcaecati pariatur quia fugiat, accusantium culpa commodi voluptatibus quas maxime molestias dolorum tempore in distinctio repellendus dolorem dolores sint dignissimos totam, sequi enim vel mollitia explicabo. Enim deleniti, quia quidem non blanditiis voluptatum quaerat consequuntur autem incidunt distinctio consequatur facere a repellendus corporis aperiam voluptatem cum quam, totam omnis, placeat nobis quisquam laudantium maiores? Nulla soluta quod dolor illum magnam neque fuga veritatis repellendus, dignissimos placeat ipsa harum magni quos quibusdam voluptates nostrum? Dolorum, ratione ipsa? Commodi ratione quasi adipisci consequatur vero voluptatem necessitatibus maiores officia. Doloribus quasi modi, ducimus, laborum expedita illum omnis sed accusamus labore, sapiente amet nam! Hic laborum et nostrum ratione, quos nisi reprehenderit qui velit commodi architecto aliquid dolor natus temporibus in rem ea harum molestiae ipsam illum sed. At magnam numquam porro culpa dolore dignissimos ea nisi eveniet nam et beatae adipisci in, labore velit cum odio, rem optio ut, praesentium eaque sed temporibus voluptates maxime. Hic magni tenetur sit placeat explicabo in impedit omnis quis reiciendis inventore, modi, quod, doloribus ea maxime optio. Nemo aspernatur nobis velit nulla odit. Expedita vitae consequatur nostrum porro maiores quaerat nam dignissimos provident. Sint, molestiae? Quod molestias consequatur quasi error eos minus labore sint amet iste in aut corrupti dolores eius, enim dolor expedita, voluptatum praesentium perferendis? Exercitationem, reprehenderit est non saepe esse reiciendis laboriosam beatae excepturi, blanditiis ad nemo, rerum iusto molestiae repellendus accusamus quae! Voluptatibus incidunt beatae perferendis ad exercitationem recusandae dolores magnam iste nisi? Possimus nostrum magni, voluptates vel consequuntur in ratione et deserunt perferendis temporibus sint, eaque quaerat illo quibusdam ipsa quo id hic iste! Excepturi quibusdam vel reprehenderit ullam nemo corrupti, repudiandae ratione obcaecati quisquam enim numquam fugiat voluptates. Exercitationem, inventore aliquam. Nesciunt dolorem dolorum molestiae necessitatibus provident nisi, possimus voluptatem tempora beatae optio a illo doloribus, aperiam laboriosam officiis voluptate consequatur ipsa quis? Quos nobis autem ea quibusdam quidem fugiat adipisci eligendi possimus iste nisi asperiores eos facere temporibus dolores pariatur, accusantium obcaecati qui commodi quasi vitae ratione sint praesentium cum? Mollitia fuga quod voluptatum explicabo aliquid qui repellat officiis harum provident magni ipsum nam quibusdam possimus natus esse consequatur porro beatae, odit placeat perspiciatis a reprehenderit aspernatur. Aliquid facere numquam fuga quae minima voluptas. Minus reiciendis cumque numquam accusamus quos, nihil modi. Eum earum illo dolorem perspiciatis dolore assumenda nostrum sunt commodi, iure minima qui. Repellat rem magnam reiciendis quasi facere. Eos vel accusamus animi nisi? Laudantium, molestiae. Nulla molestias cum molestiae deleniti minus asperiores, hic adipisci expedita similique nesciunt temporibus animi atque eum repellendus placeat repudiandae itaque laborum corporis eveniet sit rem? Et, eveniet. Fugit, aut qui quisquam hic sed ducimus, magnam blanditiis cupiditate odio nulla esse ab pariatur eius cum voluptatum quibusdam ut aliquam explicabo totam aliquid assumenda! Praesentium aspernatur nesciunt voluptatem aperiam suscipit ad voluptas exercitationem est! Animi nemo dolor nobis modi blanditiis voluptatibus voluptate excepturi ad id error non dolorem fugiat possimus dignissimos quaerat quas, impedit neque aspernatur nesciunt temporibus! Amet voluptate temporibus facilis nesciunt alias! Soluta, molestiae est nemo minima sunt consectetur placeat voluptatibus libero beatae dolore ut earum, nihil, quis nostrum aliquam hic in distinctio voluptatem deserunt ex. Aut, rem est! Reiciendis sit voluptas explicabo laboriosam, mollitia et labore vel doloremque officiis provident! Necessitatibus explicabo eum iusto? Laboriosam consequuntur, adipisci reprehenderit possimus unde at veniam officiis blanditiis aut hic neque minima tenetur deleniti molestias aliquam exercitationem quibusdam consectetur, obcaecati autem necessitatibus pariatur cumque nemo laborum. Dolor nihil, nam esse doloribus hic maxime in error exercitationem est et at ex itaque. Eum cupiditate laudantium tempore distinctio, corporis similique vitae impedit explicabo assumenda quod repellat quaerat veritatis sit, quos accusantium accusamus, nobis doloremque perspiciatis magni ex dolorum error totam laborum quia. Eius cumque non, aliquam vel molestiae obcaecati aut aspernatur. Porro accusantium voluptate vel voluptatem veritatis debitis, non possimus qui, enim nulla perspiciatis! Cum, velit tenetur nulla debitis perspiciatis, saepe repudiandae earum neque non, dolorum vel ad quis itaque animi sunt a voluptas eligendi totam minus quasi maxime? Beatae cupiditate facere voluptatum, quos repudiandae vitae perferendis reprehenderit veritatis consequuntur fuga in minima, facilis omnis aspernatur, debitis nihil? Eos corrupti ab nesciunt a eum non in quisquam facere. Vero ipsa necessitatibus iure aliquid quae at ut facilis quo? Quis magnam adipisci soluta ex tempora magni eius fugiat quas voluptatum laudantium minus eum incidunt illum quasi voluptates sequi ab, saepe ullam. Quae, ad eius porro ipsum minima deserunt distinctio suscipit, vero id saepe, quis non tenetur. Quam at harum nihil, doloribus fugiat et debitis, saepe laborum dolorum eos id impedit repudiandae cum illum excepturi!";
    private MockMvc mockMvc;
    private JournalService journalService;
    private GoalService goalService;
    private FriendService friendService;
    private ImageService imageService;
    private SharedJournalService sharedJournalService;

    @Before
    public void setup() {
        journalService  = mock(JournalService.class);
        goalService     = mock(GoalService.class);
        friendService   = mock(FriendService.class);
        imageService    = mock(ImageService.class);
        sharedJournalService = mock(SharedJournalService.class);

        JournalController journalControllerMock = new JournalController(journalService, goalService, friendService, imageService, sharedJournalService);

        mockMvc         = MockMvcBuilders.standaloneSetup(journalControllerMock).build();
    }

    @Test
    public void root() throws Exception {
        when(journalService.findAll(any(String.class))).thenReturn(TestingData.findAllJournals(5));
        mockMvc.perform(get("/journal").principal(mock(Principal.class))).andExpect(status().isOk());

        //TestingData.sendRequestToController(mockMvc, HttpMethod.GET, "/journal", HttpStatus.OK);
    }

    @Test
    public void create() throws Exception {
        Journal journal = TestingData.createJournal();
        when(journalService.save(any(Journal.class))).thenReturn(journal);

        mockMvc.perform(post("/journal/create")
                .flashAttr("journal", journal)
                .principal(mock(Principal.class)))
                .andExpect(status().is3xxRedirection());


        journal.setJournalName(TO_LONG_NAME);
        mockMvc.perform(post("/journal/create")
                .flashAttr("journal", journal)
                .principal(mock(Principal.class)))
                .andDo(resultHandler -> {
                    checkStatusAndModalHeader(resultHandler,"Journalname to long!", HttpStatus.MOVED_TEMPORARILY.toString());
                });

        journal = TestingData.createJournal();
        journal.setContent(TO_LONG_CONTENT);
        mockMvc.perform(post("/journal/create")
                .flashAttr("journal", journal)
                .principal(mock(Principal.class)))
                .andDo(resultHandler -> {
                    checkStatusAndModalHeader(resultHandler, "Content to long!", HttpStatus.MOVED_TEMPORARILY.toString());
                });
    }

    @Test
    public void edit() throws Exception{
        when(journalService.findById(any(String.class))).thenReturn(TestingData.createJournal());
        mockMvc.perform(get("/journal/edit/42")
                .principal(mock(Principal.class)))
                .andDo(result -> {
                    int statuscode = result.getResponse().getStatus();
                    Assert.assertEquals(HttpStatus.MOVED_TEMPORARILY.toString(), Integer.toString(statuscode));
                });

        mockMvc.perform(post("/journal/edit")
                .principal(mock(Principal.class))
                .flashAttr("journal", TestingData.createJournal()))
                .andDo(result -> {
                    int status = result.getResponse().getStatus();
                    Assert.assertEquals(HttpStatus.MOVED_TEMPORARILY.toString(), Integer.toString(status));
                });

        Journal journal = TestingData.createJournal();
        journal.setJournalName(TO_LONG_NAME);
        mockMvc.perform(post("/journal/edit")
                .principal(mock(Principal.class))
                .flashAttr("journal", journal))
                .andDo(result -> checkStatusAndModalHeader(result, "Journalname too long!", HttpStatus.MOVED_TEMPORARILY.toString()));

        journal = TestingData.createJournal();
        journal.setContent(TO_LONG_CONTENT);
        mockMvc.perform(post("/journal/edit")
                .principal(mock(Principal.class))
                .flashAttr("journal", journal))
                .andDo(result -> checkStatusAndModalHeader(result, "Content too long!", HttpStatus.MOVED_TEMPORARILY.toString()));

    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(get("/journal/delete")
                .principal(mock(Principal.class))
                .sessionAttr("currentJournal", TestingData.createJournal()))
                .andDo(result -> {
                    int statuscode = result.getResponse().getStatus();
                    Assert.assertEquals(HttpStatus.OK.toString(), Integer.toString(statuscode));

                    ModelAndView modelAndView = result.getModelAndView();
                    Map<String, Object> models = modelAndView.getModel();
                    String value = (String) models.get("delete");
                    Assert.assertEquals("true", value);
                });

        mockMvc.perform(post("/journal/delete")
                .principal(mock(Principal.class))
                .sessionAttr("currentJournal", TestingData.createJournal()))
                .andDo(result -> {
                    int statuscode = result.getResponse().getStatus();
                    Assert.assertEquals(HttpStatus.MOVED_TEMPORARILY.toString(), Integer.toString(statuscode));
                });
    }

    private void checkStatusAndModalHeader(MvcResult result, String headerString, String wantedStatus) {
        int status = result.getResponse().getStatus();
        Assert.assertEquals(wantedStatus, Integer.toString(status));

        ModelAndView modelAndView = result.getModelAndView();
        Map<String, Object> models = modelAndView.getModel();
        String modalHeader = (String) models.get(STATUSCODE_MODAL_HEADER);
        Assert.assertEquals(headerString, modalHeader);
    }
}
