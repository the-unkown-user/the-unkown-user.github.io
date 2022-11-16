package com.iaeep.dto;

import com.iaeep.entity.CommentInformation;
import lombok.Data;

import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname commentDto
 * @Description TODO
 * @CreateDate 2022/10/29 16:08
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/29 16:08
 */
@Data
public class CommentDto extends CommentInformation {
    private String avatar;
    private String username;
    private String toUsername;
    private String date;
    private List<CommentDto> commentList1;
}
