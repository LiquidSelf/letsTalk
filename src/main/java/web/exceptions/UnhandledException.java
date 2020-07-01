package web.exceptions;

import app.database.dto.exception.ExceptionMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class UnhandledException {

    private static final Log logger = LogFactory.getLog(UnhandledException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handle(Exception ex) throws Exception {
        logger.error(ex);
        return ResponseEntity.badRequest().body(new ExceptionMessage(ex.getMessage() == null ? "" : ex.getMessage()));
    }
}
