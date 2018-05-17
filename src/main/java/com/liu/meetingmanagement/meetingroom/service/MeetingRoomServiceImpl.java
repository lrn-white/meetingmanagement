package com.liu.meetingmanagement.meetingroom.service;

import com.github.pagehelper.PageHelper;
import com.liu.meetingmanagement.commons.msg.MsgEnum;
import com.liu.meetingmanagement.commons.msg.MsgTemplate;
import com.liu.meetingmanagement.meetingroom.dao.MeetingRoomDao;
import com.liu.meetingmanagement.meetingroom.model.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 刘仁楠
 * @date 2018/5/15 11:37
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService{

    @Autowired
    private MeetingRoomDao meetingRoomDao;

    /**
     * 获得所有会议室信息
     *
     * @author 刘仁楠
     * @date 2018/5/15 11:36
     */
    @Override
    public Map<String, Object> getAllMeetingRoom(int pageNum, int pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        List<MeetingRoom> meetingRooms = meetingRoomDao.getAllMeetingRoom();
        Integer count = countMeetingRoom();
        if (ObjectUtils.isEmpty(meetingRooms)){
            return MsgTemplate.failureMsg(MsgEnum.OPS_EMPTY);
        }
        return MsgTemplate.successMsg(meetingRooms,count);
    }

    /**
     * 获得会议室数量
     * @author 刘仁楠
     * @date 2018/5/17 16:21
     */
    @Override
    public Integer countMeetingRoom() {
        return meetingRoomDao.countMeetingRoom();
    }

    /**
     * 根据roomID删除会议室
     * @author 刘仁楠
     * @date 2018/5/17 16:58
     */
    @Override
    public Map<String,Object> deleteMeetingRoomByRoomID(Integer roomID){
        if (roomID.equals("")){
            return MsgTemplate.failureMsg(MsgEnum.PARAMS_EMPTY);
        }else {
            meetingRoomDao.deleteMeetingRoomByRoomID(roomID);
            return MsgTemplate.successMsg();
        }
    }
}
