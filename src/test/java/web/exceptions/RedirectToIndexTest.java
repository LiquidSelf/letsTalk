package web.exceptions;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RedirectToIndexTest {

    private RedirectToIndex redirectToIndex = new RedirectToIndex();

    @Test
    public void shouldNotBeNull() {
        NoHandlerFoundException exc = mock(NoHandlerFoundException.class);

        ModelAndView mnv =  redirectToIndex.doResolveException(exc);

        assertNotNull(mnv);
    }

    @Test
    public void shouldBeIndex() {
        NoHandlerFoundException exc = mock(NoHandlerFoundException.class);

        ModelAndView mnv =  redirectToIndex.doResolveException(exc);

        assertEquals(mnv.getViewName(), "index.html");
    }
}