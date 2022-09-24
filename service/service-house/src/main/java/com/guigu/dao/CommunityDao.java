package com.guigu.dao;

import com.guigu.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community>{

    List<Community> findAll();
}
