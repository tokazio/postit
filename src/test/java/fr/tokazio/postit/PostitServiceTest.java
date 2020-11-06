package fr.tokazio.postit;

import fr.tokazio.postit.api.Categories;
import fr.tokazio.postit.api.Liking;
import fr.tokazio.postit.api.PostitService;
import fr.tokazio.postit.api.Saver;
import fr.tokazio.postit.exceptions.CantLikeAPostitIOwnException;
import fr.tokazio.postit.exceptions.NoUserException;
import fr.tokazio.postit.exceptions.PostitNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostitServiceTest {

    @Mock
    private Saver saver;
    @InjectMocks
    private PostitService service = new PostitServiceImpl();

    @Test
    public void get() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        //when
        final Postit actual = service.get("abcd");
        //then
        PostitAssert.assertThat(actual).hasText("one");
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
        PostitAssert.assertThat(actual).hasText("one");
        assertThat(service.all()).hasSize(1);
    }

    @Test
    public void edit() throws PostitNotFoundException {
        //given
        weHaveTwoPostit();
        //when
        final Postit actual = service.edit("abcd", PostItDummies.edition());
        //then
        PostitAssert.assertThat(actual).hasText("two edited");
        PostitAssert.assertThat(actual).hasCategorie(Categories.CONTINUER);
        assertThat(service.all()).hasSize(2);
    }

    @Test
    public void like() throws PostitNotFoundException, CantLikeAPostitIOwnException, NoUserException {
        //given
        weHaveTwoPostit();
        //when
        final Liking actual = service.like("abcd", PostItDummies.userB());
        //then
        assertThat(actual).isEqualTo(Liking.LIKE);
    }

    @Test()
    public void likeWithoutUser() throws PostitNotFoundException, CantLikeAPostitIOwnException {
        //given
        weHaveTwoPostit();
        Throwable ex = null;
        //when
        try {
            service.like("abcd", null);
        } catch (NoUserException e) {
            ex = e;
        }
        //then
        assertThat(ex).isNotNull();
    }

    @Test()
    public void cantLikeAPostitIOwn() throws PostitNotFoundException, NoUserException {
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
    public void unlike() throws PostitNotFoundException, CantLikeAPostitIOwnException, NoUserException {
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
        verify(saver, times(1)).save(anyString(), anyList());
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
        service.clear();
        service.add(PostItDummies.postitOne());
        service.add(PostItDummies.postitTwo());
    }

}
