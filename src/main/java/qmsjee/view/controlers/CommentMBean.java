/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.entities.entity.Comment;
import qmsjee.entities.entity.Item;
import qmsjee.services.entityServices.interfaces.ICommentService;
import qmsjee.services.entityServices.interfaces.IItemService;
import qmsjee.view.controlers.common.CommonMBean;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class CommentMBean extends CommonMBean implements Serializable {

    @Inject
    ICommentService commentService;
    @Inject
    IItemService itemService;
    @Inject
    UserMBean userMBean;
    private List<Comment> commentList;
    private Comment comment;
    private Item item;

    public void reinit() {
        comment = new Comment(userMBean.getUser(), item);
        commentList = commentService.findAllByItem(item.getId());
    }

    public void addComment() {
        if (null == comment) {
        } else {
            comment.setCreateDate(new Date());
            commentService.save(comment);
            reinit();
        }
    }

    public void deleteComment() {
        AppUser currentUser = userMBean.getUser();
        if (comment.getCreator().equals(currentUser) && !commentList.isEmpty()) {
            commentService.delete(commentList.get(commentList.size() - 1));
            reinit();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            String msg = bundle.getString("notCommentOwner");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
        }
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UserMBean getUserMBean() {
        return userMBean;
    }

}
