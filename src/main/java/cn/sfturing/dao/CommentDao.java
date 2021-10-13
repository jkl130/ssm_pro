package cn.sfturing.dao;

import cn.sfturing.entity.Comment;

import java.util.List;

/**
 * @author anjsh
 * @date 2021/10/13 14:26
 */
public interface CommentDao {

    List<Comment> findByDoctorId(int doctorId);

    Comment findById(int id);

    void insertComment(Comment comment);
}
