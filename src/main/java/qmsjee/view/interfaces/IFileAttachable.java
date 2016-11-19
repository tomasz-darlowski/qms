/*
 
 
 */
package qmsjee.view.interfaces;

import java.io.IOException;

/**
 *
 * @author darlotom
 */
public interface IFileAttachable {

    public void uploadDocument() throws IOException;

    public void uploadImage() throws IOException;

    public void uploadReport() throws IOException;

    public String removeFile();

    public void setFileEntityData();
}
