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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import services.ws.WSHandler;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedCRUD {

    @Autowired private Dao<DB_FEED, Long> dao;
    @Autowired private WSHandler wsHandler;

    private static final Log logger = LogFactory.getLog(FeedCRUD.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getRecords(@RequestParam(required = false) Long fromId) {

        List<DB_FEED> dbRecords = dao.getAll();
        List<FeedDTO> dtos = new ArrayList<FeedDTO>();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(DB_FEED dbRecord : dbRecords) dtos.add(new FeedDTO(dbRecord));

        try {
            return ResponseEntity.ok(DTO.mk(dtos));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> addRecord(@RequestBody FeedDTO feedDTO) {

        if(feedDTO == null || StringUtils.isEmpty(feedDTO.getDescription()))
            return new ResponseEntity(new RuntimeException("empty description"), HttpStatus.BAD_REQUEST);


        try {
            DB_FEED updated = dao.save(new DB_FEED(feedDTO));
            wsHandler.onNewFeedItem();
            return ResponseEntity.ok(DTO.mk(new FeedDTO(updated)));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecord(@PathVariable Long id) {

        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(DTO.mk(new FeedDTO(dao.find(id))));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

}
