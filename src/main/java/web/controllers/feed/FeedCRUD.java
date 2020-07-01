package web.controllers.feed;

import app.database.dao.Dao;
import app.database.dao.feed.DB_FEED;
import app.database.dto.DTO;
import app.database.dto.feed.FeedDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import web.ws.WSHandler;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedCRUD {

    @Autowired private Dao<DB_FEED, Long> feedDao;
    @Autowired private WSHandler wsHandler;
    @Autowired private ModelMapper modelMapper;

    private static final Log logger = LogFactory.getLog(FeedCRUD.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getRecords(@RequestParam(required = false) Long fromId) {

        List<DB_FEED> dbRecords = feedDao.getAll();
        List<FeedDTO> dtos = new ArrayList<FeedDTO>();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(DB_FEED dbRecord : dbRecords) dtos.add(modelMapper.map(dbRecord, FeedDTO.class));

        try {
            return ResponseEntity.ok(DTO.mk(dtos));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> addRecord(@RequestBody @Valid FeedDTO feedDTO) {

        if(feedDTO == null || StringUtils.isEmpty(feedDTO.getDescription()))
            return new ResponseEntity<>(new RuntimeException("empty description"), HttpStatus.BAD_REQUEST);


        try {
            DB_FEED updated = feedDao.saveOrUpdate(modelMapper.map(feedDTO, DB_FEED.class));
            wsHandler.onNewFeedItem();
            return ResponseEntity.ok(DTO.mk(modelMapper.map(updated, FeedDTO.class)));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
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
            return ResponseEntity.ok(DTO.mk(modelMapper.map(feedDao.find(id), FeedDTO.class)));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

}
