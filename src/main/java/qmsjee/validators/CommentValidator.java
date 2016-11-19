/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import qmsjee.view.controlers.CommentMBean;
import qmsjee.view.controlers.UserMBean;

/**
 *
 * @author Tomek
 */
@FacesValidator("commentValidator")
public class CommentValidator implements Validator {

    @Inject
    UserMBean userMBean;
    @Inject
    CommentMBean commentMBean;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String content = value.toString();
        if (content.isEmpty()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Text is mandatory."));
        }
        if (commentMBean.getCommentList().size() > 0) {
            if (commentMBean.getCommentList().get(commentMBean.getCommentList().size() - 1).getCreator().equals(userMBean.getUser())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Last comment is your. Delete it and add new content."));
            }
        }
    }

}
