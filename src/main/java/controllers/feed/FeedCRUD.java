package controllers.feed;

import dao.Dao;
import dao.feed.DB_FEED;
import dto.DTO;
import dto.feed.FeedDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedCRUD {

    @Autowired private Dao<DB_FEED, Long> dao;

    private static final Log logger = LogFactory.getLog(FeedCRUD.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getRecords(@RequestParam(required = false) Long fromId) {

        List<DB_FEED> dbRecords = dao.getAll();
        List<FeedDTO> dtos = new ArrayList<FeedDTO>();

        for(DB_FEED dbRecord : dbRecords) dtos.add(new FeedDTO(dbRecord));

        try {
            return ResponseEntity.ok(DTO.mk(dtos));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

}
