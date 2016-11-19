/*


 */
package qmsjee.view.controlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import qmsjee.entities.entity.FileEntity;
import qmsjee.entities.entity.Item;
import qmsjee.enums.FileType;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class FileMBean implements Serializable {

    private static final long serialVersionUID = -5380784255350038550L;
    private FileEntity fileEntity;
    private Part file;
    private StreamedContent downloadFile;
    private String description;
    private boolean reportStatus;
    FileType fileType;

    public void downloadFile() throws IOException {
        if (this.getFileEntity() != null) {
            InputStream stream = new ByteArrayInputStream(this.getFileEntity().getContent());
            this.setDownloadFile(new DefaultStreamedContent(stream, this.getFileEntity().getMimeType(), this.getFileEntity().getFileName()));
        }
    }

    public void uploadFile(FileType type, Item item) throws IOException {
        FacesMessage msg = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        switch (type) {
            case IMAGE:
                msg = new FacesMessage(bundle.getString("image") + " " + getFilename(this.getFile()) + " " + bundle.getString("isUploaded"), null);
                this.setFileEntity(new FileEntity(IOUtils.toByteArray(this.getFile().getInputStream()), this.getFile().getContentType(), getFilename(this.getFile()), FileType.IMAGE.toString(), "", true));
                this.fileEntity.setItem(item);
                break;
            case DOCUMENT:
                msg = new FacesMessage(bundle.getString("document") + " " + getFilename(this.getFile()) + " " + bundle.getString("isUploaded"), null);
                this.setFileEntity(new FileEntity(IOUtils.toByteArray(this.getFile().getInputStream()), this.getFile().getContentType(), getFilename(this.getFile()), FileType.DOCUMENT.toString(), this.description, true));
                this.fileEntity.setItem(item);
                break;
            case REPORT:
                msg = new FacesMessage(bundle.getString("report") + " " + getFilename(this.getFile()) + " " + bundle.getString("isUploaded"), null);
                this.setFileEntity(new FileEntity(IOUtils.toByteArray(this.getFile().getInputStream()), this.getFile().getContentType(), getFilename(this.getFile()), FileType.REPORT.toString(), this.description, this.reportStatus));
                this.fileEntity.setItem(item);
                break;
        }
        this.fileEntity.setCreateDate(new Date());
        this.setFile(null);
        this.setDescription("");
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private static String getFileExtension(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
                int cropIndex = filename.lastIndexOf(".");
                String extension = filename.substring(filename.length() - 4, filename.length());
                return extension;
            }
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(boolean reportStatus) {
        this.reportStatus = reportStatus;
    }
    //</editor-fold>
}
