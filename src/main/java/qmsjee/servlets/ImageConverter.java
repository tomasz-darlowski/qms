package qmsjee.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import qmsjee.entities.entity.FileEntity;
import qmsjee.services.entityServices.interfaces.IFileService;

@Named
@ApplicationScoped
public class ImageConverter {

    @Inject
    private IFileService fileService;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("uid");
            FileEntity image = fileService.findByUid(imageId);
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getContent()));
        }
    }
}
