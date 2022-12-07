package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dao.GiftDao;
import com.giftservice.gift_service.dao.UserDao;
import com.giftservice.gift_service.entities.Gift;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftDao giftDao;
    private final UserDao userDao;

    public List<Gift> getGiftList(){
        return giftDao.findAll();
    }

    public Gift createNewGift(String newGiftTitle){
        Gift newGift = new Gift();
        newGift.setTitle(newGiftTitle);
        return new Gift(giftDao.save(newGift));
    }

    public Gift getGiftByID(Long id){
        return new Gift(giftDao.findById(id).get());
    }

    public void deleteById(long id){
        giftDao.deleteById(id);
    }

    public Page<Gift> findPage(int pageIndex, int pageSize) {
        return giftDao.findAll(PageRequest.of(pageIndex, pageSize)).map(Gift::new);
    }
}
