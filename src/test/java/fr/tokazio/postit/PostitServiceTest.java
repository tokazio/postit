package fr.tokazio.postit;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@QuarkusTest
public class PostitServiceTest {

    private @Mock
    Saver saver;
    private @InjectMocks
    @Inject
    PostitService service;

    @BeforeEach
    public void setup() {
        service.clear();
    }

    @Test
    public void get() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        //when
        final Postit actual = service.get("abcd");
        //then
        assertThat(actual.getText()).isEqualTo("one");
    }

    @Test
    public void getNotFound() {
        //given
        Throwable ex = null;
        //when
        try {
            service.get("abcd");
        } catch (PostitNotFoundException e) {
            ex = e;
        }
        //then
        assertThat(ex).isNotNull();
    }

    @Test
    public void add() {
        //given

        //when
        service.add(PostItDummies.postitOne());
        //then
        assertThat(service.all()).hasSize(1);
    }

    @Test
    public void delete() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        //when
        final Postit actual = service.delete("abcd");
        //then
        assertThat(actual.getText()).isEqualTo("one");
        assertThat(service.all()).hasSize(1);
    }

    @Test
    public void edit() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        //when
        final Postit actual = service.edit("abcd", PostItDummies.edition());
        //then
        assertThat(actual.getText()).isEqualTo("two edited");
        assertThat(actual.getCategorie()).isEqualTo(Categories.CONTINUER);
        assertThat(service.all()).hasSize(2);
    }

    @Test
    public void like() throws PostitNotFoundException, CantLikeAPostitIOwnException {
        //given
        weHaveTwoPostit();
        //when
        final Liking actual = service.like("abcd", PostItDummies.userB());
        //then
        assertThat(actual).isEqualTo(Liking.LIKE);
    }

    @Test()
    public void cantLikeAPostitIOwn() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        Throwable ex = null;
        //when
        try {
            service.like("abcd", PostItDummies.userA());
        } catch (CantLikeAPostitIOwnException e) {
            ex = e;
        }
        //then
        assertThat(ex).isNotNull();
    }

    @Test
    public void unlike() throws PostitNotFoundException, CantLikeAPostitIOwnException {
        //given
        weHaveTwoPostit();
        //when
        final Liking actual = service.like("efgh", PostItDummies.userA());
        //then
        assertThat(actual).isEqualTo(Liking.UNLIKE);
    }

    @Test
    public void all() {
        //given
        weHaveTwoPostit();
        //when
        final List<Postit> actual = service.all();
        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    public void save() throws IOException {
        //given

        //when
        final boolean actual = service.save();

        //then
        verify(saver, times(1)).save(anyString(), new ArrayList<>());
        assertThat(actual).isTrue();
    }

    @Test
    public void load() throws IOException {
        //given

        //when
        final boolean actual = service.load();

        //then
        verify(saver, times(1)).load(anyString());
        assertThat(actual).isTrue();
    }

    //==========================================
    //Utilities
    //==========================================

    private void weHaveTwoPostit() {
        service.add(PostItDummies.postitOne());
        service.add(PostItDummies.postitTwo());
        System.out.println(service.all());
    }

}
