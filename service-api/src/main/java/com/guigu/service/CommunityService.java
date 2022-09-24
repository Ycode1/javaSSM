package com.guigu.service;

import com.guigu.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();

}
