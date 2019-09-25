package filters;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionsManager extends AbstractHandlerExceptionResolver {
    @Nullable
    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            @Nullable Object handler,
            Exception ex
    ) {
        try {
            if (ex instanceof NoHandlerFoundException) {
                response.sendRedirect("/");
                return new ModelAndView();
            }
        }catch (Exception exce){
            System.out.println(exce.getMessage());
        }
        return null;
    }


    //    @ExceptionHandler(NoHandlerFoundException.class)
//    protected ResponseEntity<Object> noHandler(
//            NoHandlerFoundException ex, HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request
//    ){
//        System.out.println("handleNoHandlerFoundException() ");
//        return new ResponseEntity<Object>(HttpStatus.MULTI_STATUS);
//    }
//
//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//        System.out.println("handleAll " + ex.getMessage());
//        return new ResponseEntity<Object>("", new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
